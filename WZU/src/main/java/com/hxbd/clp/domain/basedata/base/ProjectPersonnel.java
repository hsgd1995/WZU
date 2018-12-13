package com.hxbd.clp.domain.basedata.base;

import java.io.Serializable;

/**
 * 企业项目员工信息
 */
public class ProjectPersonnel implements Serializable {
    private Integer id;

    private String name;//姓名

    private Integer sex;//性别【下拉菜单选项：1-男，2-女】

    private String clazz;//所在班级/单位

    private String position;//企业职务

    private String phone;//联系电话

    private Integer type;//1:全职，2:兼职

    private Integer projectId;//企业id

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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "ProjectPersonnel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", clazz='" + clazz + '\'' +
                ", position='" + position + '\'' +
                ", phone='" + phone + '\'' +
                ", type=" + type +
                ", projectId=" + projectId +
                '}';
    }
}