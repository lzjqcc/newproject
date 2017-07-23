package com.liyang.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.liyang.controller.GlobalAccessDeniedHandler;
import com.liyang.domain.user.UserRepository;
import com.liyang.filter.MessageDispatchFilter;
import com.liyang.filter.WechatAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserRepository userRepository;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean("usernamePasswordAuthenticationFilter")
	public WechatAuthenticationFilter wechatAuthenticationFilter() {
		WechatAuthenticationFilter upaf = new WechatAuthenticationFilter();
		upaf.setAuthenticationManager(authenticationManager());
		return upaf;
	}

	
	@Bean
	public MessageDispatchFilter dispatchFilter() {
		MessageDispatchFilter dispatch = new MessageDispatchFilter();
		return dispatch;
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return new AuthenticationManager() {

			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {

				return authentication;
			}
		};
	}
	
	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
		return new SecurityEvaluationContextExtension();
		
	}

	/**
	 * 主过滤器
	 *
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterAt(wechatAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//		http.addFilterBefore(dispatchFilter(), UsernamePasswordAuthenticationFilter.class);
		
		http
		.csrf().disable().anonymous().authorities("ANONYMOUS")
//		.authorizeRequests().antMatchers("/rest","/rest/*","/rest/*/")
//		.hasAuthority("DEVELOPER")
		.and()
		.exceptionHandling().accessDeniedHandler(new GlobalAccessDeniedHandler())
		.and()
		.authorizeRequests().antMatchers( "/connect", "/login").permitAll()
//		.anyRequest().authenticated()
		.and().formLogin().loginPage("/connect").permitAll()
		.and().logout().permitAll();

	}
	

}
