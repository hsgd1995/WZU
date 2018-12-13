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
import com.hxbd.clp.domain.ShortAnswer;
import com.hxbd.clp.service.ShortAnswerService;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 简答题 控制层
 * @author Administrator
 *
 */
@Controller
public class ShortAnswerController {
	@Autowired
	private ShortAnswerService shortAnswerService;
	
	/**
	 * 根据检索和分页条件查询列表
	 * @param sa
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/shortAnswer/list")
	@ResponseBody
	public Object list(ShortAnswer sa,Integer pageIndex,Integer pageSize){
		if(pageIndex == null){
			pageIndex = 1;
		}
		if(pageSize == null){
			pageSize = RasConstants.PAGE_DEFAULT_SIZE;
		}
		return shortAnswerService.getByPage(sa, pageIndex, pageSize);
	}
	
	/**
	 * 根据id获取消息
	 * @param id
	 * @return
	 */
	@GetMapping("/shortAnswer/findById")
	@ResponseBody
	public Object getById(Integer id){
		return shortAnswerService.getById(id);
	}
	
	/**
	 * 保存消息
	 * @param news
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/shortAnswer/add", method = RequestMethod.POST)
	public @ResponseBody String save(HttpServletRequest http, ShortAnswer sa) {
		Manager manager = (Manager) http.getSession().getAttribute(RasConstants.MANAGER_SESSION);
		if(manager==null){
			return "{\"status\":false}";
		}
		shortAnswerService.add(sa);
		return "{\"status\":true}";

	}
	
	/**
	 * 更新
	 * @param sa
	 * @return
	 */
	@PostMapping("/shortAnswer/update")
	@ResponseBody
	public String update(ShortAnswer sa){
		shortAnswerService.edit(sa);
		return "{\"status\":true}";
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@PostMapping("/shortAnswer/batchDel")
	@ResponseBody
	public String batchDel(@RequestParam("ids[]") Integer[] ids){
		shortAnswerService.batchDel(ids);
		return "{\"status\":true}";
	}
	
}
