package com.hxbd.clp.service;


import java.util.List;
import java.util.Map;

import com.hxbd.clp.domain.User;
import com.hxbd.clp.utils.tag.PageModel;

public interface UserService {
	
	//验证用户 登录名和密码
	User validateUser(String loginName ,String password);
	//根据手机号查询用户
	User phoneValidate(String phoneNumber);
	//根据手机号验证用户
	boolean phoneValidate1(String phoneNumber);
	//根据邮箱查询用户
	User mailValidate(String mailNumber);
	PageModel<User> getAllUserByPage(User user, PageModel<User> pageModel);
	
	
	//根据身份证验证用户
	User idCardValidate(String cardId);
	// 保存用户
	void saveUser(User user);
	// 更新用户
	void updateUser(User user);
	// 批量删除用户
	void batchDelUser(Integer[] ids);
	// 根据id查询用户
	User selectById(Integer id);
	// 分页查询用户
	PageModel<User> findUser(User user, PageModel<User> pageModel);
	
	Map<Integer,User> findAllMap();
	
	Map<Integer, User> findUserByUsername(String username);
	
	void batchUpdate(List<String> list);
}
