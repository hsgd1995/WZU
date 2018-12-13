package com.hxbd.clp.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{

	private static final long serialVersionUID = 4793846481495258884L;
	private Integer id;
	private String loginName; // 登录名
	private String username; // 姓名
	private String password; // 密码
	private String phoneNumber; // 手机号
	private String email; // 邮箱
	private String cardId; // 身份证号
	private String area; // 地区
	private String userPic;  //用户头像
	private String remark;
	private String subscribe;//邮箱订阅
	private Date loginTime;//登陆时间
	private Date logoutTime;//退出时间
	
	public User() {
	}

	public Integer getId() {
		return id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

	public String getUserPic() {
		return userPic;
	}

	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}
	
	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}
	
	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", loginName=" + loginName + ", username=" + username + ", password=" + password
				+ ", phoneNumber=" + phoneNumber + ", email=" + email + ", cardId=" + cardId + ", area=" + area
				+ ", userPic=" + userPic + ", remark=" + remark + ",subscribe=" + subscribe + ",loginTime=" + loginTime + ",logoutTime=" + logoutTime + "]";
	}



}
