package com.liyang.controller;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.liyang.service.WechatLoginService;

@Controller
public class ConnectController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	WechatLoginService wechatLoginService;
	
	@RequestMapping("/connect")
	public String login(Model model ){
		wechatLoginService.connect(model);
		return "auth/login";
	}
		
	
}
