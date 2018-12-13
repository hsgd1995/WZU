package com.hxbd.clp.vo;

import com.hxbd.clp.domain.CourseAndUser;

/**
 * @author hxcl
 *
 * 2018年3月23日下午5:01:39
 *
 */
public class CourseAndUserVO extends CourseAndUser{

	private String user; //用户名字
	private Integer userId;//用户ID
	private String course;//课程名字

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}

	
	
}
