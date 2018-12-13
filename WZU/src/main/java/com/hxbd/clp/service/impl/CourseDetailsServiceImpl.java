package com.hxbd.clp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.dao.CourseDetailsDao;
import com.hxbd.clp.domain.CourseDetails;
import com.hxbd.clp.domain.CourseType;
import com.hxbd.clp.service.CourseDetailsService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * @author hxcl
 *
 * 2018年3月27日下午4:26:31
 *
 */
@Transactional(propagation = Propagation.REQUIRED , isolation = Isolation.DEFAULT)
@Service("courseDetailsService")
public class CourseDetailsServiceImpl implements CourseDetailsService {
	
	@Autowired
	private CourseDetailsDao courseDetailsDao;

	@Override
	public CourseDetails selectById(Integer id) {
		return courseDetailsDao.selectCourseDetailsById(id);
	}

	@Override
	public PageModel<CourseDetails> findCourseDetails(CourseDetails courseDetails, PageModel<CourseDetails> pageModel) {
		Map<String,Object> params = new HashMap<>();
		params.put("courseDetails", courseDetails);
		Integer recordCount = courseDetailsDao.count(params);
		if(recordCount > 0){
			pageModel.setRecordCount(recordCount);
			params.put("pageModel",pageModel);
		}
		//查询分页记录
		List<CourseDetails> list = courseDetailsDao.selectCourseDetailsList(params);
		pageModel.setList(list);
		return pageModel;
	}

	@Override
	public List<CourseDetails> getCourseDetailsList() {
		return courseDetailsDao.selectCourseDetailsList(new HashMap<String,Object>());
	}

	@Transactional(readOnly = true)
	@Override
	public void saveCourseDetails(CourseDetails courseDetails) {
		courseDetailsDao.saveCourseDetails(courseDetails);

	}

	@Transactional(readOnly = true)
	@Override
	public void updateCourseDetails(CourseDetails courseDetails) {
		courseDetailsDao.updateCourseDetails(courseDetails);
	}

	@Override
	public void batchDelCourseDetails(Integer[] ids) {
		Map<String, Integer[]> map = new HashMap<>();
		map.put("ids", ids);
		courseDetailsDao.batchDelCourseDetails(map);

	}

}
