package com.hxbd.clp.service;

import com.hxbd.clp.domain.Record;

public interface RecordService {

	//根据手机号查询
	Record selectRecordByPhone(String phone);
	//保存
	void savePhone(Record record);
	//删除
	void deleteRecord();
	//查询所有
	Integer findAllRecord();
	//更新
	void updateRecord(Record record);

}
