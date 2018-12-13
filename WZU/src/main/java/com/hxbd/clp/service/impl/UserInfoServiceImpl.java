package com.hxbd.clp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxbd.clp.dao.UserInfoDao;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.domain.UserInfo;
import com.hxbd.clp.service.UserInfoService;
import com.hxbd.clp.utils.tag.PageModel;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService{
	
	@Autowired
	private UserInfoDao userInfoDao;

	@Override
	public UserInfo selectByUserId(Integer userId) {
		return userInfoDao.selectByUserId(userId);
	}

	@Override
	public void saveUser(UserInfo userInfo) {
		userInfoDao.saveUserInfo(userInfo);
	}

	@Override
	public PageModel<UserInfo> findUserInfo(UserInfo userInfo, PageModel<UserInfo> pageModel) {
		Map<String,Object> params = new HashMap<>();
		params.put("userInfo", userInfo);
		Integer recordCount = userInfoDao.countUserInfo(params);
		//System.out.println(recordCount);
		if(recordCount > 0){
			pageModel.setRecordCount(recordCount);
			params.put("pageModel", pageModel);
		}
		
		List<UserInfo> list = userInfoDao.selectByParam(params);
		pageModel.setList(list);
		return pageModel;
	}

	@Override
	public void updateUserInfo(UserInfo userInfo) {
		userInfoDao.updateUserInfo(userInfo);
	}

	@Override
	public void batchDelUserInfo(Integer[] ids) {
		Map<String, Integer[]> map = new HashMap<>();
		map.put("ids", ids);
		userInfoDao.batchDelete(map);
	}

	@Override
	public UserInfo selectById(Integer id) {
		return userInfoDao.selectById(id);
	}

}
