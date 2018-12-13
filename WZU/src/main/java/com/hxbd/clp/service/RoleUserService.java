package com.hxbd.clp.service;

import com.hxbd.clp.domain.RoleUser;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * （前台）角色-用户业务接口
 * @author Administrator
 *
 */
public interface RoleUserService {
	/**
	 * 根据检索和分页条件获取记录
	 * @param roleUser
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageModel<RoleUser> getByPage(RoleUser roleUser, Integer pageIndex, Integer pageSize);
	
	/**
	 * 添加
	 * @param roleUser
	 */
	void add(RoleUser roleUser);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	void batchDel(Integer[] ids);

	/**
	 * 添加（多个用户和角色）
	 * @param roleIds
	 * @param userIds
	 * @param isAllUser
	 */
	void add(Integer[] roleIds, Integer[] userIds, Boolean isAllUser);
}
