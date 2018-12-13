package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.TeacherContentPic;

public interface TeacherContentPicService {
	
	void add(TeacherContentPic teacherContentPic);
	// 根据id删除图片
	void batchDeletePic(Integer[] ids);
	/**
	 * 根据id删除记录
	 * @param id
	 */
	void delectById(Integer id);
	
	TeacherContentPic selectPicById(Integer id);
	
	List<TeacherContentPic> SelectPicByTeacherId(Integer teacherId);
}
