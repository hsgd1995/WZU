package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.CourseDetails;
import com.hxbd.clp.utils.common.RasConstants;

public class CourseDetailsDynaSqlProvider {
	
	public String selectByParma(Map<String, Object> params) {
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(RasConstants.COURSEDETAILSTABLE);
				ORDER_BY("id desc");
				CourseDetails courseDetails  = (CourseDetails) params.get("courseDetails");
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	public String countCourseDetails(Map<String, Object> params) {
		String sql =  new SQL(){
			{
				SELECT("count(*)");
				FROM(RasConstants.COURSEDETAILSTABLE);
				CourseDetails courseDetails  = (CourseDetails) params.get("courseDetails");
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	public String addCourseDetails(CourseDetails courseDetails) {
		return new SQL(){
			{
				if(courseDetails != null){
					INSERT_INTO(RasConstants.COURSEDETAILSTABLE);
					if(courseDetails.getCoursePeriod() != null && !courseDetails.getCoursePeriod().equals("")){
						VALUES("course_period", "#{coursePeriod}");
					}
					if(courseDetails.getExpendTime() != null && !courseDetails.getExpendTime().equals("")){
						VALUES("expend_time", "#{expendTime}");
					}
					if(courseDetails.getCourseLanguage() != null && !courseDetails.getCourseLanguage().equals("")){
						VALUES("course_language", "#{courseLanguage}");
					}
					if(courseDetails.getSubtitleType() != null && !courseDetails.getSubtitleType().equals("")){
						VALUES("subtitle_type", "#{subtitleType}");
					}
					if(courseDetails.getCoursePic() != null && !courseDetails.getCoursePic().equals("")){
						VALUES("course_pic", "#{coursePic}");
					}
					VALUES("remark", "#{remark}");
				}
			}
		}.toString();
	}

	public String updateCourseDetails(CourseDetails courseDetails) {
		return new SQL(){
			{
				if(courseDetails != null){
					UPDATE(RasConstants.COURSEDETAILSTABLE);
					if(courseDetails.getCoursePeriod() != null && !courseDetails.getCoursePeriod().equals("")){
						SET("course_period = #{coursePeriod}");
					}
					if(courseDetails.getExpendTime() != null && !courseDetails.getExpendTime().equals("")){
						SET("expend_time = #{expendTime}");
					}
					if(courseDetails.getCourseLanguage() != null && !courseDetails.getCourseLanguage().equals("")){
						SET("course_language = #{courseLanguage}");
					}
					if(courseDetails.getSubtitleType() != null && !courseDetails.getSubtitleType().equals("")){
						SET("subtitle_type = #{subtitleType}");
					}
					//System.out.println("更新课程详情，图片路径："+courseDetails.getCoursePic());
					if(courseDetails.getCoursePic() != null && !courseDetails.getCoursePic().equals("")){
						SET("course_pic = #{coursePic}");
					}
					SET("remark = #{remark}");
					WHERE(" id = #{id}");
				}
			}
		}.toString();
	}
	
	public String batchDel(Map<String, Integer[]> ids){
		return new SQL(){
			{
				if(ids != null && ids.get("ids") != null){
					DELETE_FROM(RasConstants.COURSEDETAILSTABLE);
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
