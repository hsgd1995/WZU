package com.hxbd.clp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxbd.clp.dao.ManagerDao;
import com.hxbd.clp.domain.Manager;
import com.hxbd.clp.service.ManagerService;
import com.hxbd.clp.utils.tag.PageModel;

@Service("managerService")
public class ManagerServiceImpl implements ManagerService{
	
	@Autowired
	private ManagerDao managerDao;
	
	@Override
	public Manager validateManager(String username, String password) {
		return managerDao.selectManeger(username, password) ;
	}

	@Override
	public void saveManager(Manager manager) {
		managerDao.saveManager(manager);
		
	}

	@Override
	public void updateManager(Manager manager) {
		managerDao.updateManager(manager);
		
	}

	@Override
	public void batchDelManager(Integer[] ids) {
		Map<String, Integer[]> map = new HashMap<>();
		map.put("ids", ids);
		managerDao.batchDelManager(map);
		
	}

	@Override
	public Manager selectById(Integer id) {
		return managerDao.selectById(id);
	}

	@Override
	public PageModel<Manager> findManager(Manager manager, PageModel<Manager> pageModel) {
		Map<String,Object> params = new HashMap<>();
		params.put("manager", manager);
		Integer recordCount = managerDao.countManager(params);
		if(recordCount > 0){
			pageModel.setRecordCount(recordCount);
			params.put("pageModel", pageModel);
		}
		
		List<Manager> list = managerDao.selectByParam(params);
		pageModel.setList(list);
		return pageModel;
	}

	@Override
	public List<Manager> getOrdinaryManager() {
		return managerDao.selectNoAdmin();
	}

	
}
