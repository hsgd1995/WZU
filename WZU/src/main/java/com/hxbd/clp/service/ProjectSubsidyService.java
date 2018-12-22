package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.basedata.base.ProjectSubsidy;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 权限业务层
 * @author Administrator
 *
 */
public interface ProjectSubsidyService {
	List<ProjectSubsidy> getAll();

	
	PageModel<ProjectSubsidy> getByParam(ProjectSubsidy base, Integer pagesize, Integer pageIndex);


	void update(ProjectSubsidy base);


	void add(ProjectSubsidy base);


	void del(Integer[] ids);


	ProjectSubsidy findById(Integer id);
	
}
