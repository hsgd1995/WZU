package com.hxbd.clp.domain.bus.base;

/**
 * 项目进驻审批表
 * @author Administrator
 *
 */
public class ProjectInto {
	private Integer id;
	private Integer managerId;
	private Integer projectId;
	private String jobId;
	private Integer isAgree;
	private String opinion;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public Integer getIsAgree() {
		return isAgree;
	}
	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	@Override
	public String toString() {
		return "ProjectInto [id=" + id + ", managerId=" + managerId + ", projectId=" + projectId + ", jobId=" + jobId
				+ ", isAgree=" + isAgree + ", opinion=" + opinion + "]";
	}

	
	
}
