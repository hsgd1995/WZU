package com.hxbd.clp.service;

import com.hxbd.clp.domain.User;
import com.hxbd.clp.domain.UserInfo;
import com.hxbd.clp.utils.tag.PageModel;

public interface UserInfoService {
	
	
	UserInfo selectById(Integer id);
    // 根据用户id查询用户信息
	UserInfo selectByUserId(Integer userId);
	// 保存用户信息
	void saveUser(UserInfo userInfo);
	// 更新用户信息
	void updateUserInfo(UserInfo userInfo);
	//
	void batchDelUserInfo(Integer[] ids);
	
	PageModel<UserInfo> findUserInfo(UserInfo userInfo, PageModel<UserInfo> pageModel);
	
}
