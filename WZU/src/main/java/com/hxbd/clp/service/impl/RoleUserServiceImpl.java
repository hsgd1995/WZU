package com.hxbd.clp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxbd.clp.dao.RoleUserDao;
import com.hxbd.clp.dao.UserDao;
import com.hxbd.clp.domain.Role;
import com.hxbd.clp.domain.RoleUser;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.service.RoleUserService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * （前台）角色-用户业务实现类
 * @author Administrator
 *
 */
@Service("roleUserService")
public class RoleUserServiceImpl implements RoleUserService{

	@Autowired
	private RoleUserDao roleUserDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public PageModel<RoleUser> getByPage(RoleUser roleUser, Integer pageIndex, Integer pageSize) {
		//1.整理参数
		Map<String,Object> params = new HashMap<>();
		params.put("roleUser", roleUser);
		//2.根据检索条件查询记录总数
		int recordCount = roleUserDao.count(params);
		PageModel<RoleUser> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		pageModel.setPageSize(pageSize);
		pageModel.setRecordCount(recordCount);
		if(recordCount>0){
			params.put("pageModel", pageModel);
			//3.根据检索和分页条件查询记录，并保存到分页对象中
			pageModel.setList(roleUserDao.selectByPage(params));
		}
		return pageModel;
	}

	@Override
	public void add(RoleUser roleUser) {
		roleUserDao.insert(roleUser);
	}

	@Override
	public void batchDel(Integer[] ids) {
		Map<String, Integer[]> params = new HashMap<>();
		params.put("ids", ids);
		roleUserDao.batchDel(params);
	}

	@Override
	public void add(Integer[] roleIds, Integer[] userIds, Boolean isAllUser) {
		//待优化的代码
		RoleUser roleUser;
		Role role ;
		//如果选择发送给所有用户
		if(isAllUser!=null&&isAllUser){
			List<User> list =   userDao.findAllList();
			for (Integer integer : roleIds) {
				for (User user : list) {
					roleUser = new RoleUser();
					role = new Role();
					role.setId(integer);
					roleUser.setRole(role);
					roleUser.setUser(user);
					roleUserDao.insert(roleUser);
				}
			}
		}else{
			User user;
			for (Integer integer : roleIds) {
				for (Integer userId : userIds) {
					roleUser = new RoleUser();
					role = new Role();
					role.setId(integer);
					user = new User();
					user.setId(userId);
					roleUser.setRole(role);
					roleUser.setUser(user);
					roleUserDao.insert(roleUser);
				}
			}
		}		
	}

	
}
