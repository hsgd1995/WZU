package com.hxbd.clp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.dao.RoleAuthorityDao;
import com.hxbd.clp.domain.RoleAuthority;
import com.hxbd.clp.service.RoleAuthorityService;

/**
 * 角色权限业务实现类
 * @author Administrator
 *
 */
@Service("roleAuthorityService")
@Transactional(propagation = Propagation.REQUIRED,isolation=Isolation.DEFAULT)
public class RoleAuthorityServiceImpl implements RoleAuthorityService{
	
	@Autowired
	private RoleAuthorityDao roleAuthorityDao;

	@Override
	public List<RoleAuthority> getAuthorityByRole(Integer roleId) {
		return roleAuthorityDao.selectByRoleId(roleId);
	}

	
}
