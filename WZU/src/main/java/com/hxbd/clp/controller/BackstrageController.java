package com.hxbd.clp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxbd.clp.domain.Manager;
import com.hxbd.clp.domain.RoleAuthority;
import com.hxbd.clp.service.ManagerService;
import com.hxbd.clp.service.RoleManagerService;
import com.hxbd.clp.utils.authority.CheckAuthority;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 后台控制层
 * 
 * @author 恒信博大
 *
 */
@Controller
public class BackstrageController {

	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private RoleManagerService roleManagerService;

	/**
	 * 进入后台页面
	 * 
	 * @return
	 */
	@GetMapping("/backstage")
	public String backstage() {
		return "backLogin";
	}

	/**
	 * 验证管理员是否允许登录
	 * 
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping("/backManagerLogin")
	public @ResponseBody String validateManager(@RequestParam("username") String username,
			@RequestParam("password") String password, HttpSession session) {
		// 验证用户名和密码
		Manager manager = managerService.validateManager(username, password);
		if (manager != null) {
			session.setAttribute(RasConstants.MANAGER_SESSION, manager);
			List<RoleAuthority> list =  roleManagerService.getManagerAuthority(manager.getId());
			session.setAttribute(RasConstants.MANAGERROLEAUTHORITY_SESSION, list);
			Map<String,String> map = new HashMap<>();
			System.out.println(" ------------ 权限 >>>>>>>>>>>> ");
			for (RoleAuthority roleAuthority : list) {
				System.out.println("roleAuthority--"+roleAuthority);
				System.out.println(roleAuthority.getAuthority().getId());
				map.put(roleAuthority.getAuthority().getId(), roleAuthority.getAuthority().getId());
			}
			System.out.println(" <<<<<<<<<<<< 权限 ------------ ");
			session.setAttribute("authority",map);
			return "{\"message\":true}";
		}
		return "{\"message\":false}";
	}

	@RequestMapping("/main")
	public String main(HttpSession session,ModelMap modelMap) {
		Manager manager = (Manager) session.getAttribute(RasConstants.MANAGER_SESSION);
		if (manager != null) {
			modelMap.put("CheckAuthority",new CheckAuthority());
			return "welcome";
		}
		return "backLogin";
	}
	@RequestMapping("/main2")
	public String main2(HttpSession session,ModelMap modelMap) {
		Manager manager = (Manager) session.getAttribute(RasConstants.MANAGER_SESSION);
		if (manager != null) {
			modelMap.put("CheckAuthority",new CheckAuthority());
			return "welcome2";
		}
		return "backLogin";
	}
	@RequestMapping("/main3")
	public String main3(HttpSession session,ModelMap modelMap) {
		Manager manager = (Manager) session.getAttribute(RasConstants.MANAGER_SESSION);
		if (manager != null) {
			modelMap.put("CheckAuthority",new CheckAuthority());
			return "welcome3";
		}
		return "backLogin";
	}
	@RequestMapping("/main4")
	public String main4(HttpSession session,ModelMap modelMap) {
		Manager manager = (Manager) session.getAttribute(RasConstants.MANAGER_SESSION);
		if (manager != null) {
			modelMap.put("CheckAuthority",new CheckAuthority());
			return "welcome4";
		}
		return "backLogin";
	}

	/**
	 * 请求服务器中session中的用户信息
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/getSession")
	public @ResponseBody Manager getSession(HttpSession session) {
		// 取出session中的用户
		Manager manager = (Manager) session.getAttribute(RasConstants.MANAGER_SESSION);
		if (manager != null) {
			return manager;
		} else {
			return new Manager();
		}
	}

	/**
	 * 跳转到修改课程页面
	 * 
	 * @return
	 */
	@GetMapping("/main/modifyCourse")
	public String modifyCourse() {
		return "modifyCourse";
	}

	/**
	 * 跳转到课程类型页面
	 * 
	 * @return
	 */
	@GetMapping("/main/modifyCourseType")
	public String modifyCourseType() {
		return "modifyCourseType";
	}

	/**
	 * 跳转到课程类型页面
	 * 
	 * @return
	 */
/*	@GetMapping("/main/modifyCourseDetails")
	public String modifyCourseDetails() {
		return "modifyCourseDetails";
	}*/

