package com.tang.mapper.test2;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.tang.entity.test2.Order;

/**
 * 订单
 * 
 * @author Administrator
 * @date 2019年5月21日
 */
@Mapper
public interface OrderMapper {
	@Select("select * from order_table where user_id=#{userId}")
	@Results({ @Result(column = "user_id", property = "userId", javaType = java.lang.Integer.class),
			@Result(column = "order_Name", property = "orderName", javaType = java.lang.String.class) })
	public Order selectByUserId(Integer userId);
}
