package com.hxbd.clp.domain.basedata.base;

import java.io.Serializable;

/**
 * 众创空间总收入类型
 */
public class TotalRevenueType implements Serializable {
    private Integer id;

    private String name;//类型名称

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

    @Override
    public String toString() {
        return "TotalRevenueType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}