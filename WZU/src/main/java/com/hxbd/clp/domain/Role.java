package com.hxbd.clp.domain;

import java.io.Serializable;

/**
 * 角色类
 * 
 * @author Administrator
 *
 */
public class Role implements Serializable {
	private Integer id;
	private String name;
	private Integer orgId;// 单位id,暂不使用
	private String state;// 备注
	private String createTime;// 创建时间
	private Integer createrId;// 创建者
	private Integer type;// 0：无效角色 1 ： 有效角色

	public Role() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", orgId=" + orgId + ", state=" + state + ", createTime="
				+ createTime + ", createrId=" + createrId + ", type=" + type + "]";
	}

}
