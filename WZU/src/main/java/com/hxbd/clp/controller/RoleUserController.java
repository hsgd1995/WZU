package com.hxbd.clp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxbd.clp.domain.Role;
import com.hxbd.clp.domain.RoleUser;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.service.RoleUserService;
import com.hxbd.clp.utils.common.RasConstants;

@Controller
public class RoleUserController {

	@Autowired
	private RoleUserService roleUserService;
	
	/**
	 * 根据检索和分页条件查询列表
	 * @param roleUser
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/roleUser/list")
	@ResponseBody
	public Object list(RoleUser roleUser,Integer pageIndex,Integer pageSize,String username,String name){
		if(pageIndex == null){
			pageIndex = 1;
		}
		if(pageSize == null){
			pageSize = RasConstants.PAGE_DEFAULT_SIZE;
		}
		if(!StringUtils.isEmpty(username)){
			User user = new User();
			user.setUsername(username);
			roleUser.setUser(user);
		}
		
		if(!StringUtils.isEmpty(name)){
			Role role = new Role();
			role.setName(name);
			roleUser.setRole(role);
		}
		
		System.err.println("roleUser:" + roleUser);
		return roleUserService.getByPage(roleUser, pageIndex, pageSize);
	}
	
	@PostMapping("roleUser/add")
	@ResponseBody
	public Object add(@RequestParam("roleIds[]") Integer[] roleIds,@RequestParam("userIds[]") Integer[] userIds,@RequestParam("allUser") Boolean isAllUser){
		if(roleIds==null||userIds==null){
			return "{\"status\":false}";
		}
		roleUserService.add(roleIds,userIds,isAllUser);
		return "{\"status\":true}";
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@PostMapping("/roleUser/batchDel")
	@ResponseBody
	public String batchDel(@RequestParam("ids[]") Integer[] ids){
		roleUserService.batchDel(ids);
		return "{\"status\":true}";
	}
}
