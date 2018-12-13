package com.hxbd.clp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.dao.MessageAndUserDao;
import com.hxbd.clp.dao.MessageDao;
import com.hxbd.clp.dao.UserDao;
import com.hxbd.clp.domain.Message;
import com.hxbd.clp.domain.MessageAndUser;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.service.MessageService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 消息业务层实现类
 * @author Administrator
 *
 */
@Service("messageService")
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
public class MessageServiceImpl implements MessageService{
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MessageAndUserDao messageAndUserDao;
	
	@Transactional(readOnly=true)
	@Override
	public PageModel<Message> getMessageByPage(Message message, Integer pageIndex, Integer pageSize) {
		//1.整理参数
		Map<String,Object> params = new HashMap<>();
		params.put("message", message);
		//2.根据检索条件查询记录总数
		int recordCount = messageDao.count(params);
		PageModel<Message> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		pageModel.setPageSize(pageSize);
		pageModel.setRecordCount(recordCount);
		if(recordCount>0){
			//3.根据检索和分页条件查询记录，并保存到分页对象中
			params.put("pageModel", pageModel);
			pageModel.setList(messageDao.selectByPage(params));
		}
		return pageModel;
	}

	@Transactional(readOnly=true)
	@Override
	public Message getById(Integer id) {
		return messageDao.selectById(id);
	}

	@Override
	public void addMessage(Message message) {
		messageDao.insert(message);
	}

	@Override
	public void editMessage(Message message) {
		messageDao.update(message);
	}

	@Override
	public void batchDel(Integer[] ids) {
		Map<String, Object> params = new HashMap<>();
		params.put("ids", ids);
		messageDao.batchDel(params);
	}

	@Override
	public void sendMessage(Integer[] ids) {
		MessageAndUser messageAndUser;
		Message message ;
		if(ids.length==0){
			return;
		}
		List<User> list =   userDao.findAllList();
		for (Integer integer : ids) {
			for (User user : list) {
				messageAndUser = new MessageAndUser();
				message = new Message();
				message.setId(integer);
				messageAndUser.setMessage(message);
				messageAndUser.setUser(user);
				messageAndUser.setStatus(0);
				messageAndUser.setIsClear(0);
				messageAndUserDao.insert(messageAndUser);
			}
		}
	}
	
	
}
