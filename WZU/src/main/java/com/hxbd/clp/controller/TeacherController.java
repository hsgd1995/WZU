package com.hxbd.clp.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.CourseAndTeacher;
import com.hxbd.clp.domain.Teacher;
import com.hxbd.clp.domain.TeacherContentPic;
import com.hxbd.clp.service.CourseAndTeacherService;
import com.hxbd.clp.service.CourseService;
import com.hxbd.clp.service.TeacherContentPicService;
import com.hxbd.clp.service.TeacherService;
import com.hxbd.clp.utils.common.RandomUtil;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.CourseAndTeacherVO;

@Controller
public class TeacherController {

	// 保存教师图片路径 C:\Program Files\web\Tomcat\webapps\HXCLImages\teacherPic
	private static final String SAVETEACHERPICPATH = "C:\\Program Files\\web\\Tomcat\\webapps\\HXCLImages\\teacherPic";

	private static final String SAVETEACHERCONTENTPICPATH = "C:\\Program Files\\web\\Tomcat\\webapps\\HXCLImages\\teacherContentPic";
	
	@Autowired
	private TeacherService teacherService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseAndTeacherService courseAndTeacherService;
	
	@Autowired
	private TeacherContentPicService teacherContentPicService;
	
	//用于存储教师详细介绍图片的url
	private List<String> teacherContentPicList = new ArrayList<String>();

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
	public @ResponseBody String saveTeacher(Teacher teacher, MultipartFile file) throws IOException {
		// 判断教师是否已经存在，如果存在那么直接返回false
		if (teacherService.selectById(teacher.getId()) != null) {
			return "{\"status\":false}";
		}
		// 如果文件不为空
		if (!file.isEmpty()) {
			// 生成随机数
			String random = RandomUtil.getRandom15();
			// 第一步：上传文件 C:\\Program
			// Files\\web\\Tomcat\\webapps\\HXCLImages\\teacherPic
			/*
			 * String path = "/teacherPic" + random + ".jpg"; String
			 * absolutePath =
			 * "http://www.hengmu-edu.com/HXCLImages/teacherPic/teacherPic" +
			 * random + ".jpg";
			 * FileUtils.copyInputStreamToFile(file.getInputStream(), new
			 * File(SAVETEACHERPICPATH + path));
			 */ // http://www.hengmu-edu.com/HXCLImages/teacherPic/teacherPic465834658763789.jpg
			String path = "/teacherPic" + random + ".jpg";
			//实际路径，用于服务器
			String absolutePath = "http://www.hengmu-edu.com/HXCLImages/teacherPic/teacherPic" + random + ".jpg";
			String picPath = "/teacherPic"+path;
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(SAVETEACHERPICPATH + path));
			// 第二步：将文件部分路径保存到数据库
			teacher.setTeacherPic(absolutePath);
			//teacher.setTeacherPic(picPath);
			//保存教师信息并返回自增id
			int id = teacherService.addTeacher(teacher);
			Teacher t = null;
			TeacherContentPic tcp = null;
			for (String string : teacherContentPicList) {
				//处理用户在编写教师详细介绍时上传图片后又删除图片的情况
				//如果上传某张图片后，保存是内容中确实用到了这张图片
				if(teacher.getContent().contains(string)){
					t = new Teacher();
					tcp = new TeacherContentPic();
					t.setId(id);
					tcp.setTeacher(teacher);
					tcp.setUrl(string);
					//添加数据到教师详细介绍表
					teacherContentPicService.add(tcp);
				}else{
					//上传了图片但是在教师详细介绍中没用到这张图片
					//删除文件夹中对应图片
					File oldFile = new File(SAVETEACHERCONTENTPICPATH+"/"+string);
					if(oldFile!=null){
						oldFile.delete();
					}
				}
			}
			//清空url集合
			teacherContentPicList.clear();
			
			return "{\"status\":true}";
		} else {
			return "{\"status\":false}";
		}
	}

	/**
	 * 上传教师详细介绍图片
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("teacher/uploads")
	@ResponseBody
	public String saveTeacherContentPic(MultipartFile file) throws Exception{
		if(!file.isEmpty()){
			String random = RandomUtil.getRandom15();
			Calendar calendar = Calendar.getInstance();
			String today = calendar.get(Calendar.YEAR)+"_"+(calendar.get(Calendar.MONTH)+1)+"_"+calendar.get(Calendar.DAY_OF_MONTH);
			String path = "/teacherContentPic"+today+"_"+random+".jpg";
			
			//1.上传文件
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(SAVETEACHERCONTENTPICPATH+path));
			
			//2.添加url到集合
			teacherContentPicList.add(path.substring(1,path.length()));
			//3.返回json格式数据，用于页面显示上传的图片
			//虚拟路径，用于本地测试
			String pageImgUrl = "/teacherContentPic"+path;
			//实际路径，用于服务器
			String absolutePath = "http://www.hengmu-edu.com/HXCLImages/teacherContentPic"+path;
			//return "{\"errno\": 0,\"data\": [\""+pageImgUrl+"\"]}";
			return "{\"errno\": 0,\"data\": [\""+absolutePath+"\"]}";
		}
		return "{\"errno\": 0,\"data\": [\""+null+"\"]}";
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
	public @ResponseBody String updateNews(Teacher teacher, MultipartFile file) throws IOException {
		if (file != null) {
			Teacher t = teacherService.selectById(teacher.getId());
			String filePath = t.getTeacherPic();
			//System.err.println(filePath); // http://www.hengmu-edu.com/HXCLImages/teacherPic/teacherPic465834658763789.jpg
			//String relPath = filePath.substring(47);
			String relPath = "/teacherPic"+filePath.split("teacherPic")[2];	
			//System.err.println(relPath); // 465834658763789.jpg
			String rellPath = SAVETEACHERPICPATH + relPath;
			//System.err.println(rellPath);
			File oldfile = new File(rellPath);
			// 删除旧文件
			oldfile.delete();

			// 上传文件
			String random = RandomUtil.getRandom15();
			String path = "/teacherPic" + random + ".jpg";
			String absolutePath = "http://www.hengmu-edu.com/HXCLImages/teacherPic/teacherPic" + random + ".jpg";
			//虚拟路径
			String picPath = "/teacherPic"+path;
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(SAVETEACHERPICPATH + path));
			// 将文件部分路径保存到数据库
			teacher.setTeacherPic(absolutePath);
			teacherService.modifyTeacher(teacher);
		}
		//System.err.println("保存教师信息:" + teacher);
		teacherService.modifyTeacher(teacher);
		
		//处理教师详细介绍中的图片
		//查找教师对应的详细介绍图片
		List<TeacherContentPic> tt= teacherContentPicService.SelectPicByTeacherId(teacher.getId());
		for (TeacherContentPic teacherContentPic : tt) {
			//如果原先的图片在修改教师后没用到，则删除原先图片
			if(!teacher.getContent().contains(teacherContentPic.getUrl())){
				//删除数据库中对应记录
				teacherContentPicService.delectById(teacherContentPic.getId());
				//删除文件夹中的图片
				File oldFile = new File(SAVETEACHERCONTENTPICPATH+"/"+teacherContentPic.getUrl());
				if(oldFile!=null){
					oldFile.delete();
				}
			}
		}
		
		//处理新上传的图片
		TeacherContentPic tcp = null;
		for (String string : teacherContentPicList) {
			//处理用户在编写教师详细介绍时上传图片后又删除图片的情况
			//如果上传某张图片后，保存是内容中确实用到了这张图片
			if(teacher.getContent().contains(string)){
				tcp = new TeacherContentPic();
				tcp.setTeacher(teacher);
				tcp.setUrl(string);
				//添加数据到教师详细介绍表
				teacherContentPicService.add(tcp);
			}else{
				//上传了图片但是在教师详细介绍中没用到这张图片
				//删除文件夹中对应图片
				File oldFile = new File(SAVETEACHERCONTENTPICPATH+"/"+string);
				if(oldFile!=null){
					oldFile.delete();
				}
			}
		}
		//清空url集合
		teacherContentPicList.clear();
		
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
		String teacherPic = "C:\\Program Files\\web\\Tomcat\\webapps\\HXCLImages\\teacherPic";
		//处理教师详细介绍的图片
		for (Integer integer : ids) {
			//找出每个教师对应的详细介绍图片
			List<TeacherContentPic> tList = teacherContentPicService.SelectPicByTeacherId(integer);
			for (TeacherContentPic teacherContentPic : tList) {
				//删除旧图片
				File oldFile = new File(SAVETEACHERCONTENTPICPATH+"\\"+teacherContentPic.getUrl());
				if(oldFile!=null){
					oldFile.delete();
				}
				//删除数据库中对应记录
				teacherContentPicService.delectById(teacherContentPic.getId());
			}
			//删除教师图片
			Teacher t = teacherService.selectById(integer);
			String teacherPicName = "teacherPic"+t.getTeacherPic().split("teacherPic")[2];
			File oldTFile = new File(teacherPic+"\\"+teacherPicName);
			if(oldTFile!=null){
				oldTFile.delete();
			}
		}
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
		if (teacherName != null && !teacherName.equals("")) {
			teacher.setTeacherName(teacherName);
		}
		if (teacherNo != null && !teacherNo.equals("")) {
			teacher.setTeacherNo(teacherNo);
		}
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

	/**
	 * 保存教师和课程的绑定
	 * 
	 * @param teacherId
	 * @param courseId
	 * @return
	 */
	@RequestMapping("/saveCourseToTeacher")
	public @ResponseBody String saveCourseToTeacher(@RequestParam("teacherNo") String teacherNo,
			@RequestParam("courseNo") String courseNo) {
		System.out.println("参数：" + teacherNo +"," + courseNo);
		Teacher teacher = teacherService.selectByTeacherNo(teacherNo);
		Course course = courseService.selectByTeacherCourseNo(courseNo);
		CourseAndTeacher cat = courseAndTeacherService.findCourseAndTeacher(teacher.getId(), course.getId());
		if (cat == null && teacher != null && course != null) {
			CourseAndTeacher courseAndTeacher = new CourseAndTeacher();
			courseAndTeacher.setTeacher(teacher);
			courseAndTeacher.setCourse(course);
			System.err.println(teacher +"," +course);
			courseAndTeacherService.saveCourseAndTeacher(courseAndTeacher);
			return "{\"status\":true}";
		} else {
			return "{\"status\":false}";
		}
	}

	/**
	 * CourseAndTeacherList
	 * 
	 * @param pageIndex
	 * @param keyword
	 * @param teacherId
	 * @param courseId
	 * @return
	 */
	@RequestMapping("/CourseAndTeacher/{pageIndex}/getCourseAndTeacherPageModel")
	public @ResponseBody PageModel<CourseAndTeacher> getCourseAndTeacherPageModel(@PathVariable Integer pageIndex, String teacherNo,
			String teacherName, String courseName) {
		CourseAndTeacher courseAndTeacher = new CourseAndTeacher();
		Teacher t2 = new Teacher();
		Course course = new Course();
		if(!StringUtils.isEmpty(teacherNo)){
			t2.setTeacherNo(teacherNo);
			courseAndTeacher.setTeacher(t2);
		}
		if(!StringUtils.isEmpty(teacherName)){
			t2.setTeacherName(teacherName);
			courseAndTeacher.setTeacher(t2);
		}
		if(!StringUtils.isEmpty(courseName)){
			course.setCourseName(courseName);
			courseAndTeacher.setCourse(course);
		}
		
		PageModel<CourseAndTeacher> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		return courseAndTeacherService.findCourseAndTeacher(courseAndTeacher, pageModel);

	}

	/**
	 * 删除教师和课程的关系
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/CourseAndTeacher/deleteCourseToTeacher")
	public @ResponseBody String deleteCourseToTeacher(@RequestParam("ids[]") Integer[] ids) {
		courseAndTeacherService.batchCourseAndTeacher(ids);
		return "{\"status\":true}";
	}

	
	@RequestMapping("/teacher/queryTeacher")
	public @ResponseBody PageModel<Teacher> queryTeacherPageModel(
			@RequestParam("teacherName") String teacherName) {
		PageModel<Teacher> pageModel = new PageModel<>();
		pageModel.setPageIndex(1);
		Teacher teacher = new Teacher();
		teacher.setTeacherName(teacherName);
		//System.out.println("teachaerName"+teacherName);
		return teacherService.findTeacher(teacher, pageModel);
	}

}
