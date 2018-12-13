package com.hxbd.clp.service;


import java.util.Map;

import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.CourseAndTeacher;
import com.hxbd.clp.domain.CourseAndUser;
import com.hxbd.clp.domain.Teacher;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.CourseAndTeacherVO;

/**
 * @author hxcl
 *
 * 2018年3月23日上午10:50:27
 *
 */
public interface CourseAndTeacherService {
	
	// 保存
	void saveCourseAndTeacher(CourseAndTeacher courseAndTeacher);
	// 修改
	void editCourseAndTeacher(CourseAndTeacher courseAndTeacher); 
	// 批量删除教师
	void batchCourseAndTeacher(Integer[] ids);
	// 根据两个参数去查询
	CourseAndUser findByteacherIdAndCourseId(Integer teacherId, Integer courseId);
	//根据两个id去查数据
	CourseAndTeacher findCourseAndTeacher(Integer teacherId, Integer courseId);
	// 映射查询
	PageModel<CourseAndTeacher> findCourseAndTeacher(CourseAndTeacher courseAndTeacher,
			PageModel<CourseAndTeacher> pageModel);


}
