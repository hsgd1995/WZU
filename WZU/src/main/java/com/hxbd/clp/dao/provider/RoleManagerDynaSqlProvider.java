package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import com.hxbd.clp.domain.RoleManager;
import com.hxbd.clp.utils.common.RasConstants;

public class RoleManagerDynaSqlProvider {
	/**
	 * 按检索条件和分页条件查询记录总数
	 * @param params
	 * @return
	 */
	public String count(Map<String, Object> params) {
		String sql =  new SQL(){
			{
				RoleManager roleManager = (RoleManager) params.get("roleManager");
				SELECT("count(*)");
				String dynaSql = RasConstants.SYSROLEMANAGER+" s ";
				if (roleManager != null) {
					if(roleManager.getRole()!=null && !StringUtils.isEmpty(roleManager.getRole().getName())){
						dynaSql += " , " + RasConstants.SYSROLE + " r";
					}
					if(roleManager.getManager()!=null && !StringUtils.isEmpty(roleManager.getManager().getUsername())){
						dynaSql += " , " + RasConstants.MANAGERTABLE + " m";
					}
				}
				FROM(dynaSql);
				
				if(roleManager != null){
					if(roleManager.getRole()!=null && !StringUtils.isEmpty(roleManager.getRole().getName())){
						WHERE(" s.role_id=r.id and r.name like  CONCAT('%',#{roleManager.role.name},'%') ");
					}
					if(roleManager.getManager()!=null && !StringUtils.isEmpty(roleManager.getManager().getUsername())){
						WHERE(" s.manage_id=m.id and m.username like  CONCAT('%',#{roleManager.manager.username},'%') ");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		System.err.println(sql);
		return sql;
	}
	
	/**
	 * 按检索条件和分页条件查询记录
	 * @param params
	 * @return
	 */
	public String selectByPage(Map<String, Object> params) {
		String sql =  new SQL(){
			{
				RoleManager roleManager = (RoleManager) params.get("roleManager");
				SELECT("s.*");
				String dynaSql = RasConstants.SYSROLEMANAGER+" s ";
				if (roleManager != null) {
					if(roleManager.getRole()!=null && !StringUtils.isEmpty(roleManager.getRole().getName())){
						dynaSql += " , " + RasConstants.SYSROLE + " r";
					}
					if(roleManager.getManager()!=null && !StringUtils.isEmpty(roleManager.getManager().getUsername())){
						dynaSql += " , " + RasConstants.MANAGERTABLE + " m";
					}
				}
				FROM(dynaSql);
				
				if(roleManager != null){
					if(roleManager.getRole()!=null && !StringUtils.isEmpty(roleManager.getRole().getName())){
						WHERE(" s.role_id=r.id and r.name like  CONCAT('%',#{roleManager.role.name},'%') ");
					}
					if(roleManager.getManager()!=null && !StringUtils.isEmpty(roleManager.getManager().getUsername())){
						WHERE(" s.manage_id=m.id and m.username like  CONCAT('%',#{roleManager.manager.username},'%') ");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		System.err.println(sql);
		return sql;
	}
	
	/**
	 * 插入数据
	 * @param roleManager
	 * @return
	 */
	public String insert(RoleManager roleManager){
		return new SQL(){{
			if(roleManager!=null){
				INSERT_INTO(RasConstants.SYSROLEMANAGER);
				if(roleManager.getManager()!=null&&roleManager.getManager().getId()!=null){
					VALUES("manage_id", "#{manager.id}");
				}
				if(roleManager.getRole()!=null&&roleManager.getRole().getId()!=null){
					VALUES("role_id", "#{role.id}");
				}
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
			s.append(RasConstants.SYSROLEMANAGER).append(" where id in (");
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
