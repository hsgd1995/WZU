package com.hxbd.clp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.dao.ShortAnswerDao;
import com.hxbd.clp.domain.ShortAnswer;
import com.hxbd.clp.service.ShortAnswerService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 简答题 业务实现类
 * @author Administrator
 *
 */
@Service("shortAnswerService")
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
public class ShortAnswerServiceImpl implements ShortAnswerService{
	
	@Autowired
	private ShortAnswerDao shortAnswerDao;
	
	@Transactional(readOnly=true)
	@Override
	public PageModel<ShortAnswer> getByPage(ShortAnswer sa, Integer pageIndex, Integer pageSize) {
		//1.整理参数
		Map<String,Object> params = new HashMap<>();
		params.put("sa", sa);
		//2.根据检索条件查询记录总数
		int recordCount = shortAnswerDao.count(params);
		PageModel<ShortAnswer> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		pageModel.setPageSize(pageSize);
		pageModel.setRecordCount(recordCount);
		if(recordCount>0){
			//3.根据检索和分页条件查询记录，并保存到分页对象中
			params.put("pageModel", pageModel);
			pageModel.setList(shortAnswerDao.selectByPage(params));
		}
		return pageModel;
	}

	@Transactional(readOnly=true)
	@Override
	public ShortAnswer getById(Integer id) {
		return shortAnswerDao.selectById(id);
	}

	@Override
	public void add(ShortAnswer message) {
		shortAnswerDao.insert(message);
	}

	@Override
	public void edit(ShortAnswer message) {
		shortAnswerDao.update(message);
	}

	@Override
	public void batchDel(Integer[] ids) {
		Map<String, Object> params = new HashMap<>();
		params.put("ids", ids);
		shortAnswerDao.batchDel(params);
	}
}
