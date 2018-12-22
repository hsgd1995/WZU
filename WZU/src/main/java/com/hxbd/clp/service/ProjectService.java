package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.basedata.base.Project;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 申请进驻基地业务层
 * @author Administrator
 *
 */
public interface ProjectService {
	List<Project> getAll();

	
	PageModel<Project> getByParam(Project base, Integer pagesize, Integer pageIndex);


	void update(Project base);


	int add(Project base);


	void del(Integer[] ids);


	Project findById(Integer id);


	PageModel<Project> getByParam(Project base, Integer pageSize, Integer pageIndex, String keyword);

	
}
