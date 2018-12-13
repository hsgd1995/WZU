package com.hxbd.clp.domain;

import java.io.Serializable;

/**
 * 权限类
 * @author Administrator
 *
 */
public class Authority implements Serializable{
	
	private String id;
	private String name;
	private String url;//所能访问的资源
	private Integer type;//0：可以设置 1：不可设置
	private String parentId;//父权限id
	private String state;//说明
	
	public Authority() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Authority [id=" + id + ", name=" + name + ", url=" + url + ", type=" + type + ", parentId=" + parentId
				+ ", state=" + state + "]";
	}
	
}
