package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.MessageAndUser;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 发送消息接口
 * @author Administrator
 *
 */
public interface MessageAndUserService {

	/**
	 * 根据检索和分页条件获取记录
	 * @param messageAndUser
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageModel<MessageAndUser> getByPage(MessageAndUser messageAndUser, Integer pageIndex, Integer pageSize);

	/**
	 * 获取某条消息记录
	 * @param id
	 * @return
	 */
	MessageAndUser getById(Integer id);
	

	
	/**
	 * 批量删除
	 * @param ids
	 */
	void batchDel(Integer[] ids);

	/**
	 * 获取用户的消息
	 * @param userId
	 * @return
	 */
	List<MessageAndUser> getUserMessage(Integer userId);

	/**
	 * 用户全部已读
	 * @param userId
	 */
	void allRead(Integer userId);

	/**
	 * 清空某用户的消息
	 * @param userId
	 */
	void allClear(Integer userId);

	/**
	 * 发送消息
	 * @param messageId
	 * @param userIds
	 * @param isAllUser
	 */
	void sendMessage(Integer[] messageIds, Integer[] userIds, Boolean isAllUser);

	/**
	 * 已读
	 * @param messageId
	 * @param userId
	 */
	void read(Integer id);
	
}
