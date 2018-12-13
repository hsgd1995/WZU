package com.hxbd.clp.service;

import com.hxbd.clp.domain.SystemMessage;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 系统消息业务层
 * @author Administrator
 *
 */
public interface SystemMessageService {
	
	/**
	 * 考试提醒
	 */
	void examStudyWarn();
	
	/**
	 * 根据检索和分页条件获取记录
	 * @param systemMessage
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageModel<SystemMessage> getByPage(SystemMessage systemMessage, Integer pageIndex, Integer pageSize);

	/**
	 * 获取某条消息记录
	 * @param id
	 * @return
	 */
	SystemMessage getById(Integer id);
	

	
	/**
	 * 批量删除
	 * @param ids
	 */
	void batchDel(Integer[] ids);


	/**
	 * 已读
	 * @param id
	 */
	void read(Integer id,Integer userId);

	/**
	 * 用户全部已读
	 * @param userId
	 */
	void allRead(Integer userId);

	/**
	 * 用户全部情空
	 * @param id
	 */
	void allClear(Integer id);

	/**
	 * 用户删除消息
	 * @param ids
	 * @param userId
	 */
	void UserDelSysMessage(Integer[] ids, Integer userId);
}
