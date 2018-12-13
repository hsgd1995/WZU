package com.hxbd.clp.domain;

import java.io.Serializable;

public class Process implements Serializable{
	private Integer id;
	private Integer userId;
	private Integer courseId;
	private Integer courseVideoId;
	private Double process;//视频当前进度
	private Integer isFinish;//是否完成学习视频，0：未完成，1：完成
	
	public Process() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Double getProcess() {
		return process;
	}

	public void setProcess(Double process) {
		this.process = process;
	}
	
	

	public Integer getCourseVideoId() {
		return courseVideoId;
	}

	public void setCourseVideoId(Integer courseVideoId) {
		this.courseVideoId = courseVideoId;
	}

	public Integer getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}

	@Override
	public String toString() {
		return "Process [id=" + id + ", userId=" + userId + ", courseId=" + courseId + ", courseVideoId="
				+ courseVideoId + ", process=" + process + ", isFinish=" + isFinish + "]";
	}

	

	
	
	
}
