package com.hxbd.clp.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.dao.provider.RecordDynaSqlProvider;
import com.hxbd.clp.domain.Record;
import com.hxbd.clp.utils.common.RasConstants;

public interface RecordDao {

	//根据手机号码查询
	@Select("select * from " + RasConstants.RECORDTABLE + " where phone = #{phone}")
	@Results(value = {@Result(column = "phone",property = "phone",javaType = java.lang.String.class),
			@Result(column = "count",property = "count", javaType = java.lang.Integer.class)})
	com.hxbd.clp.domain.Record selectRecordByPhone(String phone);

	//保存手机验证次数
	@InsertProvider(type = RecordDynaSqlProvider.class, method = "saveRecord")
	void saveRecord(Record record);

	//删除记录
	@Delete("delete from " + RasConstants.RECORDTABLE)
	void deleteRecord();

	//查询记录总数
	@Select("select count(*) from " + RasConstants.RECORDTABLE)
	Integer findRecord();
	
	// 更新记录
	@UpdateProvider(type = RecordDynaSqlProvider.class , method = "updateRecord")
	void updateRecord(Record record);

}
