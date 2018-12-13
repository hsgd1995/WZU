package com.hxbd.clp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxbd.clp.domain.Manager;
import com.hxbd.clp.domain.Role;
import com.hxbd.clp.service.AuthorityService;
import com.hxbd.clp.service.RoleAuthorityService;
import com.hxbd.clp.service.RoleService;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 角色控制层
 * @author Administrator
 *
 */
@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private RoleAuthorityService roleAuthorityService;
	
	/**
	 * 根据检索和分页条件查询列表
	 * @param messageAndUser
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/role/list")
	@ResponseBody
	public Object list(Integer pageIndex,Integer pageSize,String username){
		if(pageIndex == null){
			pageIndex = 1;
		}
		if(pageSize == null){
			pageSize = RasConstants.PAGE_DEFAULT_SIZE;
		}
		return roleService.getByPage(pageIndex, pageSize);
	}
	
	/**
	 * 批量停用
	 * @param ids
	 * @return
	 */
	@PostMapping("/role/batchDisable")
	@ResponseBody
	public String batchDisable(@RequestParam("ids[]") Integer[] ids){
		roleService.batchDisable(ids);
		return "{\"status\":true}";
	}
	
	/**
	 * 批量启用
	 * @param ids
	 * @return
	 */
	@PostMapping("/role/batchEnable")
	@ResponseBody
	public String batchEnable(@RequestParam("ids[]") Integer[] ids){
		roleService.batchEnable(ids);
		return "{\"status\":true}";
	}
	
	/**
	 * 更新角色
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/role/update",method=RequestMethod.POST)
	public @ResponseBody String update(Role role,@RequestParam("authority[]") String[] authority,HttpServletRequest httpServletRequest){
		Manager m = (Manager) httpServletRequest.getSession().getAttribute(RasConstants.MANAGER_SESSION);
		if(m==null){
			return "{\"status\":false}";
		}
		roleService.update(role,authority);
		return "{\"status\":true}";
	}
	
	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/role/add",method=RequestMethod.POST)
	public @ResponseBody String add(Role role,@RequestParam("authority[]") String[] authority,HttpServletRequest httpServletRequest){
		
		Manager m = (Manager) httpServletRequest.getSession().getAttribute(RasConstants.MANAGER_SESSION);
		if(m==null){
			return "{\"status\":false}";
		}
		role.setCreaterId(m.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		role.setCreateTime(sdf.format(date));
		roleService.add(role,authority);
		return "{\"status\":true}";
	}
	
	/**
	 * 获取所有权限
	 * @return
	 */
	@GetMapping("/role/getAuthorityList")
	@ResponseBody
	public Object getAuthorityList(){
		return authorityService.getAllAuthority();
	}
	
	/**
	 * 获取某个角色对应的权限
	 * @param roleId
	 * @return
	 */
	@GetMapping("/role/getRoleAuthority")
	@ResponseBody
	public Object getRoleAuthority(Integer roleId){
		return roleAuthorityService.getAuthorityByRole(roleId);
	}
}
