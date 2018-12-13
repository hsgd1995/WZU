package com.hxbd.clp.domain.basedata.base;

import java.io.Serializable;

/**
 * 基地信息
 */
public class Base implements Serializable {
    private Integer id;//双创基地信息

    private String name;//双创基地名称

    private Double area;//场所面积（平方米）

    private Integer stationNum;//提供工位数

    private Integer managerNum;//管理服务人数

    private Double baseInvestmentTotal;//基地累计获得投资金额总数

    private Integer traineeNum;//当前就业见习基地数量

    private Integer type;//基地类型【下拉菜单选项：1-园区型，2-楼宇型】

    private Double utilization;//-场地利用率

    private Double totalRevenue;//众创空间总收入

    private Double operatingCost;//众创空间运营成本

    private Integer totalTeacherNum;//双创导师总人数

    private Integer fullTimeTeacherNum;//专职双创导师人数

    private Integer partTimeTeacherNum;//兼职双创导师人数

    private Integer registeredCompaniesNum;//现有已注册企业数量

    private Integer projectGroupsNum;//现有项目组数量

    private Integer totalRegisteredCompanies;//累计已注册企业数

    private Integer totalProjectGroups;//-累计项目组数量

    private Double investmentTotal;//累计总投资额

    private Double totalTurnover;//累计总营业额

    private Double siteAndHydropower;//累计享受场地和水电补贴

    private Double ventureSubsidy;//累计享受一次性创业补贴

    private Double employmentDifficulties;//累计享受吸纳就业困难群体一次性补助

    private Double socialInsuranceSubsidy;//累计享受社会保险补贴

    private Double entrepreneurialSecuredLoan;//累计获得创业担保贷款总额

    private Integer employmentInternship;//当年累计吸纳就业见习人数

    private Double employmentSubsidyAmount;//当年累计发放就业见习补贴金额

    private Integer cumulativeEmployment;//累计带动就业人数

    private Double incubationRate;//孵化到期出园率

    private Integer directEmployment;//直接就业人数

    private Integer hatchingExitsNum;//-孵化到期出园数量

    private Integer entitiesNum;//当年实体数量

    private Integer startupCompany;//当年初创企业数量

    private Integer totalStartupCompany;//累计初创企业数量

    private Integer teamFinancingNum;//当年获得投融资的团队及企业的数量

    private Integer totalTeamFinancing;//累计获得投融资的团队及企业的数量

    private Double socialInvestment;//团队及企业当年获得投资总额-社会投资

    private Double selfInvestment;//团队及企业当年获得投资总额-众创空间自身投资

    private Double totalTeamInvestment;//团队及企业累计获得投资总额

    private Integer collegeStudentsNum;//大学生创业数量

    private Integer entrepreneursNum;//科技人员创业数量

    private Integer consecutiveVenturesNum;//连续创业数量

    private Integer validIntellectualNum;//拥有的有效知识产权数量

    private Integer newlyRegisteredNum;//当年新注册企业数量

    private String mainBusiness;//创业实体简况、主营业务

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

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Integer getStationNum() {
        return stationNum;
    }

    public void setStationNum(Integer stationNum) {
        this.stationNum = stationNum;
    }

    public Integer getManagerNum() {
        return managerNum;
    }

    public void setManagerNum(Integer managerNum) {
        this.managerNum = managerNum;
    }

    public Double getBaseInvestmentTotal() {
        return baseInvestmentTotal;
    }

    public void setBaseInvestmentTotal(Double baseInvestmentTotal) {
        this.baseInvestmentTotal = baseInvestmentTotal;
    }

    public Integer getTraineeNum() {
        return traineeNum;
    }

