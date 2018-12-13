package com.hxbd.clp.domain;

import java.io.Serializable;

public class CourseAndTeacher implements Serializable{

	private static final long serialVersionUID = 7790754647330124520L;
	
	private Integer id;
	private Course course;	//课程
	private Teacher teacher;	//教师
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	@Override
	public String toString() {
		return "CourseAndTeacher [id=" + id + ", course=" + course + ", teacher=" + teacher + "]";
	}
	
	
	
}
