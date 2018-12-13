package com.hxbd.clp.domain;

import java.io.Serializable;

public class CourseType implements Serializable{

	private static final long serialVersionUID = -2828347869637407705L;

	private Integer id;
	private String typeName;	//类型名称
	private String remark;	//标记


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "CourseType [id=" + id + ", typeName=" + typeName + ", remark=" + remark + "]";
	}
	
}
