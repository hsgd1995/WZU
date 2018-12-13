package com.hxbd.clp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hxbd.clp.dao.provider.RoleAuthorityDynaSqlProvider;
import com.hxbd.clp.domain.RoleAuthority;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 角色-权限Dao
 * @author Administrator
 *
 */
public interface RoleAuthorityDao {

	/**
	 * 根据roleId查询记录
	 * @param roleId
	 * @return
	 */
	@Select(" select * from "+RasConstants.SYSROLEAUTHORITY+" where role_id = #{roleId}")
	@Results({
		@Result(column="role_id",property="role",javaType=com.hxbd.clp.domain.Role.class,
				one=@One(select="com.hxbd.clp.dao.RoleDao.selectById")),
		@Result(column="aut_id",property="authority",javaType=com.hxbd.clp.domain.Authority.class,
				one=@One(select="com.hxbd.clp.dao.AuthorityDao.selectById")),
	})
	List<RoleAuthority> selectByRoleId(Integer roleId);
	
	/**
	 * 插入记录
	 * @param r
	 */
	@InsertProvider(type=RoleAuthorityDynaSqlProvider.class,method="insert")
	void Insert(RoleAuthority r);
	
	/**
	 * 删除记录
	 * @param roleId
	 */
	@Delete(" delete from " + RasConstants.SYSROLEAUTHORITY + " where role_id = #{roleId}")
	void deleteByRoleId(Integer roleId);
}
