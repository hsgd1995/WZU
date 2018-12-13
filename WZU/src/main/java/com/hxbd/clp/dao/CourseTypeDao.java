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

import com.hxbd.clp.dao.provider.CourseTypeDynaSqlProvider;
import com.hxbd.clp.domain.CourseType;
import com.hxbd.clp.utils.common.RasConstants;

public interface CourseTypeDao {
	
	// 根据id查询课程类型
	@Select("select * from " + RasConstants.COURSETYPETABLE + " where id = #{id}")
	@Results(value = {@Result(column = "type_name" , property = "typeName" , javaType = java.lang.String.class)})
	CourseType selectCourseTypeById(Integer id);
	
	// 根据课程类型名查询课程类型
	@Select("select * from " + RasConstants.COURSETYPETABLE + " where type_name = #{typeName}")
	@Results(value = {@Result(column = "teacher_name" , property = "teacherName" , javaType = java.lang.String.class)})
	CourseType selectCourseTypeByName(String typeName);
	
	// 查询课程类型列表
	@SelectProvider(type = CourseTypeDynaSqlProvider.class , method = "selectByParma")
	@Results(value = {@Result(column = "type_name" , property = "typeName" , javaType = java.lang.String.class)})
	List<CourseType> selectByPage(Map<String, Object> params);
	
	// 查询课程类型总数
	@SelectProvider(type = CourseTypeDynaSqlProvider.class , method = "countCourseType")
	Integer count(Map<String, Object> params);
	
	// 保存课程类型
	@InsertProvider(type = CourseTypeDynaSqlProvider.class , method = "addCourseType")
	void saveCourseType(CourseType courseType);
	
	// 更新课程类型
	@UpdateProvider(type = CourseTypeDynaSqlProvider.class , method = "updateCourseType")
	void updateCourseType(CourseType courseType);
	
	// 批量删除课程类型
	@DeleteProvider(type = CourseTypeDynaSqlProvider.class , method = "batchDel")
	void batchDelCourseType(Map<String, Integer[]> map);
	
	// 查询课程类型list
	@Select("select * from " + RasConstants.COURSETYPETABLE +" ORDER BY id DESC")
	@Results(value = {@Result(column = "id" , property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "type_name" , property = "typeName" , javaType = java.lang.String.class)})
	List<CourseType> findList();
}
