package com.hxbd.clp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.dao.BaseBaseDao;
import com.hxbd.clp.domain.basedata.base.Base;
import com.hxbd.clp.service.BaseBaseService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 权限业务实现类
 * @author Administrator
 *
 */
@Service("baseBaseService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class BaseBaseServiceImpl implements BaseBaseService{

	@Autowired
	private BaseBaseDao baseBaseDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Base> getAll() {
		return baseBaseDao.selectAll();
	}

	@Override
	public PageModel<Base> getByParam(Base base, Integer pageSize, Integer pageIndex) {
		//1.整理参数
		Map<String,Object> params = new HashMap<>();
		//2.根据检索条件查询记录总数
		int recordCount = baseBaseDao.count(params);
		PageModel<Base> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		pageModel.setPageSize(pageSize);
		pageModel.setRecordCount(recordCount);
		if(recordCount>0){
			params.put("pageModel", pageModel);
			//3.根据检索和分页条件查询记录，并保存到分页对象中
			pageModel.setList(baseBaseDao.selectByPage(params));
		}
		return pageModel;
	}

	@Override
	public void update(Base base) {
		baseBaseDao.update(base);
	}

	@Override
	public void add(Base base) {
		baseBaseDao.insert(base);
	}

	@Override
	public void del(Integer[] ids) {
		Map<String,Object> params = new HashMap<>();
		params.put("ids", ids);
		baseBaseDao.del(params);
	}

	@Override
	public Base findById(Integer id) {
		return baseBaseDao.selectById(id);
	}
	
	

}
