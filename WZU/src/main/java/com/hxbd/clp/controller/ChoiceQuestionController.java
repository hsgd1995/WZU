package com.hxbd.clp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hxbd.clp.domain.ChoiceQuestion;
import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.service.ChoiceQuestionService;
import com.hxbd.clp.service.CourseVideoService;
import com.hxbd.clp.to.UploadStatus;
import com.hxbd.clp.utils.common.ErrorImport;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.ChoiceQuestionVO;

@Controller
public class ChoiceQuestionController {

	@Autowired
	private ChoiceQuestionService choiceQuestionService;

	@Autowired
	private CourseVideoService courseVideoService;

	/**
	 * 未使用
	 * 
	 * @return
	 */
	@GetMapping("/main/cqTest")
	public String cqTest() {
		return "cqTest";
	}

	/**
	 * 进入选择题页面
	 * 
	 * @return
	 */
	@GetMapping("/main/question")
	public String question() {
		return "question";
	}

	@RequestMapping("/cq/getQuestion/{id}")
	public String getQuestionById(@PathVariable("id") Integer id, HttpSession httpSession) {
		httpSession.setAttribute("courseVideoId", id);
		return "cqTest";
	}

	/**
	 * 打开答题页面
	 * @return
	 */
	@GetMapping("/answer/{courseVideoId}")
	public String openQuestion(HttpServletRequest request,@PathVariable Integer courseVideoId,HttpSession session){
		User user = (User)session.getAttribute("user_session");
		if(user == null){
			return "sessionByNull";
		}
		int userId = user.getId();
		String answer = choiceQuestionService.selectAcswerByUserIdAndCouId(userId, courseVideoId);
		if(answer == null){
			answer = "0";
		}
		request.setAttribute("courseVideoId", courseVideoId);
		request.setAttribute("answer", answer);
		return "answer";
	}
	
	@GetMapping("/cq/result")
	public String openResult(){
		return "result";
	}
	
	@GetMapping("/result/{courseVideoId}")
	public String openResult(HttpServletRequest request,@PathVariable Integer courseVideoId,HttpSession session){
		request.setAttribute("courseVideoId", courseVideoId);
		User user = (User)session.getAttribute("user_session");
		if(user == null){
			return "sessionByNull";
		}
		int userId = user.getId();
		String answer = choiceQuestionService.selectAcswerByUserIdAndCouId(userId, courseVideoId);
		String as = answer.replace('"','-');
		request.setAttribute("answer",as);
		return "seeResult";
	}
	
	/**
	 * 获取课程对应的测试题
	 * 
	 * @param courseVideoId
	 * @return Map<String, Object> key值是测试题类型，value是对应的List<>
	 */
	@RequestMapping("/cq/{courseVideoId}/getQuestions")
	public String getQuestions(@PathVariable Integer courseVideoId, Model model) {
		Map<String, Object> map = choiceQuestionService.selectByCourseVideoId(courseVideoId);
		model.addAttribute("map", map);
		return "answer";
	}
	
	/**
	 * 获取答题结果
	 * 
	 * @param courseVideoId
	 * @return Map<String, Object> key值是测试题类型，value是对应的List<>
	 */
	@RequestMapping("/cq/{courseVideoId}/getResult")
	public String getResult(@PathVariable Integer courseVideoId, @RequestParam String answer,HttpSession session, Model model) {
		JSONObject jo = new JSONObject(answer);
		Map<String, Object> map = choiceQuestionService.selectByCourseVideoId(courseVideoId);
		Map<String, Object> newmap = choiceQuestionService.verificationAnswer(courseVideoId,jo);
		model.addAttribute("map", map);
		model.addAttribute("newmap", newmap);
		
		User user = (User)session.getAttribute("user_session");
		int userId = user.getId();
		String answers = choiceQuestionService.selectAcswerByUserIdAndCouId(userId, courseVideoId);
		if(answers == null){
			double fraction = (double)newmap.get("fraction");
			choiceQuestionService.addAchievementByUserId(userId,courseVideoId,fraction,answer);
		}
		return "seeResult";
	}
	
