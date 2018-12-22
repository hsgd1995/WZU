package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.FetchType;

import com.hxbd.clp.dao.provider.BaseBaseDynaSqlProvider;
import com.hxbd.clp.dao.provider.BaseBaseIntoDynaSqlProvider;
import com.hxbd.clp.domain.bus.base.BaseInto;
import com.hxbd.clp.utils.common.RasConstants;

public interface BaseIntoDao {

	@Select(" select * from "+RasConstants.BASEINTO+" where 1 = 1 ")
	@Results({
		@Result(column="manager_num",property="managerNum",javaType=java.lang.Integer.class)
	})
	List<BaseInto> selectAll();
	
	
	@Select(" select * from "+RasConstants.BASEINTO+" where id = #{id} ")
	@Results({
		@Result(column="base_id",property="base",javaType=com.hxbd.clp.domain.basedata.base.Base.class,
				one=@One(select="com.hxbd.clp.dao.BaseBaseDao.selectById",fetchType=FetchType.EAGER))
	})
	BaseInto selectById(Integer id);

	@SelectProvider(type=BaseBaseIntoDynaSqlProvider.class,method="count")
	int count(Map<String, Object> params);

	@SelectProvider(type=BaseBaseIntoDynaSqlProvider.class,method="selectByPage")
	@Results({
		@Result(column="base_id",property="base",javaType=com.hxbd.clp.domain.basedata.base.Base.class,
				one=@One(select="com.hxbd.clp.dao.BaseBaseDao.selectById",fetchType=FetchType.EAGER))
	})
	List<BaseInto> selectByPage(Map<String, Object> params);

	@UpdateProvider(type=BaseBaseIntoDynaSqlProvider.class,method="update")
	void update(BaseInto base);

	@InsertProvider(type=BaseBaseIntoDynaSqlProvider.class,method="insert")
	void insert(BaseInto base);

	@DeleteProvider(type=BaseBaseIntoDynaSqlProvider.class,method="batchDelect")
	void del(Map<String, Object> params);
}
