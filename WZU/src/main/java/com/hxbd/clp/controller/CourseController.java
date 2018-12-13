package com.hxbd.clp.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hxbd.clp.Form.CourseForm;
import com.hxbd.clp.domain.ChoiceQuestion;
import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.CourseAndUser;
import com.hxbd.clp.domain.CourseDetails;
import com.hxbd.clp.domain.CourseType;
import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.domain.Notice;
import com.hxbd.clp.domain.Process;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.service.ChoiceQuestionService;
import com.hxbd.clp.service.CourseAndUserService;
import com.hxbd.clp.service.CourseDetailsService;
import com.hxbd.clp.service.CourseService;
import com.hxbd.clp.service.CourseTypeService;
import com.hxbd.clp.service.CourseVideoService;
import com.hxbd.clp.service.NoticeService;
import com.hxbd.clp.utils.common.RandomUtil;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 课程管理控制层
 * 
 * @author 恒信博大
 *
 */
@Controller
public class CourseController {
	
	//保存新闻图片路径
	private static final String SAVECOURSEPICPATH = "C:\\Program Files\\web\\Tomcat\\webapps\\HXCLImages\\coursePic";
	
	@Resource
	private CourseService courseService;
	
	@Resource
	private NoticeService noticeService;
	
	@Resource
	private CourseVideoService courseVideoService;
	
	@Resource
	private CourseAndUserService courseAndUserService;
	
	@Resource
	private CourseTypeService courseTypeService;
	
	@Resource
	private CourseDetailsService courseDetailsService;
	
	@Resource
	private ChoiceQuestionService choiceService;
	
	
	@RequestMapping("/course/{courseId}/getCourse")
	public @ResponseBody Course selectByCourseId(@PathVariable String courseId){
		return courseService.selectByCourseId(courseId);
	}
	
	@RequestMapping("/findCourseById")
	public @ResponseBody Course findCourseById(@RequestParam("id") Integer id){
		return courseService.selectById(id);
	}
	
