package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.bus.base.Annex;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 权限业务层
 * @author Administrator
 *
 */
public interface AnnexService {
	List<Annex> getAll();

	
	PageModel<Annex> getByParam(Annex base, Integer pagesize, Integer pageIndex);


	void update(Annex base);


	void add(Annex base);


	void del(Integer[] ids);


	Annex findById(Integer id);
	
	List<Annex> findByProjectId(Integer id);
}
