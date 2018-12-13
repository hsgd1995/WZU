package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.CourseAndUser;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * @author hxcl
 *
 *         2018年3月16日下午3:09:18
 *
 */
public class CourseAndUserDynaSqlProvider {

	public String saveCourseAndUser(CourseAndUser courseAndUser) {
		return new SQL() {
			{
				INSERT_INTO(RasConstants.COURSEANDUSERTABLE);
				if (courseAndUser != null) {
					if (courseAndUser.getCourseId() != null && !courseAndUser.getJoincourse().equals("")) {
						VALUES("course_id", "#{courseId}");
					}
					if (courseAndUser.getUserId() != null && !courseAndUser.getJoincourse().equals("")) {
						VALUES("user_id", "#{userId}");
					}
					if (courseAndUser.getJoincourse() != null && !courseAndUser.getJoincourse().equals("")) {
						VALUES("joincourse", "#{joincourse}");
					}
					if (courseAndUser.getCollect() != null && !courseAndUser.getJoincourse().equals("")) {
						VALUES("collect", "#{collect}");
					}

				}
			}
		}.toString();

	}

	public String updateCourseAndUser(CourseAndUser courseAndUser) {
		return new SQL() {
			{
				if (courseAndUser != null) {
					UPDATE(RasConstants.COURSEANDUSERTABLE);
					SET("joincourse = #{joincourse}");
					SET("collect = #{collect}");
					WHERE(" id = #{id} ");
				}
			}
		}.toString();
	}

	public String countCourseAndUser(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("count(*)");
				FROM(RasConstants.COURSEANDUSERTABLE);
				CourseAndUser courseAndUser = (CourseAndUser) params.get("courseAndUser");
				if (courseAndUser != null) {
					if (courseAndUser.getCourseId() != null && !courseAndUser.getCourseId().equals("")
							&& courseAndUser.getCourseId() != 0) {
						WHERE(" course_id like #{courseAndUser.courseId}");
					}
					if (courseAndUser.getUserId() != null && !courseAndUser.getUserId().equals("")
							&& courseAndUser.getUserId() != 0) {
						WHERE("user_id like #{courseAndUser.userId}");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	public String selectByParent(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(RasConstants.COURSEANDUSERTABLE);
				CourseAndUser courseAndUser = (CourseAndUser) params.get("courseAndUser");
				if (courseAndUser != null) {
					if (courseAndUser.getCourseId() != null && !courseAndUser.getCourseId().equals("")
							&& courseAndUser.getCourseId() != 0) {
						WHERE(" course_id like #{courseAndUser.courseId}");
					}
					if (courseAndUser.getUserId() != null && !courseAndUser.getUserId().equals("")
							&& courseAndUser.getUserId() != 0) {
						WHERE("user_id like #{courseAndUser.userId}");
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

	public String batchDelCourseAndUser(Map<String, Integer[]> ids) {
		return new SQL(){
			{
				if(ids != null && ids.get("ids") != null){
					DELETE_FROM(RasConstants.COURSEANDUSERTABLE);
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
