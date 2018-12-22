package com.hxbd.clp.domain.bus.base;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.hxbd.clp.domain.basedata.base.Base;

/**
 * 基地进驻入口
 * @author Administrator
 *
 */
public class BaseInto {
	
	private Integer id;
	private String name;
	private String state;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;
	private Base base;
	
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
	public Base getBase() {
		return base;
	}
	public void setBase(Base base) {
		this.base = base;
	}
	@Override
	public String toString() {
		return "BaseInto [id=" + id + ", name=" + name + ", state=" + state + ", startTime=" + startTime + ", endTime="
				+ endTime + ", base=" + base + "]";
	}
	
	
}
