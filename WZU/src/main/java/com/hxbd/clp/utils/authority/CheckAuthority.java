package com.hxbd.clp.utils.authority;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * 校验权限：用于控制主页面上页面链接的显示
 * @author Administrator
 *
 */
public class CheckAuthority implements TemplateMethodModelEx{

	/**
	 * 如果用户没有页面上模块的权限，则返回false，即不显示该模块
	 */
	@Override
	public Object exec(List arg0) throws TemplateModelException {
		HttpServletRequest request =  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Map<String,String> map = (Map<String, String>) request.getSession().getAttribute("authority");
		if(map.get(arg0.get(0).toString())!=null){
			return true;
		}
		System.out.println("该用户未拥有此权限："+arg0.get(0));
		return false;
	}
	//添加两层系统保护，防止用户恶意破坏系统
	//页面增加权限控制标签，用于控制功能模块在页面上的显示
	//角色用户拦截器，用于拦截每次后台用户发起的请求，防止伪权限操作
}
