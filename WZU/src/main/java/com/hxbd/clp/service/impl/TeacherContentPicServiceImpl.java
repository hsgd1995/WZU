package com.hxbd.clp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxbd.clp.dao.TeacherContentPicDao;
import com.hxbd.clp.domain.TeacherContentPic;
import com.hxbd.clp.service.TeacherContentPicService;

@Service("teacherContentPicService")
public class TeacherContentPicServiceImpl implements TeacherContentPicService{

	@Autowired
	private TeacherContentPicDao teacherContentPicDao;
	
	@Override
	public void add(TeacherContentPic teacherContentPic) {
		teacherContentPicDao.save(teacherContentPic);
		
	}

	@Override
	public void batchDeletePic(Integer[] ids) {
		// TODO Auto-generated method stub
	}

	@Override
	public void delectById(Integer id) {
		teacherContentPicDao.delById(id);
	}

	@Override
	public TeacherContentPic selectPicById(Integer id) {
		return teacherContentPicDao.selectPicById(id);
	}

	@Override
	public List<TeacherContentPic> SelectPicByTeacherId(Integer teacherId) {
		return teacherContentPicDao.SelectPicByTeacherId(teacherId);
	}

	
}
