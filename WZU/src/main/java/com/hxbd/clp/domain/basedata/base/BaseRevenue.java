package com.hxbd.clp.domain.basedata.base;

import java.io.Serializable;
import java.util.Date;

/**
 * 众创空间总收入
 */
public class BaseRevenue implements Serializable {
    private Integer id;

    private Date time;//时间

    private String from;//收入来源

    private Double moneyNum;//金额

    private Integer revenueType;//收入类型

    private Integer baseId;//基地id

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

    public Double getMoneyNum() {
        return moneyNum;
    }

    public void setMoneyNum(Double moneyNum) {
        this.moneyNum = moneyNum;
    }

    public Integer getRevenueType() {
        return revenueType;
    }

    public void setRevenueType(Integer revenueType) {
        this.revenueType = revenueType;
    }

    public Integer getBaseId() {
        return baseId;
    }

    public void setBaseId(Integer baseId) {
        this.baseId = baseId;
    }

    @Override
    public String toString() {
        return "BaseRevenue{" +
                "id=" + id +
                ", time=" + time +
                ", from='" + from + '\'' +
                ", moneyNum=" + moneyNum +
                ", revenueType=" + revenueType +
                ", baseId=" + baseId +
                '}';
    }
}