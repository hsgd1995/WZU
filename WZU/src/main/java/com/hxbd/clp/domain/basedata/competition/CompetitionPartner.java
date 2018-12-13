package com.hxbd.clp.domain.basedata.competition;

import java.io.Serializable;

/**
 * 项目参与者信息
 */
public class CompetitionPartner implements Serializable {
    private Integer id;

    private String name;//F0401-姓名

    private Integer sex;//F0402-性别【下拉菜单选项：1-男，2-女】

    private String sno;//F0403-学号（12位数字，填错不可提交）

    private Integer secondaryCollegeId;//所在二级学院

    private Integer gradeId;//所在年级

    private String clazz;//所在班级

    private String dormitory;//-所在宿舍

    private String phone;//联系电话

    private String qq;//qq

    private String email;//个人电子邮箱

    private Integer competitionId;//学生参赛项目id

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

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno == null ? null : sno.trim();
    }

    public Integer getSecondaryCollegeId() {
        return secondaryCollegeId;
    }

    public void setSecondaryCollegeId(Integer secondaryCollegeId) {
        this.secondaryCollegeId = secondaryCollegeId;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz == null ? null : clazz.trim();
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory == null ? null : dormitory.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Integer competitionId) {
        this.competitionId = competitionId;
    }

    @Override
    public String toString() {
        return "CompetitionPartner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", sno='" + sno + '\'' +
                ", secondaryCollegeId=" + secondaryCollegeId +
                ", gradeId=" + gradeId +
                ", clazz='" + clazz + '\'' +
                ", dormitory='" + dormitory + '\'' +
                ", phone='" + phone + '\'' +
                ", qq='" + qq + '\'' +
                ", email='" + email + '\'' +
                ", competitionId=" + competitionId +
                '}';
    }
}