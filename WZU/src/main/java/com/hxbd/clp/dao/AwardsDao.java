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

import com.hxbd.clp.dao.provider.AwardsDynaSqlProvider;
import com.hxbd.clp.domain.basedata.Awards;
import com.hxbd.clp.utils.common.RasConstants;

public interface AwardsDao {

	@Select(" select * from "+RasConstants.AWARDS+" where 1 = 1 ")
	@Results({
		@Result(column="manager_num",property="managerNum",javaType=java.lang.Integer.class)
	})
	List<Awards> selectAll();
	
	
	@Select(" select * from "+RasConstants.AWARDS+" where id = #{id} ")
	@Results({
		@Result(column="manager_num",property="managerNum",javaType=java.lang.Integer.class)
	})
	Awards selectById(Integer id);

	@SelectProvider(type=AwardsDynaSqlProvider.class,method="count")
	int count(Map<String, Object> params);

	@SelectProvider(type=AwardsDynaSqlProvider.class,method="selectByPage")
	@Results({
		@Result(column="manager_num",property="managerNum",javaType=java.lang.Integer.class)
	})
	List<Awards> selectByPage(Map<String, Object> params);

	@UpdateProvider(type=AwardsDynaSqlProvider.class,method="update")
	void update(Awards base);

	@InsertProvider(type=AwardsDynaSqlProvider.class,method="insert")
	void insert(Awards base);

	@DeleteProvider(type=AwardsDynaSqlProvider.class,method="batchDelect")
	void del(Map<String, Object> params);
}
