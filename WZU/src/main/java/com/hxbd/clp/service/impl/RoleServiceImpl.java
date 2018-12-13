package com.hxbd.clp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.dao.RoleAuthorityDao;
import com.hxbd.clp.dao.RoleDao;
import com.hxbd.clp.domain.Authority;
import com.hxbd.clp.domain.Role;
import com.hxbd.clp.domain.RoleAuthority;
import com.hxbd.clp.service.RoleService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 角色 业务类
 * @author Administrator
 *
 */
@Transactional(propagation = Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("roleService")
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleDao roleDao;

	@Autowired
	private RoleAuthorityDao roleAuthorityDao;
	
	
	@Transactional(readOnly=true)
	@Override
	public PageModel<Role> getByPage(Integer pageIndex, Integer pageSize) {
				//1.整理参数
				Map<String,Object> params = new HashMap<>();
				//2.根据检索条件查询记录总数
				int recordCount = roleDao.count(params);
				PageModel<Role> pageModel = new PageModel<>();
				pageModel.setPageIndex(pageIndex);
				pageModel.setPageSize(pageSize);
				pageModel.setRecordCount(recordCount);
				if(recordCount>0){
					params.put("pageModel", pageModel);
					//3.根据检索和分页条件查询记录，并保存到分页对象中
					pageModel.setList(roleDao.selectByPage(params));
				}
				return pageModel;
	}

	@Override
	public void batchDisable(Integer[] ids) {
		Map<String, Object> params = new HashMap<>();
		params.put("ids", ids);
		roleDao.batchDisable(params);	
	}
	
	

	@Override
	public void batchEnable(Integer[] ids) {
		Map<String, Object> params = new HashMap<>();
		params.put("ids", ids);
		roleDao.batchEnable(params);	
		
	}

	@Override
	public void update(Role role,String[] authority) {
		//删除旧权限
		roleAuthorityDao.deleteByRoleId(role.getId());
		roleDao.update(role);
		
		for (String string : authority) {
			//添加新权限
			RoleAuthority ra = new RoleAuthority();
			Authority a = new Authority();
			ra.setRole(role);
			a.setId(string);
			ra.setAuthority(a);
			roleAuthorityDao.Insert(ra);
		}
	}

	@Override
	public void add(Role role,String[] authority) {
		 roleDao.insert(role);
		 RoleAuthority r = null;
		 for (String string : authority) {
			 r = new RoleAuthority();
			 Authority a = new Authority();
			 a.setId(string);
			 r.setAuthority(a);
			 r.setRole(role);
			 roleAuthorityDao.Insert(r);
		}
	}
	
}
