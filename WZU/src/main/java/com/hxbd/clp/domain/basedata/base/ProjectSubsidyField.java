package com.hxbd.clp.domain.basedata.base;

import java.io.Serializable;

/**
 * 企业获得投资、补助情况字段表
 */
public class ProjectSubsidyField implements Serializable {
    private Integer id;

    private String name;//投资、补助名称

    private String from;//来源

    private String money;//金额

    private static final long serialVersionUID = 1L;

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
        this.name = name == null ? null : name.trim();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from == null ? null : from.trim();
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money == null ? null : money.trim();
    }

    @Override
    public String toString() {
        return "ProjectSubsidyField{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", from='" + from + '\'' +
                ", money='" + money + '\'' +
                '}';
    }
}