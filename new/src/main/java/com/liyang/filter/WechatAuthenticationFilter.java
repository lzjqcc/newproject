package com.liyang.filter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.service.WechatLoginService;
import com.liyang.util.FailReturnObject;

public class WechatAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	// ~ Static fields/initializers
	// =====================================================================================

	@Autowired
	private WechatLoginService wechatLoginService;

	private boolean postOnly = false;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	// ~ Constructors
	// ===================================================================================================

	public WechatAuthenticationFilter() {
		super(new AntPathRequestMatcher("/login"));
	}

	// ~ Methods
	// ========================================================================================================

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> hashMap = new HashMap<>();

		try {
			if (request.getParameter("code") == null || request.getParameter("state") == null) {
				throw new FailReturnObject(170, "格式不符合");
			}
			Authentication authRequest = wechatLoginService.authorize(request.getParameter("code"),
					request.getParameter("state"));
			// applicationContext.publishEvent(new
			// UserLoginSuccessEvent(authRequest));
			//登录
			if (authRequest != null) {
				return authRequest;
				//申请
			} else {
				hashMap.put("ActionStatus", "OK");
				hashMap.put("ErrorCode", 0);
				hashMap.put("ErrorInfo", "");
				String json = mapper.writeValueAsString(hashMap);
				response.setContentType("application/json;charset=utf-8");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				return null;
			}

		} catch (FailReturnObject failReturnObject) {

			hashMap.put("ActionStatus", failReturnObject.getActionStatus());
			hashMap.put("ErrorCode", failReturnObject.getErrorCode());
			hashMap.put("ErrorInfo", failReturnObject.getErrorInfo());

			String json = mapper.writeValueAsString(hashMap);
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			return null;
		}

	}

	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/**
	 * Defines whether only HTTP POST requests will be allowed by this filter.
	 * If set to true, and an authentication request is received which is not a
	 * POST request, an exception will be raised immediately and authentication
	 * will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
	 * will be called as if handling a failed authentication.
	 * <p>
	 * Defaults to <tt>true</tt> but may be overridden by subclasses.
	 */
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}
}
