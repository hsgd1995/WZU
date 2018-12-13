package com.hxbd.clp.domain.basedata.base;

import java.io.Serializable;
import java.util.Date;

/**
 * 众创空间运营成本
 */
public class BaseOperatingCost implements Serializable {
    //众创空间运营成本
    private Integer id;

    private Double staffCosts;//人员费用（万元/月）

    private Double venueFees;//场地费用（万元/月）

    private Double manageCosts;//-管理费用（万元/月）

    private Double othersCosts;//其他费用（万元/月）

    private Date startTime;//统计起始时间【下拉时间菜单选择年份、月份、日期】

    private Date endTime;//统计截止时间【下拉时间菜单选择年份、月份、日期】

    private Integer baseId;//基地id

    private Integer residentTeamNum;//众创空间常驻团队数量

    private Double businessUseArea;//企业使用面积

    private Double publicArea;//众创空间公共服务面积

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getStaffCosts() {
        return staffCosts;
    }

    public void setStaffCosts(Double staffCosts) {
        this.staffCosts = staffCosts;
    }

    public Double getVenueFees() {
        return venueFees;
    }

    public void setVenueFees(Double venueFees) {
        this.venueFees = venueFees;
    }

    public Double getManageCosts() {
        return manageCosts;
    }

    public void setManageCosts(Double manageCosts) {
        this.manageCosts = manageCosts;
    }

    public Double getOthersCosts() {
        return othersCosts;
    }

    public void setOthersCosts(Double othersCosts) {
        this.othersCosts = othersCosts;
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

    public Integer getBaseId() {
        return baseId;
    }

    public void setBaseId(Integer baseId) {
        this.baseId = baseId;
    }

    public Integer getResidentTeamNum() {
        return residentTeamNum;
    }

    public void setResidentTeamNum(Integer residentTeamNum) {
        this.residentTeamNum = residentTeamNum;
    }

    public Double getBusinessUseArea() {
        return businessUseArea;
    }

    public void setBusinessUseArea(Double businessUseArea) {
        this.businessUseArea = businessUseArea;
    }

    public Double getPublicArea() {
        return publicArea;
    }

    public void setPublicArea(Double publicArea) {
        this.publicArea = publicArea;
    }

    @Override
    public String toString() {
        return "BaseOperatingCost{" +
                "id=" + id +
                ", staffCosts=" + staffCosts +
                ", venueFees=" + venueFees +
                ", manageCosts=" + manageCosts +
                ", othersCosts=" + othersCosts +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", baseId=" + baseId +
                ", residentTeamNum=" + residentTeamNum +
                ", businessUseArea=" + businessUseArea +
                ", publicArea=" + publicArea +
                '}';
    }
}