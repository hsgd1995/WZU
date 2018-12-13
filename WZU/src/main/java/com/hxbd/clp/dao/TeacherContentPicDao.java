package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hxbd.clp.dao.provider.TeacherContentPicDynaSqlProvider;
import com.hxbd.clp.domain.TeacherContentPic;
import com.hxbd.clp.utils.common.RasConstants;

public interface TeacherContentPicDao {
	
	// 新增图片
		@InsertProvider(type = TeacherContentPicDynaSqlProvider.class ,method="saveTeacherContentPic")
		void save(TeacherContentPic teaPic);
		
		//批量删除图片
		@DeleteProvider(type = TeacherContentPicDynaSqlProvider.class , method = "batchDelPic")
		void batchDelPic(Map<String, Integer[]> ids);

		/**
		 * 根据id查找教师详细介绍图片
		 * @param id
		 * @return
		 */
		@Select("select * from "+RasConstants.TEACHERCONTENTPIC +" where id = #{id}")
		@Results({
			@Result(column="teacher_id",property="teacher",javaType=com.hxbd.clp.domain.TeacherContentPic.class,
					one=@One(select="com.hxbd.clp.dao.TeacherDao.selectTeacherById"))
		})
		TeacherContentPic selectPicById(Integer id);
		
		/**
		 * 根据教师详细介绍id查找图片
		 * @param newsId
		 * @return
		 */
		@Select("select * from "+RasConstants.TEACHERCONTENTPIC+" where teacher_id = #{teacherId}")
		@Results({
			@Result(column="teacher_id",property="teacher",javaType=com.hxbd.clp.domain.TeacherContentPic.class,
					one=@One(select="com.hxbd.clp.dao.TeacherDao.selectTeacherById"))
		})
		List<TeacherContentPic> SelectPicByTeacherId(Integer teacherId);

		@Delete("delete from "+RasConstants.TEACHERCONTENTPIC +" where id = #{id}")
		void delById(Integer id);
}
