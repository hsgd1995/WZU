package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.Form.CourseForm;
import com.hxbd.clp.dao.provider.CourseDynaSqlProvider;
import com.hxbd.clp.domain.Course;
import com.hxbd.clp.utils.common.RasConstants;

public interface CourseDao {
	
	// 根据id查询课程
	@Select("select * from " + RasConstants.COURSETABLE + " where id = #{id}")
	@Results(value = {@Result(column = "id", property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "course_period" ,property = "coursePeriod" , javaType = java.lang.String.class),
			@Result(column = "expend_time" ,property = "expendTime" , javaType = java.lang.String.class),
			@Result(column = "course_language" ,property = "courseLanguage" , javaType = java.lang.String.class),
			@Result(column = "subtitle_type" ,property = "subtitleType" , javaType = java.lang.String.class),
			@Result(column = "course_pic" ,property = "coursePic" , javaType = java.lang.String.class),
			@Result(column = "course_no" ,property = "courseNo" , javaType = java.lang.String.class),
			@Result(column = "course_name" , property = "courseName" , javaType = java.lang.String.class),
			@Result(column = "teaching_material" , property = "teachingMateril" , javaType = java.lang.String.class),
			@Result(column = "study_start_time" , property = "studyStartTime" , javaType = java.util.Date.class),
			@Result(column = "study_end_time" , property = "studyEndTime" , javaType = java.util.Date.class),
			@Result(column = "exam_start_time" , property = "examStartTime" , javaType = java.util.Date.class),
			@Result(column = "exam_end_time" , property = "examEndTime" , javaType = java.util.Date.class),
			@Result(column = "course_status" , property = "courseStatus" , javaType = java.lang.Integer.class),
			@Result(column = "orfree" , property = "orfree" , javaType = java.lang.Integer.class),
			@Result(column = "course_type_id" ,property = "courseType" ,javaType = com.hxbd.clp.domain.CourseType.class,
			one = @One(select = "com.hxbd.clp.dao.CourseTypeDao.selectCourseTypeById")),
			@Result(column = "course_details_id" , property = "courseDetails" ,javaType = com.hxbd.clp.domain.CourseDetails.class,
			one = @One(select = "com.hxbd.clp.dao.CourseDetailsDao.selectCourseDetailsById")),
			@Result(column = "id" ,property="teacher",javaType = List.class,
			many = @Many(select ="com.hxbd.clp.dao.TeacherDao.selectTeacher"))

	})
	Course selectById(Integer id);

	//根据教师ID查询教师对应的课程
	@Select("select * from " + RasConstants.COURSETABLE + " where id in (select course_id from "+RasConstants.COURSEANDTEACHERTABLE+" where teacher_id = #{id})")
	@Results(value = {@Result(column = "id", property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "course_no" ,property = "courseNo" , javaType = java.lang.String.class),
			@Result(column = "course_name" , property = "courseName" , javaType = java.lang.String.class),
			@Result(column = "teaching_material" , property = "teachingMateril" , javaType = java.lang.String.class),
			@Result(column = "study_start_time" , property = "studyStartTime" , javaType = java.util.Date.class),
			@Result(column = "study_end_time" , property = "studyEndTime" , javaType = java.util.Date.class),
			@Result(column = "exam_start_time" , property = "examStartTime" , javaType = java.util.Date.class),
			@Result(column = "exam_end_time" , property = "examEndTime" , javaType = java.util.Date.class),
			@Result(column = "course_status" , property = "courseStatus" , javaType = java.lang.Integer.class),
			@Result(column = "orfree" , property = "orfree" , javaType = java.lang.Integer.class),
			
			@Result(column = "course_period" , property = "coursePeriod" , javaType = java.lang.String.class),
			@Result(column = "expend_time" , property = "expendTime" , javaType = java.lang.String.class),
			@Result(column = "course_language" , property = "courseLanguage" , javaType = java.lang.String.class),
			@Result(column = "subtitle_type" , property = "subtitleType" , javaType = java.lang.String.class),
			@Result(column = "course_pic" , property = "coursePic" , javaType = java.lang.String.class),
			
			@Result(column = "course_type_id" ,property = "courseType" ,javaType = com.hxbd.clp.domain.CourseType.class,
			one = @One(select = "com.hxbd.clp.dao.CourseTypeDao.selectCourseTypeById")),
			@Result(column = "course_details_id" , property = "courseDetails" ,javaType = com.hxbd.clp.domain.CourseDetails.class,
			one = @One(select = "com.hxbd.clp.dao.CourseDetailsDao.selectCourseDetailsById"))
	})
    List<Course> selectCourse(Integer id);
	
