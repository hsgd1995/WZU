package com.hxbd.clp.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.RoleAuthority;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 角色-权限 动态sql
 * @author Administrator
 *
 */
public class RoleAuthorityDynaSqlProvider {

	public String insert(RoleAuthority r){
		return new SQL(){{
			if(r!=null){
				INSERT_INTO(RasConstants.SYSROLEAUTHORITY);
				if(r.getRole()!=null&&r.getRole().getId()!=null){
					VALUES("role_id", "#{role.id}");
				}
				if(r.getAuthority()!=null && r.getAuthority().getId()!=null){
					VALUES("aut_id", "#{authority.id}");
				}
			}
		}}.toString();
	}
}
