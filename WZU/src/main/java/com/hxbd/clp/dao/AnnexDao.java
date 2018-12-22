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

import com.hxbd.clp.dao.provider.AnnexDynaSqlProvider;
import com.hxbd.clp.domain.bus.base.Annex;
import com.hxbd.clp.utils.common.RasConstants;

public interface AnnexDao {

	@Select(" select * from "+RasConstants.ANNEX+" where 1 = 1 ")
	@Results({
		@Result(column="manager_num",property="managerNum",javaType=java.lang.Integer.class)
	})
	List<Annex> selectAll();
	
	
	@Select(" select * from "+RasConstants.ANNEX+" where id = #{id} ")
	@Results({
		@Result(column="manager_num",property="managerNum",javaType=java.lang.Integer.class)
	})
	Annex selectById(Integer id);

	@SelectProvider(type=AnnexDynaSqlProvider.class,method="count")
	int count(Map<String, Object> params);

	@SelectProvider(type=AnnexDynaSqlProvider.class,method="selectByPage")
	@Results({
		@Result(column="project_id",property="projectId",javaType=java.lang.Integer.class),
		@Result(column="competition_id",property="competitionId",javaType=java.lang.Integer.class),
		@Result(column="open_id",property="openId",javaType=java.lang.Integer.class),
		@Result(column="end_id",property="endId",javaType=java.lang.Integer.class)
	})
	List<Annex> selectByPage(Map<String, Object> params);

	@UpdateProvider(type=AnnexDynaSqlProvider.class,method="update")
	void update(Annex base);

	@InsertProvider(type=AnnexDynaSqlProvider.class,method="insert")
	void insert(Annex base);

	@DeleteProvider(type=AnnexDynaSqlProvider.class,method="batchDelect")
	void del(Map<String, Object> params);

	@Select("select * from "+RasConstants.ANNEX+" where project_id = #{id}")
	@Results({
		@Result(column="project_id",property="projectId",javaType=java.lang.Integer.class),
		@Result(column="competition_id",property="competitionId",javaType=java.lang.Integer.class),
		@Result(column="open_id",property="openId",javaType=java.lang.Integer.class),
		@Result(column="end_id",property="endId",javaType=java.lang.Integer.class)
	})
	List<Annex> selectByProjectId(Integer id);
}
