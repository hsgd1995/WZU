package com.hxbd.clp.domain;

import java.io.Serializable;
import java.util.List;

public class Teacher implements Serializable{

	private static final long serialVersionUID = 4360619944294787977L;
	
	private Integer id; 
	private String teacherNo; // 教师编号
	private String teacherName; // 教师名
	private String position; // 职位
	private String introduce; // 介绍
	private String content;	//详细内容
	private String teacherPic;	//教师头像
	private String remark; // 备注
	
	private List<Course> course;  //课程  多对多
	
	public Teacher() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTeacherNo() {
		return teacherNo;
	}

	public void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTeacherPic() {
		return teacherPic;
	}

	public void setTeacherPic(String teacherPic) {
		this.teacherPic = teacherPic;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Course> getCourse() {
		return course;
	}

	public void setCourse(List<Course> course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", teacherNo=" + teacherNo + ", teacherName=" + teacherName + ", position="
				+ position + ", introduce=" + introduce + ", content=" + content + ", teacherPic=" + teacherPic
				+ ", remark=" + remark + ", course=" + course + "]";
	}



}
