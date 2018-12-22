package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.bus.base.BaseInto;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 权限业务层
 * @author Administrator
 *
 */
public interface BaseIntoService {
	List<BaseInto> getAll();

	
	PageModel<BaseInto> getByParam(BaseInto base, Integer pagesize, Integer pageIndex);


	void update(BaseInto base);


	void add(BaseInto base);


	void del(Integer[] ids);


	BaseInto findById(Integer id);
	
}
