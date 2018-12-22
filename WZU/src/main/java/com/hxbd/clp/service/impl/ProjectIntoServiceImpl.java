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

import com.hxbd.clp.dao.ProjectIntoDao;
import com.hxbd.clp.domain.bus.base.ProjectInto;
import com.hxbd.clp.service.ProjectIntoService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 权限业务实现类
 * @author Administrator
 *
 */
@Service("projectIntoService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ProjectIntoServiceImpl implements ProjectIntoService{

	@Autowired
	private ProjectIntoDao projectIntoDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<ProjectInto> getAll() {
		return projectIntoDao.selectAll();
	}

	@Override
	public PageModel<ProjectInto> getByParam(ProjectInto base, Integer pageSize, Integer pageIndex) {
		//1.整理参数
		Map<String,Object> params = new HashMap<>();
		//2.根据检索条件查询记录总数
		int recordCount = projectIntoDao.count(params);
		PageModel<ProjectInto> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		pageModel.setPageSize(pageSize);
		pageModel.setRecordCount(recordCount);
		if(recordCount>0){
			params.put("pageModel", pageModel);
			//3.根据检索和分页条件查询记录，并保存到分页对象中
			pageModel.setList(projectIntoDao.selectByPage(params));
		}
		return pageModel;
	}

	@Override
	public void update(ProjectInto base) {
		projectIntoDao.update(base);
	}

	@Override
	public void add(ProjectInto base) {
		projectIntoDao.insert(base);
	}

	@Override
	public void del(Integer[] ids) {
		Map<String,Object> params = new HashMap<>();
		params.put("ids", ids);
		projectIntoDao.del(params);
	}

	@Override
	public ProjectInto findById(Integer id) {
		return projectIntoDao.selectById(id);
	}
	
	

}
