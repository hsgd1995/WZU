package com.hxbd.clp.domain.basedata;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业所获成果/奖项
 */
public class Awards implements Serializable {
    private Integer id;

    private Date awardTime;//获奖时间

    private Integer awardLevel;//获奖等级【下拉菜单选项：1-国际级，2-国家级，3-省部级，4-市厅级，5-县局级，6-校级】

    private String awardName;//奖项名称

    private Integer level;//奖项等级【下拉菜单选项：1-特等奖，2-一等奖，3-二等奖，4-三等奖，5-优秀奖，6-金奖，7-银奖，8-铜奖，9-第一名，10-第二名，11-第三名，12-第四名，13-第五名】

    private Integer projectId;//企业id

    private Integer competitionId;//

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(Date awardTime) {
        this.awardTime = awardTime;
    }


    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName == null ? null : awardName.trim();
    }

    public Integer getAwardLevel() {
        return awardLevel;
    }

    public void setAwardLevel(Integer awardLevel) {
        this.awardLevel = awardLevel;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "ProjectAwards{" +
                "id=" + id +
                ", awardTime=" + awardTime +
                ", awardLevel=" + awardLevel +
                ", awardName='" + awardName + '\'' +
                ", level=" + level +
                ", projectId=" + projectId +
                '}';
    }
}