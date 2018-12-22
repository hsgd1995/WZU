package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.bus.base.ProjectInto;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 权限业务层
 * @author Administrator
 *
 */
public interface ProjectIntoService {
	List<ProjectInto> getAll();

	
	PageModel<ProjectInto> getByParam(ProjectInto base, Integer pagesize, Integer pageIndex);


	void update(ProjectInto base);


	void add(ProjectInto base);


	void del(Integer[] ids);


	ProjectInto findById(Integer id);
	
}
