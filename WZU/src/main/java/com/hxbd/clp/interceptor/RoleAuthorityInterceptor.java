package com.hxbd.clp.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hxbd.clp.domain.RoleAuthority;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 角色-权限拦截器
 * @author Administrator
 *
 */
public class RoleAuthorityInterceptor implements HandlerInterceptor{

	
	private static final String[] IGNORE_URI = {"backstage","backManagerLogin","outlogin","getSession"};
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//以下功能未完善，暂不启用，即最终结果总是返回true
		System.out.println("角色-权限拦截器");
		//此处代码待优化
		String servletPath = request.getServletPath(); 
		System.out.println("path:"+servletPath);
		for (String string : IGNORE_URI) {
			if(servletPath.contains(string) || (servletPath.contains("/main")&&!servletPath.contains("main/"))){
				return true;
			}
		}
		
		//从session中获取后台用户所拥有的权限
		List<RoleAuthority> list = 	(List<RoleAuthority>) request.getSession().getAttribute(RasConstants.MANAGERROLEAUTHORITY_SESSION);
		if (list != null) {
			
			for (RoleAuthority roleAuthority : list) {
				//如果当前请求的url是后台用户的权限中url
				if(servletPath.contains(roleAuthority.getAuthority().getUrl())){
					return true;
				}
			}
		}
		System.out.println("角色-用户拦截结果：false");
		//return false;
		
		//以上功能未完善，暂不启用，即最终结果总是返回true
		return true;
	}

	
	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
