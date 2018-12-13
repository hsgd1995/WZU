package com.hxbd.clp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.CourseAndUser;
import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.service.CourseAndUserService;
import com.hxbd.clp.service.CourseService;
import com.hxbd.clp.service.CourseVideoService;
import com.hxbd.clp.service.UserService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * @author hxcl
 *
 * 2018年3月16日上午10:49:51
 * 
 * 学习进度模块
 *
 */
@Controller
public class LearningProcessController {

	@Resource
	private UserService userService;
	@Resource
	private CourseService courseService;
	@Resource
	private CourseAndUserService courseAndUserService;
	
	@Autowired
	private CourseVideoService courseVideoService;
	//加载learning_process页面数据
	/**
	 * 学习进度
	 * @return
	 */
	@RequestMapping("/learning_process")
	public String learningProcess(HttpSession httpSession) {
		User userSession = (User)httpSession.getAttribute("user_session");
		//System.err.println(userSession);
		
		if(userSession != null){
			return "learning_process";
		}else{
			return "login";
		}
	}
	
	/**
	 * 搜索加入的学习课程
	 */
	@RequestMapping("/joinCourse/searchByparams")
	public String searchjoinCourseByparams(@RequestParam("search") String search){
		//System.err.println(search);
		return "learning_process";
	}
	
	/**
	 * 加载学习课程list
	 * @param httpSession
	 * @param pageIndex
	 * @param keyword
	 * @return
	 */
	@RequestMapping("/learningCourseList/{pageIndex}/getCoursePageModel")
	public @ResponseBody PageModel<Course> getLearningCoursePageModel(HttpSession httpSession,@PathVariable Integer pageIndex,
			@RequestParam("keyword") String keyword,@RequestParam("start") Integer start){
		User userSession = (User)httpSession.getAttribute("user_session");
		Integer userId = userSession.getId();
		PageModel<Course> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		if(keyword != null && !keyword.equals("")){
			Course course = new Course();
			course.setCourseName(keyword); 
			
			return courseAndUserService.selectStudyCourseByPage(userId,course, pageModel,start);
		}
		return courseAndUserService.selectStudyCourseByPage(userId,null,pageModel,start);
	}
	
	/**
	 * 获取用户对某课程的学习进度，
	 * 添加观看完视频时对isFinish修改的方法，检查查询语句逻辑，
	 * @param userId
	 * @param courseId
	 * @return
	 */
	@RequestMapping("/learningCourseList/getStudyProcess")
	@ResponseBody
	public Object getStudyProcess(Integer userId,Integer courseId){
		double process = 0;
		process = courseAndUserService.getStudyProcess(userId,courseId);
		CourseVideo cv = courseVideoService.getLastStudyVideo(userId,courseId);
		Map<String, Object> map = new HashMap<>();
		map.put("process", process);
		map.put("cv", cv);
		return map;
	}
	
	
	/**
	 * 加载收藏课程list
	 * @param httpSession
	 * @param pageIndex
	 * @param keyword
	 * @return
	 */
	@RequestMapping("/collectCourseList/{pageIndex}/getCoursePageModel")
	public @ResponseBody PageModel<Course> getCollectCoursePageModel(HttpSession httpSession,@PathVariable Integer pageIndex,@RequestParam("keyword") String keyword){
		User userSession = (User)httpSession.getAttribute("user_session");
		Integer userId = userSession.getId();
		PageModel<Course> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		if(keyword != null && !keyword.equals("")){
			Course course = new Course();
			course.setCourseName(keyword); 
			
			return courseAndUserService.selectCollectCourseByPage(userId,course, pageModel);
		}
		return courseAndUserService.selectCollectCourseByPage(userId,null,pageModel);
	}
	
	/**
	 * 用户删除收藏的课程
	 */
	@RequestMapping("/deleteCollectCourse/{courseId}")
	public @ResponseBody String deleteCollectCourse(HttpSession httpSession,@PathVariable Integer courseId){
		User userSession = (User)httpSession.getAttribute("user_session");
		if(userSession != null) {
			Integer userId = userSession.getId();
			CourseAndUser courseAndUser = courseAndUserService.findByUserIdAndCourseId(userId,courseId);
			if(courseAndUser != null){
				if(courseAndUser.getCollect() == 1){
					courseAndUser.setCollect(0);	  //0表示要删除收藏课程
				courseAndUserService.editCourseAndUser(courseAndUser);
				return "{\"status\":true}";
				}
		}
		}
		return "{\"status\":false}";
	}
}
