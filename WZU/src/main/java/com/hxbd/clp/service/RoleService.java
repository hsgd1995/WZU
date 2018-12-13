package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.Role;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 角色 业务接口
 * @author Administrator
 *
 */
public interface RoleService {
	/**
	 * 根据检索和分页条件获取记录
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageModel<Role> getByPage(Integer pageIndex, Integer pageSize);

	/**
	 * 批量停用
	 * @param ids
	 */
	void batchDisable(Integer[] ids);
	
	/**
	 * 批量启用
	 * @param ids
	 */
	void batchEnable(Integer[] ids);
	
	/**
	 * 修改
	 * @param role
	 */
	void update(Role role,String[] authority);
	
	/**
	 * 增加
	 * @param role
	 */
	void add(Role role,String[] authority);
}
