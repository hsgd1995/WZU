package com.tang.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.tang.entity.test1.User;
import com.tang.mapper.UserMapper;

/**
 * 角色权限控制
 * 
 * @author Administrator
 * @date 2019年5月22日
 */
public class UserRealm extends AuthorizingRealm {
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 这里做权限控制
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 这里做登录控制
		AuthenticationInfo info;
		String loginname = (String) token.getPrincipal();
		Wrapper<User> wrapper = new EntityWrapper<User>();
		wrapper.eq("loginname", loginname);
		List<User> list = userMapper.selectList(wrapper);
		if(list!=null && list.size()>0){
			info = new SimpleAuthenticationInfo(loginname, list.get(0).getPassword(), list.get(0).getRealname());
			return info;
		}
		return null;
	}
}
