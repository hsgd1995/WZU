package com.hxbd.clp.vo;

import java.util.List;

public class AuthorityVO {
	
	private String id;
	
	private String name;
	
	private String state;
	
	private String parentId;
	
	private List<AuthorityVO> list;

	public AuthorityVO() {
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<AuthorityVO> getList() {
		return list;
	}

	public void setList(List<AuthorityVO> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "AuthorityVO [id=" + id + ", name=" + name + ", state=" + state + ", parentId=" + parentId + ", list="
				+ list + "]";
	}

	
}
