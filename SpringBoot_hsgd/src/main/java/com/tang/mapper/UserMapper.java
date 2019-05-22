package com.tang.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tang.entity.test1.User;

import static com.tang.common.UserTableConst.USER_TABLE;

/**
 * 用户mapper
 * 
 * @author Administrator
 * @date 2019年5月21日
 */
@Mapper
public interface UserMapper extends BaseMapper<User>{

	// 手写的sql
	@Select("select * from " + USER_TABLE + " where id=#{id}")
	User selectById(Integer id);

	@Insert("insert into " + USER_TABLE + " values(null,#{username},null,#{sex})")
	void insert(@Param("username") String username, @Param("sex") String sex);

	@Update("update " + USER_TABLE + " set username=#{username} where id=#{id}")
	void update(@Param("username") String username, @Param("id") Integer id);

	@Delete("delete from " + USER_TABLE + " where id=#{id}")
	void delete(Integer id);

	@Select("select * from " + USER_TABLE + " where username=#{username}")
	User selectByUsername(String username);
}
