package com.hxbd.clp.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxbd.clp.domain.Manager;
import com.hxbd.clp.domain.Message;
import com.hxbd.clp.service.MessageService;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 消息管理
 * @author Administrator
 *
 */
@Controller
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	/**
	 * 根据检索和分页条件查询列表
	 * @param message
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/message/list")
	@ResponseBody
	public Object list(Message message,Integer pageIndex,Integer pageSize){
		if(pageIndex == null){
			pageIndex = 1;
		}
		if(pageSize == null){
			pageSize = RasConstants.PAGE_DEFAULT_SIZE;
		}
		return messageService.getMessageByPage(message, pageIndex, pageSize);
	}
	
	/**
	 * 根据id获取消息
	 * @param id
	 * @return
	 */
	@GetMapping("/message/findMessageById")
	@ResponseBody
	public Object getMessageById(Integer id){
		return messageService.getById(id);
	}
	
	/**
	 * 保存消息
	 * @param news
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/message/addMessage", method = RequestMethod.POST)
	public @ResponseBody String saveMessage(HttpServletRequest http, Message message) {
		Manager manager = (Manager) http.getSession().getAttribute(RasConstants.MANAGER_SESSION);
		if(manager==null){
			return "{\"status\":false}";
		}
		message.setCreater(manager);
		message.setType(1);
		messageService.addMessage(message);
		return "{\"status\":true}";

	}
	
	/**
	 * 更新
	 * @param message
	 * @return
	 */
	@PostMapping("/message/update")
	@ResponseBody
	public String updateMessage(Message message){
		messageService.editMessage(message);
		return "{\"status\":true}";
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@PostMapping("/message/batchDelMessage")
	@ResponseBody
	public String batchDelMessage(@RequestParam("ids[]") Integer[] ids){
		messageService.batchDel(ids);
		return "{\"status\":true}";
	}
	
	/**
	 * 发送消息给所有用户
	 * @param ids
	 * @return
	 */
	@PostMapping("/message/sendMessage")
	@ResponseBody
	public String sendMessage(@RequestParam("ids[]") Integer[] ids){
		messageService.sendMessage(ids);
		return "{\"status\":true}";
	}
}
