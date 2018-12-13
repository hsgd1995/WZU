package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.Authority;
import com.hxbd.clp.vo.AuthorityVO;

/**
 * 权限业务层
 * @author Administrator
 *
 */
public interface AuthorityService {
	List<Authority> getAll();
	
	/**
	 * 获取权限的树形结构
	 * @return
	 */
	AuthorityVO getAllAuthority();
}
