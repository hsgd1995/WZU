package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.CourseDetails;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * @author hxcl
 *
 * 2018年3月27日下午4:20:25
 *
 */
public interface CourseDetailsService {

	// 根据ID查询课程详情
	CourseDetails selectById(Integer id);
	// 分页查询课程详情
	PageModel<CourseDetails> findCourseDetails(CourseDetails courseDetails , PageModel<CourseDetails> pageModel);
	// 查询课程详情列表
	List<CourseDetails> getCourseDetailsList();
	// 保存课程详情
	void saveCourseDetails(CourseDetails courseDetails);
	// 更新课程详情
	void updateCourseDetails(CourseDetails courseDetails);
	// 根据id数组删除课程详情
	void batchDelCourseDetails(Integer[] ids);
}
