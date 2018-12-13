package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.dao.provider.CourseAndUserDynaSqlProvider;
import com.hxbd.clp.dao.provider.CourseDynaSqlProvider;
import com.hxbd.clp.dao.provider.CourseVideoDynaSqlProvider;
import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.CourseAndUser;
import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * @author hxcl
 *
 * 2018年3月16日下午2:58:05
 *
 */
public interface CourseAndUserDao {
	// 根据用户id查询学习课程
	@SelectProvider(type = CourseDynaSqlProvider.class,method = "selectStudyCourseWithParam")
	@Results(value = {@Result(column = "id", property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "course_no" ,property = "courseNo" , javaType = java.lang.String.class),
			@Result(column = "course_name" , property = "courseName" , javaType = java.lang.String.class),
			@Result(column = "teaching_material" , property = "teachingMateril" , javaType = java.lang.String.class),
			
			@Result(column = "study_start_time" , property = "studyStartTime" , javaType = java.util.Date.class),
			@Result(column = "study_end_time" , property = "studyEndTime" , javaType = java.util.Date.class),
			@Result(column = "exam_start_time" , property = "examStartTime" , javaType = java.util.Date.class),
			@Result(column = "exam_end_time" , property = "examEndTime" , javaType = java.util.Date.class),
			
			@Result(column = "course_status" , property = "courseStatus" , javaType = java.lang.Integer.class),
			@Result(column = "course_period" , property = "coursePeriod" , javaType = java.lang.String.class),
			@Result(column = "expend_time" , property = "expendTime" , javaType = java.lang.String.class),
			@Result(column = "course_language" , property = "courseLanguage" , javaType = java.lang.String.class),
			@Result(column = "subtitle_type" , property = "subtitleType" , javaType = java.lang.String.class),
			@Result(column = "course_pic" , property = "coursePic" , javaType = java.lang.String.class),
			
			@Result(column = "course_type_id" ,property = "courseType" ,javaType = com.hxbd.clp.domain.CourseType.class,
			one = @One(select = "com.hxbd.clp.dao.CourseTypeDao.selectCourseTypeById")),
			@Result(column = "course_details_id" , property = "courseDetails" ,javaType = com.hxbd.clp.domain.CourseDetails.class,
			one = @One(select = "com.hxbd.clp.dao.CourseDetailsDao.selectCourseDetailsById")),
			@Result(column = "id" ,property="teacher",javaType = List.class,
			many = @Many(select ="com.hxbd.clp.dao.TeacherDao.selectTeacher"))

	})
	List<Course> selectStudyCourseByPage(Map<String, Object> params);
	
	// 根据用户id查询收藏课程
	@SelectProvider(type = CourseDynaSqlProvider.class,method = "selectCollectCourseWithParam")
	@Results(value = {@Result(column = "id", property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "course_no" ,property = "courseNo" , javaType = java.lang.String.class),
			@Result(column = "course_name" , property = "courseName" , javaType = java.lang.String.class),
			@Result(column = "teaching_material" , property = "teachingMateril" , javaType = java.lang.String.class),
			
			@Result(column = "study_start_time" , property = "studyStartTime" , javaType = java.util.Date.class),
			@Result(column = "study_end_time" , property = "studyEndTime" , javaType = java.util.Date.class),
			@Result(column = "exam_start_time" , property = "examStartTime" , javaType = java.util.Date.class),
			@Result(column = "exam_end_time" , property = "examEndTime" , javaType = java.util.Date.class),
			
			@Result(column = "course_status" , property = "courseStatus" , javaType = java.lang.Integer.class),
			@Result(column = "course_period" , property = "coursePeriod" , javaType = java.lang.String.class),
			@Result(column = "expend_time" , property = "expendTime" , javaType = java.lang.String.class),
			@Result(column = "course_language" , property = "courseLanguage" , javaType = java.lang.String.class),
			@Result(column = "subtitle_type" , property = "subtitleType" , javaType = java.lang.String.class),
			@Result(column = "course_pic" , property = "coursePic" , javaType = java.lang.String.class),
			
			@Result(column = "course_type_id" ,property = "courseType" ,javaType = com.hxbd.clp.domain.CourseType.class,
			one = @One(select = "com.hxbd.clp.dao.CourseTypeDao.selectCourseTypeById")),
			@Result(column = "course_details_id" , property = "courseDetails" ,javaType = com.hxbd.clp.domain.CourseDetails.class,
			one = @One(select = "com.hxbd.clp.dao.CourseDetailsDao.selectCourseDetailsById")),
			@Result(column = "id" ,property="teacher",javaType = List.class,
			many = @Many(select ="com.hxbd.clp.dao.TeacherDao.selectTeacher"))

	})
	List<Course> selectCollectCourseByPage(Map<String, Object> params);
	
	// 查询用户id对应的学习课程总数
	@SelectProvider(type = CourseDynaSqlProvider.class, method = "countStudyCourse")
	Integer countStudyCourse(Map<String, Object> parmas);
	
	// 查询用户id对应的收藏课程总数
	@SelectProvider(type = CourseDynaSqlProvider.class, method = "countCollectCourse")
	Integer countCollectCourse(Map<String, Object> parmas);
	
	// 新增
	@InsertProvider(type = CourseAndUserDynaSqlProvider.class ,method="saveCourseAndUser")
	void save(CourseAndUser courseAndUser);
	
	// 更新
	@UpdateProvider(type = CourseAndUserDynaSqlProvider.class, method = "updateCourseAndUser")
	void editJion(CourseAndUser courseAndUser);

	// 根据两个参数去查询 userId 、 courseId
	@Select("select * from " + RasConstants.COURSEANDUSERTABLE + " where user_id = #{userId} and course_id = #{courseId}")
	@Results(value = {@Result(column = "id" , property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "course_id" , property = "courseId" , javaType = java.lang.Integer.class),
			@Result(column = "user_id" , property = "userId", javaType = java.lang.Integer.class)})
	CourseAndUser findByUserIdAndCourseId(@Param("userId") Integer userId,@Param("courseId")Integer courseId);

	
	// 查询这门课程已学习人数
	@Select("select count(user_id) from " +RasConstants.COURSEANDUSERTABLE + " where course_id = #{courseId} and joincourse = 1")
	Integer findStudyPeopleCountByCourseId(Integer courseId);

	//查询学习指定课程的用户总数
	@SelectProvider(type = CourseAndUserDynaSqlProvider.class, method = "countCourseAndUser")
	Integer count(Map<String, Object> params);

	// 分页查询课程
	@SelectProvider(type = CourseAndUserDynaSqlProvider.class, method = "selectByParent")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "course_id",property = "courseId" ,javaType = java.lang.Integer.class),
			@Result(column = "user_id" , property = "userId", javaType = java.lang.Integer.class)})
	List<CourseAndUser> selectByParent(Map<String, Object> params);

	//批量删除
	@DeleteProvider(type = CourseAndUserDynaSqlProvider.class, method = "batchDelCourseAndUser")
	void batchDelete(Map<String, Integer[]> map);

	
	/**
	 * 根据课程id和已经加入该课程查询记录
	 * @param courseId
	 * @return
	 */
	@Select("select * from "+RasConstants.COURSEANDUSERTABLE+" where course_id= #{courseId} and joincourse = 1")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "course_id",property = "courseId" ,javaType = java.lang.Integer.class),
			@Result(column = "user_id" , property = "userId", javaType = java.lang.Integer.class)})
	List<CourseAndUser> selectByCourseIdAndJoincourse(@Param("courseId") Integer courseId);

}

