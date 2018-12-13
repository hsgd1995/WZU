package com.hxbd.clp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hxbd.clp.domain.Authority;
import com.hxbd.clp.utils.common.RasConstants;

public interface AuthorityDao {

	@Select(" select * from "+RasConstants.SYSAUTHORITY+" where 1 = 1 ")
	@Results({
		@Result(column="parent_id",property="parentId",javaType=java.lang.String.class)
	})
	List<Authority> selectAll();
	
	
	@Select(" select * from "+RasConstants.SYSAUTHORITY+" where type = -1")
	@Results({
		@Result(column="parent_id",property="parentId",javaType=java.lang.String.class)
	})
	List<Authority> selectModuleAuthority();
	
	@Select(" select * from "+RasConstants.SYSAUTHORITY+" where parent_id = #{parentId} and type = 0")
	@Results({
		@Result(column="parent_id",property="parentId",javaType=java.lang.String.class)
	})
	List<Authority> selectByParentId(@Param("parentId") String parentId);
	
	@Select(" select * from "+RasConstants.SYSAUTHORITY+" where id = #{id} ")
	@Results({
		@Result(column="parent_id",property="parentId",javaType=java.lang.String.class)
	})
	Authority selectById(String id);
}
