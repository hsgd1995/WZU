package com.tang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转类
 * 
 * @author Administrator
 * @date 2019年5月21日
 */
@Controller
public class PageController {

	@RequestMapping("index")
	public String index(Model model) {
		model.addAttribute("pageName","index");
		model.addAttribute("imgUrl","img/1.jpg");
		return "index";
	}
	
	@RequestMapping("helloJsp")
	public String helloJsp() {
		return "helloJsp";
	}
	
	@RequestMapping("login")
	public String login(){
		return "login";
	}
}
