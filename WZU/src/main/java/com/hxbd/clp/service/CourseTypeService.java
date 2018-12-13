package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.CourseType;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * @author hxcl
 *
 * 2018年3月27日上午10:56:57
 *
 */
public interface CourseTypeService {

	// 根据ID查询课程类型
	CourseType selectById(Integer id);
	// 根据类型名字查询课程类型
	CourseType selectByTypeName(String typeName);
	// 分页查询课程类型
	PageModel<CourseType> findCourseType(CourseType courseType , PageModel<CourseType> pageModel);
	// 查询课程类型列表
	List<CourseType> getCourseTypeList();
	// 保存课程类型
	void saveCourseType(CourseType courseType);
	// 更新课程类型
	void updateCourseType(CourseType courseType);
	// 根据id数组删除课程类型
	void batchDelCoursetype(Integer[] ids);
	//查询课程类型list
	List<CourseType> findList();
}
