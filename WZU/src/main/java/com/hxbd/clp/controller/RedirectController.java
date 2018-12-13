package com.hxbd.clp.controller;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hxbd.clp.domain.News;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.service.CourseService;
import com.hxbd.clp.service.NewsService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 页面跳转控制层
 * 
 * @author 恒信博大
 *
 */
@Controller
public class RedirectController {
	
	
	@Resource
	private CourseService courseService;
	@Resource
	private NewsService newsService;

	/**
	 * 进入登陆页面
	 * 
	 * @return
	 */
	@GetMapping("/index")
	public String index() {
		return "index";
	}

	/**
	 * 登录
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	/**
	 * 注册
	 * @return
	 */
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	/**
	 * 忘记密码
	 * @return
	 */
	@GetMapping("/retrieve_password")
	public String retrieve_password() {
		return "retrieve_password";
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	@GetMapping("/editpwd")
	public String editpwd() {
		return "editpwd";
	}
	
	/**
	 * 直播讲堂
	 * @return
	 */
	@GetMapping("/live")
	public String live() {
		return "live";
	}
	
	
	@GetMapping("/pushLive")
	public String pushLive() {
		return "pushLive";
	}
	
	/**
	 * 课程列表(全部)
	 * @return
	 */
	@GetMapping("/list_of_catalogues")
	public String listOfCatalogues() {
/*		int pageIndex = 1;
		PageModel<Course> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		PageModel<Course> model = courseService.findCourse(null, pageModel);
		request.setAttribute("courseList", model);*/
		return "list_of_catalogues";
	}
	
	/**
	 * 课程（文学）
	 * @return
	 */
	@GetMapping("/list_of_literature")
	public String listOfLiterature() {
		return "list_of_literature";
	}
	
	/**
	 * 课程（经济学）
	 * @return
	 */
	@GetMapping("/list_of_economics")
	public String listOfEconomics() {
		return "list_of_economics";
	}
	
	/**
	 * 课程（法学）
	 * @return
	 */
	@GetMapping("/list_of_law")
	public String listOfLaw() {
		return "list_of_law";
	}
	
	
/*	*//**
	 * 学习进度
	 * @return
	 *//*
	@GetMapping("/learning_process")
	public String learningProcess(HttpSession httpSession) {
		User userSession = (User)httpSession.getAttribute("user_session");
		System.err.println(userSession);
		if(userSession != null){
			return "learning_process";
		}else{
			return "login";
		}
	}*/
	
	/**
	 * 最新资讯
	 * @return
	 */
	@GetMapping("/latest_information")
	public String latestInformation(HttpServletRequest request,@RequestParam(value="pageIndex",required=false) Integer currentPage) {
		int pageIndex = 1;
		if(currentPage!=null){
			//System.out.println("latest_information currentPage:"+currentPage);
			pageIndex = currentPage;
		}
		PageModel<News> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		PageModel<News> model = newsService.findNews(null, pageModel);
		request.setAttribute("newsList", model);
		//System.err.println(model.getList());
		return "latest_information";
	}
	
	/**
	 * 新闻动态 type=0
	 * @param pageIndex
	 * @return
	 */
	@GetMapping("/newsTrends")
	public String newsTrends(HttpServletRequest request,@RequestParam(value="pageIndex",required=false) Integer currentPage) {
		int pageIndex = 1;
		if(currentPage!=null){
			//System.out.println("newsTrends currentPage:"+currentPage);
			pageIndex = currentPage;
		}
		PageModel<News> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		News news = new News();
		int newsType = 0;
		news.setNewsType(newsType);
		PageModel<News> model = newsService.findNews(news, pageModel);
		request.setAttribute("newsList", model);
		//System.err.println(model.getList());
		return "newsTrends";
	}
	
	/**
	 * 媒体报道 type=1
	 * @param pageIndex
	 * @return
	 */
	@GetMapping("/newsReport")
	public String newsReport(HttpServletRequest request,@RequestParam(value="pageIndex",required=false) Integer currentPage) {
		int pageIndex = 1;
		if(currentPage!=null){
			//System.out.println("newsReport currentPage:"+currentPage);
			pageIndex = currentPage;
		}
		PageModel<News> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		News news = new News();
		int newsType = 1;
		news.setNewsType(newsType);
		PageModel<News> model = newsService.findNews(news, pageModel);
		request.setAttribute("newsList", model);
		//System.err.println(model.getList());
		return "newsReport";
	}

	
	/**
	 * 关于我们
	 * @return
	 */
	@GetMapping("/about_us")
	public String aboutUs() {
		return "about_us";
	}
	
	/**
	 * 加入课程
	 * @return
	 */
	@GetMapping("/catalog")
	public String catalog(HttpSession httpSession) {
		User userSession = (User)httpSession.getAttribute("user_session");
		//System.err.println(userSession);
		if(userSession != null){
			return "catalog";
		}else{
			return "login";
		}
	}
	
	
	/**
	 * 课程公告
	 * @return
	 */
	@GetMapping("/details")
	public String details() {
		return "details";
	}
	
	/**
	 * 课程介绍
	 * @return
	 */
	@GetMapping("/introduce")
	public String introduce() {
		return "introduce";
	}
	
	/**
	 * 课程视频
	 * @return
	 */
	@GetMapping("/curriculum_video")
	public String curriculum_video() {
		return "curriculum_video";
	}
	
	/**
	 * 能力测试
	 * @return
	 */
	@GetMapping("/ability_test")
	public String ability_test() {
		return "ability_test";
	}
	
	/**
	 * 论坛
	 * @return
	 */
	@GetMapping("/forum")
	public String forum() {
		return "forum";
	}
	
	/**
	 * 论坛详情
	 * @return
	 */
	@GetMapping("/forum_content")
	public String forum_content() {
		return "forum_content";
	}
	
	/**
	 * 收藏的课程
	 * @return
	 */
	@GetMapping("/collection_course")
	public String collection_course() {
		return "collection_course";
	}
	
	
	/**
	 * 打开管理员消息页面
	 * @return
	 */
	@GetMapping("/managerMessage")
	public String openMessage() {
		return "my_message";
	}
	
	@GetMapping("/sysMessage")
	public String openSysMessage(){
		return "news_page";
	}
	
	
	/**
	 * 个人设置
	 * @return
	 */
	@GetMapping("/my_set_up")
	public String  my_set_up(){
		return "my_set_up";
	}
	
	@GetMapping("/noLogin")
	public String  noLogin(){
		return "sessionByNull";
	}
}
