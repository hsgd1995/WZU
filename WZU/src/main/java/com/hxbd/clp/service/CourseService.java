package com.hxbd.clp.service;

import java.util.List;
import java.util.Map;

import com.hxbd.clp.Form.CourseForm;
import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.utils.tag.PageModel;

public interface CourseService {
	
	// 根据id查询课程
	Course selectById(Integer id);
	// 根据课程编号查询课程
	Course selectByCourseId(String courseId);
	// 分页查询课程
	PageModel<Course> findCourse(Course course , PageModel<Course> pageModel);
	// 获取课程列表数据
	List<Course> getCourseList();
	// 新增课程
	void addCourse(CourseForm courseForm);
	// 更新课程
	void updateCourse(CourseForm courseForm);
	// 根据课程编号删除课程
	void batchDelCourse(Integer[] ids);
	// 查询最新8条数据
	List<Course> findLimitEight();
	// 查询map
	Map<Integer, Course> findAllMap();
	
	//查询需要查询的课程数量
	Integer findCourseSort(Course course);
	//根据编号查询课程
	Course selectByTeacherCourseNo(String courseNo);
	
	
}	
