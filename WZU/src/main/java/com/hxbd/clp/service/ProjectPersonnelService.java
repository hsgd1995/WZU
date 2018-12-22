package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.basedata.base.ProjectPersonnel;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 权限业务层
 * @author Administrator
 *
 */
public interface ProjectPersonnelService {
	List<ProjectPersonnel> getAll();

	
	PageModel<ProjectPersonnel> getByParam(ProjectPersonnel base, Integer pagesize, Integer pageIndex);


	void update(ProjectPersonnel base);


	void add(ProjectPersonnel base);


	void del(Integer[] ids);


	ProjectPersonnel findById(Integer id);
	
}
