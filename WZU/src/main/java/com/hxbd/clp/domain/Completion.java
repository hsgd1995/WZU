package com.hxbd.clp.domain;

import java.io.Serializable;

/**
 * 填空题
 * @author Administrator
 *
 */
public class Completion implements Serializable{

	private static final long serialVersionUID = 4936292611087731533L;
	
	private Integer id;
	private Integer courseVideoId;//课程视频ID
	private String title;//题目
	private String answer;//答案
	private String jieshi;//解释
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCourseVideoId() {
		return courseVideoId;
	}
	public void setCourseVideoId(Integer courseVideoId) {
		this.courseVideoId = courseVideoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getJieshi() {
		return jieshi;
	}
	public void setJieshi(String jieshi) {
		this.jieshi = jieshi;
	}
	
}
