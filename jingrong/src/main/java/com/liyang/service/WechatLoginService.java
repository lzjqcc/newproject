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
	
	@Transactional
	public Authentication authorize(String code, String state) {

		ValueWrapper valueWrapper = cacheManager.getCache("wechatLogin").get(state);
		if (valueWrapper == null) {
			throw new FailReturnObject(110, "缓存已经过期");
		}

		String str = cacheManager.getCache("wechatLogin").get(state, String.class);

		Map token = getAccessToken(code);
		User exist_user = userRepository.findByUnionid(token.get("unionid").toString());

		if (str.startsWith("login:")) {
			return doLogin(exist_user);

		} else if (str.startsWith("apply:")) {
			doApply(token, exist_user, Integer.valueOf(str.substring(6)));
			return null;
		} else {
			throw new FailReturnObject(175, "微信登录缓存格式不符");
		}

	}

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
		UserState state = userStateRepository.findByStateCode("NEW");
		user.setState(state);
		user.setSig(TIMSignature.generate(token.get("unionid").toString()).urlSig);
		
		Role role = new Role();
		role.setRoleCode(Role.RoleCode.valueOf("USER"));
		UserAct actApply = userService.getAct(user, "apply",role);
		User fromUser = null;
		if (fromUserId != null && fromUserId!=0) {
			fromUser = userRepository.findOne(fromUserId);
		}
		if(fromUser !=null){
			user.setDepartment(fromUser.getDepartment());
		}
		
		user = userService.doBeforeAct(user, actApply, fromUser);
		user = userRepository.save(user);
		
		timService.addUser(user.getUnionid(), user.getNickname(), user.getHeadimgurl());
		userService.doAfterAct(user, fromUser);

		// userService.apply("apply",CommonUtil.actMessageWrapper(user,fromUserId),
		// user);

//		throw new FailReturnObject(168, "申请成功");
	}

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

	private Map getAccessToken(String code) {
		RestTemplate restTemplate = new RestTemplate();
		String user = restTemplate
				.getForObject(
						"https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + properties.getAppid() + "&secret="
								+ properties.getSecret() + "&code=" + code + "&grant_type=authorization_code",
						String.class);
		try {
			ObjectMapper mapper = new ObjectMapper();
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
