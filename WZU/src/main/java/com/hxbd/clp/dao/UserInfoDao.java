package com.hxbd.clp.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.dao.provider.CourseDynaSqlProvider;
import com.hxbd.clp.dao.provider.UserDynaSqlProvider;
import com.hxbd.clp.dao.provider.UserInfoDynaSqlProvider;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.domain.UserInfo;
import com.hxbd.clp.utils.common.RasConstants;

public interface UserInfoDao {

	
	//根据id查询用户 信息
	@Select("select * from " + RasConstants.PERSONAL_SETTINGS + " where id = #{id}")
	@Results(value = {@Result(column = "user_id" , property = "userId" , javaType = java.lang.Integer.class),
			@Result(column = "at_school" , property = "atSchool" , javaType = java.lang.String.class),
			@Result(column = "universities_colleges" , property = "school" , javaType = java.lang.String.class),
			@Result(column = "personal_profile" , property = "personalProfile" , javaType = java.lang.String.class),})
	UserInfo selectById(Integer id);
	
	
	//根据用户id查询用户 信息
	@Select("select * from " + RasConstants.PERSONAL_SETTINGS + " where user_id = #{userId}")
	@Results(value = {@Result(column = "user_id" , property = "userId" , javaType = java.lang.Integer.class),
			@Result(column = "at_school" , property = "atSchool" , javaType = java.lang.String.class),
			@Result(column = "universities_colleges" , property = "school" , javaType = java.lang.String.class),
			@Result(column = "personal_profile" , property = "personalProfile" , javaType = java.lang.String.class),})
	UserInfo selectByUserId(Integer userId);
	
	//新增用户信息
	@InsertProvider(type = UserInfoDynaSqlProvider.class , method = "saveUserInfo")
	void saveUserInfo(UserInfo userInfo);
	
	//保存用户信息
	@UpdateProvider(type = UserInfoDynaSqlProvider.class , method = "updateUserInfo")
	void updateUserInfo(UserInfo userInfo);



	// 查询所有用户总数
	@SelectProvider(type = UserInfoDynaSqlProvider.class , method = "countUser")
	Integer countUserInfo(Map<String, Object> params);

	//获取所有用户list
	@SelectProvider(type = UserInfoDynaSqlProvider.class , method = "selectByParam")
	@Results(value = {@Result(column = "user_id" , property = "userId" , javaType = java.lang.Integer.class),
			@Result(column = "at_school" , property = "atSchool" , javaType = java.lang.String.class),
			@Result(column = "universities_colleges" , property = "school" , javaType = java.lang.String.class),
			@Result(column = "personal_profile" , property = "personalProfile" , javaType = java.lang.String.class),})
	List<UserInfo> selectByParam(Map<String, Object> params);

	//根据课程id删除课程
	@DeleteProvider(type = UserInfoDynaSqlProvider.class , method = "batchDelete")
	void batchDelete(Map<String, Integer[]> map);
	
	
	
}
