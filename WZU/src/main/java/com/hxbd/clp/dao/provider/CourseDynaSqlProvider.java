package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.Form.CourseForm;
import com.hxbd.clp.domain.Course;
import com.hxbd.clp.utils.common.RasConstants;

public class CourseDynaSqlProvider {
	
	public String selectWithParam(Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(RasConstants.COURSETABLE);
				ORDER_BY("id desc");
				Course course = (Course) params.get("course");
				//System.out.println("course:"+course);
				//Integer[] ids = (Integer[]) params.get("ids");
				if(course != null){
					if(course.getCourseName() != null && !course.getCourseName().equals("")){
						WHERE(" course_name like CONCAT('%',#{course.courseName},'%')");
					}
					if(course.getCourseNo() != null && !course.getCourseNo().equals("")){
						WHERE(" course_no like CONCAT('%',#{course.courseNo},'%')");
					}
					if(course.getOrfree()!=null && course.getOrfree() != 2 ){
						WHERE(" orfree like CONCAT('%',#{course.orfree},'%')");
					}
					if(course.getCourseTypeId() != null){
						WHERE(" course_type_id = #{course.courseTypeId}");
					}
					//System.out.println("dao-》selectWithParam->语言："+course.getCourseLanguage());
					if(course.getCourseLanguage()!=null&&course.getCourseLanguage()!=""){
						//选中多个语言
						if(course.getCourseLanguage().contains(",")){
							
							String[] language = course.getCourseLanguage().split(",");
							StringBuffer languageSql = new StringBuffer();
							String sql2 = "";
							
							for(int i=0;i<language.length;i++){
								if(i==0){
									languageSql.append("(");
								}
								languageSql.append(" course_language = ").append("\"").append(language[i]).append("\"") ;
								if(i!=language.length-1){
									languageSql.append(" or ");
								}else{
									languageSql.append(" ) ");
								}
							}
							System.out.println("动态languageSql:"+languageSql.toString());
							sql2 = languageSql.toString();
							//System.out.println("sql2:"+sql2);
							WHERE(sql2);
						}else{
							//只选择一门语言
							//System.out.println("一门语言:");
							WHERE(" course_language = #{course.courseLanguage} ");
						}
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		System.err.println("4-28:"+sql);
		return sql;
	}
	
	public String countCourse(Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("count(*)");
				FROM(RasConstants.COURSETABLE);
				Course course = (Course) params.get("course");
				//System.out.println("course:"+course);
				//Integer[] ids = (Integer[]) params.get("ids");
				if(course != null){
					if(course.getCourseName() != null && !course.getCourseName().equals("")){
						WHERE(" course_name like CONCAT('%',#{course.courseName},'%')");
					}
					if(course.getOrfree() != null && course.getOrfree() != 2){
						WHERE(" orfree like CONCAT('%',#{course.orfree},'%')");
					}
					if(course.getCourseTypeId() != null){
						WHERE(" course_type_id = #{course.courseTypeId}");
					}
					//System.out.println("dao-》countCourse->语言："+course.getCourseLanguage());
					if(course.getCourseLanguage()!=null&&course.getCourseLanguage()!=""){
						//选中多个语言
						if(course.getCourseLanguage().contains(",")){
							
							String[] language = course.getCourseLanguage().split(",");
							StringBuffer languageSql = new StringBuffer();
							String sql2 = "";
							for (String string : language) {
								languageSql.append(" course_language = ").append("\"").append(string).append("\"").append(" or") ;
							}
							//System.out.println("动态languageSql:"+languageSql.toString());
							sql2 = languageSql.toString().substring(0, languageSql.length()-2);
							//System.out.println("sql2:"+sql2);
							WHERE(sql2);
						}else{
							//只选择一门语言
							WHERE(" course_language = #{course.courseLanguage} ");
						}
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}
	
	public String saveCourse(CourseForm course){
		return new SQL(){
			{
				if(course != null){
					INSERT_INTO(RasConstants.COURSETABLE);
					if(course.getCourseNo() != null && !course.getCourseNo().equals("")){
						VALUES("course_no", "#{courseNo}");
					}
					if(course.getCourseName() != null && !course.getCourseName().equals("")){
						VALUES("course_name", "#{courseName}");
					}
					if(course.getIntroduce() != null && !course.getIntroduce().equals("")){
						VALUES("introduce", "#{introduce}");
					}
					if(course.getOutline() != null && !course.getOutline().equals("")){
						VALUES("outline", "#{outline}");
					}
					if(course.getExamine() != null && !course.getExamine().equals("")){
						VALUES("examine", "#{examine}");
					}
					if(course.getTeachingMateril() != null && !course.getTeachingMateril().equals("")){
						VALUES("teaching_material", "#{teachingMateril}");
					}
					if(course.getStudyStartTime() != null ){
						VALUES("study_start_time", "#{studyStartTime}");
					}
					if(course.getStudyEndTime() != null ){
						VALUES("study_end_time", "#{studyEndTime}");
					}
					if(course.getExamStartTime() != null ){
						VALUES("exam_start_time", "#{examStartTime}");
					}
					if(course.getStudyStartTime() != null ){
						VALUES("exam_end_time", "#{examEndTime}");
					}
					if(course.getCourseStatus() != null){
						VALUES("course_status","#{courseStatus}");
					}
					
					VALUES("orfree","#{orfree}");
					VALUES("course_type_id","#{courseTypeId}");
					VALUES("course_details_id","#{courseDetailsId}");
					VALUES("remark", "#{remark}");
					VALUES("course_period","#{coursePeriod}");
					VALUES("expend_time","#{expendTime}");
					VALUES("course_language","#{courseLanguage}");
					VALUES("subtitle_type","#{subtitleType}");
					VALUES("course_pic","#{coursePic}");
				}
			}
		}.toString();
	}
	
	public String updateCourse(CourseForm course){
		return new SQL(){
			{
				if(course != null){
					UPDATE(RasConstants.COURSETABLE);
					if(course.getCourseNo() != null && !course.getCourseNo().equals("")){
						SET("course_no = #{courseNo}");
					}
					if(course.getCourseName() != null && !course.getCourseName().equals("")){
						SET("course_name = #{courseName}");
					}
					if(course.getIntroduce() != null && !course.getIntroduce().equals("")){
						SET("introduce = #{introduce}");
					}
					if(course.getOutline() != null && !course.getOutline().equals("")){
						SET("outline = #{outline}");
					}
					if(course.getExamine() != null && !course.getExamine().equals("")){
						SET("examine = #{examine}");
					}
					if(course.getTeachingMateril() != null && !course.getTeachingMateril().equals("")){
						SET("teaching_material = #{teachingMateril}");
					}
					if(course.getStudyStartTime() != null ){
						SET("study_start_time = #{studyStartTime}");
					}
					if(course.getStudyEndTime() != null ){
						SET("study_end_time = #{studyEndTime}");
					}
					if(course.getExamStartTime() != null ){
						SET("exam_start_time = #{examStartTime}");
					}
					if(course.getStudyStartTime() != null ){
						SET("exam_end_time = #{examEndTime}");
					}
					if(course.getCourseStatus() != null){
						SET("course_status = #{courseStatus}");
					}
					SET("orfree = #{orfree}");
					SET("course_type_id = #{courseTypeId}");
					SET("course_period = #{coursePeriod}");
					SET("expend_time = #{expendTime}");
					SET("course_language = #{courseLanguage}");
					SET("subtitle_type = #{subtitleType}");
					SET("course_pic = #{coursePic}");
					SET("remark = #{remark}");
					WHERE(" id = #{id} ");
				}
			}
		}.toString();
	}
	
	public String batchDelete(Map<String, Integer[]> ids){
		return new SQL(){
			{
				if(ids != null && ids.get("ids") != null){
					DELETE_FROM(RasConstants.COURSETABLE);
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
	
	
	//根据用户ID查询相应的学习课程列表
	public String selectStudyCourseWithParam(Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(RasConstants.COURSETABLE);
				//Integer userId = (Integer)params.get("userId");
				WHERE(" id in (select course_id from course_and_user_table where user_id = #{userId} and joincourse = 1)");
				ORDER_BY("id desc");
				Course course = (Course) params.get("course");
				if(course != null){
					if(course.getCourseName() != null && !course.getCourseName().equals("")){
						WHERE(" course_name like CONCAT('%',#{course.courseName},'%')");
					}
				}
				int start = (Integer)params.get("start");
				if(start == 1){
					WHERE("CURDATE() >= study_end_time");
				} else {
					WHERE("CURDATE() >= study_start_time and CURDATE() <= study_end_time");
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		System.err.println(sql);
		return sql;
	}
	
	//根据用户ID查询相应的收藏课程列表
	public String selectCollectCourseWithParam(Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(RasConstants.COURSETABLE);
				//Integer userId = (Integer)params.get("userId");
				WHERE(" id in (select course_id from course_and_user_table where user_id = #{userId} and collect = 1)");
				ORDER_BY("id desc");
				Course course = (Course) params.get("course");
				if(course != null){
					if(course.getCourseName() != null && !course.getCourseName().equals("")){
						WHERE(" course_name like CONCAT('%',#{course.courseName},'%')");
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
	
	//根据用户id对应的学习课程查询总数
	public String countStudyCourse(Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("count(*)");
				FROM(RasConstants.COURSETABLE);
				//Integer userId = (Integer)params.get("userId");
				WHERE(" id in (select course_id from course_and_user_table where user_id = #{userId} and joincourse = 1)");
				Course course = (Course) params.get("course");
				if(course != null){
					if(course.getCourseName() != null && !course.getCourseName().equals("")){
						WHERE(" course_name like CONCAT('%',#{course.courseName},'%')");
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
	
	//根据用户id对应的收藏课程查询总数
	public String countCollectCourse(Map<String, Object> params){
		String sql =  new SQL(){
			{
				SELECT("count(*)");
				FROM(RasConstants.COURSETABLE);
				//Integer userId = (Integer)params.get("userId");
				WHERE(" id in (select course_id from course_and_user_table where user_id = #{userId} and collect = 1)");
				Course course = (Course) params.get("course");
				if(course != null){
					if(course.getCourseName() != null && !course.getCourseName().equals("")){
						WHERE(" course_name like CONCAT('%',#{course.courseName},'%')");
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
}
