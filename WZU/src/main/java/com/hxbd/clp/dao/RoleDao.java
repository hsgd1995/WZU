package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.dao.provider.RoleDynaSqlProvider;
import com.hxbd.clp.domain.Role;
import com.hxbd.clp.utils.common.RasConstants;


/**
 * 角色dao
 * @author Administrator
 *
 */
public interface RoleDao {
	/**
	 * 插入
	 * @param role
	 */
	@InsertProvider(type=RoleDynaSqlProvider.class,method="insert")
	@Options(useGeneratedKeys=true,keyProperty = "id")
	void insert(Role role);
	
	/**
	 * 检索-分页查询记录
	 * @param parmas
	 * @return
	 */
	@SelectProvider(type = RoleDynaSqlProvider.class, method = "selectByParma")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "org_id",property = "orgId" ,javaType = java.lang.Integer.class),
			@Result(column = "create_time",property = "createTime" ,javaType = java.lang.String.class),
			@Result(column = "creater",property = "createrId" ,javaType = java.lang.Integer.class),
	})
	List<Role> selectByPage(Map<String, Object> parmas);
	
	/**
	 * 检索-分页查询记录总数
	 * @param parmas
	 * @return
	 */
	@SelectProvider(type = RoleDynaSqlProvider.class, method = "count")
	Integer count(Map<String, Object> parmas);
	
	/**
	 * 批量停用
	 * @param params
	 */
	@UpdateProvider(type=RoleDynaSqlProvider.class,method="batchDisable")
	void batchDisable(Map<String, Object> params);
	
	/**
	 * 批量启用
	 * @param params
	 */
	@UpdateProvider(type=RoleDynaSqlProvider.class,method="batchEnable")
	void batchEnable(Map<String, Object> params);
	/**
	 * 修改
	 * @param role
	 */
	@UpdateProvider(type=RoleDynaSqlProvider.class,method="update")
	void update(Role role);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	@Select("select * from "+RasConstants.SYSROLE+" where id = #{id}")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "org_id",property = "orgId" ,javaType = java.lang.Integer.class),
			@Result(column = "create_time",property = "createTime" ,javaType = java.lang.String.class),
			@Result(column = "creater",property = "createrId" ,javaType = java.lang.Integer.class),
	})
	Role selectById(Integer id);
}
