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

import com.hxbd.clp.domain.Completion;
import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.service.CompletionService;
import com.hxbd.clp.service.CourseVideoService;
import com.hxbd.clp.to.UploadStatus;
import com.hxbd.clp.utils.common.ErrorImport;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.CompletionVO;

@Controller
public class CompletionController {

	@Autowired
	private CompletionService completionService;
	
	@Autowired
	private CourseVideoService courseVideoService;
	
	
	
	@RequestMapping("/completion/getQuestion/{id}")
	public String getQuestionById(@PathVariable("id") Integer id,HttpSession httpSession) {
		httpSession.setAttribute("courseVideoId", id);
		return "completionTest";
	}
	
	/**
	 * 修改选择题：根据id获取选择题信息
	 * @param id
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/completion/{id}/getQuestion")
	public @ResponseBody Completion getQuestionById(@PathVariable Integer id) {
		return completionService.selectById(id);
	}
	
	/**
	 * 获取列表
	 * @param pageIndex
	 * @param keyword
	 * @param courseVideoId
	 * @return
	 */
	@RequestMapping("/completion/{pageIndex}/getQuestionPageModel")
	public @ResponseBody PageModel<CompletionVO> getQuestionPageModel(@PathVariable Integer pageIndex,String subject,String title) {
		PageModel<CompletionVO> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		Map<Integer,CourseVideo> courseVideoMap = courseVideoService.findAllMap();
		CompletionVO completionVO = new CompletionVO();
		if (subject != null && subject != "") {
			completionVO.setTitle(subject);
		}
		if (title != null && title != "") {
			completionVO.setCourseName(title);
		}
		return completionService.selectByPageModel(completionVO, pageModel,courseVideoMap);
	}
	
	/**
	 * 新增
	 * @param cq
	 * @return
	 */
	@RequestMapping("/completion/saveQuestion")
	public @ResponseBody String saveQuestion(Completion completion) {
		completionService.saveCompletion(completion);
		return "{\"status\":true}";
	}
	
	/**
	 * 导入Excel
	 * @param request
	 * @return
	 */
	@RequestMapping("/completion/importExcel")
	public @ResponseBody Object importQuestion(MultipartFile file){
		try {
         //获取上传的文件
         /*MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
         MultipartFile file = multipart.getFile("file");*/
		 InputStream in = file.getInputStream();
		 //数据导入
		 ErrorImport<CourseVideo> error = completionService.batchAddQuestion(in, file.getOriginalFilename());
		 in.close();
		 //不存在错误记录和不合格记录
		 if(error.getErrorList().size()==0&&error.getFailIndex()==0){
			 return "{\"status\":true}";
		 }else{
			 return error;
		 }
		} catch (IOException e) {
			e.printStackTrace();
			//失败处理
			return "{\"status\":false}";
		}
	}

	/**
     * 这里是获取上传文件状态信息的访问接口
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/completion/getStatus")
    public UploadStatus getStatus(HttpSession session){
    	
    	UploadStatus u = (UploadStatus)session.getAttribute("upload_status");
    	//当进度达到80%
    	if(u.getPercent()>=80){
    		//设置为80%，只有在全部遍历后才设置为100%
    		u.setPercent(80);
    		//判断记录是否遍历完成
    		if(completionService.getImportStatus()==1){
    			//是，返回100%
    			u.setPercent(100);
    		}
    	}
        return u;
    }
	
	/**
	 * 更新
	 * @param cq
	 * @return
	 */
	@RequestMapping("/completion/updateQuestion")
	public @ResponseBody String updateQuestion(Completion completion) {
		completionService.updateCompletion(completion);
		return "{\"status\":true}";
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/completion/delQuestion")
	public @ResponseBody String delQuestion(@RequestParam("ids[]") Integer[] ids) {
		completionService.delCompletion(ids);
		return "{\"status\":true}";
	}
}
