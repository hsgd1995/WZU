package com.tang.entity.test1;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;

/**
 * 用户（数据源1）
 * 
 * @author Administrator
 * @date 2019年5月21日
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String loginname;
	private String username;
	private String realname;
	private String password;
	private String sex;
	@TableField("role_id")
	private Integer roleId;

	public User() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", loginname=" + loginname + ", username=" + username + ", realname=" + realname
				+ ", password=" + password + ", sex=" + sex + ", roleId=" + roleId + "]";
	}

}
