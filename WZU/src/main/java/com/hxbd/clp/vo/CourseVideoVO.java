package com.hxbd.clp.vo;

import com.hxbd.clp.domain.CourseVideo;

/**
 * @author hxcl
 *
 * 2018年4月10日下午3:36:51
 *
 */
public class CourseVideoVO extends CourseVideo{

	private String courseNo;
	private String courseName;
	private String parentName;
	
	public String getCourseNo() {
		return courseNo;
	}
	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	
}
