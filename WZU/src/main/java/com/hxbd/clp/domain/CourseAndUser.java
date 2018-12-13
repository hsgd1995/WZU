package com.hxbd.clp.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author hxcl
 *
 * 2018年3月16日下午2:50:23
 *
 */
public class CourseAndUser implements Serializable{
	
	private static final long serialVersionUID = 4726003089403677130L;
	
	private Integer id;
	private Integer courseId;	//课程ID
	private Integer userId;	//用户ID
	private Integer joincourse;	//加入课程    0--加入课程  1--进入学习
	private Integer collect;	//用户收藏  0--未收藏  1--收藏
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getJoincourse() {
		return joincourse;
	}
	public void setJoincourse(Integer joincourse) {
		this.joincourse = joincourse;
	}
	public Integer getCollect() {
		return collect;
	}
	public void setCollect(Integer collect) {
		this.collect = collect;
	}
	
}
