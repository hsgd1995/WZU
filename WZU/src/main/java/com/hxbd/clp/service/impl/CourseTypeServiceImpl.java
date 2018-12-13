package com.hxbd.clp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.dao.CourseTypeDao;
import com.hxbd.clp.domain.CourseType;
import com.hxbd.clp.service.CourseTypeService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * @author hxcl
 *
 * 2018年3月27日上午11:06:05
 *
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service("courseTypeService")
public class CourseTypeServiceImpl implements CourseTypeService {
	
	@Autowired
	private CourseTypeDao courseTypeDao;

	@Override
	public CourseType selectById(Integer id) {
		return courseTypeDao.selectCourseTypeById(id);
	}

	@Override
	public CourseType selectByTypeName(String typeName) {
		return courseTypeDao.selectCourseTypeByName(typeName);
	}

	@Override
	public PageModel<CourseType> findCourseType(CourseType courseType, PageModel<CourseType> pageModel) {
		Map<String,Object> params = new HashMap<>();
		params.put("courseType", courseType);
		Integer recordCount = courseTypeDao.count(params);
		if(recordCount > 0){
			pageModel.setRecordCount(recordCount);
			params.put("pageModel",pageModel);
		}
		//查询分页记录
		List<CourseType> list = courseTypeDao.selectByPage(params);
		pageModel.setList(list);
		return pageModel;
	}

	@Override
	public List<CourseType> getCourseTypeList() {
		return courseTypeDao.selectByPage(new HashMap<String,Object>());
	}

	@Transactional(readOnly = true)
	@Override
	public void saveCourseType(CourseType courseType) {
		courseTypeDao.saveCourseType(courseType);
	}

	@Transactional(readOnly = true)
	@Override
	public void updateCourseType(CourseType courseType) {
		courseTypeDao.updateCourseType(courseType);
	}

	@Override
	public void batchDelCoursetype(Integer[] ids) {
		Map<String, Integer[]> map = new HashMap<>();
		map.put("ids", ids);
		courseTypeDao.batchDelCourseType(map);
		
	}

	@Override
	public List<CourseType> findList() {
		return courseTypeDao.findList();
	}

}
