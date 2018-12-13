package com.hxbd.clp.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.dao.provider.CourseAndTeacherDynaSqlProvider;
import com.hxbd.clp.domain.CourseAndTeacher;
import com.hxbd.clp.utils.common.RasConstants;


public interface CourseAndTeacherDao {
	
	// 新增
	@InsertProvider(type = CourseAndTeacherDynaSqlProvider.class ,method="saveCourseAndTeacher")
	void save(Integer teacherId,Integer courseId);
	// 更新
	@UpdateProvider(type = CourseAndTeacherDynaSqlProvider.class, method = "updateCourseAndTeacher")
	void edit(CourseAndTeacher courseAndTeacher);

	// 根据两个参数去查询 teacherId、 courseId
	@Select("select * from " + RasConstants.COURSEANDTEACHERTABLE + " where teacher_id = #{teacherId} and course_id = #{courseId}")
	@Results(value = {@Result(column = "id" , property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "course_id" , property = "courseId" , javaType = java.lang.Integer.class),
			@Result(column = "teacher_id" , property = "teacherId", javaType = java.lang.Integer.class)})
	CourseAndTeacher findByTeacherIdAndCourseId(@Param("teacherId") Integer teacherId,@Param("courseId")Integer courseId);	
	
	
	// 查询总数
	@SelectProvider(type = CourseAndTeacherDynaSqlProvider.class , method = "countCourseAndTeacher")
	Integer count(Map<String, Object> params);
	
	
	// 查询列表
	@SelectProvider(type = CourseAndTeacherDynaSqlProvider.class , method = "selectByParma")
	@Results(value = {@Result(column = "id", property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "teacher_id" , property = "teacher" , javaType = java.lang.Integer.class,
					one=@One(select="com.hxbd.clp.dao.TeacherDao.selectTeacherById")),
			@Result(column = "course_id" , property = "course" , javaType = java.lang.Integer.class,
					one=@One(select="com.hxbd.clp.dao.CourseDao.selectById"))
	})
	List<CourseAndTeacher> selectCourseAndTeacherList(Map<String, Object> params);
	
	
	// 批量删除教师课程关系
	@DeleteProvider(type = CourseAndTeacherDynaSqlProvider.class , method = "batchDel")
	void batchDelCourseAndTeacher(Map<String, Integer[]> map);

	
}

