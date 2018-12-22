package com.hxbd.clp.domain.bus.base;

/**
 * 附件表
 * @author Administrator
 *
 */
public class Annex {
	private Integer id;
	private String name;
	private Integer projectId;
	private Integer competitionId;
	private Integer openId;
	private Integer endId;
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
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getCompetitionId() {
		return competitionId;
	}
	public void setCompetitionId(Integer competitionId) {
		this.competitionId = competitionId;
	}
	public Integer getOpenId() {
		return openId;
	}
	public void setOpenId(Integer openId) {
		this.openId = openId;
	}
	public Integer getEndId() {
		return endId;
	}
	public void setEndId(Integer endId) {
		this.endId = endId;
	}
	@Override
	public String toString() {
		return "Annex [id=" + id + ", name=" + name + ", projectId=" + projectId + ", competitionId=" + competitionId
				+ ", openId=" + openId + ", endId=" + endId + "]";
	}
	
	
}
