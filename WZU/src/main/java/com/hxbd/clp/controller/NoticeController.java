package com.hxbd.clp.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.Notice;
import com.hxbd.clp.service.CourseService;
import com.hxbd.clp.service.NoticeService;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.NoticeVO;


/**
 * 课程公告管理控制层
 * 
 * @author 恒信博大
 *
 */
@Controller
public class NoticeController {
	@Resource
	private NoticeService noticeService;
	
	@Resource
	private CourseService courseService;
	
	/**
	 * 保存课程公告
	 * 
	 * @param news
	 * @return
	 */
	@RequestMapping("notice/saveNotice")
	public @ResponseBody String saveNotice(Notice notice) {
		noticeService.addNotice(notice);
		return "{\"status\":true}";
	}
	
	/**
	 * 更新课程公告
	 * 
	 * @param news
	 * @return
	 */
	@RequestMapping("notice/updateNotice")
	public @ResponseBody String updateNotice(Notice notice) {
		System.err.println("notice:" + notice);
		noticeService.modifyNotice(notice);
		return "{\"status\":true}";
	}

	
	/**
	 * 根据ID查询课程公告
	 * @param id
	 * @return
	 */
	@RequestMapping("/findNoticeById")
	public @ResponseBody Notice findNoticeById(@RequestParam("id") Integer id){
		return noticeService.selectById(id);
	}
	
	
	/**
	 * 根据id批量删除课程公告
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/notice/deleteNotice")
	public @ResponseBody String batchDelNotice(@RequestParam("ids[]") Integer[] ids){
		noticeService.batchDelNotice(ids);
		return "{\"status\":true}";
	}
	
	/**
	 * 
	 * @param pageIndex
	 * @param keyword
	 * @param courseId
	 * @return
	 */
	@RequestMapping("/notice/{pageIndex}/getNoticePageModel")
	public @ResponseBody PageModel<NoticeVO> getNoticePageModel(@PathVariable Integer pageIndex,String courseName,String title,String content){
		PageModel<NoticeVO> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		Map<Integer,Course> courseMap = courseService.findAllMap();
		NoticeVO noticeVO = new NoticeVO();
		if(courseName != null && !courseName.equals("")){
			noticeVO.setCourseName(courseName);
		}
		if(title != null && !title.equals("")){
			noticeVO.setTitle(title);
		}
		if(content != null && !content.equals("")){
			noticeVO.setContent(content);
		}
			return noticeService.findNotice(noticeVO, pageModel,courseMap);
	}
	
	
/*	//根据课程ID查询课程公告
	@RequestMapping("/getNoticeByCourseId/{courseId}")
	public @ResponseBody List<Notice> getNoticeByCourseId(@RequestParam("courseId") Integer courseId){
		return noticeService.findNoticeByCourseId(courseId);
	}*/
}
