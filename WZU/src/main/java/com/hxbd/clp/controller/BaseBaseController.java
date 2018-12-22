package com.hxbd.clp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxbd.clp.domain.basedata.base.Base;
import com.hxbd.clp.service.BaseBaseService;
import com.hxbd.clp.utils.common.RasConstants;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 区域
 * @author Administrator
 *
 */
@Controller
public class BaseBaseController {
	
	@Autowired
	private BaseBaseService baseBaseService;
	
	@GetMapping("baseBase/list")
	@ResponseBody
	public PageModel<Base> list(Base base,Integer pageSize,Integer pageIndex){
		if(pageSize==null){
			pageSize = RasConstants.PAGE_DEFAULT_SIZE;
		}
		if(pageIndex==null){
			pageIndex = 1;
		}
		return baseBaseService.getByParam(base,pageSize,pageIndex);
	}
	
	
	@GetMapping("baseBase/findById")
	@ResponseBody
	public Base findById(Integer id){
		return baseBaseService.findById(id);
	}
	
	@RequestMapping(value="/baseBase/update",method=RequestMethod.POST)
	public @ResponseBody String update(Base base){
		baseBaseService.update(base);
		return "{\"status\":true,\"message\":\"更新成功\"}";
	}
	
	@RequestMapping(value="/baseBase/add",method=RequestMethod.POST)
	public @ResponseBody String add(Base base){
		baseBaseService.add(base);
		return "{\"status\":true,\"message\":\"添加成功\"}";
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@PostMapping("/baseBase/del")
	@ResponseBody
	public String batchDisable(@RequestParam("ids[]") Integer[] ids){
		baseBaseService.del(ids);
		return "{\"status\":true}";
	}
}
