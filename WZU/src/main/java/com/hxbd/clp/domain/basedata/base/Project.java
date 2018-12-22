package com.hxbd.clp.domain.basedata.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.hxbd.clp.domain.basedata.Awards;
import com.hxbd.clp.domain.basedata.Teacher;
import com.hxbd.clp.domain.bus.base.ProjectInto;

/**
 * B0-在驻企业项目情况
 */
public class Project implements Serializable {
    private Integer id;

    private String name;//企业项目名称

    private Integer stationId;//企业项目工位情况

    private String principalName;//负责人信息-姓名

    private Integer principalSex;//负责人信息-性别【下拉菜单选项：1-男，2-女】

    private String principalCorporatePosition;//负责人信息-企业职务

    private Integer principalPoliticalStatus;//负责人信息-政治面貌【下拉菜单选项：1-中共党员，2-共青团员，3-群众】

    private String principalSno;//负责人信息-学号

    private Integer principalSecondaryCollegeId;//负责人信息-所在二级学院

    private Integer principalGradeId;//负责人信息-所在年级

    private String principalClass;//负责人信息-所在班级

    private String principalDormitory;//负责人信息-所在宿舍

    private String principalPhone;//负责人信息-联系电话

    private String principalQq;//负责人信息-QQ号

    private String principalEmail;//负责人信息-个人电子邮箱

    private String principalPosition;//负责人信息-在校期间担任学生职务情况

    private String principalCertificate;//负责人信息-在校期间所获等级证书及技能证书

    private String principalScoreDescription;//负责人信息-社会实践主要成绩简述（300字以内）
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date projectSetupTime;//企业项目成立时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date projectEnteringTime;//企业项目入驻时间

    private Integer isBusinessRegistration;//企业是否已进行工商注册【下拉菜单选项：1-是，2否】
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registeredTime;//注册时间

    private Double registrationCapital;//企业注册资金（万元）

    private String legalName;//企业法人代表信息-姓名

    private Integer legalSex;//企业法人代表信息-性别【下拉菜单选项：1-男，2-女】

    private Integer legalPoliticalStatus;//政治面貌【下拉菜单选项：1-中共党员，2-共青团员，3-群众】

    private String legalBirthplace;//企业法人代表信息-籍贯

    private String legalGrade;//企业法人代表信息-所在年级

    private String legalClazz;//企业法人代表信息-所在班级/单位

    private String legalPosition;//企业法人代表信息-所在单位职务

    private String legalPhone;//企业法人代表信息-联系电话

    private String legalEmail;//企业法人代表信息-邮箱

    private String manageContent;//项目经营内容

    private String projectIndustry;//企业所属行业

    private String projectType;//企业类型

    private Double initialFunds;//创办企业初始投资资金（万元）

    private Integer workStudyNum;//提供勤工助学岗位数

    private Integer recentGraduatesNum;//企业成员中的应届毕业生人数

    private Integer previousGraduatesNum;//企业成员中的历届毕业生人数

    private Integer totalEmployment;//企业成立以来累计带动就业人数

    private Integer totalPractice;//企业成立以来累计带动实习见习人数

    private Integer yearEmploymentNum;//本年度吸纳就业人数

    private Integer yearPracticeNum;//本年度吸纳实习见习人数

    private Integer isEffective;//本企业是否拥有有效知识产权？【下拉菜单选项：1-是，2-否】

    private Integer isBase;//本企业是否就业见习基地【下拉菜单选项：1-是，2-否】

    private Integer status;//企业当前状态【下拉菜单选项：1-在驻，2-孵化成功出园，3-孵化失败出园。】

    private Integer baseId;//基地id
    
    private Integer managerId;
    
    private List<Teacher> teacherList;//教师
    private List<ProjectPersonnel> projectPersonnelList;//员工
    private List<ProjectSubsidy> ProjectSubsidyList;//收入
    private List<Awards> awardsList;//获奖
    
