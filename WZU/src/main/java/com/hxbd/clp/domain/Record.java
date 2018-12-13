package com.hxbd.clp.domain;

import java.io.Serializable;

public class Record implements Serializable {

	private static final long serialVersionUID = -2070970897454871305L;
	private Integer id;
	private String phone;  //电话
	private Integer count;  //总数
	
	public Record() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Recode [id=" + id + ", phone=" + phone + ", count=" + count + "]";
	}
	
}
