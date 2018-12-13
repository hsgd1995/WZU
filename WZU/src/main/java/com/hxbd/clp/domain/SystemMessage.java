package com.hxbd.clp.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 系统消息类
 * @author Administrator
 *
 */
public class SystemMessage implements Serializable{
	private Integer id;
	private User user;
	private Course course;
	private String title;//标题
	private String content;//内容
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date sendTime;//发送时间
	private Integer status;// 0:未读，1:已读
	
	public SystemMessage() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
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
	
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "SystemMessage [id=" + id + ", user=" + user + ", course=" + course + ", title=" + title + ", content="
				+ content + ", sendTime=" + sendTime + ", status=" + status + "]";
	}
	
	
}
