package com.hxbd.clp.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.CourseAndUser;
import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.domain.Teacher;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.service.CourseAndUserService;
import com.hxbd.clp.service.CourseService;
import com.hxbd.clp.service.CourseVideoService;
import com.hxbd.clp.service.UserService;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.CourseAndTeacherVO;
import com.hxbd.clp.vo.CourseAndUserVO;
import com.hxbd.clp.vo.CourseVideoVO;

/**
 * 课程与用户管理控制层
 * 
 * @author 恒信博大
 *
 */
@Controller
public class CourseAndUserController {

	@Resource
	private CourseAndUserService courseAndUserService;
	@Resource
	private CourseService courseService;
	@Resource
	private UserService userService;

	/**
	 * CourseAndTeacherList
	 * 
	 * @param pageIndex
	 * @param keyword
	 * @param teacherId
	 * @param courseId
	 * @return
	 */
	@RequestMapping("/courseAndUser/{pageIndex}/getUserNamePageModel")
	public @ResponseBody PageModel<CourseAndUserVO> getCourseAndTeacherPageModel(@PathVariable Integer pageIndex,String courseNo,String userId) {
		PageModel<CourseAndUserVO> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		Map<Integer, Course> courseMap = courseService.findAllMap();
		CourseAndUser courseAndUser = new CourseAndUser();
		if(!StringUtils.isEmpty(courseNo)){
		Course course = courseService.selectByTeacherCourseNo(courseNo);
			if(course != null){
				courseAndUser.setCourseId(course.getId());
			}else{
				return pageModel;
			}
		}
		if(!StringUtils.isEmpty(userId)){
			courseAndUser.setUserId(Integer.parseInt(userId));
		}
		return courseAndUserService.findCourseAndUser(courseAndUser, pageModel, courseMap);
	}
	
	/**
	 * 保存课程与用户关系
	 * 
	 * @param courseVideo
	 * @return
	 */
	@RequestMapping("courseAndUser/saveCourseAndUser")
	public @ResponseBody String saveParentName(@RequestParam("courseId") String courseId,@RequestParam("userId") String userId) {
		Course course = courseService.selectByTeacherCourseNo(courseId);
		if(course == null){
			return "{\"status\":false}";
		}
		User user = userService.selectById(Integer.parseInt(userId));
		if(user == null){
			return "{\"status\":false}";
		}
		if(courseAndUserService.findByUserIdAndCourseId(user.getId(), course.getId()) == null){
			CourseAndUser courseAndUser = new CourseAndUser();
			courseAndUser.setUserId(user.getId());
			courseAndUser.setCourseId(course.getId());
			//默认添加课程
			courseAndUser.setJoincourse(0);
			//默认不收藏
			courseAndUser.setCollect(0);
			courseAndUserService.saveCourseAndUser(courseAndUser);
			return "{\"status\":true}";
		}
		return "{\"status\":false}";
	}
	
	//解除用户与课程的绑定关系
	@RequestMapping("/courseAndUser/deleteCourseAndUser")
	public @ResponseBody String batchDelCourseAndUser(@RequestParam("ids[]") Integer[] ids){
		courseAndUserService.batchDelCourseAndUser(ids);
		return "{\"status\":true}";
	}
}