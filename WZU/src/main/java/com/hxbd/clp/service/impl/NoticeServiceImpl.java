package com.hxbd.clp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.dao.NoticeDao;
import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.CourseAndTeacher;
import com.hxbd.clp.domain.Notice;
import com.hxbd.clp.domain.Teacher;
import com.hxbd.clp.service.NoticeService;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.CourseAndTeacherVO;
import com.hxbd.clp.vo.NoticeVO;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private NoticeDao noticeDao;
	
	@Transactional(readOnly = true)
	@Override
	public Notice selectById(Integer noticeId) {
		return noticeDao.selectById(noticeId);
	}

	@Transactional(readOnly = true)
	@Override
	public PageModel<NoticeVO> findNotice(NoticeVO noticeVO, PageModel<NoticeVO> pageModel,Map<Integer,Course> courseMap) {
		Map<String, Object> params = new HashMap<>();
		params.put("noticeVO", noticeVO);
		Integer recordCount = noticeDao.count(params);
		System.err.println("recordCount:" + recordCount);
		if(recordCount > 0){
			pageModel.setRecordCount(recordCount);
			params.put("pageModel",pageModel);
		}
		
		// 查询分页记录
		List<Notice> listt  = noticeDao.selectByPage(params);
		List<NoticeVO> list = listt.stream().map(m ->getVO(m,courseMap.get(m.getCourseId()))).collect(Collectors.toList());
		pageModel.setList(list);
		return pageModel;
	}
	public NoticeVO getVO(Notice notice,Course course){
		NoticeVO vo = new NoticeVO();
		vo.setId(notice.getId());
		vo.setTitle(notice.getTitle());
		vo.setContent(notice.getContent());
		vo.setReleaseTime(notice.getReleaseTime());
		vo.setCourseName(course.getCourseName());
		vo.setCourseNo(course.getCourseNo());
		return vo;
	}
	
	
	@Override
	public void addNotice(Notice notice) {
		noticeDao.addNotice(notice);
	}
	
	@Override
	public void modifyNotice(Notice notice) {
		noticeDao.updateNotice(notice);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Notice> getNoticeByPage(Map<String, Object> parmas) {
		return noticeDao.selectByPage(parmas);
	}

	@Override
	public void batchDelNotice(Integer[] ids) {
		Map<String, Integer[]> map = new HashMap<>();
		map.put("ids", ids);
		noticeDao.batchDelete(map);
	}

	@Override
	public List<Notice> findNoticeByCourseId(Integer courseId) {
		return noticeDao.findNoticeByCourseId(courseId);
	}

}
