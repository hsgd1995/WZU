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

import com.hxbd.clp.dao.provider.ProjectPersonnelDynaSqlProvider;
import com.hxbd.clp.domain.basedata.base.ProjectPersonnel;
import com.hxbd.clp.utils.common.RasConstants;

public interface ProjectPersonnelDao {

	@Select(" select * from "+RasConstants.PROJECTPERSONNEL+" where 1 = 1 ")
	@Results({
		@Result(column="project_id",property="projectId",javaType=java.lang.Integer.class)
	})
	List<ProjectPersonnel> selectAll();
	
	
	@Select(" select * from "+RasConstants.PROJECTPERSONNEL+" where id = #{id} ")
	@Results({
		@Result(column="project_id",property="projectId",javaType=java.lang.Integer.class)
	})
	ProjectPersonnel selectById(Integer id);

	@SelectProvider(type=ProjectPersonnelDynaSqlProvider.class,method="count")
	int count(Map<String, Object> params);

	@SelectProvider(type=ProjectPersonnelDynaSqlProvider.class,method="selectByPage")
	@Results({
		@Result(column="project_id",property="projectId",javaType=java.lang.Integer.class)
	})
	List<ProjectPersonnel> selectByPage(Map<String, Object> params);

	@UpdateProvider(type=ProjectPersonnelDynaSqlProvider.class,method="update")
	void update(ProjectPersonnel base);

	@InsertProvider(type=ProjectPersonnelDynaSqlProvider.class,method="insert")
	void insert(ProjectPersonnel base);

	@DeleteProvider(type=ProjectPersonnelDynaSqlProvider.class,method="batchDelect")
	void del(Map<String, Object> params);
}
