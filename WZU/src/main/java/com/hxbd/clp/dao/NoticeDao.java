package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.dao.provider.NoticeDynaSqlProvider;
import com.hxbd.clp.domain.Notice;
import com.hxbd.clp.utils.common.RasConstants;

public interface NoticeDao {
	
	//根据id查询课程公告
	@Select("select * from " + RasConstants.NOTICETABLE + " where id = #{id}")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "release_time",property = "releaseTime" ,javaType = java.util.Date.class),
			@Result(column = "course_id",property = "courseId" ,javaType = java.lang.Integer.class)
	})
	Notice selectById(Integer id);
	
	//分页查询课程公告
	@SelectProvider(type = NoticeDynaSqlProvider.class, method = "selectByParma")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "release_time",property = "releaseTime" ,javaType = java.util.Date.class),
			@Result(column = "course_id",property = "courseId" ,javaType = java.lang.Integer.class)
	})
	List<Notice> selectByPage(Map<String, Object> parmas);
	
	//课程公告总数
	@SelectProvider(type = NoticeDynaSqlProvider.class, method = "countNotice")
	Integer count(Map<String, Object> parmas);
	
	//新增课程公告
	@InsertProvider(type = NoticeDynaSqlProvider.class, method = "addNotice")
	void addNotice(Notice notice);
	
	//更新课程公告
	@UpdateProvider(type = NoticeDynaSqlProvider.class, method = "updateNotice")
	void updateNotice(Notice notice);
	
	//根据id删除课程公告
	@DeleteProvider(type = NoticeDynaSqlProvider.class, method = "batchDelNotice")
	void batchDelete(Map<String, Integer[]>  ids);

	//根据课程ID查询课程公告
	@Select("select * from " + RasConstants.NOTICETABLE + " where course_id = #{courseId} ORDER BY release_time DESC")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "release_time",property = "releaseTime" ,javaType = java.util.Date.class),
			@Result(column = "course_id",property = "courseId" ,javaType = java.lang.Integer.class)
	})
	List<Notice> findNoticeByCourseId(Integer courseId);
	
	
}
