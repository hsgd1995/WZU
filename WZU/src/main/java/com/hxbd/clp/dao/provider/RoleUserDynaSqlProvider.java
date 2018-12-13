package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import com.hxbd.clp.domain.RoleUser;
import com.hxbd.clp.utils.common.RasConstants;

public class RoleUserDynaSqlProvider {
	/**
	 * 按检索条件和分页条件查询记录总数
	 * @param params
	 * @return
	 */
	public String count(Map<String, Object> params) {
		String sql =  new SQL(){
			{
				RoleUser roleUser = (RoleUser) params.get("roleUser");
				SELECT("count(*)");
				String dynaSql = RasConstants.SYSROLEUSER+" s ";
				if (roleUser != null) {
					if(roleUser.getUser()!=null && !StringUtils.isEmpty(roleUser.getUser().getUsername())){
						dynaSql += " , " + RasConstants.USERTABLE+" u ";
					}
					if(roleUser.getRole()!=null && !StringUtils.isEmpty(roleUser.getRole().getName())){
						dynaSql += " , " + RasConstants.SYSROLE + " r";
					}
				}
				FROM(dynaSql);
				
				if(roleUser != null){
					if(roleUser.getUser()!=null && !StringUtils.isEmpty(roleUser.getUser().getUsername())){
						WHERE(" s.user_id=u.id and u.username like CONCAT('%',#{roleUser.user.username},'%')  ");
					}
					if(roleUser.getRole()!=null && !StringUtils.isEmpty(roleUser.getRole().getName())){
						WHERE(" s.role_id=r.id and r.name like  CONCAT('%',#{roleUser.role.name},'%') ");
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
				RoleUser roleUser = (RoleUser) params.get("roleUser");
				SELECT("s.*");
				String dynaSql = RasConstants.SYSROLEUSER+" s ";
				if (roleUser != null) {
					if(roleUser.getUser()!=null && !StringUtils.isEmpty(roleUser.getUser().getUsername())){
						dynaSql += " , " + RasConstants.USERTABLE+" u ";
					}
					if(roleUser.getRole()!=null && !StringUtils.isEmpty(roleUser.getRole().getName())){
						dynaSql += " , " + RasConstants.SYSROLE + " r";
					}
				}
				FROM(dynaSql);
				ORDER_BY(" s.id Desc ");
				if(roleUser != null){
					if(roleUser.getUser()!=null && !StringUtils.isEmpty(roleUser.getUser().getUsername())){
						WHERE(" s.user_id=u.id and u.username like CONCAT('%',#{roleUser.user.username},'%')  ");
					}
					if(roleUser.getRole()!=null && !StringUtils.isEmpty(roleUser.getRole().getName())){
						WHERE(" s.role_id=r.id and r.name like  CONCAT('%',#{roleUser.role.name},'%') ");
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
	 * @param roleUser
	 * @return
	 */
	public String insert(RoleUser roleUser){
		return new SQL(){{
			if(roleUser!=null){
				INSERT_INTO(RasConstants.SYSROLEUSER);
				if(roleUser.getRole()!=null&&roleUser.getRole().getId()!=null){
					VALUES("role_id", "#{role.id}");
				}
				if(roleUser.getUser()!=null&&roleUser.getUser().getId()!=null){
					VALUES("user_id", "#{user.id}");
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
			s.append(RasConstants.SYSROLEUSER).append(" where id in (");
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
