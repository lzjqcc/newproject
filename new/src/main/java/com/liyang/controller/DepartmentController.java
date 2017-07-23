package com.liyang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liyang.service.WechatLoginService;
import com.liyang.util.CommonUtil;

@Controller
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	private WechatLoginService wechatLoginSerivce;
	
	
	@RequestMapping(value = "/employeeApply")
	public String employeeApply(Model model) {

		wechatLoginSerivce.apply(model , CommonUtil.getPrincipal());
		return "auth/login";
	}


}