	/**
	 * 跳转到课程公告页面
	 * 
	 * @return
	 */
	@GetMapping("/main/modifyCourseNotice")
	public String modifyCourseNotice() {
		return "modifyCourseNotice";
	}

	/**
	 * 跳转到绑定主标题页面
	 * 
	 * @return
	 */
	@GetMapping("/main/bindTitle")
	public String bindTitle() {
		return "bindTitle";
	}

	/**
	 * 跳转到课程视频页面
	 * 
	 * @return
	 */
	@GetMapping("/main/modifyCourseVideo")
	public String modifyCourseVideo() {
		return "modifyCourseVideo";
	}

	// /**
	// * 跳转到选择题管理页面
	// *
	// * @return
	// */
	// @GetMapping("/main/question")
	// public String question() {
	// return "question";
	// }
	//
	/**
	 * 跳转到判断题管理页面
	 * 
	 * @return
	 */
	@GetMapping("/main/trueAndFalseQue")
	public String trueAndFalseQue() {
		return "trueAndFalseQue";
	}
	
	/**
	 * 跳转到填空题管理页面
	 * 
	 * @return
	 */
	@GetMapping("/main/completion")
	public String completion() {
		return "completion";
	}
	
	/**
	 * 跳转到填空题管理页面
	 * 
	 * @return
	 */
	@GetMapping("/main/shortAnswer")
	public String shortAnswer() {
		return "shortAnswer";
	}

	/**
	 * 跳转到修改新闻页面
	 * 
	 * @return
	 */
	@GetMapping("/main/modifyNews")
	public String modifyNews() {
		return "modifyNews";
	}

	/**
	 * 跳转到修改图片页面
	 * 
	 * @return
	 */
	@GetMapping("/main/modifyPic")
	public String modifyPic() {
		return "modifyPic";
	}

	/**
	 * 跳转到修改用户页面
	 * 
	 * @return
	 */
	@GetMapping("/main/modifyUser")
	public String modifyUser() {
		return "modifyUser";
	}
	
	/**
	 * 跳转到修改用户详细信息页面
	 * 
	 * @return
	 */
	@GetMapping("/main/modifyUserInfo")
	public String modifyUserInfo() {
		return "modifyUserInfo";
	}

	/**
	 * 跳转到修改用户与课程页面
	 * 
	 * @return
	 */
	@GetMapping("/main/modifyCourseAndUser")
	public String modifyCourseAndUser() {
		return "modifyCourseAndUser";
	}

	/**
	 * 跳转到修改管理员页面
	 * 
	 * @return
	 */
	@GetMapping("/main/modifyManager")
	public String modifyManager() {
		return "modifyManager";
	}

	//
	@GetMapping("/main/coursePlanManage")	
	public String coursePlanManage() {
		return "coursePlanManage";
	}

	// 教师信息修改页面
	@GetMapping("/main/modifyTeacher")
	public String modifyTeacher() {
		return "modifyTeacher";
	}

	// 绑定课程页面
	@GetMapping("/main/bindCourseToTeacher")
	public String bindCourseToTeacher() {
		return "bindCourseToTeacher";
	}

	/**
	 * 打开消息管理页面
	 * @return
	 */
	@GetMapping("/main/modifyMessage")
	public String openMessagePage(){
		return "modifyMessage";
	}
	
	/**
	 * 打开发送消息页面
	 * @return
	 */
	@GetMapping("/main/sendMessage")
	public String openSendMessage(){
		return "modifyMessageAndUser";
	}
	
	@GetMapping("/main/modifySystemMessage")
	public String openSysMessage(){
		return "modifySystemMessage";
	}
	
	@GetMapping("/main/modifyRole")
	public String openRole(){
		return "modifyRole";
	}
	
	@GetMapping("/main/modifyRoleUser")
	public String openRoleUser(){
		return "modifyRoleUser";
	}
	
	@GetMapping("/main/modifyRoleManager")
	public String openRoleManager(){
		return "modifyRoleManager";
	}
	
	// 后台退出
	@RequestMapping("/outlogin")
	public String outlogin(HttpSession session) {
		session.invalidate();
		return "redirect:backstage";
	}
}
