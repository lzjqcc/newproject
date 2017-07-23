package com.liyang.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.domain.role.Role;
import com.liyang.domain.role.RoleRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserAct;
import com.liyang.domain.user.UserRepository;
import com.liyang.domain.user.UserState;
import com.liyang.domain.user.UserStateRepository;
import com.liyang.util.FailReturnObject;
import com.liyang.util.TIMSignature;
/**
 * 微信公众号服务
 * @author win7
 *
 */
@Service
public class WechatPublicService {
	private final static String WECHATURL="https://open.weixin.qq.com/connect/oauth2/authorize?";
	private String appid= "appid=";
	private String  redirect_uri="&redirect_uri=";
	private String responseType= "&response_type=code";
	private String scope="&scope=";
	private String state="&state=STATE#wechat_redirect ";
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserStateRepository userStateRepository;
	@Autowired
	private RoleRepository roleRepository;
	public void authenize(HttpServletRequest request){
		String appid=request.getParameter("appid");
		
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
		UserState state = userStateRepository.findByStateCode("NONAMOL");
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
	}
	/**
	 * 这里面封装微信返回的数据
	 * @param token
	 * @param openid
	 * @return
	 */
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
