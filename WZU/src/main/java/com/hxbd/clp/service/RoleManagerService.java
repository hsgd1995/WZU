package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.RoleAuthority;
import com.hxbd.clp.domain.RoleManager;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * （后台）角色-管理员 业务接口
 * @author Administrator
 *
 */
public interface RoleManagerService {
	/**
	 * 根据检索和分页条件获取记录
	 * @param roleManager
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageModel<RoleManager> getByPage(RoleManager roleManager, Integer pageIndex, Integer pageSize);
	
	/**
	 * 添加<br>
	 * 1:添加成功，2:managerId不存在，3:该managerId已分配角色
	 * @param roleManager
	 */
	Integer add(RoleManager roleManager);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	void batchDel(Integer[] ids);

	/**
	 * 添加（多个用户和角色）
	 * @param roleIds
	 * @param managerIds
	 * @param isAllUser
	 */
	void add(Integer[] roleIds, Integer[] managerIds, Boolean isAllManager);
	
	/**
	 * 获取后台某个用户的权限
	 * @param ManagerId
	 * @return
	 */
	List<RoleAuthority> getManagerAuthority(Integer managerId);
}
