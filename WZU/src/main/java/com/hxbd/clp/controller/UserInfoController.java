package com.hxbd.clp.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxbd.clp.domain.User;
import com.hxbd.clp.domain.UserInfo;
import com.hxbd.clp.service.UserInfoService;
import com.hxbd.clp.service.UserService;
import com.hxbd.clp.utils.common.RasConstants;
import com.hxbd.clp.utils.tag.PageModel;

@Controller
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private UserService userService;
	
		// 根据id查询
		@RequestMapping("/findUserInfoById")
		public @ResponseBody UserInfo findUserInfoById(@RequestParam("id") Integer id) {
			return userInfoService.selectById(id);
		}
	

	@RequestMapping(value = "userInfo/saveUserInfo", method = RequestMethod.POST)
	public @ResponseBody String saveUserInfo(UserInfo userInfo) throws IOException {
		if(userService.selectById(userInfo.getUserId()) == null){
			return "{\"status\":false}";
		}
		if (userInfoService.selectByUserId(userInfo.getUserId()) != null) {
			userInfoService.updateUserInfo(userInfo);
		} else {
			userInfoService.saveUser(userInfo);
		}
		return "{\"status\":true}";
	}
	
	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "userInfo/updateUserInfo", method = RequestMethod.POST)
	public @ResponseBody String updateUserInfo(UserInfo userInfo) throws IOException {
		userInfoService.updateUserInfo(userInfo);
		return "{\"status\":true}";
	}
	
	/**
	 * 更新用户
	 * 
	 * @param user
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "userInfo/updateUser", method = RequestMethod.POST)
	public @ResponseBody String updateUser(User user,HttpSession session) throws IOException {
		userService.updateUser(user);
		session.setAttribute(RasConstants.USER_SESSION, user);
		return "{\"status\":true}";
	}
	
	
		// 分页查询用户详细信息
		@RequestMapping("/userInfo/{pageIndex}/getUserInfoPageModel")
		public @ResponseBody PageModel<UserInfo> getUserPageModel(@PathVariable Integer pageIndex,String userId) {
			PageModel<UserInfo> pageModel = new PageModel<>();
			pageModel.setPageIndex(pageIndex);
			if (userId != null && !userId.equals("")) {
				UserInfo userInfo = new UserInfo();
				userInfo.setUserId((Integer.valueOf(userId)));
				return userInfoService.findUserInfo(userInfo, pageModel);
			}
			return userInfoService.findUserInfo(null, pageModel);
		}
		
		/**
		 * 批量删除用户信息
		 * 
		 * @param ids
		 * @return
		 */
		@RequestMapping("/userInfo/deleteUserInfo")
		public @ResponseBody String batchDelUser(@RequestParam("ids[]") Integer[] ids) {
			// 删除用户前，将用户绑定的课程解除
			userInfoService.batchDelUserInfo(ids);
			return "{\"status\":true}";
		}
		
}
