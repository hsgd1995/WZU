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

import com.hxbd.clp.dao.AwardsDao;
import com.hxbd.clp.domain.basedata.Awards;
import com.hxbd.clp.service.AwardsService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 权限业务实现类
 * @author Administrator
 *
 */
@Service("awardsService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class AwardsServiceImpl implements AwardsService{

	@Autowired
	private AwardsDao awardsDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Awards> getAll() {
		return awardsDao.selectAll();
	}

	@Override
	public PageModel<Awards> getByParam(Awards base, Integer pageSize, Integer pageIndex) {
		//1.整理参数
		Map<String,Object> params = new HashMap<>();
		//2.根据检索条件查询记录总数
		int recordCount = awardsDao.count(params);
		PageModel<Awards> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		pageModel.setPageSize(pageSize);
		pageModel.setRecordCount(recordCount);
		if(recordCount>0){
			params.put("pageModel", pageModel);
			//3.根据检索和分页条件查询记录，并保存到分页对象中
			pageModel.setList(awardsDao.selectByPage(params));
		}
		return pageModel;
	}

	@Override
	public void update(Awards base) {
		awardsDao.update(base);
	}

	@Override
	public void add(Awards base) {
		awardsDao.insert(base);
	}

	@Override
	public void del(Integer[] ids) {
		Map<String,Object> params = new HashMap<>();
		params.put("ids", ids);
		awardsDao.del(params);
	}

	@Override
	public Awards findById(Integer id) {
		return awardsDao.selectById(id);
	}
	
	

}
