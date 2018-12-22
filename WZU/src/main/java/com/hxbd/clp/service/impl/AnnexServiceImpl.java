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

import com.hxbd.clp.dao.AnnexDao;
import com.hxbd.clp.domain.bus.base.Annex;
import com.hxbd.clp.service.AnnexService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 附件类
 * @author Administrator
 *
 */
@Service("annexService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class AnnexServiceImpl implements AnnexService{

	@Autowired
	private AnnexDao annexDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Annex> getAll() {
		return annexDao.selectAll();
	}

	@Override
	public PageModel<Annex> getByParam(Annex base, Integer pageSize, Integer pageIndex) {
		//1.整理参数
		Map<String,Object> params = new HashMap<>();
		//2.根据检索条件查询记录总数
		int recordCount = annexDao.count(params);
		PageModel<Annex> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		pageModel.setPageSize(pageSize);
		pageModel.setRecordCount(recordCount);
		if(recordCount>0){
			params.put("pageModel", pageModel);
			//3.根据检索和分页条件查询记录，并保存到分页对象中
			pageModel.setList(annexDao.selectByPage(params));
		}
		return pageModel;
	}

	@Override
	public void update(Annex base) {
		annexDao.update(base);
	}

	@Override
	public void add(Annex base) {
		annexDao.insert(base);
	}

	@Override
	public void del(Integer[] ids) {
		Map<String,Object> params = new HashMap<>();
		params.put("ids", ids);
		annexDao.del(params);
	}

	@Override
	public Annex findById(Integer id) {
		return annexDao.selectById(id);
	}

	@Override
	public List<Annex> findByProjectId(Integer id) {
		return annexDao.selectByProjectId(id);
	}
	
	

}
