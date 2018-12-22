package com.hxbd.clp.service;

import java.util.List;
import java.util.Map;

import com.hxbd.clp.domain.Notice;


public interface NoticeService {
	
	// 根据课程公告id查询课程公告
	Notice selectById(Integer noticeId);
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
