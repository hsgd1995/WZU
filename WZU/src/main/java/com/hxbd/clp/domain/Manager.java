package com.hxbd.clp.domain;

import java.io.Serializable;

public class Manager implements Serializable{

	private static final long serialVersionUID = 5268987655300164770L;
	private Integer id;
	private String username;  //用户名
	private String password;  //密码
	
	public Manager() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Manager [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
	
}
