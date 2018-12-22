package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import com.hxbd.clp.domain.basedata.Awards;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 区域动态sql
 * @author Administrator
 *
 */
public class AwardsDynaSqlProvider {
	/**
	 * 插入
	 * @param awards
	 * @return
	 */
	public String insert(Awards awards){
		return new SQL(){{
			if(awards!=null){
				INSERT_INTO(RasConstants.AWARDS);
				if(awards.getAwardTime()!=null){
					VALUES("award_time", "#{awardTime}");
				}
				if(awards.getAwardLevel()!=null){
					VALUES("award_level", "#{awardLevel}");
				}
				if(awards.getAwardName()!=null){
					VALUES("award_name", "#{awardName}");
				}
				if(awards.getLevel()!=null){
					VALUES("level", "#{level}");
				}
				if(awards.getProjectId()!=null){
					VALUES("project_id", "#{projectId}");
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
				FROM(RasConstants.AWARDS);
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
				FROM(RasConstants.AWARDS);
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
			s.append(" from ").append(RasConstants.AWARDS).append(" where id in (");
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
	public String update(Awards awards){
		return new SQL(){{
			if(awards!=null){
				UPDATE(RasConstants.AWARDS);
				if(awards.getAwardTime()!=null){
					SET("award_time=#{awardTime}");
				}
				if(awards.getAwardLevel()!=null){
					SET("award_level=#{awardLevel}");
				}
				if(awards.getAwardName()!=null){
					SET("award_name=#{awardName}");
				}
				if(awards.getLevel()!=null){
					SET("level=#{level}");
				}
				if(awards.getProjectId()!=null){
					SET("project_id=#{project_id}");
				}
			}
			WHERE(" id = #{id}");
		}}.toString();
	}
}
