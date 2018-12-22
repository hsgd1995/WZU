package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import com.hxbd.clp.domain.bus.base.BaseInto;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 区域动态sql
 * @author Administrator
 *
 */
public class BaseBaseIntoDynaSqlProvider {
	/**
	 * 插入
	 * @param baseInto
	 * @return
	 */
	public String insert(BaseInto baseInto){
		return new SQL(){{
			if(baseInto!=null){
				INSERT_INTO(RasConstants.BASEINTO);
				if(!StringUtils.isEmpty(baseInto.getName())){
					VALUES("name", "#{name}");
				}
				if(!StringUtils.isEmpty(baseInto.getState())){
					VALUES("state", "#{state}");
				}
				if(baseInto.getStartTime()!=null){
					VALUES("startTime", "#{startTime}");
				}
				if(baseInto.getEndTime()!=null){
					VALUES("endTime", "#{endTime}");
				}
				if(baseInto.getBase()!=null&&baseInto.getBase().getId()!=null){
					VALUES("base_id", "#{base.id}");
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
				FROM(RasConstants.BASEINTO);
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
				FROM(RasConstants.BASEINTO);
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
			s.append(" from ").append(RasConstants.BASEINTO).append(" where id in (");
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
	public String update(BaseInto baseInto){
		return new SQL(){{
			if(baseInto!=null){
				UPDATE(RasConstants.BASEINTO);
				if(!StringUtils.isEmpty(baseInto.getName())){
					SET("name = #{name}");
				}
				if(!StringUtils.isEmpty(baseInto.getState())){
					SET("state=#{state}");
				}
				if(baseInto.getStartTime()!=null){
					SET("startTime=#{startTime}");
				}
				if(baseInto.getEndTime()!=null){
					SET("endTime=#{endTime}");
				}
				if(baseInto.getBase()!=null&&baseInto.getBase().getId()!=null){
					SET("base_id=#{base.id}");
				}
			}
			WHERE(" id = #{id}");
		}}.toString();
	}
}
