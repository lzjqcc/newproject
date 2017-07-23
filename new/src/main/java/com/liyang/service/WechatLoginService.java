package com.liyang.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.domain.department.Department;
import com.liyang.domain.department.DepartmentRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserRepository;
import com.liyang.domain.user.UserState;
import com.liyang.domain.user.UserStateRepository;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject;
import com.liyang.util.TIMSignature;
import com.liyang.util.TreeNode;
import com.liyang.util.TreeNodeImpl;
import com.liyang.util.WechatProperties;

@Service
public class WechatLoginService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WechatProperties properties;

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserStateRepository userStateRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private TIMService timService;

	public void connect(Model model) {
		setupAttribute(model);
		UUID randomUUID = UUID.randomUUID();
		String state = randomUUID.toString();
		model.addAttribute("state", state);
		cacheManager.getCache("wechatLogin").put(state, "login:0");

	}

	public void apply(Model model, User fromUser) {
		setupAttribute(model);
		UUID randomUUID = UUID.randomUUID();
		String state = randomUUID.toString();
		model.addAttribute("state", state);
		if (fromUser == null) {
			cacheManager.getCache("wechatLogin").put(state, "apply:0");
		} else {
			cacheManager.getCache("wechatLogin").put(state, "apply:" + fromUser.getId().toString());
		}

	}

	private void setupAttribute(Model model) {
		model.addAttribute("appid", properties.getAppid());
		model.addAttribute("scope", properties.getScope());
		model.addAttribute("redirect_uri", properties.getRedirect_uri());
		model.addAttribute("secret", properties.getSecret());

	}

	/**
	 *
	 * @param code  code码
	 * @param state 包含登录或者申请信息
	 * @return
	 */
	@Transactional
	public Authentication authorize(String code, String state) {

		ValueWrapper valueWrapper = cacheManager.getCache("wechatLogin").get(state);
		if (valueWrapper == null) {
			throw new FailReturnObject(110, "缓存已经过期");
		}

		String str = cacheManager.getCache("wechatLogin").get(state, String.class);
		//这里获取的是用户凭证的map对象
		Map token = getAccessToken(code);
		User exist_user = userRepository.findByUnionid(token.get("unionid").toString());
		//用户登录
		if (str.startsWith("login:")) {
			return doLogin(exist_user);
		//用户申请
		} else if (str.startsWith("apply:")) {
			//用户申请
			doApply(token, exist_user, Integer.valueOf(str.substring(6)));
			return null;
		} else {
			throw new FailReturnObject(175, "微信登录缓存格式不符");
		}

	}

	/**
	 * 用户申请
	 * @param token
	 * @param exist_user
	 * @param fromUserId
	 */
	@Transactional
	private void doApply(Map token, User exist_user, Integer fromUserId) {
		if (exist_user != null) {
			throw new FailReturnObject(157, "用户已存在");
		}
		
		Map info = getUserInfo(token.get("access_token").toString(), token.get("openid").toString());
		User user = new User();
		user.setOpenid(token.get("openid").toString());
		user.setUnionid(token.get("unionid").toString());
		user.setNickname(info.get("nickname").toString());
		user.setSex((int) info.get("sex"));
		user.setProvince(info.get("province").toString());
		user.setCity(info.get("city").toString());
		user.setCountry(info.get("country").toString());
		user.setHeadimgurl(info.get("headimgurl").toString());
		//
		UserState state = userStateRepository.findByStateCode("NEW");
		user.setState(state);
		user.setSig(TIMSignature.generate(token.get("unionid").toString()).urlSig);
		//扫描二维码申请的用户的角色是USER
		Role role = roleRepository.findByRoleCode(Role.RoleCode.valueOf("USER"));
//		role.setRoleCode(Role.RoleCode.valueOf("USER"));
		user.setRole(role);
		//判断当前用户的状态的动作  是否可以申请USER角色
		UserAct actApply = userService.getAct(user, "apply",role);
		User fromUser = null;
		if (fromUserId != null && fromUserId!=0) {
			fromUser = userRepository.findOne(fromUserId);
		}
		if(fromUser !=null){
			user.setDepartment(fromUser.getDepartment());
		}
		//根据act对应的startWorkFlow的状态 启动工作流，跳到下一个user_state状态
		user = userService.doBeforeAct(user, actApply, fromUser);
		user = userRepository.save(user);
		//将微信用户名，头像以及唯一标识通过腾讯云通讯发送给前端
		timService.addUser(user.getUnionid(), user.getNickname(), user.getHeadimgurl());

		userService.doAfterAct(user, fromUser);

		// userService.apply("apply",CommonUtil.actMessageWrapper(user,fromUserId),
		// user);

//		throw new FailReturnObject(168, "申请成功");
	}

	/**
	 * 根据用户的state判断用户是否可以登录，只有ENABLE状态并且有角色的用户才可以登录
	 * @param exist_user
	 * @return
	 */
	private Authentication doLogin(User exist_user) {
		if (exist_user == null) {
			throw new FailReturnObject(155, "用户不存在");
		} else if ("DISABLE".equals(exist_user.getState().getStateCode())) {
			throw new FailReturnObject(160, "用户被禁用");
		} else if ("DELETE".equals(exist_user.getState().getStateCode())) {
			throw new FailReturnObject(163, "用户被删除");
		} else if ("APPLY".equals(exist_user.getState().getStateCode())) {
			throw new FailReturnObject(165, "用户正在被审核");
		}
		List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
		if(exist_user.getRole()==null){
			throw new FailReturnObject(1340, "用户没有角色");
		}
		AUTHORITIES.add(new SimpleGrantedAuthority(exist_user.getRole().getRoleCode().toString()));
		List<Department> ownAndChildrenDepartments = CommonUtil.ownAndChildrenDepartments(exist_user.getDepartment());
		if(!ownAndChildrenDepartments.isEmpty()){
			exist_user.setChildrenDepartments(ownAndChildrenDepartments);
		}
//		if(exist_user.getRole()!=null){
//			exist_user.setVisibleMenuTree(exist_user.getRole().getVisibleMenuTree());
//		}
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(exist_user, null, AUTHORITIES);

		return authentication;

	}

	/**
	 * 根据code 换取access_toek access_token拉去微信用户信息的凭证
	 * @param code
	 * @return
	 */
	private Map getAccessToken(String code) {
		RestTemplate restTemplate = new RestTemplate();
		/*
		{ "access_token":"ACCESS_TOKEN",

 		"expires_in":7200,

 		"refresh_token":"REFRESH_TOKEN",

 		"openid":"OPENID",

 		"scope":"SCOPE" }
		 */
		String user = restTemplate
				.getForObject(
						"https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + properties.getAppid() + "&secret="
								+ properties.getSecret() + "&code=" + code + "&grant_type=authorization_code",
						String.class);
		try {

			ObjectMapper mapper = new ObjectMapper();
			//这个将String类型的json数据转换为map对象
			Map<String, Object> m = mapper.readValue(user, Map.class);
			if (m.containsKey("errcode")) {
				throw new FailReturnObject(130, "获取微信token返回错误：" + m.get("errmsg").toString());
			}

			return m;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new FailReturnObject(120, "用户微信授权解析失败");
		}
	}

	private Map getUserInfo(String token, String openid) {
		RestTemplate restTemplate = new RestTemplate();

		String user = restTemplate.getForObject(
				"https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid, String.class);

		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> m = mapper.readValue(user, Map.class);
			if (m.containsKey("errcode")) {
				throw new FailReturnObject(140, "获取微信userinfo返回错误：" + m.get("errmsg").toString());
			}
			String nickname = (String) m.get("nickname");
			String name = new String(nickname.getBytes("ISO-8859-1"), "utf-8");
			m.put("nickname", name);
			return m;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new FailReturnObject(150, "用户微信信息解析失败");
		}
	}
}
