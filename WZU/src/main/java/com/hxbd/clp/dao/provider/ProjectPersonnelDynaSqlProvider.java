package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.basedata.base.ProjectPersonnel;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 区域动态sql
 * @author Administrator
 *
 */
public class ProjectPersonnelDynaSqlProvider {
	/**
	 * 插入
	 * @param base
	 * @return
	 */
	public String insert(ProjectPersonnel projectPersonnel){
		return new SQL(){{
			if(projectPersonnel!=null){
				INSERT_INTO(RasConstants.PROJECTPERSONNEL);
				if(projectPersonnel!=null){
					if(projectPersonnel.getName()!=null){
						VALUES("name", "#{name}");
					}
					if(projectPersonnel.getSex()!=null){
						VALUES("sex", "#{sex}");
					}
					if(projectPersonnel.getClazz()!=null){
						VALUES("clazz", "#{clazz}");
					}
					if(projectPersonnel.getPosition()!=null){
						VALUES("position", "#{position}");
					}
					if(projectPersonnel.getPhone()!=null){
						VALUES("phone", "#{phone}");
					}
					if(projectPersonnel.getType()!=null){
						VALUES("type", "#{type}");
					}
					if(projectPersonnel.getProjectId()!=null){
						VALUES("project_id", "#{projectId}");
					}
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
				FROM(RasConstants.PROJECTPERSONNEL);
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
				FROM(RasConstants.PROJECTPERSONNEL);
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
			s.append(" from ").append(RasConstants.PROJECTPERSONNEL).append(" where id in (");
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
	public String update(ProjectPersonnel projectPersonnel){
		return new SQL(){{
			if(projectPersonnel!=null){
				UPDATE(RasConstants.PROJECTPERSONNEL);
				if(projectPersonnel.getName()!=null){
					SET("name=#{name}");
				}
				if(projectPersonnel.getSex()!=null){
					SET("sex=#{sex}");
				}
				if(projectPersonnel.getClazz()!=null){
					SET("clazz=#{clazz}");
				}
				if(projectPersonnel.getPosition()!=null){
					SET("position=#{position}");
				}
				if(projectPersonnel.getPhone()!=null){
					SET("phone=#{phone}");
				}
				if(projectPersonnel.getType()!=null){
					SET("type=#{type}");
				}
				if(projectPersonnel.getProjectId()!=null){
					SET("project_id=#{projectId}");
				}
			}
			WHERE(" id = #{id}");
		}}.toString();
	}
}
