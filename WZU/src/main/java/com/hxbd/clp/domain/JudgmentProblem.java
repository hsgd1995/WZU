package com.hxbd.clp.domain;

import java.io.Serializable;

/**
 * 判断题
 * @author Administrator
 *
 */
public class JudgmentProblem implements Serializable{

	private static final long serialVersionUID = -8435377828493657784L;
	
	private Integer id;
	private String title;//题目
	private Integer answer;//答案
	private String jieshi; // 解释 
	private Integer courseVideoId;	//课程视频ID 
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
	public Integer getAnswer() {
		return answer;
	}
	public void setAnswer(Integer answer) {
		this.answer = answer;
	}
	public String getJieshi() {
		return jieshi;
	}
	public void setJieshi(String jieshi) {
		this.jieshi = jieshi;
	}
	public Integer getCourseVideoId() {
		return courseVideoId;
	}
	public void setCourseVideoId(Integer courseVideoId) {
		this.courseVideoId = courseVideoId;
	}
}
