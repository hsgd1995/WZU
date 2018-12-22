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

import com.hxbd.clp.dao.provider.ProjectIntoDynaSqlProvider;
import com.hxbd.clp.domain.bus.base.ProjectInto;
import com.hxbd.clp.utils.common.RasConstants;

public interface ProjectIntoDao {

	@Select(" select * from "+RasConstants.PROJECTINTO+" where 1 = 1 ")
	@Results({
		@Result(column="manager_id",property="managerId",javaType=java.lang.Integer.class),
		@Result(column="project_id",property="projectId",javaType=java.lang.Integer.class),
		@Result(column="job_id",property="jobId",javaType=java.lang.String.class),
		@Result(column="is_agree",property="isAgree",javaType=java.lang.Integer.class),
	})
	List<ProjectInto> selectAll();
	
	
	@Select(" select * from "+RasConstants.PROJECTINTO+" where id = #{id} ")
	@Results({
		@Result(column="manager_id",property="managerId",javaType=java.lang.Integer.class),
		@Result(column="project_id",property="projectId",javaType=java.lang.Integer.class),
		@Result(column="job_id",property="jobId",javaType=java.lang.String.class),
		@Result(column="is_agree",property="isAgree",javaType=java.lang.Integer.class),
	})
	ProjectInto selectById(Integer id);

	@SelectProvider(type=ProjectIntoDynaSqlProvider.class,method="count")
	int count(Map<String, Object> params);

	@SelectProvider(type=ProjectIntoDynaSqlProvider.class,method="selectByPage")
	@Results({
		@Result(column="manager_id",property="managerId",javaType=java.lang.Integer.class),
		@Result(column="project_id",property="projectId",javaType=java.lang.Integer.class),
		@Result(column="job_id",property="jobId",javaType=java.lang.String.class),
		@Result(column="is_agree",property="isAgree",javaType=java.lang.Integer.class),
	})
	List<ProjectInto> selectByPage(Map<String, Object> params);

	@UpdateProvider(type=ProjectIntoDynaSqlProvider.class,method="update")
	void update(ProjectInto base);

	@InsertProvider(type=ProjectIntoDynaSqlProvider.class,method="insert")
	void insert(ProjectInto base);

	@DeleteProvider(type=ProjectIntoDynaSqlProvider.class,method="batchDelect")
	void del(Map<String, Object> params);
	
	@Select("select * from "+RasConstants.PROJECTINTO+" where project_id = #{id}")
	@Results({
		@Result(column="manager_id",property="managerId",javaType=java.lang.Integer.class),
		@Result(column="project_id",property="projectId",javaType=java.lang.Integer.class),
		@Result(column="job_id",property="jobId",javaType=java.lang.String.class),
		@Result(column="is_agree",property="isAgree",javaType=java.lang.Integer.class),
	})
	List<ProjectInto> selectByProjectId(Integer id);
}
