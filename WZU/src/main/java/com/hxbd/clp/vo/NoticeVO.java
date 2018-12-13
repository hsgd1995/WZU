package com.hxbd.clp.vo;


import com.hxbd.clp.domain.Notice;

/**
 * @author hxcl
 *
 * 2018年3月29日下午3:01:18
 *
 */
public class NoticeVO extends Notice{

	private String courseName;
	private String courseNo;
	

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

	
}
