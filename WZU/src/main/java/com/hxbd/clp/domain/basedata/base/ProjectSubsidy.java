package com.hxbd.clp.domain.basedata.base;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业获得投资、补助情况
 */
public class ProjectSubsidy implements Serializable {
    private Integer id;

    private Date time;//投资、补助时间

    private String from;//投资方名称、补助部门

    private Double money;//金额（万元）

    private Integer subsidyFiedId;//投资、补助字段表id

    private Integer projectId;//企业id

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from == null ? null : from.trim();
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getSubsidyFiedId() {
        return subsidyFiedId;
    }

    public void setSubsidyFiedId(Integer subsidyFiedId) {
        this.subsidyFiedId = subsidyFiedId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "ProjectSubsidy{" +
                "id=" + id +
                ", time=" + time +
                ", from='" + from + '\'' +
                ", money=" + money +
                ", subsidyFiedId=" + subsidyFiedId +
                ", projectId=" + projectId +
                '}';
    }
}