package com.tang.exception;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import static com.tang.common.DefaultViewConst.DEFAULT_ERROR_VIEW;
/**
 * 全局异常捕获类
 * @author Administrator
 * @date   2019年5月21日
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	public Object runTimeException(HttpServletRequest req,Exception e){
		ModelAndView mav = new ModelAndView();
		mav.addObject("url",req.getRequestURL());
		mav.addObject("exception",e);
		mav.setViewName(DEFAULT_ERROR_VIEW);
		// return new CommonMessage(500,false,e.getMessage());
		// return DEFAULT_ERROR_VIEW;
		e.printStackTrace();
		return mav;
	}
}
