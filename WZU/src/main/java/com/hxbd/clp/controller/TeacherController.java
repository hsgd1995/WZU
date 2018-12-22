package com.hxbd.clp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxbd.clp.domain.basedata.Teacher;
import com.hxbd.clp.service.TeacherService;
import com.hxbd.clp.utils.tag.PageModel;

@Controller
public class TeacherController {

	
	@Autowired
	private TeacherService teacherService;

	

	/**
	 * 根据教师ID查询教师信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findTeacherById")
	public @ResponseBody Teacher queryTeacherById(@RequestParam("id") Integer id) {
		return teacherService.selectById(id);
	}

	/**
	 * 查询教师列表
	 * 
	 * @return
	 */
	@GetMapping("/teacher/getTeacher")
	public @ResponseBody List<Teacher> teacherList() {
		List<Teacher> list = teacherService.getTeacherList(new HashMap<String, Object>());
		/*for (Teacher teacher : list) {
			System.out.println(teacher);
		}*/
		return list;
	}

	/**
	 * 根据教师名字查询教师信息
	 * 
	 * @param teacherName
	 * @return
	 */
	@RequestMapping("/teacher/{teacherName}/getTeacher")
	public @ResponseBody Teacher queryTeacherByName(@PathVariable String teacherName) {
		return teacherService.selectByName(teacherName);
	}

	/**
	 * 保存教师
	 * 
	 * @param teacher
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "teacher/saveTeacher", method = RequestMethod.POST)
	public @ResponseBody String saveTeacher(Teacher teacher) throws IOException {
			int id = teacherService.addTeacher(teacher);
			return "{\"status\":true}";
	}

	
	
	
	/**
	 * 更新教师信息
	 * 
	 * @param teacher
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "teacher/updateTeacher", method = RequestMethod.POST)
	public @ResponseBody String updateNews(Teacher teacher) throws IOException {
			teacherService.modifyTeacher(teacher);
		return "{\"status\":true}";
	}

	/**
	 * 批量删除教师信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/teacher/deleteTeacher")
	public @ResponseBody String batchDelNews(@RequestParam("ids[]") Integer[] ids) {
		teacherService.batchDelTeacher(ids);
		return "{\"status\":true}";
	}

	/**
	 * 分页查询(教师)
	 * 
	 * @param pageIndex
	 * @param keyword
	 * @return
	 */
	@RequestMapping("/teacher/{pageIndex}/getTeacherPageModel")
	public @ResponseBody PageModel<Teacher> getTeacherPageModel(@PathVariable Integer pageIndex,String teacherName,String teacherNo) {
		PageModel<Teacher> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		Teacher teacher = new Teacher();
			return teacherService.findTeacher(teacher, pageModel);
	}

	// 重定向
	@RequestMapping("/teacher_details/{id}")
	public String RedirectTeacherDetails(HttpServletRequest request, @PathVariable Integer id) {
		if (id == 0) {
			return "undefined_teacher";
		} else {
			//System.err.println(id);
			Teacher teacher = teacherService.selectById(id);
			//System.err.println(teacher.toString());
			request.setAttribute("teacherById", teacher);
			return "/teacher_details";
		}
	}

	// 查询4条教师数据 findTeacherLimitfour
	@RequestMapping("/findTeacherLimitfour")
	public @ResponseBody List<Teacher> findTeacherLimitfour() {
		return teacherService.findLimitfour();
	}


	
	@RequestMapping("/teacher/queryTeacher")
	public @ResponseBody PageModel<Teacher> queryTeacherPageModel(
			@RequestParam("teacherName") String teacherName) {
		PageModel<Teacher> pageModel = new PageModel<>();
		pageModel.setPageIndex(1);
		Teacher teacher = new Teacher();
		//System.out.println("teachaerName"+teacherName);
		return teacherService.findTeacher(teacher, pageModel);
	}

}
