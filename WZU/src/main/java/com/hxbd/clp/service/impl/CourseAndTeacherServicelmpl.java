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

import com.hxbd.clp.dao.CourseAndTeacherDao;
import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.CourseAndTeacher;
import com.hxbd.clp.domain.CourseAndUser;
import com.hxbd.clp.domain.Teacher;
import com.hxbd.clp.service.CourseAndTeacherService;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.CourseAndTeacherVO;

/**
 * @author hxcl
 *
 * 2018年3月23日上午10:53:07
 *
 */

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service("courseAndTeacherService")
public class CourseAndTeacherServicelmpl implements CourseAndTeacherService {
	
	@Autowired
	private CourseAndTeacherDao courseAndTeacherDao;

	@Override
	public void saveCourseAndTeacher(CourseAndTeacher courseAndTeacher) {
		courseAndTeacherDao.save(courseAndTeacher.getTeacher().getId(),courseAndTeacher.getCourse().getId());

	}

	@Override
	public void editCourseAndTeacher(CourseAndTeacher courseAndTeacher) {
		// TODO Auto-generated method stub

	}

	@Override
	public CourseAndUser findByteacherIdAndCourseId(Integer teacherId, Integer courseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CourseAndTeacher findCourseAndTeacher(Integer teacherId, Integer courseId) {
		return courseAndTeacherDao.findByTeacherIdAndCourseId(teacherId, courseId);
	}
	
	@Transactional(readOnly = true)
	@Override
	public PageModel<CourseAndTeacher> findCourseAndTeacher(CourseAndTeacher courseAndTeacher ,PageModel<CourseAndTeacher> pageModel) {
		Map<String, Object> params  = new HashMap<>();
		params.put("courseAndTeacher", courseAndTeacher);
		Integer recordCount = courseAndTeacherDao.count(params);
		if(recordCount > 0){
			pageModel.setRecordCount(recordCount);
			params.put("pageModel", pageModel);
			pageModel.setList(courseAndTeacherDao.selectCourseAndTeacherList(params));
		}
		return pageModel;
	}
	
//	public CourseAndTeacherVO getVO(CourseAndTeacher courseAndTeacher,Teacher teacher,Course course){
//		CourseAndTeacherVO vo = new CourseAndTeacherVO();
//		vo.setId(courseAndTeacher.getId());
//		vo.setTeacher(teacher.getTeacherName());
//		vo.setTeacherNo(teacher.getTeacherNo());
//		vo.setCourse(course.getCourseName());
//		return vo;
//	}

	@Override
	public void batchCourseAndTeacher(Integer[] ids) {
		Map<String, Integer[]> map = new HashMap<>();
		map.put("ids", ids);
		courseAndTeacherDao.batchDelCourseAndTeacher(map);
		
	}
	
}
