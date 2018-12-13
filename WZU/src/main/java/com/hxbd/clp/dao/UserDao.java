package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.dao.provider.UserDynaSqlProvider;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.utils.common.RasConstants;

public interface UserDao {
	
	//根据id查询用户 
	@Select("select * from " + RasConstants.USERTABLE + " where id = #{id}")
	@Results(value = {@Result(column = "login_name" , property = "loginName" , javaType = java.lang.String.class),
			@Result(column = "phone_number" , property = "phoneNumber", javaType = java.lang.String.class),
			@Result(column = "user_pic" , property = "userPic", javaType = java.lang.String.class),
			@Result(column = "card_id" , property = "cardId", javaType = java.lang.String.class)})
	User selectById(Integer id);
	
	//根据登录名和密码查询用户
	@Select("select * from " + RasConstants.USERTABLE + " where login_name = #{loginName} and password = #{password}")
	@Results(value = {@Result(column = "login_name" , property = "loginName" , javaType = java.lang.String.class),
			@Result(column = "phone_number" , property = "phoneNumber", javaType = java.lang.String.class),
			@Result(column = "user_pic" , property = "userPic", javaType = java.lang.String.class),
			@Result(column = "card_id" , property = "cardId", javaType = java.lang.String.class)})
	User selectByLoginNameAndPassword(@Param("loginName") String loginName ,@Param("password") String password);
	
	//根据手机号查询用户
	@Select("select * from " + RasConstants.USERTABLE + " where phone_number = #{phoneNumber}")
	@Results(value = {@Result(column = "login_name" , property = "loginName" , javaType = java.lang.String.class),
			@Result(column = "phone_number" , property = "phoneNumber", javaType = java.lang.String.class),
			@Result(column = "user_pic" , property = "userPic", javaType = java.lang.String.class),
			@Result(column = "card_id" , property = "cardId", javaType = java.lang.String.class)})
	User selectByPhoneNumber(String phoneNumber);
	
	
	//根据身份证查询用户是否存在
	@Select("select * from " + RasConstants.USERTABLE + " where card_id = #{cardId}")
	@Results(value = {@Result(column = "login_name" , property = "loginName" , javaType = java.lang.String.class),
			@Result(column = "user_pic" , property = "userPic", javaType = java.lang.String.class),
			@Result(column = "card_id" , property = "cardId", javaType = java.lang.String.class)})
	User selectByIdCard(String cardId);
	
	//根据邮箱号查询用户
	@Select("select * from " + RasConstants.USERTABLE + " where email = #{email}")
	@Results(value = {@Result(column = "login_name" , property = "loginName" , javaType = java.lang.String.class),
			@Result(column = "email" , property = "email", javaType = java.lang.String.class),
			@Result(column = "user_pic" , property = "userPic", javaType = java.lang.String.class),
			@Result(column = "card_id" , property = "cardId", javaType = java.lang.String.class)})
	User selectByMalNumber(String mailNumber);
	
	//获取所有用户list
	@SelectProvider(type = UserDynaSqlProvider.class , method = "selectByParam")
	@Results(value = {@Result(column = "login_name" , property = "loginName" , javaType = java.lang.String.class),
			@Result(column = "phone_number" , property = "phoneNumber", javaType = java.lang.String.class),
			@Result(column = "user_pic" , property = "userPic", javaType = java.lang.String.class),
			@Result(column = "card_id" , property = "cardId", javaType = java.lang.String.class)})
	List<User> selectByParam(Map<String, Object> params);
	
	// 查询所有用户总数
	@SelectProvider(type = UserDynaSqlProvider.class , method = "countUser")
	Integer countUser(Map<String, Object> params);
	
	//保存用户
	@InsertProvider(type = UserDynaSqlProvider.class , method = "saveUser")
	void saveUser(User user);
	
	//更新用户
	@UpdateProvider(type = UserDynaSqlProvider.class , method = "updateUser")
	void updateUser(User user);
	
	//批量删除用户
	@DeleteProvider(type = UserDynaSqlProvider.class , method = "bathDel")
	void batchDelUser(Map<String, Integer[]> ids);

	//查询list
		@Select("select * from " + RasConstants.USERTABLE)
		@Results(value = {@Result(column = "id" , property = "id" , javaType = java.lang.Integer.class),
				@Result(column = "username" , property = "username" , javaType = java.lang.String.class),
				@Result(column = "email" , property = "email" , javaType = java.lang.String.class),
				@Result(column = "login_time" , property = "loginTime" , javaType = java.util.Date.class),
				@Result(column = "logout_time" , property = "logoutTime" , javaType = java.util.Date.class)})
	List<User> findAllList();

	@Select("select * from " + RasConstants.USERTABLE + " where username = #{username}")
	List<User> selectByUsername(String username);

	@UpdateProvider(type=UserDynaSqlProvider.class,method="batchUpdate")
	void batchUpdate(Map<String, List<String>> parmas);

}
