package com.hxbd.clp.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import com.hxbd.clp.service.CourseVideoService;

@Aspect
@Component
public class CourseAop {
	
	@Autowired
	private CourseVideoService courseVideoService;
	
	@Pointcut("execution(* com.hxbd.clp.controller.CourseController.videoDetails(..)) "
			+ "&& args(request,id)")
	public void saveLastStudy(HttpServletRequest request, Integer id){
		
	}
	
	@After("saveLastStudy(request,id)")
	public void after( HttpServletRequest request, Integer id){
		System.out.println("@After aop");
		System.out.println(request);
		System.out.println(id);
		System.out.println(courseVideoService);
	}
	
}

