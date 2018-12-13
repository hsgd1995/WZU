package com.hxbd.clp.domain;

/**
 * （前台）角色-用户
 * 
 * @author Administrator
 *
 */
public class RoleUser {
	
	private Integer id;
	private Role role;
	private User user;

	public RoleUser() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "RoleUser [id=" + id + ", role=" + role + ", user=" + user + "]";
	}

}
