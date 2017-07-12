package com.brush.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* 主页的页面控制类
* @author 作者 songhao
* @version 创建时间：2017年6月19日 下午4:31:00
*/
@Controller
@RequestMapping("/home")
public class HomeController {
	
	@RequestMapping(value="/loginUi")
	public String loginUi(HttpServletRequest request,ModelMap model){
		return "loginUi";
	}
	
	@RequestMapping(value="/toWelcome")
	public String toWelcome(HttpServletRequest request,ModelMap model){
		return "home/welcome";
	}
	
	@RequestMapping(value="/index")
	public String index(HttpServletRequest request,ModelMap model){
		return "home/index";
	}
	
	@RequestMapping(value="/registerUi")
	public String registerUi(HttpServletRequest request,ModelMap model){
		return "register";
	}
}
