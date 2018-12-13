package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.hxbd.clp.dao.provider.RoleManagerDynaSqlProvider;
import com.hxbd.clp.domain.RoleManager;
import com.hxbd.clp.utils.common.RasConstants;


/**
 * （后台）角色-管理员
 * @author Administrator
 *
 */
public interface RoleManagerDao {
	/**
	 * 按检索条件和分页条件查询记录总数
	 * @param params
	 * @return
	 */
	@SelectProvider(type=RoleManagerDynaSqlProvider.class,method="count")
	int count(Map<String, Object> params);

	/**
	 * 按检索条件和分页条件查询记录
	 * @param params
	 * @return
	 */
	@SelectProvider(type=RoleManagerDynaSqlProvider.class,method="selectByPage")
	@Results({
		@Result(column="role_id",property="role",javaType=com.hxbd.clp.domain.Role.class,
				one=@One(select="com.hxbd.clp.dao.RoleDao.selectById")),
		@Result(column="manage_id",property="manager",javaType=com.hxbd.clp.domain.Manager.class,
				one=@One(select="com.hxbd.clp.dao.ManagerDao.selectById"))
	})
	List<RoleManager> selectByPage(Map<String, Object> params);
	
	/**
	 * 插入
	 * @param roleManager
	 */
	@InsertProvider(type=RoleManagerDynaSqlProvider.class,method="insert")
	void insert(RoleManager roleManager);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@DeleteProvider(type = RoleManagerDynaSqlProvider.class , method = "batchDel")
	void batchDel(Map<String, Integer[]> ids);

	@Select(" select * from "+RasConstants.SYSROLEMANAGER +" where manage_id=#{id}")
	@Results({
		@Result(column="role_id",property="role",javaType=com.hxbd.clp.domain.Role.class,
				one=@One(select="com.hxbd.clp.dao.RoleDao.selectById")),
		@Result(column="manage_id",property="manager",javaType=com.hxbd.clp.domain.Manager.class,
				one=@One(select="com.hxbd.clp.dao.ManagerDao.selectById"))
	})
	RoleManager selectByManagerId(Integer id);
}
