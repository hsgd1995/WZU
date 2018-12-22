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

import com.hxbd.clp.domain.bus.base.BaseInto;
import com.hxbd.clp.service.BaseIntoService;
import com.hxbd.clp.utils.common.RasConstants;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 项目进驻入口
 * @author Administrator
 *
 */
@Controller
public class BaseIntoController {
	
	@Autowired
	private BaseIntoService baseIntoService;
	
	@GetMapping("baseInto/list")
	@ResponseBody
	public PageModel<BaseInto> list(BaseInto base,Integer pageSize,Integer pageIndex){
		if(pageSize==null){
			pageSize = RasConstants.PAGE_DEFAULT_SIZE;
		}
		if(pageIndex==null){
			pageIndex = 1;
		}
		return baseIntoService.getByParam(base,pageSize,pageIndex);
	}
	
	
	@GetMapping("baseInto/findById")
	@ResponseBody
	public BaseInto findById(Integer id){
		return baseIntoService.findById(id);
	}
	
	@RequestMapping(value="/baseInto/update",method=RequestMethod.POST)
	public @ResponseBody String update(BaseInto base){
		baseIntoService.update(base);
		return "{\"status\":true,\"message\":\"更新成功\"}";
	}
	
	@RequestMapping(value="/baseInto/add",method=RequestMethod.POST)
	public @ResponseBody String add(BaseInto base){
		baseIntoService.add(base);
		return "{\"status\":true,\"message\":\"添加成功\"}";
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@PostMapping("/baseInto/del")
	@ResponseBody
	public String batchDisable(@RequestParam("ids[]") Integer[] ids){
		baseIntoService.del(ids);
		return "{\"status\":true}";
	}
}
