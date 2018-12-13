package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.dao.provider.CourseDetailsDynaSqlProvider;
import com.hxbd.clp.domain.CourseDetails;
import com.hxbd.clp.utils.common.RasConstants;

public interface CourseDetailsDao {
	
	// 根据id查询课程详情
	@Select("select * from " + RasConstants.COURSEDETAILSTABLE + " where id = #{id}")
	@Results(value = {@Result(column = "course_period" , property = "coursePeriod" , javaType = java.lang.String.class),
			@Result(column = "expend_time" , property = "expendTime" , javaType = java.lang.String.class),
			@Result(column = "course_language" , property = "courseLanguage" , javaType = java.lang.String.class),
			@Result(column = "subtitle_type" , property = "subtitleType" , javaType = java.lang.String.class),
			@Result(column = "course_pic" , property = "coursePic" , javaType = java.lang.String.class)})
	CourseDetails selectCourseDetailsById(Integer id);
	
	
	// 查询课程详情列表
	@SelectProvider(type = CourseDetailsDynaSqlProvider.class , method = "selectByParma")
	@Results(value = {@Result(column = "course_period" , property = "coursePeriod" , javaType = java.lang.String.class),
			@Result(column = "expend_time" , property = "expendTime" , javaType = java.lang.String.class),
			@Result(column = "course_language" , property = "courseLanguage" , javaType = java.lang.String.class),
			@Result(column = "subtitle_type" , property = "subtitleType" , javaType = java.lang.String.class),
			@Result(column = "course_pic" , property = "coursePic" , javaType = java.lang.String.class)})
	List<CourseDetails> selectCourseDetailsList(Map<String, Object> params);
	
	// 查询课程详情总数
	@SelectProvider(type = CourseDetailsDynaSqlProvider.class , method = "countCourseDetails")
	Integer count(Map<String, Object> params);
	
	// 保存课程详情
	@InsertProvider(type = CourseDetailsDynaSqlProvider.class , method = "addCourseDetails")
	void saveCourseDetails(CourseDetails courseDetails);
	
	// 更新课程详情
	@UpdateProvider(type = CourseDetailsDynaSqlProvider.class , method = "updateCourseDetails")
	void updateCourseDetails(CourseDetails courseDetails);
	
	// 批量删除课程详情
	@DeleteProvider(type = CourseDetailsDynaSqlProvider.class , method = "batchDel")
	void batchDelCourseDetails(Map<String, Integer[]> map);
}
