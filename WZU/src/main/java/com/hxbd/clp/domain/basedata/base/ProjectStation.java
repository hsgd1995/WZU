package com.hxbd.clp.domain.basedata.base;

import java.io.Serializable;

/**
 * 项目组工位设置
 */
public class ProjectStation implements Serializable {
    private Integer id;

    private String no;//工位编号

    private Integer area;//工位面积（平方米）

    private Integer baseId;//基地id

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getBaseId() {
        return baseId;
    }

    public void setBaseId(Integer baseId) {
        this.baseId = baseId;
    }

    @Override
    public String toString() {
        return "ProjectStation{" +
                "id=" + id +
                ", no='" + no + '\'' +
                ", area=" + area +
                ", baseId=" + baseId +
                '}';
    }
}