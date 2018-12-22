package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import com.hxbd.clp.domain.bus.base.ProjectInto;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 动态sql
 * @author Administrator
 *
 */
public class ProjectIntoDynaSqlProvider {
	/**
	 * 插入
	 * @param projectInto
	 * @return
	 */
	public String insert(ProjectInto projectInto){
		return new SQL(){{
			if(projectInto!=null){
				INSERT_INTO(RasConstants.PROJECTINTO);
				if(projectInto.getManagerId()!=null){
					VALUES("manager_id", "#{managerId}");
				}
				if(projectInto.getJobId()!=null){
					VALUES("job_id", "#{jobId}");
				}
				if(projectInto.getProjectId()!=null){
					VALUES("project_id", "#{projectId}");
				}
				if(projectInto.getIsAgree()!=null){
					VALUES("is_agree", "#{isAgree}");
				}
				if(projectInto.getOpinion()!=null){
					VALUES("opinion", "#{opinion}");
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
				FROM(RasConstants.PROJECTINTO);
				ORDER_BY(" id desc ");
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
				FROM(RasConstants.PROJECTINTO);
				ORDER_BY(" id desc ");
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
			s.append(" from ").append(RasConstants.PROJECTINTO).append(" where id in (");
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
	public String update(ProjectInto projectInto){
		return new SQL(){{
			if(projectInto!=null){
				UPDATE(RasConstants.PROJECTINTO);
				if(projectInto.getManagerId()!=null){
					SET("manager_id=#{managerId}");
				}
				if(projectInto.getJobId()!=null){
					SET("job_id=#{jobId}");
				}
				if(projectInto.getProjectId()!=null){
					SET("project_id=#{projectId}");
				}
				if(projectInto.getIsAgree()!=null){
					SET("is_agree=#{isAgree}");
				}
				if(projectInto.getOpinion()!=null){
					SET("opinion=#{opinion}");
				}
			}
			WHERE(" id = #{id}");
		}}.toString();
	}
}
