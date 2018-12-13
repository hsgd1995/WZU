package com.hxbd.clp.service;

import java.util.Map;

import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.CourseAndTeacher;
import com.hxbd.clp.domain.CourseAndUser;
import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.CourseAndTeacherVO;
import com.hxbd.clp.vo.CourseAndUserVO;

/**
 * @author hxcl
 *
 * 2018年3月16日下午3:18:23
 *
 */
public interface CourseAndUserService {
	
	// 保存
	void saveCourseAndUser(CourseAndUser courseAndUser);
	// 修改
	void editCourseAndUser(CourseAndUser courseAndUser); 
	// 根据两个参数去查询---userId、courseId
	CourseAndUser findByUserIdAndCourseId(Integer userId, Integer courseId);
	// 查询学习人数总数
	Integer findStudyPeopleCountByCourseId(Integer courseId);
	// 分页查询用户学习的课程
	PageModel<Course> selectStudyCourseByPage(Integer userId,Course course , PageModel<Course> pageModel,Integer start);
	// 分页查询用户收藏的课程
	PageModel<Course> selectCollectCourseByPage(Integer userId,Course course , PageModel<Course> pageModel);
	//分页查询指定课程的绑定信息
	PageModel<CourseAndUserVO> findCourseAndUser(CourseAndUser courseAndUser, PageModel<CourseAndUserVO> pageModel,
			Map<Integer, Course> courseMap);
	//接触绑定关系
	void batchDelCourseAndUser(Integer[] ids);
	
	/**
	 * 获取用户的学习进度，该功能可以获取某个用户的某一门课的学习进度，如需获取某个用户的所有课程的学习进度，需要不断地在页面请求这个方法。<br>
	 * 实现：获取某个用户的某一门课的学习进度，先获取该用户已经看过的该课程的视频的已看数量，再获取该门课程的所有视频总数，最后用已看数量除以视频总数得到学习进度。
	 * @param userId 用户id
	 * @param courseId 课程id
	 */
	double getStudyProcess(Integer userId, Integer courseId);
	
}
