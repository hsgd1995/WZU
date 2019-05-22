package com.hxbd.clp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxbd.clp.domain.Message;
import com.hxbd.clp.domain.MessageAndUser;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.service.MessageAndUserService;
import com.hxbd.clp.utils.common.RasConstants;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 发送消息类
 * @author Administrator
 *
 */
@Controller
public class MessageAndUserController {
	
	@Autowired
	private MessageAndUserService messageAndUserService;
	
	/* 后台部分 */
	
	
	/**
	 * 根据检索和分页条件查询列表
	 * @param messageAndUser
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/sendMessage/list")
	@ResponseBody
	public Object list(MessageAndUser messageAndUser,Integer pageIndex,Integer pageSize,String username,String title){
		if(pageIndex == null){
			pageIndex = 1;
		}
		if(pageSize == null){
			pageSize = RasConstants.PAGE_DEFAULT_SIZE;
		}
		if(!StringUtils.isEmpty(username)){
			User user = new User();
			user.setUsername(username);
			messageAndUser.setUser(user);
		}
		
		if(!StringUtils.isEmpty(title)){
			Message message = new Message();
			message.setTitle(title);
			messageAndUser.setMessage(message);
		}
		
		return messageAndUserService.getByPage(messageAndUser, pageIndex, pageSize);
	}
	
	/**
	 * 根据id获取消息
	 * @param id
	 * @return
	 */
	@GetMapping("/sendMessage/findById")
	@ResponseBody
	public Object getById(Integer id){
		return messageAndUserService.getById(id);
	}
	
	
	/**
	 * 发送消息
	 * @param messageId
	 * @param userIds
	 * @param isAllUser
	 * @return
	 */
	@RequestMapping(value="/sendMessage/add", method = RequestMethod.POST)
	public @ResponseBody String add(@RequestParam("messageIds[]") Integer[] messageIds,@RequestParam("userIds[]") Integer[] userIds,@RequestParam("allUser") Boolean isAllUser) {
		if(messageIds==null||userIds==null){
			return "{\"status\":false}";
		}
		messageAndUserService.sendMessage(messageIds,userIds,isAllUser);
		return "{\"status\":true}";

	}
	
	
	/* 前台、后台共用部分 */
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@PostMapping("/sendMessage/batchDel")
	@ResponseBody
	public String batchDel(@RequestParam("ids[]") Integer[] ids){
		messageAndUserService.batchDel(ids);
		return "{\"status\":true}";
	}
	
	/* 前台部分 */
	
	/**
	 * 获取用户的消息
	 * @param userId
	 * @return
	 */
	@GetMapping("/sendMessage/myMessage/{pageIndex}")
	@ResponseBody
	public PageModel<MessageAndUser> getUserMessage(Integer userId,@PathVariable("pageIndex") Integer pageIndex,
			@RequestParam(value="pageSize",required=false) Integer pageSize){
		if(pageIndex==null){
			pageIndex = 1;
		}
		if(pageSize == null){
			pageSize = RasConstants.PAGE_DEFAULT_SIZE;
		}
		MessageAndUser messageAndUser = new MessageAndUser();
		User user = new User();
		user.setId(userId);
		messageAndUser.setUser(user);
		PageModel<MessageAndUser> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		pageModel.setPageSize(pageSize);
		return messageAndUserService.getByPage(messageAndUser,pageIndex,pageSize);
	}
	
	/**
	 * 用户全部已读
	 * @param userId
	 * @return "{"status":true}"
	 */
	@PostMapping("/sendMessage/allRead/{id}")
	@ResponseBody
	public String allRead(@PathVariable("id") Integer userId){
		messageAndUserService.allRead(userId);
		return "{\"status\":true}";
	}
	
	/**
	 * 用户全部消息清空
	 * @param userId
	 * @return "{"status":true}"
	 */
	@PostMapping("sendMessage/allClear/{id}")
	@ResponseBody
	public String allClear(@PathVariable("id") Integer userId){
		messageAndUserService.allClear(userId);
		return "{\"status\":true}";
	}
	
	/**
	 * 已读
	 * @param id
	 * @return
	 */
	@PostMapping("sendMessage/read")
	@ResponseBody
	public String read( Integer id){
		messageAndUserService.read(id);
		return "{\"status\":true}";
	}
	
	/**
	 * 根据id查看详细信息
	 * @param id
	 * @return
	 */
	@GetMapping("/details/{id}")
	public String details(HttpServletRequest request,@PathVariable Integer id){
		MessageAndUser messageAndUser = messageAndUserService.getById(id);
		Map<String, Object> map = new HashMap<>();
		map.put("id", messageAndUser.getMessage().getCreater().getId());
		map.put("name", messageAndUser.getMessage().getCreater().getUsername());
		map.put("title", messageAndUser.getMessage().getTitle());
		map.put("content", messageAndUser.getMessage().getContent());
		map.put("createTime", messageAndUser.getMessage().getCreateTime());
		request.setAttribute("map",map);
		return "news_details";
	}
	
}
