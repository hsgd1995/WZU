package com.hxbd.clp.domain;

import java.io.Serializable;

public class CourseDetails implements Serializable{

	private static final long serialVersionUID = 3950982744500180672L;

	private Integer id;
	private String coursePeriod;	//学时
	private String expendTime;	//每周耗时
	private String courseLanguage;	//授课语言
	private String subtitleType;	//字幕类型
	private String coursePic;	//课程图片
	private String remark;	//备注

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCoursePeriod() {
		return coursePeriod;
	}

	public void setCoursePeriod(String coursePeriod) {
		this.coursePeriod = coursePeriod;
	}

	public String getExpendTime() {
		return expendTime;
	}

	public void setExpendTime(String expendTime) {
		this.expendTime = expendTime;
	}

	public String getCourseLanguage() {
		return courseLanguage;
	}

	public void setCourseLanguage(String courseLanguage) {
		this.courseLanguage = courseLanguage;
	}

	public String getSubtitleType() {
		return subtitleType;
	}

	public void setSubtitleType(String subtitleType) {
		this.subtitleType = subtitleType;
	}

	public String getCoursePic() {
		return coursePic;
	}

	public void setCoursePic(String coursePic) {
		this.coursePic = coursePic;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "CourseDetails [id=" + id + ", coursePeriod=" + coursePeriod + ", expendTime=" + expendTime
				+ ", courseLanguage=" + courseLanguage + ", subtitleType=" + subtitleType + ", coursePic=" + coursePic
				+ ", remark=" + remark + "]";
	}



}
