package com.hxbd.clp.domain;

/**
 * （后台）角色-管理员
 * 
 * @author Administrator
 *
 */
public class RoleManager {
	private Integer id;
	private Role role;
	private Manager manager;

	public RoleManager() {
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

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return "RoleManager [id=" + id + ", role=" + role + ", manager=" + manager + "]";
	}

}
