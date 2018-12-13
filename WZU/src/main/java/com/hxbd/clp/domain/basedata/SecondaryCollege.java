package com.hxbd.clp.domain.basedata;

/**
 * 二级学院
 */
public class SecondaryCollege {
    private Integer id;
    private Integer name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SecondaryCollege{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
