package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.basedata.Awards;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 权限业务层
 * @author Administrator
 *
 */
public interface AwardsService {
	List<Awards> getAll();

	
	PageModel<Awards> getByParam(Awards base, Integer pagesize, Integer pageIndex);


	void update(Awards base);


	void add(Awards base);


	void del(Integer[] ids);


	Awards findById(Integer id);
	
}
