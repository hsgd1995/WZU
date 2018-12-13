package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import com.hxbd.clp.domain.Role;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 角色动态sql
 * @author Administrator
 *
 */
public class RoleDynaSqlProvider {
	/**
	 * 插入
	 * @param role
	 * @return
	 */
	public String insert(Role role){
		return new SQL(){{
			if(role!=null){
				INSERT_INTO(RasConstants.SYSROLE);
				if(!StringUtils.isEmpty(role.getName())){
					VALUES("name", "#{name}");
				}
				if(role.getOrgId()!=null){
					VALUES("org_id", "#{orgId}");
				}
				if(!StringUtils.isEmpty(role.getState())){
					VALUES("state", "#{state}");
				}
				if(!StringUtils.isEmpty(role.getCreateTime())){
					VALUES("create_time", "#{createTime}");
				}
				if(role.getCreaterId()!=null){
					VALUES("creater", "#{createrId}");
				}
				if(role.getType()!=null){
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
	public String selectByParma(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(RasConstants.SYSROLE);
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
				FROM(RasConstants.SYSROLE);
				ORDER_BY(" id desc ");
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}
	
	/**
	 * 批量停用
	 * @param params
	 * @return
	 */
	public String batchDisable(Map<String, Object> params){
		String sql = "";
		if(params!=null&&params.get("ids")!=null){
			StringBuffer s = new StringBuffer("update ");
			s.append(RasConstants.SYSROLE).append(" set type = 0").append(" where id in (");
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
	 * 批量启用
	 * @param params
	 * @return
	 */
	public String batchEnable(Map<String, Object> params){
		String sql = "";
		if(params!=null&&params.get("ids")!=null){
			StringBuffer s = new StringBuffer("update ");
			s.append(RasConstants.SYSROLE).append(" set type = 1").append(" where id in (");
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
	 * @param role
	 * @return
	 */
	public String update(Role role){
		return new SQL(){{
			if(role!=null){
				UPDATE(RasConstants.SYSROLE);
				if(!StringUtils.isEmpty(role.getName())){
					SET("name = #{name}");
				}
				if(role.getOrgId()!=null){
					SET("org_id = #{orgId}");
				}
				if(!StringUtils.isEmpty(role.getState())){
					SET("state =  #{state}");
				}
				if(!StringUtils.isEmpty(role.getCreateTime())){
					SET("create_time = #{createTime}");
				}
				if(role.getType()!=null){
					SET("type = #{type}");
				}
			}
			WHERE(" id = #{id}");
		}}.toString();
	}
}
