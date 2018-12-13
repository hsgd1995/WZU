package com.hxbd.clp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxbd.clp.domain.SystemMessage;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.service.SystemMessageService;
import com.hxbd.clp.utils.common.RasConstants;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 系统消息控制层
 * @author Administrator
 *
 */
@Controller
public class SystemMessageController {

	@Autowired
	private SystemMessageService systemMessageService;
	
	@Scheduled(cron="30 30 8 * * ?")
	public void systemMessage(){
		systemMessageService.examStudyWarn();
	}
	
	
	/* 后台部分 */
	
	/**
	 * 根据检索和分页条件查询列表
	 * @param messageAndUser
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/sysMessage/list")
	@ResponseBody
	public Object list(SystemMessage systemMessage,Integer pageIndex,Integer pageSize,String username){
		if(pageIndex == null){
			pageIndex = 1;
		}
		if(pageSize == null){
			pageSize = RasConstants.PAGE_DEFAULT_SIZE;
		}
		if(username!=null&&!"".equals(username)){
			User user = new User();
			user.setUsername(username);
			systemMessage.setUser(user);
		}
		return systemMessageService.getByPage(systemMessage, pageIndex, pageSize);
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@PostMapping("/sysMessage/batchDel")
	@ResponseBody
	public String batchDel(@RequestParam("ids[]") Integer[] ids){
		systemMessageService.batchDel(ids);
		return "{\"status\":true}";
	}
	
	/* 前台部分 */
	
	/**
	 * 已读
	 * @param id
	 * @return
	 */
	@PostMapping("sysMessage/read")
	@ResponseBody
	public String read(HttpServletRequest httpServletRequest, Integer id){
		User user = (User) httpServletRequest.getSession().getAttribute(RasConstants.USER_SESSION);
		if(user == null){
			//未登录，重定向到未登录提示页面
			return "{\"status\":false}";
		}
		systemMessageService.read(id,user.getId());
		return "{\"status\":true}";
	}
	
	/**
	 * 用户全部已读
	 * @param httpServletRequest
	 * @return
	 */
	@PostMapping("/sysMessage/allRead")
	@ResponseBody
	public String allRead(HttpServletRequest httpServletRequest){
		User user = (User) httpServletRequest.getSession().getAttribute(RasConstants.USER_SESSION);
		if(user == null){
			//未登录
			return "{\"status\":false}";
		}
		systemMessageService.allRead(user.getId());
		return "{\"status\":true}";
	}
	
	/**
	 * 用户全部消息清空
	 * @return 
	 */
	@PostMapping("sysMessage/allClear")
	@ResponseBody
	public String allClear(HttpServletRequest httpServletRequest){
		User user = (User) httpServletRequest.getSession().getAttribute(RasConstants.USER_SESSION);
		if(user == null){
			//未登录
			return "{\"status\":false}";
		}
		systemMessageService.allClear(user.getId());
		return "{\"status\":true}";
	}
	
	/**
	 * 获取用户的系统消息
	 * @param userId
	 * @return
	 */
	@GetMapping("/sysMessage/myMessage/{pageIndex}")
	@ResponseBody
	public PageModel<SystemMessage> getUserMessage(Integer userId,@PathVariable("pageIndex") Integer pageIndex,
			@RequestParam(value="pageSize",required=false) Integer pageSize){
		if(pageIndex==null){
			pageIndex = 1;
		}
		if(pageSize == null){
			pageSize = RasConstants.PAGE_DEFAULT_SIZE;
		}
		SystemMessage systemMessage = new SystemMessage();
		User user = new User();
		user.setId(userId);
		systemMessage.setUser(user);
		PageModel<SystemMessage> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		pageModel.setPageSize(pageSize);
		return systemMessageService.getByPage(systemMessage,pageIndex,pageSize);
	} 
	
	/**
	 * 用户删除消息
	 * @param httpServletRequest
	 * @param ids
	 * @return
	 */
	@PostMapping("sysMessage/del")
	@ResponseBody
	public String delForUser(HttpServletRequest httpServletRequest
			,@RequestParam("ids[]") Integer[] ids){
		User user = (User) httpServletRequest.getSession().getAttribute(RasConstants.USER_SESSION);
		if(user == null){
			//未登录
			return "{\"status\":false}";
		}
		systemMessageService.UserDelSysMessage(ids,user.getId());
		return "{\"status\":true}";
	}
}
