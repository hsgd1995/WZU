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

import com.hxbd.clp.dao.ProjectPersonnelDao;
import com.hxbd.clp.domain.basedata.base.ProjectPersonnel;
import com.hxbd.clp.service.ProjectPersonnelService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 权限业务实现类
 * @author Administrator
 *
 */
@Service("projectPersonnelService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ProjectPersonnelServiceImpl implements ProjectPersonnelService{

	@Autowired
	private ProjectPersonnelDao projectPersonnelDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<ProjectPersonnel> getAll() {
		return projectPersonnelDao.selectAll();
	}

	@Override
	public PageModel<ProjectPersonnel> getByParam(ProjectPersonnel base, Integer pageSize, Integer pageIndex) {
		//1.整理参数
		Map<String,Object> params = new HashMap<>();
		//2.根据检索条件查询记录总数
		int recordCount = projectPersonnelDao.count(params);
		PageModel<ProjectPersonnel> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		pageModel.setPageSize(pageSize);
		pageModel.setRecordCount(recordCount);
		if(recordCount>0){
			params.put("pageModel", pageModel);
			//3.根据检索和分页条件查询记录，并保存到分页对象中
			pageModel.setList(projectPersonnelDao.selectByPage(params));
		}
		return pageModel;
	}

	@Override
	public void update(ProjectPersonnel base) {
		projectPersonnelDao.update(base);
	}

	@Override
	public void add(ProjectPersonnel base) {
		projectPersonnelDao.insert(base);
	}

	@Override
	public void del(Integer[] ids) {
		Map<String,Object> params = new HashMap<>();
		params.put("ids", ids);
		projectPersonnelDao.del(params);
	}

	@Override
	public ProjectPersonnel findById(Integer id) {
		return projectPersonnelDao.selectById(id);
	}
	
	

}
