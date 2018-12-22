package com.hxbd.clp.domain;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 消息类
 * 
 * @author Administrator
 *
 */
public class Message implements Serializable {
	private Integer id;
	private String title;// 消息标题
	private String content;// 消息内容
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date createTime;// 创建时间
	private Manager creater;// 创建者
	private Integer type;// 类型，0:系统消息，1:管理员消息
	private String fromName;//来自，为了不修改Manager类，在此添加该属性
	
	public Message() {
		super();
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Manager getCreater() {
		return creater;
	}

	public void setCreater(Manager creater) {
		this.creater = creater;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", title=" + title + ", content=" + content + ", createTime=" + createTime
				+ ", creater=" + creater + ", type=" + type + ", fromName=" + fromName + "]";
	}


	

}
