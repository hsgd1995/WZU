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

import com.hxbd.clp.dao.ProjectSubsidyDao;
import com.hxbd.clp.domain.basedata.base.ProjectSubsidy;
import com.hxbd.clp.service.ProjectSubsidyService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 权限业务实现类
 * @author Administrator
 *
 */
@Service("projectSubsidyService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ProjectSubsidyServiceImpl implements ProjectSubsidyService{

	@Autowired
	private ProjectSubsidyDao projectSubsidyDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<ProjectSubsidy> getAll() {
		return projectSubsidyDao.selectAll();
	}

	@Override
	public PageModel<ProjectSubsidy> getByParam(ProjectSubsidy base, Integer pageSize, Integer pageIndex) {
		//1.整理参数
		Map<String,Object> params = new HashMap<>();
		//2.根据检索条件查询记录总数
		int recordCount = projectSubsidyDao.count(params);
		PageModel<ProjectSubsidy> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		pageModel.setPageSize(pageSize);
		pageModel.setRecordCount(recordCount);
		if(recordCount>0){
			params.put("pageModel", pageModel);
			//3.根据检索和分页条件查询记录，并保存到分页对象中
			pageModel.setList(projectSubsidyDao.selectByPage(params));
		}
		return pageModel;
	}

	@Override
	public void update(ProjectSubsidy base) {
		projectSubsidyDao.update(base);
	}

	@Override
	public void add(ProjectSubsidy base) {
		projectSubsidyDao.insert(base);
	}

	@Override
	public void del(Integer[] ids) {
		Map<String,Object> params = new HashMap<>();
		params.put("ids", ids);
		projectSubsidyDao.del(params);
	}

	@Override
	public ProjectSubsidy findById(Integer id) {
		return projectSubsidyDao.selectById(id);
	}
	
	

}
