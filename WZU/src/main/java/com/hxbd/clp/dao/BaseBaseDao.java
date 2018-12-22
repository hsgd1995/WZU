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

import com.hxbd.clp.dao.provider.BaseBaseDynaSqlProvider;
import com.hxbd.clp.domain.basedata.base.Base;
import com.hxbd.clp.utils.common.RasConstants;

public interface BaseBaseDao {

	@Select(" select * from "+RasConstants.BASE+" where 1 = 1 ")
	@Results({
		@Result(column="manager_num",property="managerNum",javaType=java.lang.Integer.class)
	})
	List<Base> selectAll();
	
	
	@Select(" select * from "+RasConstants.BASE+" where id = #{id} ")
	@Results({
		@Result(column="manager_num",property="managerNum",javaType=java.lang.Integer.class)
	})
	Base selectById(Integer id);

	@SelectProvider(type=BaseBaseDynaSqlProvider.class,method="count")
	int count(Map<String, Object> params);

	@SelectProvider(type=BaseBaseDynaSqlProvider.class,method="selectByPage")
	@Results({
		@Result(column="manager_num",property="managerNum",javaType=java.lang.Integer.class)
	})
	List<Base> selectByPage(Map<String, Object> params);

	@UpdateProvider(type=BaseBaseDynaSqlProvider.class,method="update")
	void update(Base base);

	@InsertProvider(type=BaseBaseDynaSqlProvider.class,method="insert")
	void insert(Base base);

	@DeleteProvider(type=BaseBaseDynaSqlProvider.class,method="batchDelect")
	void del(Map<String, Object> params);
}
