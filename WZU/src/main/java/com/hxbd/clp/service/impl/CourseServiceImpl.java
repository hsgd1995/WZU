package com.hxbd.clp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.Form.CourseForm;
import com.hxbd.clp.dao.CourseAndUserDao;
import com.hxbd.clp.dao.CourseDao;
import com.hxbd.clp.dao.SystemMessageDao;
import com.hxbd.clp.dao.UserDao;
import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.CourseAndUser;
import com.hxbd.clp.domain.SystemMessage;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.service.CourseService;
import com.hxbd.clp.utils.tag.PageModel;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service("courseService")
public class CourseServiceImpl implements CourseService{

	@Autowired
	private CourseDao courseDao;
	
	
	@Override
	public Course selectById(Integer id) {
		return courseDao.selectById(id);
	}

	@Override
	public Course selectByCourseId(String courseId) {
		return courseDao.selectByCourseId(courseId);
	}

	@Override
	public Integer findCourseSort(Course course) {
		// 当前需要查询的课程总数
		Map<String, Object> params = new HashMap<>();
		params.put("course", course);
		return courseDao.count(params);
		
	}
	
	@Override
	public PageModel<Course> findCourse(Course course, PageModel<Course> pageModel) {
		// 当前需要查询的课程总数
		Map<String, Object> params = new HashMap<>();
		params.put("course", course);
		Integer recordCount = courseDao.count(params);
		if(recordCount > 0){
			pageModel.setRecordCount(recordCount);
			params.put("pageModel",pageModel);
		}
		
		// 查询分页记录
		List<Course> list  = courseDao.selectByPage(params);
		pageModel.setList(list);
		return pageModel;
	}
	
	@Transactional(readOnly = true)
	@Override
	public void addCourse(CourseForm courseForm) {
		courseDao.save(courseForm);
	}

	@Transactional(readOnly = true)
	@Override
	public void updateCourse(CourseForm courseForm) {
		courseDao.update(courseForm);
	}

	@Override
	public List<Course> getCourseList() {
		return courseDao.selectByPage(new HashMap<String , Object>());
	}

	@Override
	public void batchDelCourse(Integer[] ids) {
		Map<String, Integer[]> map = new HashMap<>();
		map.put("ids", ids);
		courseDao.batchDelete(map);
	}

	@Override
	public List<Course> findLimitEight() {
		return courseDao.findLimitEight();
	}

	
	//查询map  ------用来映射关联ID查询
	@Override
	public Map<Integer,Course> findAllMap(){
		List<Course> list = courseDao.findAllList();
		Map<Integer,Course> map = new HashMap<>();
		for(int i = 0; i<list.size();i++){
			map.put(list.get(i).getId(), list.get(i));
		}
		return map;
	}

	@Override
	public Course selectByTeacherCourseNo(String courseNo) {
		return courseDao.selectByCourseNo(courseNo);
	}
	
}
