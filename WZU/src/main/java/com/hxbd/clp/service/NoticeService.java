package com.hxbd.clp.service;

import java.util.List;
import java.util.Map;

import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.Notice;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.NoticeVO;


public interface NoticeService {
	
	// 根据课程公告id查询课程公告
	Notice selectById(Integer noticeId);
	// 动态查询课程公告
	PageModel<NoticeVO> findNotice(NoticeVO noticeVO, PageModel<NoticeVO> pageModel,Map<Integer, Course> courseMap);
	// 分页查询获取课程公告
	List<Notice> getNoticeByPage(Map<String, Object> parmas);
	// 新增课程公告
	void addNotice(Notice notice);
	// 更新课程公告
	void modifyNotice(Notice notice);
	// 根据课程公告id删除课程公告
	void batchDelNotice(Integer[] ids);
	// 根据课程ID查询课程公告
	List<Notice> findNoticeByCourseId(Integer courseId);
}
