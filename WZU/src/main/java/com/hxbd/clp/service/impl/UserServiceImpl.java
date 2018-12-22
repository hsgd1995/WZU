package com.hxbd.clp.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxbd.clp.dao.UserDao;
import com.hxbd.clp.domain.CourseAndUser;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.service.UserService;
import com.hxbd.clp.utils.tag.PageModel;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	// 保存用户头像路径
	private static final String SAVEUSERPICPATH = "C:\\Program Files\\web\\Tomcat\\webapps\\HXCLImages\\userPic";
	
	@Override
	public User validateUser(String loginName, String password) {
		//System.err.println(userDao.selectByLoginNameAndPassword(loginName, password));
		return userDao.selectByLoginNameAndPassword(loginName, password);
	}

	@Override
	public PageModel<User> getAllUserByPage(User user, PageModel<User> pageModel) {
		Map<String, Object> params = new HashMap<>();
		params.put("user", user);
		Integer recordCount =  userDao.countUser(params);
		if(recordCount > 0){
			pageModel.setRecordCount(recordCount);
			params.put("pageModel", pageModel);
		}
		
		List<User> list = userDao.selectByParam(params);
		pageModel.setList(list);
		return pageModel;
	}

	@Override
	public void saveUser(User user) {
		userDao.saveUser(user);
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	@Override
	public void batchDelUser(Integer[] ids) {
		User user = null;
		File file = null;
		String uri = "";
		//删除头像
		for (Integer integer : ids) {
			user = userDao.selectById(integer);
			if(user != null){
				uri = SAVEUSERPICPATH+user.getUserPic().substring(44);
				System.out.println("uri:"+uri);
				file = new File(uri);
				if(file != null){
					file.delete();
				}
			}
		}
		
		Map<String, Integer[]> map = new HashMap<>();
		map.put("ids", ids);
		userDao.batchDelUser(map);
	}

	@Override
	public User phoneValidate(String phoneNumber) {
		User user = userDao.selectByPhoneNumber(phoneNumber); 
		return user;
	}

	@Override
	public boolean phoneValidate1(String phoneNumber) {
		if( userDao.selectByPhoneNumber(phoneNumber) != null){
			return true;
		}
		return false;
	}
	
	@Override
	public User mailValidate(String mailNumber) {
		User user = userDao.selectByMalNumber(mailNumber); 
		return user;
	}

	@Override
	public User idCardValidate(String cardId) {
		User user = userDao.selectByIdCard(cardId); 
		return user;
	}

	@Override
	public User selectById(Integer id) {
		return userDao.selectById(id);
	}

	@Override
	public PageModel<User> findUser(User user, PageModel<User> pageModel) {
		Map<String,Object> params = new HashMap<>();
		params.put("user", user);
		Integer recordCount = userDao.countUser(params);
		//System.out.println(recordCount);
		if(recordCount > 0){
			pageModel.setRecordCount(recordCount);
			params.put("pageModel", pageModel);
		}
		
		List<User> list = userDao.selectByParam(params);
		//System.out.println(list);
		pageModel.setList(list);
		return pageModel;
	}

	@Override
	public Map<Integer, User> findAllMap() {
		List<User> list = userDao.findAllList();
		Map<Integer,User> map = new HashMap<>();
		for(int i = 0; i<list.size();i++){
			map.put(list.get(i).getId(), list.get(i));
		}
		return map;
	}

	@Override
	public Map<Integer, User> findUserByUsername(String username) {
		List<User> list = userDao.selectByUsername(username);
		Map<Integer,User> map = new HashMap<>();
		for(int i = 0; i<list.size();i++){
			map.put(list.get(i).getId(), list.get(i));
		}
		return map;
	}

	@Override
	public void batchUpdate(List<String> list) {
		Map<String,List<String>> parmas = new HashMap<>();
		parmas.put("list", list);
		userDao.batchUpdate(parmas);
	}

	
}
