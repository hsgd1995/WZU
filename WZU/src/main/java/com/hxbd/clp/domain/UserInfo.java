package com.hxbd.clp.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class UserInfo implements Serializable{

	private static final long serialVersionUID = -2603659002522357703L;
	
	private Integer id;
	private Integer userId;//用户ID
	private String privacy;//隐私
	private String atSchool;//是否在校
	private String school;//学院名称
	private String major;//专业
	private String grade;//年级
	private String address;//通讯地址
	private String sex;//性别
	private String education;//学历
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date entrance;//入学时间 
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;//出生年月日
	private String personalProfile;//个人简介
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
	public String getPrivacy() {
		return privacy;
	}
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	public String getAtSchool() {
		return atSchool;
	}
	public void setAtSchool(String atSchool) {
		this.atSchool = atSchool;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public Date getEntrance() {
		return entrance;
	}
	public void setEntrance(Date entrance) {
		this.entrance = entrance;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPersonalProfile() {
		return personalProfile;
	}
	public void setPersonalProfile(String personalProfile) {
		this.personalProfile = personalProfile;
	}
	
}
