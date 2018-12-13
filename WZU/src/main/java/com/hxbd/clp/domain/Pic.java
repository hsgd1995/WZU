package com.hxbd.clp.domain;

import java.io.Serializable;

public class Pic implements Serializable {
	
	private static final long serialVersionUID = 4334937394189903528L;
	private Integer id;
	private String picNo;
	private String picName;
	private String path;
	private String pageview;
	private String remark;
	
	public Pic() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPicNo() {
		return picNo;
	}

	public void setPicNo(String picNo) {
		this.picNo = picNo;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPageview() {
		return pageview;
	}

	public void setPageview(String pageview) {
		this.pageview = pageview;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Pic [id=" + id + ", picNo=" + picNo + ", picName=" + picName + ", path=" + path + ", pageview="
				+ pageview + ", remark=" + remark + "]";
	}



}
