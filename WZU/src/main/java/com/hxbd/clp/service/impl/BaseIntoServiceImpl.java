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

import com.hxbd.clp.dao.BaseIntoDao;
import com.hxbd.clp.domain.bus.base.BaseInto;
import com.hxbd.clp.service.BaseIntoService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 权限业务实现类
 * @author Administrator
 *
 */
@Service("baseIntoService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class BaseIntoServiceImpl implements BaseIntoService{

	@Autowired
	private BaseIntoDao baseIntoDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<BaseInto> getAll() {
		return baseIntoDao.selectAll();
	}

	@Override
	public PageModel<BaseInto> getByParam(BaseInto base, Integer pageSize, Integer pageIndex) {
		//1.整理参数
		Map<String,Object> params = new HashMap<>();
		//2.根据检索条件查询记录总数
		int recordCount = baseIntoDao.count(params);
		PageModel<BaseInto> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		pageModel.setPageSize(pageSize);
		pageModel.setRecordCount(recordCount);
		if(recordCount>0){
			params.put("pageModel", pageModel);
			//3.根据检索和分页条件查询记录，并保存到分页对象中
			pageModel.setList(baseIntoDao.selectByPage(params));
		}
		return pageModel;
	}

	@Override
	public void update(BaseInto base) {
		baseIntoDao.update(base);
	}

	@Override
	public void add(BaseInto base) {
		baseIntoDao.insert(base);
	}

	@Override
	public void del(Integer[] ids) {
		Map<String,Object> params = new HashMap<>();
		params.put("ids", ids);
		baseIntoDao.del(params);
	}

	@Override
	public BaseInto findById(Integer id) {
		return baseIntoDao.selectById(id);
	}
	
	

}