	// 根据课程编号查询课程
	@Select("select * from " + RasConstants.COURSETABLE + " where id = #{courseId}")
	@Results(value = {@Result(column = "id", property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "course_no" ,property = "courseNo" , javaType = java.lang.String.class),
			@Result(column = "course_name" , property = "courseName" , javaType = java.lang.String.class),
			@Result(column = "teaching_material" , property = "teachingMateril" , javaType = java.lang.String.class),
			@Result(column = "study_start_time" , property = "studyStartTime" , javaType = java.util.Date.class),
			@Result(column = "study_end_time" , property = "studyEndTime" , javaType = java.util.Date.class),
			@Result(column = "exam_start_time" , property = "examStartTime" , javaType = java.util.Date.class),
			@Result(column = "exam_end_time" , property = "examEndTime" , javaType = java.util.Date.class),
			@Result(column = "course_status" , property = "courseStatus" , javaType = java.lang.Integer.class),
			@Result(column = "orfree" , property = "orfree" , javaType = java.lang.Integer.class),
			
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
	Course selectByCourseId(String courseId);
	
	// 动态查询课程
	@SelectProvider(type = CourseDynaSqlProvider.class,method = "selectWithParam")
	@Results(value = {@Result(column = "id", property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "course_no" ,property = "courseNo" , javaType = java.lang.String.class),
			@Result(column = "course_name" , property = "courseName" , javaType = java.lang.String.class),
			@Result(column = "teaching_material" , property = "teachingMateril" , javaType = java.lang.String.class),
			
			@Result(column = "study_start_time" , property = "studyStartTime" , javaType = java.util.Date.class),
			@Result(column = "study_end_time" , property = "studyEndTime" , javaType = java.util.Date.class),
			@Result(column = "exam_start_time" , property = "examStartTime" , javaType = java.util.Date.class),
			@Result(column = "exam_end_time" , property = "examEndTime" , javaType = java.util.Date.class),
			
			@Result(column = "course_status" , property = "courseStatus" , javaType = java.lang.Integer.class),
			@Result(column = "orfree" , property = "orfree" , javaType = java.lang.Integer.class),
			
			@Result(column = "course_period" , property = "coursePeriod" , javaType = java.lang.String.class),
			@Result(column = "expend_time" , property = "expendTime" , javaType = java.lang.String.class),
			@Result(column = "course_language" , property = "courseLanguage" , javaType = java.lang.String.class),
			@Result(column = "subtitle_type" , property = "subtitleType" , javaType = java.lang.String.class),
			@Result(column = "course_pic" , property = "coursePic" , javaType = java.lang.String.class),
			
			@Result(column = "course_type_id" ,property = "courseType" ,javaType = com.hxbd.clp.domain.CourseType.class,
			one = @One(select = "com.hxbd.clp.dao.CourseTypeDao.selectCourseTypeById")),
			/*@Result(column = "course_details_id" , property = "courseDetails" ,javaType = com.hxbd.clp.domain.CourseDetails.class,
			one = @One(select = "com.hxbd.clp.dao.CourseDetailsDao.selectCourseDetailsById")),*/
			@Result(column = "id" ,property="teacher",javaType = List.class,
			many = @Many(select ="com.hxbd.clp.dao.TeacherDao.selectTeacher"))

	})
	List<Course> selectByPage(Map<String, Object> params);
	
	//查询课程总数
	@SelectProvider(type = CourseDynaSqlProvider.class , method = "countCourse")
	Integer count(Map<String, Object> params);
	
	// 添加课程
	@InsertProvider(type = CourseDynaSqlProvider.class , method = "saveCourse")
	void save(CourseForm courseForm);
	
	// 更新课程
	@UpdateProvider(type = CourseDynaSqlProvider.class , method = "updateCourse")
	void update(CourseForm courseForm);
	
	//根据课程id删除课程
	@DeleteProvider(type = CourseDynaSqlProvider.class , method = "batchDelete")
	void batchDelete(Map<String, Integer[]>  ids);
	
	
	// 查询最新8条数据
	@Select("select * from " + RasConstants.COURSETABLE + " order by id desc limit 8")
	@Results(value = {@Result(column = "id", property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "course_no" ,property = "courseNo" , javaType = java.lang.String.class),
			@Result(column = "course_name" , property = "courseName" , javaType = java.lang.String.class),
			@Result(column = "teaching_material" , property = "teachingMateril" , javaType = java.lang.String.class),
			@Result(column = "study_start_time" , property = "studyStartTime" , javaType = java.util.Date.class),
			@Result(column = "study_end_time" , property = "studyEndTime" , javaType = java.util.Date.class),
			@Result(column = "exam_start_time" , property = "examStartTime" , javaType = java.util.Date.class),
			@Result(column = "exam_end_time" , property = "examEndTime" , javaType = java.util.Date.class),
			@Result(column = "course_status" , property = "courseStatus" , javaType = java.lang.Integer.class),
			@Result(column = "orfree" , property = "orfree" , javaType = java.lang.Integer.class),
			
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
	List<Course> findLimitEight();
	
	
	//查询list
	@Select("select * from " + RasConstants.COURSETABLE)
	@Results(value = {@Result(column = "id" , property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "course_name" , property = "courseName" , javaType = java.lang.String.class),
			@Result(column = "course_no" , property = "courseNo" , javaType = java.lang.String.class)})
	List<Course> findAllList();

	/**
	 * 查询当前时间距离考试开始时间 0到3天的记录
	 * @return
	 */
	@Select("SELECT  * FROM "+RasConstants.COURSETABLE+" WHERE TO_DAYS(exam_start_time)- TO_DAYS(NOW()) BETWEEN 1 AND 3;")
	@Results(value = {@Result(column = "id", property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "course_no" ,property = "courseNo" , javaType = java.lang.String.class),
			@Result(column = "course_name" , property = "courseName" , javaType = java.lang.String.class),
			@Result(column = "teaching_material" , property = "teachingMateril" , javaType = java.lang.String.class),
			@Result(column = "study_start_time" , property = "studyStartTime" , javaType = java.util.Date.class),
			@Result(column = "study_end_time" , property = "studyEndTime" , javaType = java.util.Date.class),
			@Result(column = "exam_start_time" , property = "examStartTime" , javaType = java.util.Date.class),
			@Result(column = "exam_end_time" , property = "examEndTime" , javaType = java.util.Date.class),
			@Result(column = "course_status" , property = "courseStatus" , javaType = java.lang.Integer.class),
			@Result(column = "orfree" , property = "orfree" , javaType = java.lang.Integer.class),
			
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
	List<Course> selectByExamTime();

	//查询课程
	@Select("select * from " + RasConstants.COURSETABLE + " where course_no = #{courseNo}")
	@Results(value = {@Result(column = "id", property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "course_period" ,property = "coursePeriod" , javaType = java.lang.String.class),
			@Result(column = "expend_time" ,property = "expendTime" , javaType = java.lang.String.class),
			@Result(column = "course_language" ,property = "courseLanguage" , javaType = java.lang.String.class),
			@Result(column = "subtitle_type" ,property = "subtitleType" , javaType = java.lang.String.class),
			@Result(column = "course_pic" ,property = "coursePic" , javaType = java.lang.String.class),
			@Result(column = "course_no" ,property = "courseNo" , javaType = java.lang.String.class),
			@Result(column = "course_name" , property = "courseName" , javaType = java.lang.String.class),
			@Result(column = "teaching_material" , property = "teachingMateril" , javaType = java.lang.String.class),
			@Result(column = "study_start_time" , property = "studyStartTime" , javaType = java.util.Date.class),
			@Result(column = "study_end_time" , property = "studyEndTime" , javaType = java.util.Date.class),
			@Result(column = "exam_start_time" , property = "examStartTime" , javaType = java.util.Date.class),
			@Result(column = "exam_end_time" , property = "examEndTime" , javaType = java.util.Date.class),
			@Result(column = "course_status" , property = "courseStatus" , javaType = java.lang.Integer.class),
			@Result(column = "orfree" , property = "orfree" , javaType = java.lang.Integer.class),
			@Result(column = "course_type_id" ,property = "courseType" ,javaType = com.hxbd.clp.domain.CourseType.class,
			one = @One(select = "com.hxbd.clp.dao.CourseTypeDao.selectCourseTypeById")),
			@Result(column = "course_details_id" , property = "courseDetails" ,javaType = com.hxbd.clp.domain.CourseDetails.class,
			one = @One(select = "com.hxbd.clp.dao.CourseDetailsDao.selectCourseDetailsById")),
			@Result(column = "id" ,property="teacher",javaType = List.class,
			many = @Many(select ="com.hxbd.clp.dao.TeacherDao.selectTeacher"))

	})
	Course selectByCourseNo(String courseNo);
}
