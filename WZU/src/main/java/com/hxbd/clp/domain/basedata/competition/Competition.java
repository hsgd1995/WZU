package com.hxbd.clp.domain.basedata.competition;

import java.io.Serializable;

public class Competition implements Serializable {
    private Integer id;

    private String name;

    private Integer type;

    private String principalName;

    private Integer principalSex;

    private String pricipalSno;

    private Integer principalSecondaryCollegeId;

    private Integer principalGradeId;

    private String principalClass;

    private String principalDormitory;

    private String principalPhone;

    private String principalQq;

    private String principalEmail;

    private String overview;

    private String innovation;

    private String achievement;

    private String result;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName == null ? null : principalName.trim();
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getInnovation() {
        return innovation;
    }

    public void setInnovation(String innovation) {
        this.innovation = innovation;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getPrincipalSex() {
        return principalSex;
    }

    public void setPrincipalSex(Integer principalSex) {
        this.principalSex = principalSex;
    }

    public String getPricipalSno() {
        return pricipalSno;
    }

    public void setPricipalSno(String pricipalSno) {
        this.pricipalSno = pricipalSno == null ? null : pricipalSno.trim();
    }

    public Integer getPrincipalSecondaryCollegeId() {
        return principalSecondaryCollegeId;
    }

    public void setPrincipalSecondaryCollegeId(Integer principalSecondaryCollegeId) {
        this.principalSecondaryCollegeId = principalSecondaryCollegeId;
    }

    public Integer getPrincipalGradeId() {
        return principalGradeId;
    }

    public void setPrincipalGradeId(Integer principalGradeId) {
        this.principalGradeId = principalGradeId;
    }

    public String getPrincipalClass() {
        return principalClass;
    }

    public void setPrincipalClass(String principalClass) {
        this.principalClass = principalClass == null ? null : principalClass.trim();
    }

    public String getPrincipalDormitory() {
        return principalDormitory;
    }

    public void setPrincipalDormitory(String principalDormitory) {
        this.principalDormitory = principalDormitory == null ? null : principalDormitory.trim();
    }

    public String getPrincipalPhone() {
        return principalPhone;
    }

    public void setPrincipalPhone(String principalPhone) {
        this.principalPhone = principalPhone == null ? null : principalPhone.trim();
    }

    public String getPrincipalQq() {
        return principalQq;
    }

    public void setPrincipalQq(String principalQq) {
        this.principalQq = principalQq == null ? null : principalQq.trim();
    }

    public String getPrincipalEmail() {
        return principalEmail;
    }

    public void setPrincipalEmail(String principalEmail) {
        this.principalEmail = principalEmail == null ? null : principalEmail.trim();
    }

    @Override
    public String toString() {
        return "Competition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", principalName='" + principalName + '\'' +
                ", principalSex=" + principalSex +
                ", pricipalSno='" + pricipalSno + '\'' +
                ", principalSecondaryCollegeId=" + principalSecondaryCollegeId +
                ", principalGradeId=" + principalGradeId +
                ", principalClass='" + principalClass + '\'' +
                ", principalDormitory='" + principalDormitory + '\'' +
                ", principalPhone='" + principalPhone + '\'' +
                ", principalQq='" + principalQq + '\'' +
                ", principalEmail='" + principalEmail + '\'' +
                ", overview='" + overview + '\'' +
                ", innovation='" + innovation + '\'' +
                ", achievement='" + achievement + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}