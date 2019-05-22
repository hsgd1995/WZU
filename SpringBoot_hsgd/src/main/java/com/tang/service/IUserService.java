package com.tang.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.tang.entity.test1.User;
import com.tang.entity.test2.Order;

/**
 * 用户
 * 
 * @author Administrator
 * @date 2019年5月21日
 */
public interface IUserService extends IService<User>{

	/**
	 * 根据id获取用户
	 * 
	 * @param id
	 * @return
	 */
	User getUserById(Integer id);

	/**
	 * 新增用户
	 * 
	 * @param username
	 * @param sex
	 */
	void insert(String username, String sex);

	/**
	 * 事务
	 * 
	 * @param username
	 * @param id
	 */
	void transcation(String username, Integer id);

	/**
	 * 多数据源测试 根据用户名获取订单
	 * 
	 * @param username
	 */
	Order getOrderByUsername(String username);

	/**
	 * mybatis-plus 测试 根据性别获取用户信息
	 * 
	 * @param username
	 * @return
	 */
	List<User> getUserBySex(String sex);
}
