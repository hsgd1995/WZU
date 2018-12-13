package com.hxbd.clp.domain.basedata;

/**
 * 指导老师
 */
public class Teacher {
    private Integer id;
    private String name;//姓名
    private Integer sex;//性别【下拉菜单选项：1-男，2-女】
    private SecondaryCollege sc;//二级学院
    private String position;//职务
    private Integer job;//职称【下拉菜单选项：1-正高级，2-副高级，3中级，4-初级，5无职称】
    private String phone;//联系电话
    private Integer isPosition;//-任职情况【下拉菜单选项：1-专职，2-兼职】
    private Integer projectId;//企业id
    private Integer baseId;//基地id
    private Integer competitionId;

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
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public SecondaryCollege getSc() {
        return sc;
    }

    public void setSc(SecondaryCollege sc) {
        this.sc = sc;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getJob() {
        return job;
    }

    public void setJob(Integer job) {
        this.job = job;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIsPosition() {
        return isPosition;
    }

    public void setIsPosition(Integer isPosition) {
        this.isPosition = isPosition;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getBaseId() {
        return baseId;
    }

    public void setBaseId(Integer baseId) {
        this.baseId = baseId;
    }

    public Integer getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Integer competitionId) {
        this.competitionId = competitionId;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", sc=" + sc +
                ", position='" + position + '\'' +
                ", job=" + job +
                ", phone='" + phone + '\'' +
                ", isPosition=" + isPosition +
                ", projectId=" + projectId +
                ", baseId=" + baseId +
                ", competitionId=" + competitionId +
                '}';
    }
}
