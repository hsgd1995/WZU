package com.hxbd.clp.domain;

/**
 * 角色-权限关系类
 * @author Administrator
 *
 */
public class RoleAuthority {

	private Integer id;
	private Role role;
	private Authority authority;
	
	public RoleAuthority() {
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

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "RoleAuthority [id=" + id + ", role=" + role + ", authority=" + authority + "]";
	}
	
}
