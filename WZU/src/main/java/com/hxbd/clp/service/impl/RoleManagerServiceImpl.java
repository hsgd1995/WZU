package com.hxbd.clp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxbd.clp.dao.ManagerDao;
import com.hxbd.clp.dao.RoleAuthorityDao;
import com.hxbd.clp.dao.RoleManagerDao;
import com.hxbd.clp.domain.Manager;
import com.hxbd.clp.domain.RoleAuthority;
import com.hxbd.clp.domain.RoleManager;
import com.hxbd.clp.service.RoleManagerService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * （后台）角色-管理员 业务实现类
 * @author Administrator
 *
 */
@Service("roleManagerService")
public class RoleManagerServiceImpl implements RoleManagerService{
	
	@Autowired
	private RoleManagerDao roleManagerDao;
	
	@Autowired
	private ManagerDao managerDao;
	
	@Autowired
	private RoleAuthorityDao roleAuthorityDao;

	@Override
	public PageModel<RoleManager> getByPage(RoleManager roleManager, Integer pageIndex, Integer pageSize) {
		//1.整理参数
				Map<String,Object> params = new HashMap<>();
				params.put("roleManager", roleManager);
				//2.根据检索条件查询记录总数
				int recordCount = roleManagerDao.count(params);
				PageModel<RoleManager> pageModel = new PageModel<>();
				pageModel.setPageIndex(pageIndex);
				pageModel.setPageSize(pageSize);
				pageModel.setRecordCount(recordCount);
				if(recordCount>0){
					params.put("pageModel", pageModel);
					//3.根据检索和分页条件查询记录，并保存到分页对象中
					pageModel.setList(roleManagerDao.selectByPage(params));
				}
				return pageModel;
	}

	@Override
	public Integer add(RoleManager roleManager) {
		
		Manager manager = managerDao.selectById(roleManager.getManager().getId());
		if(manager==null){
			return 2;
		}
		RoleManager r = roleManagerDao.selectByManagerId(roleManager.getManager().getId());
		if(r!=null){
			return 3;
		}
		roleManagerDao.insert(roleManager);
		
		return 1;
	}

	@Override
	public void batchDel(Integer[] ids) {
		Map<String, Integer[]> params = new HashMap<>();
		params.put("ids", ids);
		roleManagerDao.batchDel(params);		
	}

	@Override
	public void add(Integer[] roleIds, Integer[] managerIds, Boolean isAllManager) {
		
	}

	@Override
	public List<RoleAuthority> getManagerAuthority(Integer managerId){
		RoleManager rm = roleManagerDao.selectByManagerId(managerId);
		System.out.println("rm--"+rm);
		return roleAuthorityDao.selectByRoleId(rm.getRole().getId());
	}
	
}
