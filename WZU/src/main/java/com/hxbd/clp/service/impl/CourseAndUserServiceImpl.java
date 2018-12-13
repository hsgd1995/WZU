package com.hxbd.clp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.dao.CourseAndUserDao;
import com.hxbd.clp.dao.CourseVideoDao;
import com.hxbd.clp.dao.UserDao;
import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.CourseAndUser;
import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.domain.Process;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.service.CourseAndUserService;
import com.hxbd.clp.service.UserService;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.CourseAndTeacherVO;
import com.hxbd.clp.vo.CourseAndUserVO;
import com.hxbd.clp.vo.CourseVideoVO;

/**
 * @author hxcl
 *
 *         2018年3月16日下午3:21:24
 *
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service("courseAndUserService")
public class CourseAndUserServiceImpl implements CourseAndUserService {

	@Autowired
	private CourseAndUserDao courseAndUserDao;
	@Autowired
	private CourseVideoDao courseVideoDao;
	@Autowired
	private UserDao userDao;

	@Override
	public void saveCourseAndUser(CourseAndUser courseAndUser) {
		courseAndUserDao.save(courseAndUser);

	}

	@Override
	public void editCourseAndUser(CourseAndUser courseAndUser) {
		courseAndUserDao.editJion(courseAndUser);
	}

	@Override
	public CourseAndUser findByUserIdAndCourseId(Integer userId, Integer courseId) {
		return courseAndUserDao.findByUserIdAndCourseId(userId, courseId);
	}

	@Override
	public Integer findStudyPeopleCountByCourseId(Integer courseId) {
		return courseAndUserDao.findStudyPeopleCountByCourseId(courseId);
	}

	@Override
	public PageModel<Course> selectStudyCourseByPage(Integer userId, Course course, PageModel<Course> pageModel,Integer start) {
		// 用户学习的课程总数
		Map<String, Object> params = new HashMap<>();
		params.put("course", course);
		params.put("userId", userId);
		params.put("start", start);
		Integer recordCount = courseAndUserDao.countStudyCourse(params);
		if (recordCount > 0) {
			pageModel.setRecordCount(recordCount);
			params.put("pageModel", pageModel);
		}

		// 查询分页记录
		List<Course> list = courseAndUserDao.selectStudyCourseByPage(params);
		pageModel.setList(list);
		return pageModel;
	}
	
	@Override
	public double getStudyProcess(Integer userId, Integer courseId) {
		//获取该课程的视频总数
		double count = 0;
		//已完成学习的视频数
		double finishCount = 0;
		//学习进度
		double process = 0;
		List<CourseVideo> cvList = courseVideoDao.selectAllVideoByCourseId(courseId);
		if(cvList.size()>0){
			count = cvList.size();
			//查找哪个视频是用户已经学习过的
			for (CourseVideo courseVideo : cvList) {
				Process pro = courseVideoDao.findByUserIdAndCourseVideoId(userId,courseVideo.getId(),courseId);
				//如果表中存在该用户对该课程的视频的进度并且已完成学习
				if(pro!=null&&pro.getIsFinish()==1){
					finishCount++;
				}
			}
		}
		//计算学习进度
		if(count!=0&&finishCount!=0){
			process = finishCount/count;
		}
		return process;
	}

	@Override
	public PageModel<Course> selectCollectCourseByPage(Integer userId, Course course, PageModel<Course> pageModel) {
		// 用户收藏的课程总数
		Map<String, Object> params = new HashMap<>();
		params.put("course", course);
		params.put("userId", userId);
		Integer recordCount = courseAndUserDao.countCollectCourse(params);
		if (recordCount > 0) {
			pageModel.setRecordCount(recordCount);
			params.put("pageModel", pageModel);
		}

		// 查询分页记录
		List<Course> list = courseAndUserDao.selectCollectCourseByPage(params);
		pageModel.setList(list);
		return pageModel;
	}

	@Override
	public PageModel<CourseAndUserVO> findCourseAndUser(CourseAndUser courseAndUser,
			PageModel<CourseAndUserVO> pageModel, Map<Integer, Course> courseMap) {
		Map<String, Object> params = new HashMap<>();
		params.put("courseAndUser", courseAndUser);
		Integer recordCount = courseAndUserDao.count(params);
		if (recordCount > 0) {
			pageModel.setRecordCount(recordCount);
			params.put("pageModel", pageModel);
		}
		// 查询分页记录
		List<CourseAndUser> listt = courseAndUserDao.selectByParent(params);
		List<CourseAndUserVO> list = listt.stream().map(m ->getVO(m,courseMap.get(m.getCourseId()))).collect(Collectors.toList());
		pageModel.setList(list);
		return pageModel;
	}

	// 映射
	public CourseAndUserVO getVO(CourseAndUser courseAndUser,Course course) {
		CourseAndUserVO vo = new CourseAndUserVO();
		vo.setId(courseAndUser.getId());
		vo.setUserId(courseAndUser.getUserId());
		vo.setUser(userDao.selectById(courseAndUser.getUserId()).getUsername());
		vo.setCourse(course.getCourseName());
		return vo;
	}

	@Override
	public void batchDelCourseAndUser(Integer[] ids) {
		Map<String, Integer[]> map = new HashMap<>();
		map.put("ids", ids);
		courseAndUserDao.batchDelete(map);
	}
}
