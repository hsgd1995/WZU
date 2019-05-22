package com.hxbd.clp.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 发送消息类：消息和用户
 * 
 * @author Administrator
 *
 */
public class MessageAndUser implements Serializable {
	private Integer id;
	private Message message;// 消息
	private User user;// 用户
	private Integer status;// 0:未读，1:已读
	private Integer isClear;//是否清除，1：已清除，0：未清除
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date sendTime;//发送时间
	
	public MessageAndUser() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public Integer getIsClear() {
		return isClear;
	}

	public void setIsClear(Integer isClear) {
		this.isClear = isClear;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public String toString() {
		return "MessageAndUser [id=" + id + ", message=" + message + ", user=" + user + ", status=" + status
				+ ", isClear=" + isClear + ", sendTime=" + sendTime + "]";
	}


}
