package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.RoleAuthority;

/**
 * 角色权限业务接口
 * @author Administrator
 *
 */
public interface RoleAuthorityService {
	/**
	 * 根据角色id获取角色权限对应关系
	 * @param roleId
	 * @return
	 */
	List<RoleAuthority> getAuthorityByRole(Integer roleId);
}
