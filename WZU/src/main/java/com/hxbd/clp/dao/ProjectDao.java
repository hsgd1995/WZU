package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.FetchType;

import com.hxbd.clp.dao.provider.ProjectDynaSqlProvider;
import com.hxbd.clp.domain.basedata.base.Project;
import com.hxbd.clp.domain.bus.base.ProjectInto;
import com.hxbd.clp.utils.common.RasConstants;

public interface ProjectDao {

	@Select(" select * from "+RasConstants.PROJECT+" where 1 = 1 ")
	@Results({
		@Result(column="manager_num",property="managerNum",javaType=java.lang.Integer.class)
	})
	List<Project> selectAll();
	
	
	@Select(" select * from "+RasConstants.PROJECT+" where id = #{id} ")
	@Results({
		@Result(column="id",property="id",javaType=java.lang.Integer.class),
		@Result(column="base_id",property="base",javaType=com.hxbd.clp.domain.basedata.base.Base.class,
				one=@One(select="com.hxbd.clp.dao.BaseBaseDao.selectById",fetchType=FetchType.EAGER)),
		@Result(column="principal_name",property="principalName",javaType=java.lang.String.class),
		@Result(column="principal_sex",property="principalSex",javaType=java.lang.Integer.class),
		@Result(column="principal_corporate_position",property="principalCorporatePosition",javaType=java.lang.String.class),
		@Result(column="principal_political_status",property="principalPoliticalStatus",javaType=java.lang.Integer.class),
		@Result(column="principal_sno",property="principalSno",javaType=java.lang.String.class),
		@Result(column="principal_secondary_college_id",property="principalSecondaryCollegeId",javaType=java.lang.Integer.class),
		@Result(column="principal_grade_id",property="principalGradeId",javaType=java.lang.Integer.class),
		@Result(column="principal_class",property="principalClass",javaType=java.lang.String.class),
		@Result(column="principal_dormitory",property="principalDormitory",javaType=java.lang.String.class),
		@Result(column="principal_phone",property="principalPhone",javaType=java.lang.String.class),
		@Result(column="principal_qq",property="principalQq",javaType=java.lang.String.class),
		@Result(column="principal_email",property="principalEmail",javaType=java.lang.String.class),
		@Result(column="principal_position",property="principalPosition",javaType=java.lang.String.class),
		@Result(column="principal_certificate",property="principalCertificate",javaType=java.lang.String.class),
		@Result(column="principal_score_description",property="principalScoreDescription",javaType=java.lang.String.class),
		@Result(column="project_setup_time",property="projectSetupTime",javaType=java.util.Date.class),
		@Result(column="project_entering_time",property="projectEnteringTime",javaType=java.util.Date.class),
		@Result(column="is_business_registration",property="isBusinessRegistration",javaType=java.lang.Integer.class),
		@Result(column="registered_time",property="registeredTime",javaType=java.util.Date.class),
		@Result(column="registration_capital",property="registrationCapital",javaType=java.lang.Double.class),
		@Result(column="legal_name",property="legalName",javaType=java.lang.String.class),
		@Result(column="legal_sex",property="legalSex",javaType=java.lang.Integer.class),
		@Result(column="legal_political_status",property="legalPoliticalStatus",javaType=java.lang.Integer.class),
		@Result(column="legal_birthplace",property="legalBirthplace",javaType=java.lang.String.class),
		@Result(column="legal_grade",property="legalGrade",javaType=java.lang.String.class),
		@Result(column="legal_clazz",property="legalClazz",javaType=java.lang.String.class),
		@Result(column="legal_position",property="legalPosition",javaType=java.lang.String.class),
		@Result(column="legal_phone",property="legalPhone",javaType=java.lang.String.class),
		@Result(column="legal_email",property="legalEmail",javaType=java.lang.String.class),
		@Result(column="manage_content",property="manageContent",javaType=java.lang.String.class),
		@Result(column="project_industry",property="projectIndustry",javaType=java.lang.String.class),
		@Result(column="project_type",property="projectType",javaType=java.lang.String.class),
		@Result(column="initial_funds",property="initialFunds",javaType=java.lang.Double.class),
		@Result(column="work_study_num",property="workStudyNum",javaType=java.lang.Integer.class),
		@Result(column="recent_graduates_num",property="recentGraduatesNum",javaType=java.lang.Integer.class),
		@Result(column="previous_graduates_num",property="previousGraduatesNum",javaType=java.lang.Integer.class),
		@Result(column="total_employment",property="totalEmployment",javaType=java.lang.Integer.class),
		@Result(column="total_practice",property="totalPractice",javaType=java.lang.Integer.class),
		@Result(column="year_employment_num",property="yearEmploymentNum",javaType=java.lang.Integer.class),
		@Result(column="year_practice_num",property="yearPracticeNum",javaType=java.lang.Integer.class),
		@Result(column="is_effective",property="isEffective",javaType=java.lang.Integer.class),
		@Result(column="is_base",property="isBase",javaType=java.lang.Integer.class),
		@Result(column="status",property="status",javaType=java.lang.Integer.class),
		@Result(column="manager_id",property="managerId",javaType=java.lang.Integer.class),
		@Result(column="id",property="projectIntoList",javaType=java.util.List.class,
				many = @Many(select ="com.hxbd.clp.dao.ProjectIntoDao.selectByProjectId"))
	})
	Project selectById(Integer id);

