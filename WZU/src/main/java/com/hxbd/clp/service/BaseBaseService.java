package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.basedata.base.Base;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 权限业务层
 * @author Administrator
 *
 */
public interface BaseBaseService {
	List<Base> getAll();

	
	PageModel<Base> getByParam(Base base, Integer pagesize, Integer pageIndex);


	void update(Base base);


	void add(Base base);


	void del(Integer[] ids);


	Base findById(Integer id);
	
}
