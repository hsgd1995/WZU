package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.dao.provider.TeacherDynaSqlProvider;
import com.hxbd.clp.domain.Teacher;
import com.hxbd.clp.utils.common.RasConstants;

public interface TeacherDao {
	
	//根据id查询教师
	@Select("select * from " + RasConstants.TEACHERTABLE + " where id = #{id}")
	@Results(value = {@Result(column = "id", property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "teacher_name" , property = "teacherName" , javaType = java.lang.String.class),
			@Result(column = "teacher_no" , property = "teacherNo" , javaType = java.lang.String.class),
			@Result(column = "teacher_pic" , property = "teacherPic" , javaType = java.lang.String.class),
			@Result(column = "id", property="course",javaType = List.class,
			many = @Many(select="com.hxbd.clp.dao.CourseDao.selectCourse"))
	})
	Teacher selectTeacherById(Integer id);
	
	//根据课程ID查询对应课程的教师
	@Select("select * from teacher_table where id in (select teacher_id from course_and_teacher_table where course_id = #{id})")
	@Results(value = {@Result(column = "id", property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "teacher_no" , property = "teacherNo" , javaType = java.lang.String.class),
			@Result(column = "teacher_name" , property = "teacherName" , javaType = java.lang.String.class),
			@Result(column = "teacher_pic" , property = "teacherPic" , javaType = java.lang.String.class)
	})
    List<Teacher> selectTeacher(Integer id);
	
	//根据教师名查询教师
	@Select("select * from " + RasConstants.TEACHERTABLE + " where teacher_name = #{teacherName}")
	@Results(value = {@Result(column = "id", property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "teacher_name" , property = "teacherName" , javaType = java.lang.String.class),
			@Result(column = "teacher_no" , property = "teacherNo" , javaType = java.lang.String.class),
			@Result(column = "id", property="course",javaType = List.class,
			many = @Many(select="com.hxbd.clp.dao.CourseDao.selectCourse"))
	})
	Teacher selectTeacherByName(String teacherName);
	
	// 查询教师列表list
	@SelectProvider(type = TeacherDynaSqlProvider.class , method = "selectByParma")
	@Results(value = {@Result(column = "id", property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "teacher_name" , property = "teacherName" , javaType = java.lang.String.class),
			@Result(column = "teacher_no" , property = "teacherNo" , javaType = java.lang.String.class),
			@Result(column = "teacher_pic" , property = "teacherPic" , javaType = java.lang.String.class),
			@Result(column = "id", property="course",javaType = List.class,
			many = @Many(select="com.hxbd.clp.dao.CourseDao.selectCourse"))
	})
	List<Teacher> selectTeacherList(Map<String, Object> params);
	
	//查询教师总数
	@SelectProvider(type = TeacherDynaSqlProvider.class , method = "countTeacher")
	Integer count(Map<String, Object> params);
	
	// 保存教师
	@InsertProvider(type = TeacherDynaSqlProvider.class , method = "addTeacher")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void saveTeacher(Teacher teacher);
	
	// 更新教师
	@UpdateProvider(type = TeacherDynaSqlProvider.class , method = "updateTeacher")
	void updateTeacher(Teacher teacher);
	
	//批量删除教师
	@DeleteProvider(type = TeacherDynaSqlProvider.class , method = "batchDel")
	void batchDelTeacher(Map<String, Integer[]> map);

	
	//查询4条教师数据  首页用来显示
	@Select("select * from " + RasConstants.TEACHERTABLE + " order by id desc limit 4")
	@Results(value = {@Result(column = "id", property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "teacher_name" , property = "teacherName" , javaType = java.lang.String.class),
			@Result(column = "teacher_No" , property = "teacherNo" , javaType = java.lang.String.class),
			@Result(column = "teacher_pic" , property = "teacherPic" , javaType = java.lang.String.class)
/*			@Result(column = "id", property="course",javaType = List.class,
			many = @Many(select="com.hxbd.clp.dao.CourseDao.selectCourse"))*/
	})
	List<Teacher> findLimitFour();
	
	
	//查询list
	@Select("select * from " + RasConstants.TEACHERTABLE)
	@Results(value = {@Result(column = "id" , property = "id" , javaType = java.lang.Integer.class),
			@Result(column = "teacher_name" , property = "teacherName" , javaType = java.lang.String.class),
			@Result(column = "teacher_no" , property = "teacherNo" , javaType = java.lang.String.class)})
	List<Teacher> findAllList();

	
	//查询教师
		@Select("select * from " + RasConstants.TEACHERTABLE + " where teacher_no = #{teacherNo}")
		@Results(value = {@Result(column = "id", property = "id" , javaType = java.lang.Integer.class),
				@Result(column = "teacher_name" , property = "teacherName" , javaType = java.lang.String.class),
				@Result(column = "teacher_no" , property = "teacherNo" , javaType = java.lang.String.class),
				@Result(column = "teacher_pic" , property = "teacherPic" , javaType = java.lang.String.class),
				@Result(column = "id", property="course",javaType = List.class,
				many = @Many(select="com.hxbd.clp.dao.CourseDao.selectCourse"))
		})
	Teacher selectTeacherByNo(String teacherNo);
}
