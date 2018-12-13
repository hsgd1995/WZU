package com.hxbd.clp.service;

import java.util.List;
import java.util.Map;

import com.hxbd.clp.domain.Message;
import com.hxbd.clp.utils.tag.PageModel;


/**
 * 消息业务层
 * @author Administrator
 *
 */
public interface MessageService {
	/**
	 * 条件-分页动态获取消息
	 * @param message
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	PageModel<Message> getMessageByPage(Message message,Integer pageIndex,Integer pageSize);

	/**
	 * 根据id获取消息
	 * @param id
	 * @return
	 */
	Message getById(Integer id);

	/**
	 * 添加消息
	 * @param message
	 */
	void addMessage(Message message);

	/**
	 * 编辑消息
	 * @param message
	 */
	void editMessage(Message message);

	/**
	 * 批量删除
	 * @param ids
	 */
	void batchDel(Integer[] ids);

	/**
	 * 发送消息给所有用户
	 * @param ids
	 */
	void sendMessage(Integer[] ids);
}
