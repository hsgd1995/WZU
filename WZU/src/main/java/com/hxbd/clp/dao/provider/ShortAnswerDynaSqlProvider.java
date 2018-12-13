package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.ShortAnswer;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 简答题 动态sql语句
 * @author Administrator
 *
 */
public class ShortAnswerDynaSqlProvider {
	public String selectByParma(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(RasConstants.SATABLE);
				ORDER_BY("id desc");
				ShortAnswer sa = (ShortAnswer) params.get("sa");
				if (sa != null) {
					if (sa.getName() != null && !sa.getName().equals("")) {
						WHERE(" name like CONCAT('%',#{sa.name},'%')");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	public String count(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("count(*)");
				FROM(RasConstants.SATABLE);
				ShortAnswer sa = (ShortAnswer) params.get("sa");
				if (sa != null) {
					if (sa.getName()!= null && !sa.getName().equals("")) {
						WHERE(" name like CONCAT('%',#{sa.name},'%')");
					}
					
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	/**
	 * 插入数据
	 * @param sa
	 * @return
	 */
	public String insert(ShortAnswer sa){
		return new SQL(){{
			if(sa!=null){
				INSERT_INTO(RasConstants.SATABLE);
				if(sa.getName()!=null){
					VALUES("name", "#{name}");
				}
				if(sa.getAnswer()!=null){
					VALUES("answer", "#{answer}");
				}
			}
		}}.toString();
	}
	
	/**
	 * 更新数据
	 * @param sa
	 * @return
	 */
	public String update(ShortAnswer sa){
		return new SQL(){{
			if(sa!=null){
				UPDATE(RasConstants.SATABLE);
				if(sa.getName()!=null){
					SET("name = #{name}");
				}
				if(sa.getAnswer()!=null){
					SET("answer = #{answer}");
				}
				WHERE("id = #{id}");
			}
			
		}}.toString();
	}
	
	/**
	 * 批量删除
	 * @param params
	 * @return
	 */
	public String batchDel(Map<String, Object> params){
		String sql = "";
		if(params!=null&&params.get("ids")!=null){
			StringBuffer s = new StringBuffer("delete from ");
			s.append(RasConstants.SATABLE).append(" where id in (");
			Integer[] ids = (Integer[]) params.get("ids");
			for (Integer integer : ids) {
				s.append(integer).append(",");
			}
			s.append(")");
			sql = s.toString().replace(",)", ")");
		}
		return sql;
	}
}
