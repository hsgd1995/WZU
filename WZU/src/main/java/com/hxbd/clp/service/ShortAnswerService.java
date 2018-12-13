package com.hxbd.clp.service;

import com.hxbd.clp.domain.ShortAnswer;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 简答题 业务接口
 * @author Administrator
 *
 */
public interface ShortAnswerService {
	/**
	 * 条件-分页动态获取记录
	 * @param sa
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageModel<ShortAnswer> getByPage(ShortAnswer sa,Integer pageIndex,Integer pageSize);

	/**
	 * 根据id获取记录
	 * @param id
	 * @return
	 */
	ShortAnswer getById(Integer id);

	/**
	 * 新增记录
	 * @param sa
	 */
	void add(ShortAnswer sa);

	/**
	 * 编辑记录
	 * @param sa
	 */
	void edit(ShortAnswer sa);

	/**
	 * 批量删除
	 * @param ids
	 */
	void batchDel(Integer[] ids);
}
