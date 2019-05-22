package com.tang.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.entity.test1.User;
import com.tang.service.IUserService;

/**
 * 用户
 * 
 * @author Administrator
 * @date 2019年5月21日
 */
@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	private IUserService userService;

	/**
	 * mybatis
	 * @param username
	 * @param sex
	 * @return
	 */
	@RequestMapping("getUserById")
	public User getUserById(Integer id) {
		return userService.getUserById(id);
	}

	/**
	 * mybatis
	 * @param username
	 * @param sex
	 * @return
	 */
	@RequestMapping("insert")
	public Object insert(String username, String sex) {
		userService.insert(username, sex);
		return "success";
	}

	/**
	 * 事务
	 * @param username
	 * @param id
	 * @return
	 */
	@RequestMapping("tran")
	public Object transcation(String username, Integer id) {
		userService.transcation(username, id);
		return "success";
	}

	/**
	 * 多数据源测试 （暂不使用多数据源）
	 * @param username
	 * @return
	 */
	@RequestMapping("getOrderByUsername")
	public Object getOrderByUsername(String username) {
		return userService.getOrderByUsername(username);
	}

	/**
	 * mybatis-plus
	 * @param sex
	 * @return
	 */
	@RequestMapping("getUserBySex")
	public Object getBySex(String sex) {
		return userService.getUserBySex(sex);
	}
	
	/**
	 * 登录操作
	 * @param username
	 * @return
	 */
	@RequestMapping("loginAction")
	public Object loginAction(String loginname,String password){
		Subject subject = SecurityUtils.getSubject();
		String newPass = new Md5Hash(password,"123tang").toString();
		AuthenticationToken token = new UsernamePasswordToken(loginname,newPass);
		try {
			subject.login(token);
			return "login success";
		} catch (Exception e) {
			return "login Filed";
		}
	}
}
