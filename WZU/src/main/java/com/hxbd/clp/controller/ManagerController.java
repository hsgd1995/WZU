package com.hxbd.clp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxbd.clp.domain.Manager;
import com.hxbd.clp.service.ManagerService;
import com.hxbd.clp.utils.tag.PageModel;

@Controller
public class ManagerController {
	
	
	@Autowired
	private ManagerService managerService;

	//分页查询管理员
	@RequestMapping("/manager/{pageIndex}/getManagerPageModel")
	public @ResponseBody PageModel<Manager> getManagerPageModel(@PathVariable Integer pageIndex,@RequestParam("keyword") String keyword){
		PageModel<Manager> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		if(keyword != null && !keyword.equals("")){
			Manager manager  = new Manager();
			manager.setUsername(keyword);
			return managerService.findManager(manager, pageModel);
		}
		return managerService.findManager(null, pageModel);
	}
	
	//根据id查询
	@RequestMapping("/findManagerById")
	public @ResponseBody Manager findManagerById(@RequestParam("id") Integer id){
		return managerService.selectById(id);
	}
	
	
	/**
	 * 保存管理员
	 * @param manager
	 * @return
	 */
	@RequestMapping("/manager/saveManager")
	public @ResponseBody String saveManager(Manager manager){
		managerService.saveManager(manager);
		return "{\"status\":true}";
	}

	/**
	 * 更新管理员信息
	 * @param manager
	 * @return
	 */
	@RequestMapping("/manager/updateManager")
	public @ResponseBody String updateManager(Manager manager){
		managerService.updateManager(manager);
		return "{\"status\":true}";
	}
	
	@RequestMapping("/manager/deleteManager")
	public @ResponseBody String batchDelManager(@RequestParam("ids[]") Integer[] ids){
		managerService.batchDelManager(ids);
		return "{\"status\":true}";
	}
	
	@GetMapping("/manager/getOrdinaryManager")
	@ResponseBody
	public List<Manager> getOrdinaryManager(){
		return managerService.getOrdinaryManager();
	}
}