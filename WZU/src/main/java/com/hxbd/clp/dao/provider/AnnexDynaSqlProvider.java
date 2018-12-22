package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import com.hxbd.clp.domain.bus.base.Annex;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 区域动态sql
 * @author Administrator
 *
 */
public class AnnexDynaSqlProvider {
	/**
	 * 插入
	 * @param base
	 * @return
	 */
	public String insert(Annex base){
		return new SQL(){{
			if(base!=null){
				INSERT_INTO(RasConstants.ANNEX);
				if(!StringUtils.isEmpty(base.getName())){
					VALUES("name", "#{name}");
				}
				if(base.getProjectId()!=null){
					VALUES("project_id", "#{projectId}");
				}
				if(base.getCompetitionId()!=null){
					VALUES("competition_id", "#{competitionId}");
				}
				if(base.getOpenId()!=null){
					VALUES("open_id", "#{openId}");
				}
				if(base.getEndId()!=null){
					VALUES("end_id", "#{endId}");
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
				FROM(RasConstants.ANNEX);
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
				FROM(RasConstants.ANNEX);
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
			s.append(" from ").append(RasConstants.ANNEX).append(" where id in (");
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
	public String update(Annex base){
		return new SQL(){{
			if(base!=null){
				UPDATE(RasConstants.ANNEX);
				if(!StringUtils.isEmpty(base.getName())){
					SET("name=#{name}");
				}
				if(base.getProjectId()!=null){
					SET("project_id=#{projectId}");
				}
				if(base.getCompetitionId()!=null){
					SET("competition_id=#{competitionId}");
				}
				if(base.getOpenId()!=null){
					SET("open_id=#{openId}");
				}
				if(base.getEndId()!=null){
					SET("end_id=#{endId}");
				}
			}
			WHERE(" id = #{id}");
		}}.toString();
	}
}
