package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import com.hxbd.clp.domain.basedata.base.ProjectSubsidy;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 区域动态sql
 * @author Administrator
 *
 */
public class ProjectSubsidyDynaSqlProvider {
	/**
	 * 插入
	 * @param base
	 * @return
	 */
	public String insert(ProjectSubsidy projectSubsidy){
		return new SQL(){{
			if(projectSubsidy!=null){
				INSERT_INTO(RasConstants.PROJECTSUBSIDY);
				if(projectSubsidy.getFromm()!=null){
					VALUES("fromm", "#{fromm}");
				}
				if(projectSubsidy.getTtime()!=null){
					VALUES("ttime", "#{ttime}");
				}
				
				if(projectSubsidy.getMoney()!=null){
					VALUES("money", "#{money}");
				}
				if(projectSubsidy.getProjectId()!=null){
					VALUES("project_id", "#{projectId}");
				}
				if(projectSubsidy.getType()!=null){
					VALUES("type", "#{type}");
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
				FROM(RasConstants.PROJECTSUBSIDY);
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
				FROM(RasConstants.PROJECTSUBSIDY);
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
			s.append(" from ").append(RasConstants.PROJECTSUBSIDY).append(" where id in (");
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
	public String update(ProjectSubsidy projectSubsidy){
		return new SQL(){{
			if(projectSubsidy!=null){
				UPDATE(RasConstants.PROJECTSUBSIDY);
				if(projectSubsidy.getTtime()!=null){
					SET("ttime=#{ttime}");
				}
				if(projectSubsidy.getFromm()!=null){
					SET("from={from}");
				}
				if(projectSubsidy.getMoney()!=null){
					SET("money=#{money}");
				}
				if(projectSubsidy.getProjectId()!=null){
					SET("project_id=#{projectId}");
				}
				if(projectSubsidy.getType()!=null){
					SET("type=#{type}");
				}
			}
			WHERE(" id = #{id}");
		}}.toString();
	}
}
