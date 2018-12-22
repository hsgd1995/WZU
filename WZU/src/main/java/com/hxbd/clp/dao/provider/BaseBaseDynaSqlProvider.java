package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import com.hxbd.clp.domain.basedata.base.Base;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 区域动态sql
 * @author Administrator
 *
 */
public class BaseBaseDynaSqlProvider {
	/**
	 * 插入
	 * @param base
	 * @return
	 */
	public String insert(Base base){
		return new SQL(){{
			if(base!=null){
				INSERT_INTO(RasConstants.BASE);
				if(!StringUtils.isEmpty(base.getName())){
					VALUES("name", "#{name}");
				}
				if(base.getArea()!=null){
					VALUES("area", "#{area}");
				}
				if(base.getManagerNum()!=null){
					VALUES("manager_num", "#{managerNum}");
				}
				if(base.getType()!=null){
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
				FROM(RasConstants.BASE);
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
				FROM(RasConstants.BASE);
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
			s.append(" from ").append(RasConstants.BASE).append(" where id in (");
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
	public String update(Base base){
		return new SQL(){{
			if(base!=null){
				UPDATE(RasConstants.BASE);
				if(!StringUtils.isEmpty(base.getName())){
					SET("name = #{name}");
				}
				if(base.getArea()!=null){
					SET("area = #{area}");
				}
				if(base.getManagerNum()!=null){
					SET("manager_num = #{managerNum}");
				}
				if(base.getType()!=null){
					SET("type = #{type}");
				}
			}
			WHERE(" id = #{id}");
		}}.toString();
	}
}