	@RequestMapping(value ="/course/saveCourse", method = RequestMethod.POST)
	public @ResponseBody String addCourse(CourseForm courseForm, MultipartFile file)throws IOException{
		if (courseService.selectById(courseForm.getId()) != null) {
			return "{\"status\":false}";
		}
		// 如果文件不为空
		if (!file.isEmpty()) {
			// 生成随机数
			String random = RandomUtil.getRandom15();
			// 第一步：上传文件 C:\\Program
			String path = "/coursePic" + random + ".jpg";
			String absolutePath = "http://www.hengmu-edu.com/HXCLImages/coursePic/coursePic" + random + ".jpg";
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(SAVECOURSEPICPATH + path));
			// 第二步：将文件部分路径保存到数据库
			courseForm.setCoursePic(absolutePath);
			courseService.addCourse(courseForm);
			return "{\"status\":true}";
		} else {
			return "{\"status\":false}";
		}
	}
	
	@RequestMapping(value = "/course/updateCourse", method = RequestMethod.POST)
	public @ResponseBody String updateCourse(CourseForm courseForm, MultipartFile file) throws IOException{
		Course course = courseService.selectById(courseForm.getId());
		if (file != null) {
			String filePath = course.getCoursePic();
			String relPath = filePath.substring(47);
			//System.err.println(relPath); // 465834658763789.jpg
			String rellPath = SAVECOURSEPICPATH + relPath;
			//System.err.println(rellPath);
			File oldfile = new File(rellPath);
			// 删除旧文件
			oldfile.delete();

			// 上传文件
			String random = RandomUtil.getRandom15();
			String path = "/coursePic" + random + ".jpg";
			String absolutePath = "http://www.hengmu-edu.com/HXCLImages/coursePic/coursePic" + random + ".jpg";
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(SAVECOURSEPICPATH + path));
			// 将文件部分路径保存到数据库
			courseForm.setCoursePic(absolutePath);
			courseService.updateCourse(courseForm);
		}else{
			String absolutePath = course.getCoursePic();
			courseForm.setCoursePic(absolutePath);
		}
		courseService.updateCourse(courseForm);
		return "{\"status\":true}";
	}
	
	@RequestMapping("/course/deleteCourse")
	public @ResponseBody String courseList(@RequestParam("ids[]") Integer[] ids){
		courseService.batchDelCourse(ids);
		return "{\"status\":true}";
	}
	
	@RequestMapping("/course/getCourse")
	public @ResponseBody List<Course> getCourseList(){
		return courseService.getCourseList();
	}
	
	//统计收费课程数量和开放课程数量
	@RequestMapping("/course/getCourseSort")
	public @ResponseBody Map<String,Integer> getCourseSort(){
			Course course0 = new Course();
			course0.setOrfree(0);
			Course course1 = new Course();
			course1.setOrfree(1);
			Course course2 = new Course();
			course2.setOrfree(2);
			Map<String,Integer> map = new HashMap<>();
			map.put("count0", courseService.findCourseSort(course0));
			map.put("count1", courseService.findCourseSort(course1));
			map.put("count2", courseService.findCourseSort(course2));
			return map;
	
	}
	
	
	@RequestMapping("/course/{pageIndex}/getCoursePageModel")
	public @ResponseBody PageModel<Course> getCoursePageModel(@PathVariable Integer pageIndex,@RequestParam("keyword") String keyword, String orfree,String courseTypeId,@RequestParam(value="language[]",required=false) String[] language){
		
		
		String courseLanguage = "";
		if(language!=null){
			
			for (int i = 0;i<language.length;i++) {
				courseLanguage = courseLanguage+language[i]+",";
			}
			courseLanguage = courseLanguage.substring(0, courseLanguage.length()-1);
		}
		
		
		PageModel<Course> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		Course course = new Course();
		course.setCourseName(keyword); 
		if(orfree != null && !orfree.equals("2")){
			course.setOrfree(Integer.parseInt(orfree));
			if(courseTypeId != null && !"".equals(courseTypeId)){
			course.setCourseTypeId(Integer.parseInt(courseTypeId));
			}
			if(courseLanguage!="" ){
				course.setCourseLanguage(courseLanguage);
			}
			return courseService.findCourse(course, pageModel);
		}
		if(courseTypeId != null && !"".equals(courseTypeId)){
			course.setOrfree(2);
			course.setCourseTypeId(Integer.parseInt(courseTypeId));
			return courseService.findCourse(course, pageModel);
		}
		return courseService.findCourse(course, pageModel);
	}
	
	@RequestMapping("/course/{pageIndex}/getCoursePageModelBackstage")
	public @ResponseBody PageModel<Course> getCoursePageModelTuo(@PathVariable Integer pageIndex,String courseNo,String courseName){
		PageModel<Course> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		Course course = new Course();
		if(courseNo != null && !courseNo.equals("")){
			course.setCourseNo(courseNo);
		}
		if(courseName != null && !courseName.equals("")){
			course.setCourseName(courseName);
		}
		return courseService.findCourse(course, pageModel);
	}
	
	@RequestMapping("/course/getCourseType")
	public @ResponseBody List<CourseType> getCourseType(){
		return courseTypeService.findList();
	}
	
	
	//课程列表详情页面
	@RequestMapping("/curriculum/{id}")
	public String RedirectCurriculum(HttpServletRequest request,@PathVariable Integer id) {
		Course course = courseService.selectById(id);
		request.setAttribute("courseById", course);
		return "/curriculum";
	}
	
	//加入课程页面--课程大纲
	@RequestMapping("/catalog/{id}")
	public String catalog(HttpServletRequest request,@PathVariable Integer id) {
		Course course = courseService.selectById(id);
		request.setAttribute("courseById", course);
		return "/catalog";
	}
	
	
	//加入课程页面--课程介绍
	@RequestMapping("/introduce/{id}")
	public String introduce(HttpServletRequest request,@PathVariable Integer id) {
		Course course = courseService.selectById(id);
		request.setAttribute("courseById", course);
		return "/introduce";
	}
	
	//加入课程页面--课程公告
	@RequestMapping("/notice/{id}")
	public String notice(HttpServletRequest request,@PathVariable Integer id) {
		Course course = courseService.selectById(id);
		List<Notice> notice = noticeService.findNoticeByCourseId(id);
		request.setAttribute("courseById", course);
		request.setAttribute("noticeList", notice);
		return "/notice";
	}
	
	//加入课程页面--在线学习
	@RequestMapping("/onlineLearning/{id}")
	public String onlineLearning(HttpServletRequest request,@PathVariable Integer id) {
		List<CourseVideo> treeList = courseVideoService.findCourseVideoTree(id);	
		//System.err.println("fdgdf111111111111"+treeList);
/*		Gson gson = new Gson();
		String newJson = gson.toJson(treeList);
		System.err.println(newJson);*/		
		Course course = courseService.selectById(id);
		request.setAttribute("courseById", course);
		request.setAttribute("treeList", treeList);
		return "/online_learning";
	}
	
	//加入课程页面--在线学习--搜索
		@RequestMapping("/onlineLearning-search/{id}")
		public String onlineLearningWithSearch(HttpServletRequest request,@PathVariable Integer id,@RequestParam("keyword") String keyword) {
			List<CourseVideo> treeList = courseVideoService.findCourseVideoTree(id,keyword);	
			//System.err.println("fdgdf111111111111"+treeList);
	/*		Gson gson = new Gson();
			String newJson = gson.toJson(treeList);
			System.err.println(newJson);*/		
			Course course = courseService.selectById(id);
			request.setAttribute("courseById", course);
			request.setAttribute("treeList", treeList);
			request.setAttribute("keyword", keyword);
			return "/online_learning";
		}
	
	//视频详情页
	@RequestMapping("/video_details/{id}")
	public String videoDetails(HttpServletRequest request,@PathVariable Integer id) {
		System.out.println("aop测试：videoDetails");
		CourseVideo courseVideo = courseVideoService.selectById(id);
		Integer courseId = courseVideo.getCourseId();
		List<CourseVideo> treeList = courseVideoService.findCourseVideoTree(courseId);
		
		int userId ; 
		Process process = null;
		User user = ((User)request.getSession().getAttribute("user_session"));
		if(user != null){
			userId = user.getId();
			process = courseVideoService.getVideoProcess(userId, id, courseId);
		}
		String captionUrl = "C:\\Program Files\\web\\Tomcat\\webapps\\HXCLVideo\\caption";
		request.setAttribute("captionUrl", captionUrl);
		request.setAttribute("treeList", treeList);
		request.setAttribute("courseVideo", courseVideo);
		request.setAttribute("process", process);
		return "/video_details";
	}
	
	//加入课程页面--能力测试
	@RequestMapping("/abilityTest/{id}")
	public String abilityTest(HttpServletRequest request,@PathVariable Integer id) {
		Course course = courseService.selectById(id);
		List<CourseVideo> treeList = courseVideoService.findCourseVideoTree(id);
		request.setAttribute("courseById", course);
		request.setAttribute("treeList", treeList);
		//return "/ability_test_detail";
		return "/ability_test";
	}
	
	//加入课程页面-能力测试详情
	@RequestMapping("/abilityTest_detail/{id}")
	public ModelAndView abilityTestDetail(HttpServletRequest request,HttpServletResponse response,@PathVariable Integer id) {
//		return "{\"message\":true}";
		ModelAndView mav = new ModelAndView();
		List<CourseVideo> treeList = courseVideoService.findCourseVideoTree(id);
		Course course = courseService.selectById(id);
		ChoiceQuestion questList = choiceService.selectById(id);
		request.setAttribute("courseById", course);
		request.setAttribute("questList", questList);
		//题数
		request.setAttribute("questionNum", "1");
		//mav.addObject("msg","获取id为："+id);
		mav.setViewName("ability");
		return mav;
	}
	
	//加入课程页面--论坛
	@RequestMapping("/forum/{id}")
	public String forum(HttpServletRequest request,@PathVariable Integer id) {
		Course course = courseService.selectById(id);
		request.setAttribute("courseById", course);
		return "/forum";
	}
	
	//查询最新8条数据
	@RequestMapping("/findCourseLimitEight")
	public @ResponseBody List<Course> findCourseLimitEight(){
		return courseService.findLimitEight();
	}
	
	//加入课程
	@RequestMapping("/joinCourse/{courseId}")
	public @ResponseBody String joinCourse(HttpSession httpSession,@PathVariable Integer courseId){
		User userSession = (User)httpSession.getAttribute("user_session");
		if(userSession != null) {
		Integer userId = userSession.getId();
		CourseAndUser cau = new CourseAndUser();
		CourseAndUser courseAndUser = courseAndUserService.findByUserIdAndCourseId(userId,courseId);
			if(courseAndUser == null ){
				cau.setUserId(userId);
				cau.setCourseId(courseId);
				cau.setCollect(0);  //0  未收藏
				cau.setJoincourse(1);	  //1表示要加入课程
				courseAndUserService.saveCourseAndUser(cau);
			}
			//如果用户加入过课程，表不添加新数据
			if(courseAndUser != null){
				if(courseAndUser.getJoincourse() != 1){
					courseAndUser.setJoincourse(1);	  //1表示要加入课程
				courseAndUserService.editCourseAndUser(courseAndUser);
				}
			}
			return "{\"status\":true}";
		}
		return "{\"status\":false}";
	}
	
	//显示用户对这门课程的状态
	@RequestMapping("/joinCourseStatus/{courseId}")
	public @ResponseBody String joinCourseStatus(HttpSession httpSession,@PathVariable Integer courseId){
		User userSession = (User)httpSession.getAttribute("user_session");
		if(userSession != null) {
		Integer userId = userSession.getId();
		CourseAndUser courseAndUser = courseAndUserService.findByUserIdAndCourseId(userId,courseId);
			if(courseAndUser != null){
				if(courseAndUser.getJoincourse() == 1){
				return "{\"status\":true}";
				}
			}
			return "{\"status\":false}";
		}
		return "{\"status\":false}";
	}
	
	//显示收藏状态
	@RequestMapping("/collectStatus/{courseId}")
	public @ResponseBody String collectStatus(HttpSession httpSession,@PathVariable Integer courseId){
		User userSession = (User)httpSession.getAttribute("user_session");
		if(userSession != null) {
		Integer userId = userSession.getId();
		CourseAndUser courseAndUser = courseAndUserService.findByUserIdAndCourseId(userId,courseId);
			if(courseAndUser != null){
				if(courseAndUser.getCollect() == 1){
				return "{\"status\":true}";
				}
			}
			return "{\"status\":false}";
		}
		return "{\"status\":false}";
	}
	
	//更改收藏状态
	@RequestMapping("/changeCollectStatus/{courseId}")
	public @ResponseBody String changeCollectStatus(HttpSession httpSession,@PathVariable Integer courseId){
		User userSession = (User)httpSession.getAttribute("user_session");
		if(userSession != null) {
		Integer userId = userSession.getId();
		CourseAndUser courseAndUser = courseAndUserService.findByUserIdAndCourseId(userId,courseId);
			if(courseAndUser != null){
				if(courseAndUser.getCollect() == 1){
					courseAndUser.setCollect(0);	  //0表示要删除收藏课程
					courseAndUserService.editCourseAndUser(courseAndUser);
					return "{\"status\":0}";
				}
				if(courseAndUser.getCollect() == 0){
					courseAndUser.setCollect(1);	  //1表示要添加收藏课程
					courseAndUserService.editCourseAndUser(courseAndUser);
					return "{\"status\":1}";
				}
			}
			return "{\"status\":false}";
		}
		return "{\"status\":false}";
	}
	
	//收藏课程
	@RequestMapping("/collectCourse/{courseId}")
	public @ResponseBody String collectCourse(HttpSession httpSession,@PathVariable Integer courseId){
		User userSession = (User)httpSession.getAttribute("user_session");
		if(userSession != null) {
		Integer userId = userSession.getId();
		CourseAndUser cau = new CourseAndUser();
		CourseAndUser courseAndUser = courseAndUserService.findByUserIdAndCourseId(userId,courseId);
			if(courseAndUser == null){
				cau.setUserId(userId);
				cau.setCourseId(courseId);
				cau.setCollect(1);  //1  收藏
				cau.setJoincourse(0);
				courseAndUserService.saveCourseAndUser(cau);
				return "{\"status\":true}";
			}
			if(courseAndUser != null){
				if(courseAndUser.getCollect() != 1){
					courseAndUser.setCollect(1);	  //1表示要加入课程
				courseAndUserService.editCourseAndUser(courseAndUser);
				return "{\"status\":true}";
				}
			}
		}else {
			return "{\"status\":false}";
		}
		return "{\"status\":true}";
	}
	
	
	//多少个人已学习        -----curriculum页面
	@RequestMapping("/alreadyStudy/{courseId}")
	public @ResponseBody Integer alreadyStudy(@PathVariable Integer courseId){
		return courseAndUserService.findStudyPeopleCountByCourseId(courseId);
	}
	
	
	//课程类型分页查询
	@RequestMapping("/courseType/{pageIndex}/getCourseTypePageModel")
	public @ResponseBody PageModel<CourseType> getCourseTypePageModel(@PathVariable Integer pageIndex,@RequestParam("keyword") String keyword){
		PageModel<CourseType> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		if(keyword != null && !keyword.equals("")){
			CourseType courseType = new CourseType();
			courseType.setTypeName(keyword); 
			return courseTypeService.findCourseType(courseType, pageModel);
		}
		return courseTypeService.findCourseType(null, pageModel);
	}
	
	//查询课程类型List
	@RequestMapping("/courseType/getCourseType")
	public @ResponseBody List<CourseType> gerCourseType(){
		return courseTypeService.getCourseTypeList();
	}
	
	//查看课程详情
	@RequestMapping("/findCourseDetailById")
	public @ResponseBody Course seeCourseDetailById(@RequestParam("id") Integer id){
		return courseService.selectById(id);
	}
	
	//查看课程详情
	@RequestMapping("/findCourseDetailByNo")
	public @ResponseBody Course seeCourseDetailByNo(@RequestParam("courseNo") String courseNo){
		return courseService.selectByTeacherCourseNo(courseNo);
	}
	
	//查询课程详情List
	@RequestMapping("/courseDetails/getCourseDetails")
	public @ResponseBody List<CourseDetails> getCourseDetails(){
		return courseDetailsService.getCourseDetailsList();
	}
	
	
	//添加课程类型
	@RequestMapping("/courseType/saveCourseType")
	public @ResponseBody String saveCourseType(CourseType courseType){
		courseTypeService.saveCourseType(courseType);
		return "{\"status\":true}";
	}
	
	//编辑课程类型
	@RequestMapping("/courseType/updateCourseType")
	public @ResponseBody String updateCourseType(CourseType courseType){
		courseTypeService.updateCourseType(courseType);
		return "{\"status\":true}";
	}
	
	//删除课程类型
	@RequestMapping("/courseType/deleteCourseType")
	public @ResponseBody String deleteCourseType(@RequestParam("ids[]") Integer[] ids){
		courseTypeService.batchDelCoursetype(ids);
		return "{\"status\":true}";
	}
	
	//根据ID查询课程类型
	@RequestMapping("/findCourseTypeById")
	public @ResponseBody CourseType findCourseTypeById(@RequestParam("id") Integer id){
		return courseTypeService.selectById(id);
	}
	
	//根据ID查询课程类型详情
	@RequestMapping("/findCourseDetailsById")
	public @ResponseBody CourseDetails findCourseDetailsById(@RequestParam("id") Integer id){
		return courseDetailsService.selectById(id);
	}
	
	
	//课程详情分页查询
	@RequestMapping("/courseDetails/{pageIndex}/getCourseDetailsPageModel")
	public @ResponseBody PageModel<CourseDetails> getCourseDetailsPageModel(@PathVariable Integer pageIndex,@RequestParam("keyword") String keyword){
		PageModel<CourseDetails> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		if(keyword != null && !keyword.equals("")){
			CourseDetails courseDetails = new CourseDetails();
			return courseDetailsService.findCourseDetails(courseDetails, pageModel);
		}
		return courseDetailsService.findCourseDetails(null, pageModel);
	}
	
	//添加课程详情
	@RequestMapping(value =  "/courseDetails/saveCourseDetails", method = RequestMethod.POST)
	public @ResponseBody String saveCourseDetails(CourseDetails courseDetails,MultipartFile file) throws IOException {
		String random = RandomUtil.getRandom15();
		if (!file.isEmpty()) {
			// 第一步：上传文件
			String path = "/coursePic" + random  + ".jpg";
			//String absolutePath = SAVECOURSEPICPATH;
			/*String path = "/" + random  + ".jpg";*/
			String absolutePath = "http://www.hengmu-edu.com/HXCLImages/coursePic/coursePic" + random + ".jpg";
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(SAVECOURSEPICPATH + path));
			// 第二步：将文件部分路径保存到数据库
			courseDetails.setCoursePic(absolutePath);
			//System.err.println("1122:"+courseDetails);
			courseDetailsService.saveCourseDetails(courseDetails);
			return "{\"status\":true}";
		}else{
			return "{\"status\":false}";
		}
	}
	
	//编辑课程详情
	@RequestMapping(value = "/courseDetails/updateCourseDetails", method = RequestMethod.POST)
	public @ResponseBody String updateCourseDetails(CourseDetails courseDetails,MultipartFile file) throws IOException{
		String random = RandomUtil.getRandom15();
		if(file != null){
			CourseDetails cd = courseDetailsService.selectById(courseDetails.getId());
			String filePath = cd.getCoursePic();
			String relPath = filePath.substring(47);	
			String rellPath = SAVECOURSEPICPATH + relPath;
			File oldfile = new File(rellPath);
			//删除旧文件
			oldfile.delete();
			//上传文件
			String path = "/coursePic" + random  + ".jpg";
			String absolutePath = "http://www.hengmu-edu.com/HXCLImages/coursePic/coursePic" + random + ".jpg";
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(SAVECOURSEPICPATH + path));
			//将文件部分路径保存到数据库
			courseDetails.setCoursePic(absolutePath);
			courseDetailsService.updateCourseDetails(courseDetails);
			return "{\"status\":true}";	
	}else{
		courseDetailsService.updateCourseDetails(courseDetails);
		return "{\"status\":true}"; 
		}
	}
	
	//删除课程详情
	@RequestMapping("/courseDetails/deleteCourseDetails")
	public @ResponseBody String deleteCourseDetails(@RequestParam("ids[]") Integer[] ids){
		courseDetailsService.batchDelCourseDetails(ids);
		return "{\"status\":true}";
	}
}
