package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import com.hxbd.clp.domain.CourseAndTeacher;
import com.hxbd.clp.domain.MessageAndUser;
import com.hxbd.clp.domain.Teacher;
import com.hxbd.clp.utils.common.RasConstants;

public class CourseAndTeacherDynaSqlProvider {
	
	public String saveCourseAndTeacher(Integer teacherId,Integer courseId){
		return new SQL(){
			{
				INSERT_INTO(RasConstants.COURSEANDTEACHERTABLE);
					if(teacherId != null && !teacherId.equals("")){
						VALUES("teacher_id", "" + teacherId);
					}
					if(courseId != null && !courseId.equals("")){
						VALUES("course_id", "" + courseId);
					}
			}
		}.toString();
		
	}
	
	public String updateCourseAndTeacher(CourseAndTeacher courseAndTeacher) {
		return new SQL(){
			{
				if(courseAndTeacher != null){
					UPDATE(RasConstants.COURSEANDTEACHERTABLE);
					SET("teacher_id = #{teacherId}");
					SET("course_id = #{courseId}");
					WHERE(" id = #{id} ");
				}
			}
		}.toString();
	}
	
	
	public String countCourseAndTeacher(Map<String, Object> params) {
		String sql =  new SQL(){
			{
				CourseAndTeacher courseAndTeacher = (CourseAndTeacher) params.get("courseAndTeacher");
				SELECT("count(*)");
				String dynaSql =RasConstants.COURSEANDTEACHERTABLE+ " c ";
				if(courseAndTeacher != null){
					if(courseAndTeacher.getTeacher() != null && (!StringUtils.isEmpty(courseAndTeacher.getTeacher().getTeacherNo()) || !StringUtils.isEmpty(courseAndTeacher.getTeacher().getTeacherName())) ){
						dynaSql += " , " + RasConstants.TEACHERTABLE+" t ";
					}
					if(courseAndTeacher.getCourse() != null && !StringUtils.isEmpty(courseAndTeacher.getCourse().getCourseName())){
						dynaSql += " , " + RasConstants.COURSETABLE+" c2 ";
					}
				}
				FROM(dynaSql);
				if(courseAndTeacher != null){
					if(courseAndTeacher.getTeacher() != null && courseAndTeacher.getTeacher().getId() != null && !courseAndTeacher.getTeacher().getId().equals("")  && courseAndTeacher.getTeacher().getId() != 0){
						WHERE(" teacher_id like #{courseAndTeacher.teacher.teacherId}");
					}
					if(courseAndTeacher.getCourse() != null && courseAndTeacher.getCourse().getId() != null && !courseAndTeacher.getCourse().getId().equals("") && courseAndTeacher.getCourse().getId() != 0){
						WHERE(" course_id like #{courseAndTeacher.course.courseId}");
					}
					if(courseAndTeacher.getTeacher() != null && courseAndTeacher.getTeacher().getTeacherNo() != null && !courseAndTeacher.getTeacher().getTeacherNo().equals("")){
						WHERE(" c.teacher_id = t.id and t.teacher_no like CONCAT('%',#{courseAndTeacher.teacher.teacherNo},'%')  ");
					}
					if(courseAndTeacher.getTeacher() != null && courseAndTeacher.getTeacher().getTeacherName() != null && !courseAndTeacher.getTeacher().getTeacherName().equals("")){
						WHERE(" c.teacher_id = t.id and t.teacher_name like CONCAT('%',#{courseAndTeacher.teacher.teacherName},'%')  ");
					}
					if(courseAndTeacher.getCourse() != null && courseAndTeacher.getCourse().getCourseName() != null && !courseAndTeacher.getCourse().getCourseName().equals("")){
						WHERE(" c.course_id = c2.id and c2.course_name like CONCAT('%',#{courseAndTeacher.course.courseName},'%')  ");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}
	
	
	public String selectByParma(Map<String, Object> params) {
		String sql =  new SQL(){
			{
				CourseAndTeacher courseAndTeacher = (CourseAndTeacher) params.get("courseAndTeacher");
				SELECT("c.*");
				String dynaSql = RasConstants.COURSEANDTEACHERTABLE + " c ";
				if(courseAndTeacher != null){
					if(courseAndTeacher.getTeacher() != null && (!StringUtils.isEmpty(courseAndTeacher.getTeacher().getTeacherNo()) || !StringUtils.isEmpty(courseAndTeacher.getTeacher().getTeacherName()))){
						dynaSql += " , " + RasConstants.TEACHERTABLE+" t ";
					}
					if(courseAndTeacher.getCourse() != null && !StringUtils.isEmpty(courseAndTeacher.getCourse().getCourseName())){
						dynaSql += " , " + RasConstants.COURSETABLE+" c2 ";
					}
				}
				FROM(dynaSql);
				ORDER_BY("id desc");
				if(courseAndTeacher != null){
					if(courseAndTeacher.getTeacher() != null && courseAndTeacher.getTeacher().getId() != null && !courseAndTeacher.getTeacher().getId().equals("")  && courseAndTeacher.getTeacher().getId() != 0){
						WHERE(" teacher_id like #{courseAndTeacher.teacher.teacherId}");
					}
					if(courseAndTeacher.getCourse() != null && courseAndTeacher.getCourse().getId() != null && !courseAndTeacher.getCourse().getId().equals("") && courseAndTeacher.getCourse().getId() != 0){
						WHERE(" course_id like #{courseAndTeacher.course.courseId}");
					}
					if(courseAndTeacher.getTeacher() != null && courseAndTeacher.getTeacher().getTeacherNo() != null && !courseAndTeacher.getTeacher().getTeacherNo().equals("")){
						WHERE(" c.teacher_id = t.id and t.teacher_no like CONCAT('%',#{courseAndTeacher.teacher.teacherNo},'%')  ");
					}
					if(courseAndTeacher.getTeacher() != null && courseAndTeacher.getTeacher().getTeacherName() != null && !courseAndTeacher.getTeacher().getTeacherName().equals("")){
						WHERE(" c.teacher_id = t.id and t.teacher_name like CONCAT('%',#{courseAndTeacher.teacher.teacherName},'%')  ");
					}
					if(courseAndTeacher.getCourse() != null && courseAndTeacher.getCourse().getCourseName() != null && !courseAndTeacher.getCourse().getCourseName().equals("")){
						WHERE(" c.course_id = c2.id and c2.course_name like CONCAT('%',#{courseAndTeacher.course.courseName},'%')  ");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		System.err.println(sql);
		return sql;
	}
	
	
	public String batchDel(Map<String, Integer[]> ids){
		return new SQL(){
			{
				if(ids != null && ids.get("ids") != null){
					DELETE_FROM(RasConstants.COURSEANDTEACHERTABLE);
					String inConditions = "id in (";
					Integer[] idsArr = ids.get("ids");
					for (int index = 0; index < idsArr.length; index++) {
						inConditions += idsArr[index];
						if(index != idsArr.length - 1){
							inConditions += ",";
						}
					}
					inConditions +=") ";
					WHERE(inConditions);
				}
			}
		}.toString();
	}
}

