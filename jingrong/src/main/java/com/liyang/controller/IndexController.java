package com.liyang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liyang.domain.user.User;
import com.liyang.util.CommonUtil;

@Controller
public class IndexController {
	@RequestMapping("/")
	public String index(Model model){
		User principal = CommonUtil.getPrincipal();
		model.addAttribute("id", principal.getId());
		return "index";
	}
	
	@RequestMapping("/dashboard")
	public String dashboard(Model model){
		return "dashboard/DEVELOPER";
	}
	
}
