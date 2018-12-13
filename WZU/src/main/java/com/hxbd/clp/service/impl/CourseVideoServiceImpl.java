package com.hxbd.clp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.dao.CourseVideoDao;
import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.domain.Process;
import com.hxbd.clp.service.CourseVideoService;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.CourseVideoVO;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service("courseVideoService")
public class CourseVideoServiceImpl implements CourseVideoService{

	@Autowired
	private CourseVideoDao courseVideoDao;
	
	@Transactional(readOnly = true)
	@Override
	public CourseVideo selectById(Integer courseVideoId) {
		return courseVideoDao.selectById(courseVideoId);
	}

	@Transactional(readOnly = true)
	@Override
	public PageModel<CourseVideo> findCourseVideo(CourseVideo courseVideo, PageModel<CourseVideo> pageModel) {
		Map<String, Object> params = new HashMap<>();
		params.put("courseVideo", courseVideo);
		Integer recordCount = courseVideoDao.count(params);
		if(recordCount > 0){
			pageModel.setRecordCount(recordCount);
			params.put("pageModel",pageModel);
		}
		
		// 查询分页记录
		List<CourseVideo> list  = courseVideoDao.selectByPage(params);
		pageModel.setList(list);
		return pageModel;
	}
	
	@Override
	public Integer addCourseVideo(CourseVideo courseVideo) {
		courseVideoDao.addCourseVideo(courseVideo);
		return courseVideo.getId();
	}
	