	/**
	 * 修改选择题：根据id获取选择题信息
	 * 
	 * @param id
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/cq/{id}/getQuestion")
	public @ResponseBody ChoiceQuestion getQuestionById(@PathVariable Integer id) {
		return choiceQuestionService.selectById(id);
	}

	/**
	 * 获取列表
	 * 
	 * @param pageIndex
	 * @param keyword
	 * @param courseVideoId
	 * @return
	 */
	@RequestMapping("/cq/{pageIndex}/getQuestionPageModel")
	public @ResponseBody PageModel<ChoiceQuestionVO> getQuestionPageModel(@PathVariable Integer pageIndex,String subject,String title) {
		PageModel<ChoiceQuestionVO> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		//System.out.println("pageIndex:" + pageIndex);
		/* Map<Integer,Course> courseMap = courseService.findAllMap(); */
		Map<Integer, CourseVideo> courseVideoMap = courseVideoService.findAllMap();
		/* pageModel.setPageSize(8); */
		ChoiceQuestionVO cqVO = new ChoiceQuestionVO();
		if (subject != null && subject != "") {
			cqVO.setTitle(subject);
		}
		if (title != null && title != "") {
			cqVO.setCourseName(title);
		}
		return choiceQuestionService.selectByPageModel(cqVO, pageModel, courseVideoMap);
	}

	/**
	 * 新增
	 * 
	 * @param cq
	 * @return
	 */
	@RequestMapping("/cq/saveQuestion")
	public @ResponseBody String saveQuestion(ChoiceQuestion cq) {
		//System.err.println("kdshgkjfdhgkjfhg:" + cq);
		choiceQuestionService.saveCQ(cq);
		return "{\"status\":true}";
	}

	/**
	 * 导入Excel
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/cq/importExcel")
	public @ResponseBody Object importQuestion(MultipartFile file) {
		try {
			// 获取上传的文件
			/*
			 * MultipartHttpServletRequest multipart =
			 * (MultipartHttpServletRequest) request; MultipartFile file =
			 * multipart.getFile("file");
			 */
			InputStream in = file.getInputStream();
			// 数据导入
			ErrorImport<CourseVideo> error = choiceQuestionService.batchAddQuestion(in, file.getOriginalFilename());
			in.close();
			// 不存在错误记录和不合格记录
			if (error.getErrorList().size() == 0 && error.getFailIndex() == 0) {
				return "{\"status\":true}";
			} else {
				return error;
			}
		} catch (IOException e) {
			e.printStackTrace();
			// 失败处理
			return "{\"status\":false}";
		}
	}

	/**
	 * 这里是获取上传文件状态信息的访问接口
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cq/getStatus")
	public UploadStatus getStatus(HttpSession session) {

		UploadStatus u = (UploadStatus) session.getAttribute("upload_status");
		// 当进度达到80%
		if (u.getPercent() >= 80) {
			// 设置为80%，只有在全部遍历后才设置为100%
			u.setPercent(80);
			// 判断记录是否遍历完成
			if (choiceQuestionService.getImportStatus() == 1) {
				// 是，返回100%
				u.setPercent(100);
			}
		}
		return u;
	}

	/**
	 * 更新
	 * 
	 * @param cq
	 * @return
	 */
	@RequestMapping("/cq/updateQuestion")
	public @ResponseBody String updateQuestion(ChoiceQuestion cq) {
		choiceQuestionService.updateeCQ(cq);
		return "{\"status\":true}";
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/cq/delQuestion")
	public @ResponseBody String delQuestion(@RequestParam("ids[]") Integer[] ids) {
		//System.out.println(ids);
		choiceQuestionService.delCQ(ids);
		return "{\"status\":true}";
	}
}