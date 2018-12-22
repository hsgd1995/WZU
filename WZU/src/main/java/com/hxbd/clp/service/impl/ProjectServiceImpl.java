package com.hxbd.clp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.dao.ProjectDao;
import com.hxbd.clp.domain.basedata.base.Project;
import com.hxbd.clp.service.ProjectService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 申请进驻基地业务实现类
 * @author Administrator
 *
 */
@Service("projectService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	private ProjectDao projectDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Project> getAll() {
		return projectDao.selectAll();
	}

	@Override
	public PageModel<Project> getByParam(Project base, Integer pageSize, Integer pageIndex) {
		//1.整理参数
		Map<String,Object> params = new HashMap<>();
		//2.根据检索条件查询记录总数
		int recordCount = projectDao.count(params);
		PageModel<Project> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		pageModel.setPageSize(pageSize);
		pageModel.setRecordCount(recordCount);
		if(recordCount>0){
			params.put("pageModel", pageModel);
			//3.根据检索和分页条件查询记录，并保存到分页对象中
			pageModel.setList(projectDao.selectByPage(params));
		}
		return pageModel;
	}

	
	
	@Override
	public PageModel<Project> getByParam(Project base, Integer pageSize, Integer pageIndex, String keyword) {
		//1.整理参数
		Map<String,Object> params = new HashMap<>();
		params.put("keyword", keyword);
		//2.根据检索条件查询记录总数
		int recordCount = projectDao.count(params);
		PageModel<Project> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		pageModel.setPageSize(pageSize);
		pageModel.setRecordCount(recordCount);
		if(recordCount>0){
			params.put("pageModel", pageModel);
			//3.根据检索和分页条件查询记录，并保存到分页对象中
			pageModel.setList(projectDao.selectByPage(params));
		}
		return pageModel;
	}

	@Override
	public void update(Project base) {
		projectDao.update(base);
	}

	@Override
	public int add(Project base) {
		projectDao.insert(base);
		return base.getId();
	}

	@Override
	public void del(Integer[] ids) {
		Map<String,Object> params = new HashMap<>();
		params.put("ids", ids);
		projectDao.del(params);
	}

	@Override
	public Project findById(Integer id) {
		Project p =  projectDao.selectById(id);
		//p.setAwardsList(awardsList);
		return p;
	}

	

}