    public void setTraineeNum(Integer traineeNum) {
        this.traineeNum = traineeNum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getUtilization() {
        return utilization;
    }

    public void setUtilization(Double utilization) {
        this.utilization = utilization;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Double getOperatingCost() {
        return operatingCost;
    }

    public void setOperatingCost(Double operatingCost) {
        this.operatingCost = operatingCost;
    }

    public Integer getTotalTeacherNum() {
        return totalTeacherNum;
    }

    public void setTotalTeacherNum(Integer totalTeacherNum) {
        this.totalTeacherNum = totalTeacherNum;
    }

    public Integer getFullTimeTeacherNum() {
        return fullTimeTeacherNum;
    }

    public void setFullTimeTeacherNum(Integer fullTimeTeacherNum) {
        this.fullTimeTeacherNum = fullTimeTeacherNum;
    }

    public Integer getPartTimeTeacherNum() {
        return partTimeTeacherNum;
    }

    public void setPartTimeTeacherNum(Integer partTimeTeacherNum) {
        this.partTimeTeacherNum = partTimeTeacherNum;
    }

    public Integer getRegisteredCompaniesNum() {
        return registeredCompaniesNum;
    }

    public void setRegisteredCompaniesNum(Integer registeredCompaniesNum) {
        this.registeredCompaniesNum = registeredCompaniesNum;
    }

    public Integer getProjectGroupsNum() {
        return projectGroupsNum;
    }

    public void setProjectGroupsNum(Integer projectGroupsNum) {
        this.projectGroupsNum = projectGroupsNum;
    }

    public Integer getTotalRegisteredCompanies() {
        return totalRegisteredCompanies;
    }

    public void setTotalRegisteredCompanies(Integer totalRegisteredCompanies) {
        this.totalRegisteredCompanies = totalRegisteredCompanies;
    }

    public Integer getTotalProjectGroups() {
        return totalProjectGroups;
    }

    public void setTotalProjectGroups(Integer totalProjectGroups) {
        this.totalProjectGroups = totalProjectGroups;
    }

    public Double getInvestmentTotal() {
        return investmentTotal;
    }

    public void setInvestmentTotal(Double investmentTotal) {
        this.investmentTotal = investmentTotal;
    }

    public Double getTotalTurnover() {
        return totalTurnover;
    }

    public void setTotalTurnover(Double totalTurnover) {
        this.totalTurnover = totalTurnover;
    }

    public Double getSiteAndHydropower() {
        return siteAndHydropower;
    }

    public void setSiteAndHydropower(Double siteAndHydropower) {
        this.siteAndHydropower = siteAndHydropower;
    }

    public Double getVentureSubsidy() {
        return ventureSubsidy;
    }

    public void setVentureSubsidy(Double ventureSubsidy) {
        this.ventureSubsidy = ventureSubsidy;
    }

    public Double getEmploymentDifficulties() {
        return employmentDifficulties;
    }

    public void setEmploymentDifficulties(Double employmentDifficulties) {
        this.employmentDifficulties = employmentDifficulties;
    }

    public Double getSocialInsuranceSubsidy() {
        return socialInsuranceSubsidy;
    }

    public void setSocialInsuranceSubsidy(Double socialInsuranceSubsidy) {
        this.socialInsuranceSubsidy = socialInsuranceSubsidy;
    }

    public Double getEntrepreneurialSecuredLoan() {
        return entrepreneurialSecuredLoan;
    }

    public void setEntrepreneurialSecuredLoan(Double entrepreneurialSecuredLoan) {
        this.entrepreneurialSecuredLoan = entrepreneurialSecuredLoan;
    }

    public Integer getEmploymentInternship() {
        return employmentInternship;
    }

    public void setEmploymentInternship(Integer employmentInternship) {
        this.employmentInternship = employmentInternship;
    }

    public Double getEmploymentSubsidyAmount() {
        return employmentSubsidyAmount;
    }

    public void setEmploymentSubsidyAmount(Double employmentSubsidyAmount) {
        this.employmentSubsidyAmount = employmentSubsidyAmount;
    }

    public Integer getCumulativeEmployment() {
        return cumulativeEmployment;
    }

    public void setCumulativeEmployment(Integer cumulativeEmployment) {
        this.cumulativeEmployment = cumulativeEmployment;
    }

    public Double getIncubationRate() {
        return incubationRate;
    }

    public void setIncubationRate(Double incubationRate) {
        this.incubationRate = incubationRate;
    }

    public Integer getDirectEmployment() {
        return directEmployment;
    }

    public void setDirectEmployment(Integer directEmployment) {
        this.directEmployment = directEmployment;
    }

    public Integer getHatchingExitsNum() {
        return hatchingExitsNum;
    }

    public void setHatchingExitsNum(Integer hatchingExitsNum) {
        this.hatchingExitsNum = hatchingExitsNum;
    }

    public Integer getEntitiesNum() {
        return entitiesNum;
    }

    public void setEntitiesNum(Integer entitiesNum) {
        this.entitiesNum = entitiesNum;
    }

    public Integer getStartupCompany() {
        return startupCompany;
    }

    public void setStartupCompany(Integer startupCompany) {
        this.startupCompany = startupCompany;
    }

    public Integer getTotalStartupCompany() {
        return totalStartupCompany;
    }

    public void setTotalStartupCompany(Integer totalStartupCompany) {
        this.totalStartupCompany = totalStartupCompany;
    }

    public Integer getTeamFinancingNum() {
        return teamFinancingNum;
    }

    public void setTeamFinancingNum(Integer teamFinancingNum) {
        this.teamFinancingNum = teamFinancingNum;
    }

    public Integer getTotalTeamFinancing() {
        return totalTeamFinancing;
    }

    public void setTotalTeamFinancing(Integer totalTeamFinancing) {
        this.totalTeamFinancing = totalTeamFinancing;
    }

    public Double getSocialInvestment() {
        return socialInvestment;
    }

    public void setSocialInvestment(Double socialInvestment) {
        this.socialInvestment = socialInvestment;
    }

    public Double getSelfInvestment() {
        return selfInvestment;
    }

    public void setSelfInvestment(Double selfInvestment) {
        this.selfInvestment = selfInvestment;
    }

    public Double getTotalTeamInvestment() {
        return totalTeamInvestment;
    }

    public void setTotalTeamInvestment(Double totalTeamInvestment) {
        this.totalTeamInvestment = totalTeamInvestment;
    }

    public Integer getCollegeStudentsNum() {
        return collegeStudentsNum;
    }

    public void setCollegeStudentsNum(Integer collegeStudentsNum) {
        this.collegeStudentsNum = collegeStudentsNum;
    }

    public Integer getEntrepreneursNum() {
        return entrepreneursNum;
    }

    public void setEntrepreneursNum(Integer entrepreneursNum) {
        this.entrepreneursNum = entrepreneursNum;
    }

    public Integer getConsecutiveVenturesNum() {
        return consecutiveVenturesNum;
    }

    public void setConsecutiveVenturesNum(Integer consecutiveVenturesNum) {
        this.consecutiveVenturesNum = consecutiveVenturesNum;
    }

    public Integer getValidIntellectualNum() {
        return validIntellectualNum;
    }

    public void setValidIntellectualNum(Integer validIntellectualNum) {
        this.validIntellectualNum = validIntellectualNum;
    }

    public Integer getNewlyRegisteredNum() {
        return newlyRegisteredNum;
    }

    public void setNewlyRegisteredNum(Integer newlyRegisteredNum) {
        this.newlyRegisteredNum = newlyRegisteredNum;
    }

    public String getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness == null ? null : mainBusiness.trim();
    }

    @Override
    public String toString() {
        return "Base{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", area=" + area +
                ", stationNum=" + stationNum +
                ", managerNum=" + managerNum +
                ", baseInvestmentTotal=" + baseInvestmentTotal +
                ", traineeNum=" + traineeNum +
                ", type=" + type +
                ", utilization=" + utilization +
                ", totalRevenue=" + totalRevenue +
                ", operatingCost=" + operatingCost +
                ", totalTeacherNum=" + totalTeacherNum +
                ", fullTimeTeacherNum=" + fullTimeTeacherNum +
                ", partTimeTeacherNum=" + partTimeTeacherNum +
                ", registeredCompaniesNum=" + registeredCompaniesNum +
                ", projectGroupsNum=" + projectGroupsNum +
                ", totalRegisteredCompanies=" + totalRegisteredCompanies +
                ", totalProjectGroups=" + totalProjectGroups +
                ", investmentTotal=" + investmentTotal +
                ", totalTurnover=" + totalTurnover +
                ", siteAndHydropower=" + siteAndHydropower +
                ", ventureSubsidy=" + ventureSubsidy +
                ", employmentDifficulties=" + employmentDifficulties +
                ", socialInsuranceSubsidy=" + socialInsuranceSubsidy +
                ", entrepreneurialSecuredLoan=" + entrepreneurialSecuredLoan +
                ", employmentInternship=" + employmentInternship +
                ", employmentSubsidyAmount=" + employmentSubsidyAmount +
                ", cumulativeEmployment=" + cumulativeEmployment +
                ", incubationRate=" + incubationRate +
                ", directEmployment=" + directEmployment +
                ", hatchingExitsNum=" + hatchingExitsNum +
                ", entitiesNum=" + entitiesNum +
                ", startupCompany=" + startupCompany +
                ", totalStartupCompany=" + totalStartupCompany +
                ", teamFinancingNum=" + teamFinancingNum +
                ", totalTeamFinancing=" + totalTeamFinancing +
                ", socialInvestment=" + socialInvestment +
                ", selfInvestment=" + selfInvestment +
                ", totalTeamInvestment=" + totalTeamInvestment +
                ", collegeStudentsNum=" + collegeStudentsNum +
                ", entrepreneursNum=" + entrepreneursNum +
                ", consecutiveVenturesNum=" + consecutiveVenturesNum +
                ", validIntellectualNum=" + validIntellectualNum +
                ", newlyRegisteredNum=" + newlyRegisteredNum +
                ", mainBusiness='" + mainBusiness + '\'' +
                '}';
    }
}