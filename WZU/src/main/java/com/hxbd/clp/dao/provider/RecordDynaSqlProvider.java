package com.hxbd.clp.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.Record;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.utils.common.RasConstants;

public class RecordDynaSqlProvider {

	public String saveRecord(Record record){
		String sql =  new SQL(){
			{
				INSERT_INTO(RasConstants.RECORDTABLE);
				if(record != null){
					if(record.getPhone() != null && !record.getPhone().equals("")){
						VALUES("phone", "#{phone}");
					}
					if(record.getCount() != null && !record.getCount().equals("")){
						VALUES("count", "#{count}");
					}
				}
			}
		}.toString();
		return sql;
	}
	
	public String updateRecord(Record record){
		String sql =  new SQL(){
			{
				UPDATE(RasConstants.RECORDTABLE);
				if(record != null){
					if(record.getPhone() != null && !record.getPhone().equals("")){
						SET("phone = #{phone}");
					}
					if(record.getCount() != null && !record.getCount().equals("")){
						SET("count = #{count}");
					}
					WHERE(" count = #{count} ");
				}
			}
		}.toString();
		return sql;
	}
}
