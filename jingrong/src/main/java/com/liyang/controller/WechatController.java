package com.liyang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liyang.service.WechatLoginService;

@Controller
public class WechatController {
	@Autowired
	private WechatLoginService loginService;
	@RequestMapping("/authorize")
	public String login(){
		
		return null;
	}
}
