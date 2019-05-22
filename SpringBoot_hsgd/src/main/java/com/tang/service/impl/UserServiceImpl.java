package com.tang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tang.entity.test1.User;
import com.tang.entity.test2.Order;
import com.tang.mapper.UserMapper;
import com.tang.mapper.test2.OrderMapper;
import com.tang.service.IUserService;

/**
 * 用户
 * 
 * @author Administrator
 * @date 2019年5月21日
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

	@Autowired
	private UserMapper userMapper;
	//@Autowired （暂不使用多数据源）
	private OrderMapper orderMapper;

	@Override
	public User getUserById(Integer id) {
		return userMapper.selectById(id);
	}

	@Override
	public void insert(String username, String sex) {
		userMapper.insert(username, sex);
	}

	@Transactional
	@Override
	public void transcation(String username, Integer id) {
		userMapper.update(username, id);
		int a = 2 / 0;
		userMapper.delete(id);
	}

	@Override
	public Order getOrderByUsername(String username) {
		User user = userMapper.selectByUsername(username);
		return orderMapper.selectByUserId(user.getId());
	}

	@Override
	public List<User> getUserBySex(String sex) {
		Wrapper<User> wrapper = new EntityWrapper<User>();
		wrapper.eq("sex", sex);
		return userMapper.selectList(wrapper);
	}

}
