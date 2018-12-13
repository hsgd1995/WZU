package com.hxbd.clp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.dao.RecordDao;
import com.hxbd.clp.domain.Record;
import com.hxbd.clp.service.RecordService;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service("recordService")
public class RecordServiceImpl  implements RecordService{

	
	@Autowired
	private RecordDao recordDao;
	
	@Override
	public Record selectRecordByPhone(String phone) {
		return recordDao.selectRecordByPhone(phone);
	} 

	@Override
	public void savePhone(Record record) {

		recordDao.saveRecord(record);
	}

	@Override
	public void deleteRecord() {
		recordDao.deleteRecord();
		 
	}

	@Override
	public Integer findAllRecord() {
		Integer record = recordDao.findRecord();
		return record;
	}

	@Override
	public void updateRecord(Record record) {
		recordDao.updateRecord(record);
		
	}

}
