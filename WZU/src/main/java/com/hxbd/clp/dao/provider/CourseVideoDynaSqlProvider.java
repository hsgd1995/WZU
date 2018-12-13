package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.utils.common.RasConstants;
import com.hxbd.clp.vo.CourseVideoVO;

public class CourseVideoDynaSqlProvider {

	public String selectByParma(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(RasConstants.COURSEVIDEOTABLE);
				ORDER_BY(" id DESC");
				CourseVideo courseVideo = (CourseVideo) params.get("courseVideo");
				if (courseVideo != null) {
					if (courseVideo.getName() != null && !courseVideo.getName().equals("")) {
						WHERE(" name like CONCAT('%',#{courseVideo.name},'%')");
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
				FROM(RasConstants.COURSEVIDEOTABLE);
				ORDER_BY(" id DESC");
				CourseVideo courseVideo = (CourseVideo) params.get("courseVideo");
				if (courseVideo != null) {
					if (courseVideo.getCourseId() != null && !courseVideo.getCourseId().equals("")
							&& courseVideo.getCourseId() != 0) {
						WHERE(" course_id like (#{courseVideo.courseId})");
					}
					// System.out.println("parent:" + courseVideo.getParent());
					if (courseVideo.getParent() != null && !courseVideo.getParent().equals("")) {
						if (courseVideo.getParent() == -1) {
							WHERE(" parent != 0");
						} else {
							WHERE(" parent = #{courseVideo.parent}");
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

	public String countCourseVideo(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("count(*)");
				FROM(RasConstants.COURSEVIDEOTABLE);
				CourseVideo courseVideo = (CourseVideo) params.get("courseVideo");
				/* WHERE(" parent = 0"); */
				if (courseVideo != null) {
					if (courseVideo.getCourseId() != null && !courseVideo.getCourseId().equals("")
							&& courseVideo.getCourseId() != 0) {
						WHERE(" course_id like #{courseVideo.courseId}");
					}
					if (courseVideo.getParent() != null && !courseVideo.getParent().equals("")) {
						WHERE(" parent like #{courseVideo.parent}");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	public String addCourseVideo(CourseVideo courseVideo) {
		return new SQL() {
			{
				if (courseVideo != null) {
					INSERT_INTO(RasConstants.COURSEVIDEOTABLE);
					if (courseVideo.getName() != null && !courseVideo.getName().equals("")) {
						VALUES("name", "#{name}");
					}
					if (courseVideo.getUrl() != null && !courseVideo.getUrl().equals("")) {
						VALUES("url", "#{url}");
					}
					if (courseVideo.getParent() != null && !courseVideo.getParent().equals("")) {
						VALUES("parent", "#{parent}");
					}
					if (courseVideo.getCourseId() != 0) {
						VALUES("course_id", "#{courseId}");
					}
					VALUES("remark", "#{remark}");
				}
			}
		}.toString();
	}

	public String updateCourseVideo(CourseVideo courseVideo) {
		System.err.println(courseVideo);
		String sql =  new SQL() {
			{
				if (courseVideo != null) {
					UPDATE(RasConstants.COURSEVIDEOTABLE);
					if (courseVideo.getName() != null && !courseVideo.getName().equals("")) {
						SET("name = #{name}");
					}
					if (courseVideo.getUrl() != null && !courseVideo.getUrl().equals("")) {
						SET("url = #{url}");
					}
					if (courseVideo.getParent() != null && !courseVideo.getParent().equals("")) {
						SET("parent = #{parent}");
					}
					if (courseVideo.getCourseId() != 0) {
						SET("course_id = #{courseId}");
					}
					SET("remark = #{remark}");
					WHERE(" id = #{id} ");
				}
			}
		}.toString();
		System.err.println(sql);
		return sql;
	}

	public String batchDelCourseVideo(Map<String, Integer[]> ids) {
		return new SQL() {
			{
				if (ids != null && ids.get("ids") != null) {
					DELETE_FROM(RasConstants.COURSEVIDEOTABLE);
					String inConditions = "id in (";
					Integer[] idsArr = ids.get("ids");
					for (int index = 0; index < idsArr.length; index++) {
						inConditions += idsArr[index];
						if (index != idsArr.length - 1) {
							inConditions += ",";
						}
					}
					inConditions += ") ";
					WHERE(inConditions);
				}
			}
		}.toString();
	}

	public String countCourseVideoVO(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("count(*)");
				FROM(RasConstants.COURSEVIDEOTABLE);
				CourseVideoVO courseVideoVO = (CourseVideoVO) params.get("courseVideoVO");
				/* WHERE(" parent = 0"); */
				if (courseVideoVO != null) {
					if (courseVideoVO.getCourseName() != null && !courseVideoVO.getCourseName().equals("")) {
						String sql2 = new SQL() {
							{
								SELECT("id");
								FROM(RasConstants.COURSETABLE);
								WHERE(" course_name like CONCAT('%',#{courseVideoVO.courseName},'%')");
							}
						}.toString();
						WHERE(" course_id IN (" + sql2 + ")");
					}
					if (courseVideoVO.getParentName() != null && !courseVideoVO.getParentName().equals("")) {
						WHERE(" name like CONCAT('%',#{courseVideoVO.parentName},'%')");
					}
					if (courseVideoVO.getParent() != null && !courseVideoVO.getParent().equals("")) {
						if (courseVideoVO.getParent() == -1) {
							WHERE(" parent != 0");
						} else {
							WHERE(" parent = #{courseVideoVO.parent}");
						}
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

	public String selectByParentVO(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(RasConstants.COURSEVIDEOTABLE);
				ORDER_BY(" id DESC");
				CourseVideoVO courseVideoVO = (CourseVideoVO) params.get("courseVideoVO");
				/* WHERE(" parent = 0"); */
				if (courseVideoVO != null) {
					if (courseVideoVO.getCourseName() != null && !courseVideoVO.getCourseName().equals("")) {
						String sql2 = new SQL() {
							{
								SELECT("id");
								FROM(RasConstants.COURSETABLE);
								WHERE(" course_name like CONCAT('%',#{courseVideoVO.courseName},'%')");
							}
						}.toString();
						WHERE(" course_id IN (" + sql2 + ")");
					}
					if (courseVideoVO.getParentName() != null && !courseVideoVO.getParentName().equals("")) {
						WHERE(" name like CONCAT('%',#{courseVideoVO.parentName},'%')");
					}
					if (courseVideoVO.getParent() != null && !courseVideoVO.getParent().equals("")) {
						if (courseVideoVO.getParent() == -1) {
							WHERE(" parent != 0");
						} else {
							WHERE(" parent = #{courseVideoVO.parent}");
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
	
	public String slectByCourseNo(String courseNo) {
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(RasConstants.COURSEVIDEOTABLE);
				ORDER_BY(" id DESC");
					if ( courseNo != null && !courseNo.equals("")) {
						String sql2 = new SQL() {
							{
								SELECT("id");
								FROM(RasConstants.COURSETABLE);
								WHERE(" course_no = "+ courseNo );
							}
						}.toString();
						WHERE(" parent = 0  and course_id IN (" + sql2 + ")");
				}
			}
		}.toString();
		return sql;
	}

}
