package com.hxbd.clp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxbd.clp.domain.Manager;
import com.hxbd.clp.domain.Role;
import com.hxbd.clp.domain.RoleManager;
import com.hxbd.clp.service.RoleManagerService;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * （后台）角色-管理员 控制层
 * @author Administrator
 *
 */

@Controller
public class RoleManagerController {

	@Autowired
	private RoleManagerService managerService;
	
	/**
	 * 根据检索和分页条件查询列表
	 * @param roleManager
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/roleManager/list")
	@ResponseBody
	public Object list(RoleManager roleManager,Integer pageIndex,Integer pageSize,String username,String name){
		if(pageIndex == null){
			pageIndex = 1;
		}
		if(pageSize == null){
			pageSize = RasConstants.PAGE_DEFAULT_SIZE;
		}
		if(!StringUtils.isEmpty(username)){
			Manager ranager = new Manager();
			ranager.setUsername(username);
			roleManager.setManager(ranager);
		}
		
		if(!StringUtils.isEmpty(name)){
			Role role = new Role();
			role.setName(name);
			roleManager.setRole(role);
		}
		
		return managerService.getByPage(roleManager, pageIndex, pageSize);
	}
	
	/**
	 * 新增
	 * @param roleManager
	 * @return
	 */
	@PostMapping("roleManager/add")
	@ResponseBody
	public Object add(Integer roleId,Integer managerId){
		if(roleId==null){
			return "{\"status\":false}";
		}
		
		if(managerId==null){
			return "{\"status\":false}";
		}
		
		RoleManager roleManager = new RoleManager();
		Role role = new Role();
		Manager manager = new Manager();
		role.setId(roleId);
		manager.setId(managerId);
		roleManager.setRole(role);
		roleManager.setManager(manager);
		Integer flag = managerService.add(roleManager);
		if(flag==1){
			return "{\"status\":true,\"message\":\"分配成功\"}";
		}else if(flag == 2){
			return "{\"status\":false,\"message\":\"分配失败，该用户不存在！！！\"}";
		}else{
			return "{\"status\":false,\"message\":\"分配失败，该用户已经分配过了！！！\"}";
		}
		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@PostMapping("/roleManager/batchDel")
	@ResponseBody
	public String batchDel(@RequestParam("ids[]") Integer[] ids){
		managerService.batchDel(ids);
		return "{\"status\":true}";
	}
}