    private List<ProjectInto> projectIntoList;

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

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName == null ? null : principalName.trim();
    }

    public Integer getPrincipalSex() {
        return principalSex;
    }

    public void setPrincipalSex(Integer principalSex) {
        this.principalSex = principalSex;
    }

    public String getPrincipalCorporatePosition() {
        return principalCorporatePosition;
    }

    public void setPrincipalCorporatePosition(String principalCorporatePosition) {
        this.principalCorporatePosition = principalCorporatePosition == null ? null : principalCorporatePosition.trim();
    }

    public Integer getPrincipalPoliticalStatus() {
        return principalPoliticalStatus;
    }

    public void setPrincipalPoliticalStatus(Integer principalPoliticalStatus) {
        this.principalPoliticalStatus = principalPoliticalStatus;
    }

    

    public String getPrincipalSno() {
		return principalSno;
	}

	public void setPrincipalSno(String principalSno) {
		this.principalSno = principalSno;
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

    public String getPrincipalPosition() {
        return principalPosition;
    }

    public void setPrincipalPosition(String principalPosition) {
        this.principalPosition = principalPosition;
    }

    public String getPrincipalCertificate() {
        return principalCertificate;
    }

    public void setPrincipalCertificate(String principalCertificate) {
        this.principalCertificate = principalCertificate;
    }

    public String getPrincipalScoreDescription() {
        return principalScoreDescription;
    }

    public void setPrincipalScoreDescription(String principalScoreDescription) {
        this.principalScoreDescription = principalScoreDescription;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getProjectSetupTime() {
        return projectSetupTime;
    }

    public void setProjectSetupTime(Date projectSetupTime) {
        this.projectSetupTime = projectSetupTime;
    }

    public Date getProjectEnteringTime() {
        return projectEnteringTime;
    }

    public void setProjectEnteringTime(Date projectEnteringTime) {
        this.projectEnteringTime = projectEnteringTime;
    }

    public Integer getIsBusinessRegistration() {
        return isBusinessRegistration;
    }

    public void setIsBusinessRegistration(Integer isBusinessRegistration) {
        this.isBusinessRegistration = isBusinessRegistration;
    }

    public Date getRegisteredTime() {
        return registeredTime;
    }

    public void setRegisteredTime(Date registeredTime) {
        this.registeredTime = registeredTime;
    }

    public Double getRegistrationCapital() {
        return registrationCapital;
    }

    public void setRegistrationCapital(Double registrationCapital) {
        this.registrationCapital = registrationCapital;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName == null ? null : legalName.trim();
    }

    public Integer getLegalSex() {
        return legalSex;
    }

    public void setLegalSex(Integer legalSex) {
        this.legalSex = legalSex;
    }

    
    public Integer getLegalPoliticalStatus() {
		return legalPoliticalStatus;
	}

	public void setLegalPoliticalStatus(Integer legalPoliticalStatus) {
		this.legalPoliticalStatus = legalPoliticalStatus;
	}

	public String getLegalBirthplace() {
        return legalBirthplace;
    }

    public void setLegalBirthplace(String legalBirthplace) {
        this.legalBirthplace = legalBirthplace == null ? null : legalBirthplace.trim();
    }

    public String getLegalGrade() {
        return legalGrade;
    }

    public void setLegalGrade(String legalGrade) {
        this.legalGrade = legalGrade == null ? null : legalGrade.trim();
    }

    public String getLegalClazz() {
        return legalClazz;
    }

    public void setLegalClazz(String legalClazz) {
        this.legalClazz = legalClazz == null ? null : legalClazz.trim();
    }

    public String getLegalPosition() {
        return legalPosition;
    }

    public void setLegalPosition(String legalPosition) {
        this.legalPosition = legalPosition == null ? null : legalPosition.trim();
    }

    public String getLegalPhone() {
        return legalPhone;
    }

    public void setLegalPhone(String legalPhone) {
        this.legalPhone = legalPhone == null ? null : legalPhone.trim();
    }

    public String getLegalEmail() {
        return legalEmail;
    }

    public void setLegalEmail(String legalEmail) {
        this.legalEmail = legalEmail == null ? null : legalEmail.trim();
    }

    public String getManageContent() {
        return manageContent;
    }

    public void setManageContent(String manageContent) {
        this.manageContent = manageContent == null ? null : manageContent.trim();
    }

    public String getProjectIndustry() {
        return projectIndustry;
    }

    public void setProjectIndustry(String projectIndustry) {
        this.projectIndustry = projectIndustry == null ? null : projectIndustry.trim();
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType == null ? null : projectType.trim();
    }

    public Double getInitialFunds() {
        return initialFunds;
    }

    public void setInitialFunds(Double initialFunds) {
        this.initialFunds = initialFunds;
    }

    public Integer getWorkStudyNum() {
        return workStudyNum;
    }

    public void setWorkStudyNum(Integer workStudyNum) {
        this.workStudyNum = workStudyNum;
    }

    public Integer getRecentGraduatesNum() {
        return recentGraduatesNum;
    }

    public void setRecentGraduatesNum(Integer recentGraduatesNum) {
        this.recentGraduatesNum = recentGraduatesNum;
    }

    public Integer getPreviousGraduatesNum() {
        return previousGraduatesNum;
    }

    public void setPreviousGraduatesNum(Integer previousGraduatesNum) {
        this.previousGraduatesNum = previousGraduatesNum;
    }

    public Integer getTotalEmployment() {
        return totalEmployment;
    }

    public void setTotalEmployment(Integer totalEmployment) {
        this.totalEmployment = totalEmployment;
    }

    public Integer getTotalPractice() {
        return totalPractice;
    }

    public void setTotalPractice(Integer totalPractice) {
        this.totalPractice = totalPractice;
    }

    public Integer getYearEmploymentNum() {
        return yearEmploymentNum;
    }

    public void setYearEmploymentNum(Integer yearEmploymentNum) {
        this.yearEmploymentNum = yearEmploymentNum;
    }

    public Integer getYearPracticeNum() {
        return yearPracticeNum;
    }

    public void setYearPracticeNum(Integer yearPracticeNum) {
        this.yearPracticeNum = yearPracticeNum;
    }

    public Integer getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Integer isEffective) {
        this.isEffective = isEffective;
    }

    public Integer getIsBase() {
        return isBase;
    }

    public void setIsBase(Integer isBase) {
        this.isBase = isBase;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBaseId() {
        return baseId;
    }

    public void setBaseId(Integer baseId) {
        this.baseId = baseId;
    }
    

    public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	
	
	public List<Teacher> getTeacherList() {
		return teacherList;
	}

	public void setTeacherList(List<Teacher> teacherList) {
		this.teacherList = teacherList;
	}

	public List<ProjectPersonnel> getProjectPersonnelList() {
		return projectPersonnelList;
	}

	public void setProjectPersonnelList(List<ProjectPersonnel> projectPersonnelList) {
		this.projectPersonnelList = projectPersonnelList;
	}

	public List<ProjectSubsidy> getProjectSubsidyList() {
		return ProjectSubsidyList;
	}

	public void setProjectSubsidyList(List<ProjectSubsidy> projectSubsidyList) {
		ProjectSubsidyList = projectSubsidyList;
	}

	public List<Awards> getAwardsList() {
		return awardsList;
	}

	public void setAwardsList(List<Awards> awardsList) {
		this.awardsList = awardsList;
	}

	
	
	public List<ProjectInto> getProjectIntoList() {
		return projectIntoList;
	}

	public void setProjectIntoList(List<ProjectInto> projectIntoList) {
		this.projectIntoList = projectIntoList;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", stationId=" + stationId + ", principalName=" + principalName
				+ ", principalSex=" + principalSex + ", principalCorporatePosition=" + principalCorporatePosition
				+ ", principalPoliticalStatus=" + principalPoliticalStatus + ", principalSno=" + principalSno
				+ ", principalSecondaryCollegeId=" + principalSecondaryCollegeId + ", principalGradeId="
				+ principalGradeId + ", principalClass=" + principalClass + ", principalDormitory=" + principalDormitory
				+ ", principalPhone=" + principalPhone + ", principalQq=" + principalQq + ", principalEmail="
				+ principalEmail + ", principalPosition=" + principalPosition + ", principalCertificate="
				+ principalCertificate + ", principalScoreDescription=" + principalScoreDescription
				+ ", projectSetupTime=" + projectSetupTime + ", projectEnteringTime=" + projectEnteringTime
				+ ", isBusinessRegistration=" + isBusinessRegistration + ", registeredTime=" + registeredTime
				+ ", registrationCapital=" + registrationCapital + ", legalName=" + legalName + ", legalSex=" + legalSex
				+ ", legalPoliticalStatus=" + legalPoliticalStatus + ", legalBirthplace=" + legalBirthplace
				+ ", legalGrade=" + legalGrade + ", legalClazz=" + legalClazz + ", legalPosition=" + legalPosition
				+ ", legalPhone=" + legalPhone + ", legalEmail=" + legalEmail + ", manageContent=" + manageContent
				+ ", projectIndustry=" + projectIndustry + ", projectType=" + projectType + ", initialFunds="
				+ initialFunds + ", workStudyNum=" + workStudyNum + ", recentGraduatesNum=" + recentGraduatesNum
				+ ", previousGraduatesNum=" + previousGraduatesNum + ", totalEmployment=" + totalEmployment
				+ ", totalPractice=" + totalPractice + ", yearEmploymentNum=" + yearEmploymentNum + ", yearPracticeNum="
				+ yearPracticeNum + ", isEffective=" + isEffective + ", isBase=" + isBase + ", status=" + status
				+ ", baseId=" + baseId + ", managerId=" + managerId + ", teacherList=" + teacherList
				+ ", projectPersonnelList=" + projectPersonnelList + ", ProjectSubsidyList=" + ProjectSubsidyList
				+ ", awardsList=" + awardsList + ", projectIntoList=" + projectIntoList + "]";
	}

	

	
}