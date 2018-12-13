package com.hxbd.clp.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.hxbd.clp.java.ANote;

public class Notice implements Serializable{
	
	private static final long serialVersionUID = -9167268771419297556L;

	@ANote(value = "公告ID")
	private Integer id;
	
	@ANote(value = "标题")
	private String title;
	
	@ANote(value = "内容")
	private String content;
	
	@ANote(value = "创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date releaseTime;
	
	@ANote(value = "课程ID")
	private Integer courseId;

	
	public Notice() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	@Override
	public String toString() {
		return "Notice [id=" + id + ", title=" + title + ", content=" + content + ", releaseTime=" + releaseTime
				+ ", courseId=" + courseId + "]";
	}
	
	
}
