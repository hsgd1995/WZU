package com.hxbd.clp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxbd.clp.dao.MessageAndUserDao;
import com.hxbd.clp.dao.UserDao;
import com.hxbd.clp.domain.Message;
import com.hxbd.clp.domain.MessageAndUser;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.service.MessageAndUserService;
import com.hxbd.clp.utils.tag.PageModel;
/**
 * 发送消息实现类
 *
 */
@Service("messageAndUserService")
public class MessageAndUserServiceImpl implements MessageAndUserService{
	
	@Autowired
	private MessageAndUserDao messageAndUserDao;
	
	@Autowired
	private UserDao userDao;

	@Override
	public PageModel<MessageAndUser> getByPage(MessageAndUser messageAndUser, Integer pageIndex, Integer pageSize) {
		//1.整理参数
				Map<String,Object> params = new HashMap<>();
				params.put("messageAndUser", messageAndUser);
				//2.根据检索条件查询记录总数
				int recordCount = messageAndUserDao.count(params);
				PageModel<MessageAndUser> pageModel = new PageModel<>();
				pageModel.setPageIndex(pageIndex);
				pageModel.setPageSize(pageSize);
				pageModel.setRecordCount(recordCount);
				if(recordCount>0){
					params.put("pageModel", pageModel);
					//3.根据检索和分页条件查询记录，并保存到分页对象中
					pageModel.setList(messageAndUserDao.selectByPage(params));
				}
				return pageModel;
	}

	@Override
	public MessageAndUser getById(Integer id) {
		return messageAndUserDao.selectById(id);
	}


	@Override
	public void batchDel(Integer[] ids) {
		Map<String, Object> params = new HashMap<>();
		params.put("ids", ids);
		messageAndUserDao.batchDel(params);
	}

	@Override
	public List<MessageAndUser> getUserMessage(Integer userId) {
		return messageAndUserDao.selectByUserId(userId);
	}

	@Override
	public void allRead(Integer userId) {
		messageAndUserDao.updateStatusByUserId(userId);
	}

	@Override
	public void allClear(Integer userId) {
		messageAndUserDao.delMessageByUserId(userId);
	}

	@Override
	public void sendMessage(Integer[] messageIds, Integer[] userIds, Boolean isAllUser) {
		MessageAndUser messageAndUser;
		Message message ;
		//如果选择发送给所有用户
		if(isAllUser!=null&&isAllUser){
			List<User> list =   userDao.findAllList();
			for (Integer integer : messageIds) {
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
		}else{
			User user;
			for (Integer integer : messageIds) {
				for (Integer userId : userIds) {
					messageAndUser = new MessageAndUser();
					message = new Message();
					message.setId(integer);
					user = new User();
					user.setId(userId);
					messageAndUser.setMessage(message);
					messageAndUser.setUser(user);
					messageAndUser.setStatus(0);
					messageAndUser.setIsClear(0);
					messageAndUserDao.insert(messageAndUser);
				}
			}
		}
	}

	@Override
	public void read(Integer id) {
		messageAndUserDao.updateStatusById(id);
	}

	
}
