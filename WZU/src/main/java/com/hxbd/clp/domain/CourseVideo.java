package com.hxbd.clp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author hxcl
 *
 * 2018年3月14日上午9:09:48
 *
 */
public class CourseVideo implements Serializable{

	private static final long serialVersionUID = 3402801148496671127L;
	
	private Integer id;	//ID
	private String name;	//标题
	private String url;		//URL
	private Integer parent;		//父节点
	private Integer courseId;	//课程ID
	private String remark;
	
	List<CourseVideo> childrenList = new ArrayList<CourseVideo>();
	//字幕列表
	List<Caption> captionList = new ArrayList<Caption>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getParent() {
		return parent;
	}
	public void setParent(Integer parent) {
		this.parent = parent;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<CourseVideo> getChildrenList() {
		return childrenList;
	}
	public void setChildrenList(List<CourseVideo> childrenList) {
		this.childrenList = childrenList;
	}
	
	public List<Caption> getCaptionList() {
		return captionList;
	}
	
	public void setCaptionList(List<Caption> captionList) {
		this.captionList = captionList;
	}
	
	@Override
	public String toString() {
		return "CourseVideo [id=" + id + ", name=" + name + ", url=" + url + ", parent=" + parent + ", courseId="
				+ courseId + ", remark=" + remark + ", childrenList=" + childrenList + ", captionList=" + captionList
				+ "]";
	}
	
}
