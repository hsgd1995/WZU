package com.tang.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * hello
 * 
 * @author Administrator
 * @date 2019年5月21日
 */
@RestController
public class HelloController {
	/**
	 * hello
	 * 
	 * @return
	 */
	@RequestMapping("hello")
	public String hello() {
		return "hello";
	}

	/**
	 * 产生异常
	 * 
	 * @return
	 */
	@RequestMapping("exce")
	public String exce() {
		int a = 2 / 0;
		return "";
	}
}
