package com.hxbd.clp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class Course implements Serializable{
	
	private static final long serialVersionUID = -2187529858290484840L;
	
	private Integer id;
	private String courseNo; // 课程编号
	private String courseName; // 课程名称
	private String introduce; // 课程简介
	private String outline; // 课程大纲
	private String examine; // 课程考核
	private String teachingMateril; // 课程教材
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date studyStartTime; // 学习开始时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date studyEndTime; // 学习结束时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date examStartTime; // 考试开始时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date examEndTime; // 考试结束时间
	
	private int courseStatus;	//课程状态
	
	private Integer orfree;	//是否收费
	
	private CourseType courseType;	//课程类型  一对一
	
	private Integer courseTypeId;
	
	private CourseDetails courseDetails;	//课程详情  一对一
	
	private List<Teacher> teacher;	//教师 ，多对多
	
	private String remark; 
	
	private String coursePeriod;	//学时
	private String expendTime;	//每周耗时
	private String courseLanguage;	//授课语言
	private String subtitleType;	//字幕类型
	private String coursePic;	//课程图片
	
	public Course() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getOutline() {
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

	public String getExamine() {
		return examine;
	}

	public void setExamine(String examine) {
		this.examine = examine;
	}

	public String getTeachingMateril() {
		return teachingMateril;
	}

	public void setTeachingMateril(String teachingMateril) {
		this.teachingMateril = teachingMateril;
	}

	public Date getStudyStartTime() {
		return studyStartTime;
	}

	public void setStudyStartTime(Date studyStartTime) {
		this.studyStartTime = studyStartTime;
	}

	public Date getStudyEndTime() {
		return studyEndTime;
	}

	public void setStudyEndTime(Date studyEndTime) {
		this.studyEndTime = studyEndTime;
	}

	public Date getExamStartTime() {
		return examStartTime;
	}

	public void setExamStartTime(Date examStartTime) {
		this.examStartTime = examStartTime;
	}

	public Date getExamEndTime() {
		return examEndTime;
	}

	public void setExamEndTime(Date examEndTime) {
		this.examEndTime = examEndTime;
	}

	public int getCourseStatus() {
		return courseStatus;
	}

	public void setCourseStatus(int courseStatus) {
		this.courseStatus = courseStatus;
	}

	public CourseType getCourseType() {
		return courseType;
	}

	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
	}

	public CourseDetails getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(CourseDetails courseDetails) {
		this.courseDetails = courseDetails;
	}

	public List<Teacher> getTeacher() {
		return teacher;
	}

	public void setTeacher(List<Teacher> teacher) {
		this.teacher = teacher;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Integer getOrfree() {
		return orfree;
	}

	public void setOrfree(Integer orfree) {
		this.orfree = orfree;
	}
	
	public Integer getCourseTypeId() {
		return courseTypeId;
	}

	public void setCourseTypeId(Integer courseTypeId) {
		this.courseTypeId = courseTypeId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", courseNo=" + courseNo + ", courseName=" + courseName + ", introduce=" + introduce
				+ ", outline=" + outline + ", examine=" + examine + ", teachingMateril=" + teachingMateril
				+ ", studyStartTime=" + studyStartTime + ", studyEndTime=" + studyEndTime + ", examStartTime="
				+ examStartTime + ", examEndTime=" + examEndTime + ", courseStatus=" + courseStatus + ", orfree="
				+ orfree + ", courseType=" + courseType + ", courseDetails=" + courseDetails + ", teacher=" + teacher
				+ ", remark=" + remark + ",coursePeriod=" + coursePeriod + ",expendTime=" + expendTime + ",courseLanguage=" + courseLanguage 
				+ ",subtitleType=" + subtitleType + ",coursePic=" + coursePic + "]";
	}


}
