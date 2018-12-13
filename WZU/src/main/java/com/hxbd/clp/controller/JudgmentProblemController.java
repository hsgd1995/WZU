package com.hxbd.clp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.domain.JudgmentProblem;
import com.hxbd.clp.service.CourseVideoService;
import com.hxbd.clp.service.JudgmentProblemService;
import com.hxbd.clp.to.UploadStatus;
import com.hxbd.clp.utils.common.ErrorImport;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.JudgmentProblemVO;

@Controller
public class JudgmentProblemController {

	@Autowired
	private JudgmentProblemService judgmentProblemService;

	@Autowired
	private CourseVideoService courseVideoService;

	@RequestMapping("/jp/getQuestion/{id}")
	public String getQuestionById(@PathVariable("id") Integer id, HttpSession httpSession) {
		httpSession.setAttribute("courseVideoId", id);
		return "jpTest";
	}

	/**
	 * 修改选择题：根据id获取选择题信息
	 * 
	 * @param id
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/jp/{id}/getQuestion")
	public @ResponseBody JudgmentProblem getQuestionById(@PathVariable Integer id) {
		return judgmentProblemService.selectById(id);
	}

	/**
	 * 获取列表
	 * 
	 * @param pageIndex
	 * @param keyword
	 * @param courseVideoId
	 * @return
	 */
	@RequestMapping("/jp/{pageIndex}/getQuestionPageModel")
	public @ResponseBody PageModel<JudgmentProblemVO> getQuestionPageModel(@PathVariable Integer pageIndex,String subject,String title) {
		PageModel<JudgmentProblemVO> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		Map<Integer, CourseVideo> courseVideoMap = courseVideoService.findAllMap();
		JudgmentProblemVO jpVO = new JudgmentProblemVO();
		if (subject != null && subject != "") {
			jpVO.setTitle(subject);
		}
		if (title != null && title != "") {
			jpVO.setCourseName(title);
		}
		return judgmentProblemService.selectByPageModel(jpVO, pageModel, courseVideoMap);
	}

	/**
	 * 新增
	 * 
	 * @param cq
	 * @return
	 */
	@RequestMapping("/jp/saveQuestion")
	public @ResponseBody String saveQuestion(JudgmentProblem jp) {
		//System.err.println("kdshgkjfdhgkjfhg:" + jp);
		judgmentProblemService.saveJP(jp);
		return "{\"status\":true}";
	}

	/**
	 * 导入Excel
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/jp/importExcel")
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
			ErrorImport<CourseVideo> error = judgmentProblemService.batchAddQuestion(in, file.getOriginalFilename());
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
	@RequestMapping("/jp/getStatus")
	public UploadStatus getStatus(HttpSession session) {

		UploadStatus u = (UploadStatus) session.getAttribute("upload_status");
		// 当进度达到80%
		if (u.getPercent() >= 80) {
			// 设置为80%，只有在全部遍历后才设置为100%
			u.setPercent(80);
			// 判断记录是否遍历完成
			if (judgmentProblemService.getImportStatus() == 1) {
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
	@RequestMapping("/jp/updateQuestion")
	public @ResponseBody String updateQuestion(JudgmentProblem jp) {
		judgmentProblemService.updateeJP(jp);
		return "{\"status\":true}";
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/jp/delQuestion")
	public @ResponseBody String delQuestion(@RequestParam("ids[]") Integer[] ids) {
		//System.out.println(ids);
		judgmentProblemService.delJP(ids);
		return "{\"status\":true}";
	}
}