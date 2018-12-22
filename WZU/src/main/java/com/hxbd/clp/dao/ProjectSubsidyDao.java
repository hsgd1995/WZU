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

import com.hxbd.clp.dao.provider.ProjectSubsidyDynaSqlProvider;
import com.hxbd.clp.domain.basedata.base.ProjectSubsidy;
import com.hxbd.clp.utils.common.RasConstants;

public interface ProjectSubsidyDao {

	@Select(" select * from "+RasConstants.PROJECTSUBSIDY+" where 1 = 1 ")
	@Results({
		@Result(column="project_id",property="projectId",javaType=java.lang.Integer.class)
	})
	List<ProjectSubsidy> selectAll();
	
	
	@Select(" select * from "+RasConstants.PROJECTSUBSIDY+" where id = #{id} ")
	@Results({
		@Result(column="project_id",property="projectId",javaType=java.lang.Integer.class)
	})
	ProjectSubsidy selectById(Integer id);

	@SelectProvider(type=ProjectSubsidyDynaSqlProvider.class,method="count")
	int count(Map<String, Object> params);

	@SelectProvider(type=ProjectSubsidyDynaSqlProvider.class,method="selectByPage")
	@Results({
		@Result(column="project_id",property="projectId",javaType=java.lang.Integer.class)
	})
	List<ProjectSubsidy> selectByPage(Map<String, Object> params);

	@UpdateProvider(type=ProjectSubsidyDynaSqlProvider.class,method="update")
	void update(ProjectSubsidy base);

	@InsertProvider(type=ProjectSubsidyDynaSqlProvider.class,method="insert")
	void insert(ProjectSubsidy base);

	@DeleteProvider(type=ProjectSubsidyDynaSqlProvider.class,method="batchDelect")
	void del(Map<String, Object> params);
}
