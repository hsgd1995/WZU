package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import com.hxbd.clp.domain.basedata.base.Project;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 区域动态sql
 * @author Administrator
 *
 */
public class ProjectDynaSqlProvider {
	/**
	 * 插入
	 * @param project
	 * @return
	 */
	public String insert(Project project){
		return new SQL(){{
			if(project!=null){
				INSERT_INTO(RasConstants.PROJECT);
				if(!StringUtils.isEmpty(project.getName())){
					VALUES("name", "#{name}");
				}
				if(project.getProjectSetupTime()!=null){
					VALUES("project_setup_time", "#{projectSetupTime}");
				}
				if(project.getProjectEnteringTime()!=null){
					VALUES("project_entering_time", "#{projectEnteringTime}");
				}
				if(project.getIsBusinessRegistration()!=null){
					VALUES("is_business_registration", "#{isBusinessRegistration}");
				}
				if(project.getRegisteredTime()!=null){
					VALUES("registered_time", "#{registeredTime}");
				}
				if(project.getRegistrationCapital()!=null){
					VALUES("registration_capital", "#{registrationCapital}");
				}
				if(project.getProjectIndustry()!=null){
					VALUES("project_industry", "#{projectIndustry}");
				}
				if(project.getPrincipalName()!=null){
					VALUES("principal_name", "#{principalName}");
				}
				if(project.getPrincipalSex()!=null){
					VALUES("principal_sex", "#{principalSex}");
				}
				if(project.getPrincipalCorporatePosition()!=null){
					VALUES("principal_corporate_position", "#{principalCorporatePosition}");
				}
				if(project.getPrincipalPoliticalStatus()!=null){
					VALUES("principal_political_status", "#{principalPoliticalStatus}");
				}
				if(project.getPrincipalSno()!=null){
					VALUES("principal_sno", "#{principalSno}");
				}
				if(project.getPrincipalSecondaryCollegeId()!=null){
					VALUES("principal_secondary_college_id", "#{principalSecondaryCollegeId}");
				}
				if(project.getPrincipalGradeId()!=null){
					VALUES("principal_grade_id", "#{principalGradeId}");
				}
				if(project.getPrincipalClass()!=null){
					VALUES("principal_class", "#{principalClass}");
				}
				if(project.getPrincipalDormitory()!=null){
					VALUES("principal_dormitory", "#{principalDormitory}");
				}
				if(project.getPrincipalPhone()!=null){
					VALUES("principal_phone", "#{principalPhone}");
				}
				if(project.getPrincipalQq()!=null){
					VALUES("principal_qq", "#{principalQq}");
				}
				if(project.getPrincipalEmail()!=null){
					VALUES("principal_email", "#{principalEmail}");
				}
				if(project.getPrincipalPosition()!=null){
					VALUES("principal_position", "#{principalPosition}");
				}
				if(project.getPrincipalCertificate()!=null){
					VALUES("principal_certificate", "#{principalCertificate}");
				}
				if(project.getPrincipalScoreDescription()!=null){
					VALUES("principal_score_description", "#{principalScoreDescription}");
				}
				if(project.getLegalName()!=null){
					VALUES("legal_name", "#{legalName}");
				}
				if(project.getLegalSex()!=null){
					VALUES("legal_sex", "#{legalSex}");
				}
				if(project.getLegalPoliticalStatus()!=null){
					VALUES("legal_political_status", "#{legalPoliticalStatus}");
				}
				if(project.getLegalBirthplace()!=null){
					VALUES("legal_birthplace", "#{legalBirthplace}");
				}
				if(project.getLegalGrade()!=null){
					VALUES("legal_grade", "#{legalGrade}");
				}
				if(project.getLegalClazz()!=null){
					VALUES("legal_clazz", "#{legalClazz}");
				}
				if(project.getLegalPosition()!=null){
					VALUES("legal_position", "#{legalPosition}");
				}
				if(project.getLegalPhone()!=null){
					VALUES("legal_phone", "#{legalPhone}");
				}
				if(project.getLegalEmail()!=null){
					VALUES("legal_email", "#{legalEmail}");
				}
				if(project.getProjectType()!=null){
					VALUES("project_type", "#{projectType}");
				}
				if(project.getInitialFunds()!=null){
					VALUES("initial_funds", "#{initialFunds}");
				}
				if(project.getWorkStudyNum()!=null){
					VALUES("work_study_num", "#{workStudyNum}");
				}
				if(project.getRecentGraduatesNum()!=null){
					VALUES("recent_graduates_num", "#{recentGraduatesNum}");
				}
				if(project.getPreviousGraduatesNum()!=null){
					VALUES("previous_graduates_num", "#{previousGraduatesNum}");
				}
				if(project.getTotalEmployment()!=null){
					VALUES("total_employment", "#{totalEmployment}");
				}
				if(project.getTotalPractice()!=null){
					VALUES("total_practice", "#{totalPractice}");
				}
				if(project.getYearEmploymentNum()!=null){
					VALUES("year_employment_num", "#{yearEmploymentNum}");
				}
				if(project.getYearPracticeNum()!=null){
					VALUES("year_practice_num", "#{yearPracticeNum}");
				}
				if(project.getIsEffective()!=null){
					VALUES("is_effective", "#{isEffective}");
				}
				if(project.getIsBase()!=null){
					VALUES("is_base", "#{isBase}");
				}
				if(project.getStatus()!=null){
					VALUES("status", "#{status}");
				}
				if(project.getManageContent()!=null){
					VALUES("manage_content", "#{manageContent}");
				}
			}
		}}.toString();
	}
	
	/**
	 * 按检索-分页查找记录
	 * @param params
	 * @return
	 */
	public String selectByPage(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(RasConstants.PROJECT+" b"+","+RasConstants.PROJECTINTO+" p");
				String keyword = (String) params.get("keyword");
				if(keyword!=null){
					WHERE(" b.id=p.project_id ");
					WHERE(" p.job_id=#{keyword}");
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	/**
	 * 按检索-分页查找记录总数
	 * @param params
	 * @return
	 */
	public String count(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("count(*)");
				FROM(RasConstants.PROJECT+" b"+","+RasConstants.PROJECTINTO+" p");
				String keyword = (String) params.get("keyword");
				if(keyword!=null){
					WHERE(" b.id=p.project_id ");
					WHERE(" p.job_id=#{keyword}");
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}
	
	/**
	 * 批量删除
	 * @param params
	 * @return
	 */
	public String batchDelect(Map<String, Object> params){
		String sql = "";
		if(params!=null&&params.get("ids")!=null){
			StringBuffer s = new StringBuffer("delete  ");
			s.append(" from ").append(RasConstants.PROJECT).append(" where id in (");
			Integer[] ids = (Integer[]) params.get("ids");
			for (Integer integer : ids) {
				s.append(integer).append(",");
			}
			s.append(")");
			sql = s.toString().replace(",)", ")");
		}
		return sql;
	}
	
	/**
	 * 修改
	 * @param base
	 * @return
	 */
	public String update(Project project){
		return new SQL(){{
			if(project!=null){
				UPDATE(RasConstants.PROJECT);
				if(!StringUtils.isEmpty(project.getName())){
					SET("name = #{name}");
				}
			}
			WHERE(" id = #{id}");
		}}.toString();
	}
}
