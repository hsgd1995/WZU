package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import com.hxbd.clp.dao.provider.RoleUserDynaSqlProvider;
import com.hxbd.clp.domain.RoleUser;


/**
 * （前台）角色-用户
 * @author Administrator
 *
 */
public interface RoleUserDao {

	/**
	 * 按检索条件和分页条件查询记录总数
	 * @param params
	 * @return
	 */
	@SelectProvider(type=RoleUserDynaSqlProvider.class,method="count")
	int count(Map<String, Object> params);

	/**
	 * 按检索条件和分页条件查询记录
	 * @param params
	 * @return
	 */
	@SelectProvider(type=RoleUserDynaSqlProvider.class,method="selectByPage")
	@Results({
		@Result(column="role_id",property="role",javaType=com.hxbd.clp.domain.Role.class,
				one=@One(select="com.hxbd.clp.dao.RoleDao.selectById")),
		@Result(column="user_id",property="user",javaType=com.hxbd.clp.domain.User.class,
				one=@One(select="com.hxbd.clp.dao.UserDao.selectById"))
	})
	List<RoleUser> selectByPage(Map<String, Object> params);
	
	/**
	 * 插入
	 * @param roleUser
	 */
	@InsertProvider(type=RoleUserDynaSqlProvider.class,method="insert")
	void insert(RoleUser roleUser);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DeleteProvider(type = RoleUserDynaSqlProvider.class , method = "batchDel")
	void batchDel(Map<String, Integer[]> ids);
}