	@Override
	public void modifyCourseVideo(CourseVideo courseVideo) {
		courseVideoDao.updateCourseVideo(courseVideo);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<CourseVideo> getCourseVideoByPage(Map<String, Object> parmas) {
		return courseVideoDao.selectByPage(parmas);
	}

	@Override
	public void batchDelCourseVideo(Integer[] ids) {
		Map<String, Integer[]> map = new HashMap<>();
		map.put("ids", ids);
		courseVideoDao.batchDelete(map);
	}

	@Override
	public List<CourseVideo> findCourseVideoByCourseId(Integer courseId) {
		return courseVideoDao.findCourseVideoByCourseId(courseId);
	}
	

	public List<CourseVideo> findChildrenList(Integer parentId,List<CourseVideo> list){
		List<CourseVideo> courseVideoList = list.stream().filter(d->d.getParent()==parentId).collect(Collectors.toList());
		List<CourseVideo> volist = new ArrayList<CourseVideo>();
		for(int i=0;i<courseVideoList.size();i++){
			CourseVideo courseVideo = courseVideoList.get(i);
			CourseVideo vo = new CourseVideo();
			vo.setId(courseVideo.getId());
			vo.setName(courseVideo.getName());
			vo.setParent(courseVideo.getParent());
			vo.setUrl(courseVideo.getUrl());
			vo.setCourseId(courseVideo.getCourseId());
			vo.setRemark(courseVideo.getRemark());
			vo.setChildrenList(findChildrenList(vo.getId(), list));
			volist.add(vo);
		}
		return volist;
	}

	
	@Override
	public List<CourseVideo> findCourseVideoTree(Integer courseId) {
		List<CourseVideo> list = courseVideoDao.findCourseVideoByCourseId(courseId);
		return findChildrenList(0, list); 
	}
	
	@Override
	public List<CourseVideo> findCourseVideoTree(Integer courseId, String keyword) {
		List<CourseVideo> list = courseVideoDao.findCourseVideoByCourseIdAndName(courseId,keyword);
		return findChildrenList(0, list); 
	}

	@Transactional(readOnly = true)
	@Override
	public PageModel<CourseVideoVO> findCourseVideo(CourseVideo courseVideo,PageModel<CourseVideoVO> pageModel,Map<Integer, Course> courseMap) {
		Map<String, Object> params = new HashMap<>();
		params.put("courseVideo", courseVideo);
		Integer recordCount = courseVideoDao.count(params);
		if(recordCount > 0){
			pageModel.setRecordCount(recordCount);
			params.put("pageModel",pageModel);
		}
		
		// 查询分页记录
		List<CourseVideo> listt  = courseVideoDao.selectByParent(params);
		List<CourseVideoVO> list = listt.stream().map(m ->getVO(m,courseMap.get(m.getCourseId()))).collect(Collectors.toList());
		pageModel.setList(list);
		return pageModel;
	}
	
	//映射
	public CourseVideoVO getVO(CourseVideo courseVideo,Course course){
		CourseVideoVO vo = new CourseVideoVO();
		vo.setId(courseVideo.getId());
		if(courseVideo.getParent() != 0){
			vo.setParentName(courseVideoDao.selectById(courseVideo.getParent()).getName());
		}else{
			vo.setParentName(courseVideo.getName());
		}
		vo.setCourseName(course.getCourseName());
		if(courseVideo.getParent() != 0){			
			vo.setName(courseVideo.getName());
		}else{
			vo.setName("-");
		}
		if(courseVideo.getUrl() != null){
			vo.setUrl(courseVideo.getUrl());
		}else{
			vo.setUrl("-");
		}
		return vo;
	}

	@Override
	public CourseVideo findCourseVideoByCourseIdAndParent(Integer courseId, String name,int i) {
		return courseVideoDao.findCourseVideoByCourseIdAndParent(courseId,name,i);
	}

	@Override
	public List<CourseVideo> findCourseVideoByParent(int i) {
		return courseVideoDao.findCourseVideoListByParent(i);
	}

	@Override
	public CourseVideo findByCourseIdAndParentAndName(Integer courseId, Integer parent, String name) {
		return courseVideoDao.findByCourseIdAndParentAndName(courseId,parent,name);
	}

	@Override
	public List<CourseVideo> findCourseVideoByCourseIdAndParent(Integer courseId, int i) {
		return courseVideoDao.findCourseVideoListByCourseIdAndParent(courseId, i);
	}

	//查询map  ------用来映射关联ID查询
	@Override
	public Map<Integer, CourseVideo> findAllMap() {
		List<CourseVideo> list = courseVideoDao.findAllList();
		Map<Integer,CourseVideo> map = new HashMap<>();
		for(int i = 0;i<list.size();i++){
			map.put(list.get(i).getId(), list.get(i));
		}
		return map;
	}

	@Override
	public void saveVideoProcess(Integer userId, Integer courseVideoId, Integer courseId, Double currentTime) {
		Process p =courseVideoDao.findByUserIdAndCourseVideoId(userId,courseVideoId,courseId);
		if(p==null){
			courseVideoDao.insertProcess(userId,courseVideoId,courseId,currentTime);
		}else{
			courseVideoDao.updateProcess(userId,courseVideoId,courseId,currentTime);
		}
	}

	@Override
	public Process getVideoProcess(Integer userId, Integer courseVideoId, Integer courseId) {
		return courseVideoDao.findByUserIdAndCourseVideoId(userId,courseVideoId,courseId);
	}

	@Override
	public void finishStudyVideo(Integer userId, Integer courseVideoId, Integer courseId) {
		courseVideoDao.updateIsFinish( userId,  courseVideoId,  courseId);
	}

	@Override
	public CourseVideo getLastStudyVideo(Integer userId, Integer courseId) {
		//查询用户对于某课程最近一次观看的视频进度
		Process process = courseVideoDao.selectLastCourseVideoId(userId,courseId);
		if(process!=null){
			if(process.getCourseVideoId()!=null){
				//找出该视频的信息
				return courseVideoDao.selectById(process.getCourseVideoId());
			}
		}
		return null;
	}

	@Override
	public PageModel<CourseVideoVO> findCourseVideoVO(CourseVideoVO courseVideoVO, PageModel<CourseVideoVO> pageModel,
			Map<Integer, Course> courseMap) {
		Map<String, Object> params = new HashMap<>();
		params.put("courseVideoVO", courseVideoVO);
		Integer recordCount = courseVideoDao.countCourseVideoVO(params);
		if(recordCount > 0){
			pageModel.setRecordCount(recordCount);
			params.put("pageModel",pageModel);
		}
		
		// 查询分页记录
		List<CourseVideo> listt  = courseVideoDao.selectByParentVO(params);
		List<CourseVideoVO> list = listt.stream().map(m ->getVO(m,courseMap.get(m.getCourseId()))).collect(Collectors.toList());
		pageModel.setList(list);
		return pageModel;
	}

	@Override
	public List<CourseVideo> findCourseVideoByCourseNo(String courseNo) {

		return courseVideoDao.slectByCourseNo(courseNo);
	}

	
}