	@SelectProvider(type=ProjectDynaSqlProvider.class,method="count")
	int count(Map<String, Object> params);

	@SelectProvider(type=ProjectDynaSqlProvider.class,method="selectByPage")
		@Results({
			@Result(column="base_id",property="base",javaType=com.hxbd.clp.domain.basedata.base.Base.class,
					one=@One(select="com.hxbd.clp.dao.BaseBaseDao.selectById",fetchType=FetchType.EAGER)),
			@Result(column="principal_name",property="principalName",javaType=java.lang.String.class),
			@Result(column="principal_sex",property="principalSex",javaType=java.lang.Integer.class),
			@Result(column="principal_corporate_position",property="principalCorporatePosition",javaType=java.lang.String.class),
			@Result(column="principal_political_status",property="principalPoliticalStatus",javaType=java.lang.Integer.class),
			@Result(column="principal_sno",property="principalSno",javaType=java.lang.String.class),
			@Result(column="principal_secondary_college_id",property="principalSecondaryCollegeId",javaType=java.lang.Integer.class),
			@Result(column="principal_grade_id",property="principalGradeId",javaType=java.lang.Integer.class),
			@Result(column="principal_class",property="principalClass",javaType=java.lang.String.class),
			@Result(column="principal_dormitory",property="principalDormitory",javaType=java.lang.String.class),
			@Result(column="principal_phone",property="principalPhone",javaType=java.lang.String.class),
			@Result(column="principal_qq",property="principalQq",javaType=java.lang.String.class),
			@Result(column="principal_email",property="principalEmail",javaType=java.lang.String.class),
			@Result(column="principal_position",property="principalPosition",javaType=java.lang.String.class),
			@Result(column="principal_certificate",property="principalCertificate",javaType=java.lang.String.class),
			@Result(column="principal_score_description",property="principalScoreDescription",javaType=java.lang.String.class),
			@Result(column="project_setup_time",property="projectSetupTime",javaType=java.util.Date.class),
			@Result(column="project_entering_time",property="projectEnteringTime",javaType=java.util.Date.class),
			@Result(column="is_business_registration",property="isBusinessRegistration",javaType=java.lang.Integer.class),
			@Result(column="registered_time",property="registeredTime",javaType=java.util.Date.class),
			@Result(column="registration_capital",property="registrationCapital",javaType=java.lang.Double.class),
			@Result(column="legal_name",property="legalName",javaType=java.lang.String.class),
			@Result(column="legal_sex",property="legalSex",javaType=java.lang.Integer.class),
			@Result(column="legal_political_status",property="legalPoliticalStatus",javaType=java.lang.Integer.class),
			@Result(column="legal_birthplace",property="legalBirthplace",javaType=java.lang.String.class),
			@Result(column="legal_grade",property="legalGrade",javaType=java.lang.String.class),
			@Result(column="legal_clazz",property="legalClazz",javaType=java.lang.String.class),
			@Result(column="legal_position",property="legalPosition",javaType=java.lang.String.class),
			@Result(column="legal_phone",property="legalPhone",javaType=java.lang.String.class),
			@Result(column="legal_email",property="legalEmail",javaType=java.lang.String.class),
			@Result(column="manage_content",property="manageContent",javaType=java.lang.String.class),
			@Result(column="project_industry",property="projectIndustry",javaType=java.lang.String.class),
			@Result(column="project_type",property="projectType",javaType=java.lang.String.class),
			@Result(column="initial_funds",property="initialFunds",javaType=java.lang.Double.class),
			@Result(column="work_study_num",property="workStudyNum",javaType=java.lang.Integer.class),
			@Result(column="recent_graduates_num",property="recentGraduatesNum",javaType=java.lang.Integer.class),
			@Result(column="previous_graduates_num",property="previousGraduatesNum",javaType=java.lang.Integer.class),
			@Result(column="total_employment",property="totalEmployment",javaType=java.lang.Integer.class),
			@Result(column="total_practice",property="totalPractice",javaType=java.lang.Integer.class),
			@Result(column="year_employment_num",property="yearEmploymentNum",javaType=java.lang.Integer.class),
			@Result(column="year_practice_num",property="yearPracticeNum",javaType=java.lang.Integer.class),
			@Result(column="is_effective",property="isEffective",javaType=java.lang.Integer.class),
			@Result(column="is_base",property="isBase",javaType=java.lang.Integer.class),
			@Result(column="status",property="status",javaType=java.lang.Integer.class),
			@Result(column="manager_id",property="managerId",javaType=java.lang.Integer.class)
		})
	List<Project> selectByPage(Map<String, Object> params);

	@UpdateProvider(type=ProjectDynaSqlProvider.class,method="update")
	void update(Project base);

	@InsertProvider(type=ProjectDynaSqlProvider.class,method="insert")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(Project base);

	@DeleteProvider(type=ProjectDynaSqlProvider.class,method="batchDelect")
	void del(Map<String, Object> params);
}
