package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.dao.provider.CourseVideoDynaSqlProvider;
import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.domain.Process;
import com.hxbd.clp.utils.common.RasConstants;

public interface CourseVideoDao {
	
	// 根据id查询课程视频
	@Select("select * from " + RasConstants.COURSEVIDEOTABLE + " where id = #{id}")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "course_id",property = "courseId" ,javaType = java.lang.Integer.class),
			@Result(column = "id",property="captionList",javaType=List.class,
			many=@Many(select="com.hxbd.clp.dao.CaptionDao.selectByVideoId"))
	})
	CourseVideo selectById(Integer id);
	
	@Select("select * from "+ RasConstants.COURSEVIDEOTABLE + " where name = #{name}")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "course_id",property = "courseId" ,javaType = java.lang.Integer.class),
			@Result(column = "id",property="captionList",javaType=List.class,
			many=@Many(select="com.hxbd.clp.dao.CaptionDao.selectByVideoId"))
	})
	List<CourseVideo> selectByName(String name);
	
	
	// 分页查询课程视频
	@SelectProvider(type = CourseVideoDynaSqlProvider.class, method = "selectByParma")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "course_id",property = "courseId" ,javaType = java.lang.Integer.class),
			@Result(column = "id",property="captionList",javaType=List.class,
			many=@Many(select="com.hxbd.clp.dao.CaptionDao.selectByVideoId"))
	})
	List<CourseVideo> selectByPage(Map<String, Object> parmas);
	
	// 分页查询课程视频  parent =0  父标题
	@SelectProvider(type = CourseVideoDynaSqlProvider.class, method = "selectByParent")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "course_id",property = "courseId" ,javaType = java.lang.Integer.class),
			@Result(column = "id",property="captionList",javaType=List.class,
			many=@Many(select="com.hxbd.clp.dao.CaptionDao.selectByVideoId"))
	})
	List<CourseVideo> selectByParent(Map<String, Object> parmas);
	
	// 课程视频总数
	@SelectProvider(type = CourseVideoDynaSqlProvider.class, method = "countCourseVideo")
	Integer count(Map<String, Object> parmas);
	
	// 新增课程视频
	@InsertProvider(type = CourseVideoDynaSqlProvider.class, method = "addCourseVideo")
	@Options(useGeneratedKeys=true,keyProperty = "id")
	void addCourseVideo(CourseVideo courseVideo);
	
	// 更新课程视频
	@UpdateProvider(type = CourseVideoDynaSqlProvider.class, method = "updateCourseVideo")
	void updateCourseVideo(CourseVideo courseVideo);
	
	// 根据id删除课程视频
	@DeleteProvider(type = CourseVideoDynaSqlProvider.class, method = "batchDelCourseVideo")
	void batchDelete(Map<String, Integer[]>  ids);

	// 根据课程ID查询课程视频
	@Select("select * from " + RasConstants.COURSEVIDEOTABLE + " where course_id = #{courseId}")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "course_id",property = "courseId" ,javaType = java.lang.Integer.class),
			@Result(column = "id",property="captionList",javaType=List.class,
			many=@Many(select="com.hxbd.clp.dao.CaptionDao.selectByVideoId"))
	})
	List<CourseVideo> findCourseVideoByCourseId(Integer courseId);
	
	// 根据课程ID和关键字查询课程视频
		@Select("select * from " + RasConstants.COURSEVIDEOTABLE + " where (course_id = #{courseId} and name like CONCAT('%',#{keyword},'%') AND parent <>0 ) or (parent =0 AND course_id=#{courseId})")
		@Results(value = {
				@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
				@Result(column = "course_id",property = "courseId" ,javaType = java.lang.Integer.class),
				@Result(column = "id",property="captionList",javaType=List.class,
				many=@Many(select="com.hxbd.clp.dao.CaptionDao.selectByVideoId"))
		})
	List<CourseVideo> findCourseVideoByCourseIdAndName(@Param("courseId") Integer courseId,@Param("keyword") String keyword);
	
	// 查询所有list
	@Select("select * from " + RasConstants.COURSEVIDEOTABLE)
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "course_id",property = "courseId" ,javaType = java.lang.Integer.class),
			@Result(column = "id",property="captionList",javaType=List.class,
			many=@Many(select="com.hxbd.clp.dao.CaptionDao.selectByVideoId"))
	})
	List<CourseVideo> findAllList();

	//根据课程ID和parent=0查询
	@Select("select * from " + RasConstants.COURSEVIDEOTABLE + " where course_id = #{courseId} and name = #{name} and parent = #{i}")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "course_id",property = "courseId" ,javaType = java.lang.Integer.class),
			@Result(column = "id",property="captionList",javaType=List.class,
			many=@Many(select="com.hxbd.clp.dao.CaptionDao.selectByVideoId"))
	})
	CourseVideo findCourseVideoByCourseIdAndParent(@Param("courseId")Integer courseId, @Param("name")String name, @Param("i")int i);

	//int i = 0;
	@Select("select * from " + RasConstants.COURSEVIDEOTABLE + " where parent = #{i}")
	List<CourseVideo> findCourseVideoListByParent(int i);
	
	//courseId, int i = 0;
	@Select("select * from " + RasConstants.COURSEVIDEOTABLE + " where course_id = #{courseId} and parent = #{parent}")
	List<CourseVideo> findCourseVideoListByCourseIdAndParent(@Param("courseId") Integer courseId , @Param("parent")Integer parent);


	@Select("select * from " + RasConstants.COURSEVIDEOTABLE +" where course_id = #{courseId} and parent = #{parent} and name = #{name}")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "course_id",property = "courseId" ,javaType = java.lang.Integer.class),
			@Result(column = "id",property="captionList",javaType=List.class,
			many=@Many(select="com.hxbd.clp.dao.CaptionDao.selectByVideoId"))
	})
	CourseVideo findByCourseIdAndParentAndName(@Param("courseId")Integer courseId, @Param("parent")Integer parent, @Param("name")String name);

	@Select("select * from " + RasConstants.VIDEOPROCESS + " where user_id=#{userId} and course_id=#{courseId} and course_video_id=#{courseVideoId}")
	@Results(value={
			@Result(column = "user_id",property="userId",javaType = java.lang.Integer.class),
			@Result(column = "course_id",property="courseId",javaType = java.lang.Integer.class),
			@Result(column = "course_video_id",property="courseVideoId",javaType = java.lang.Integer.class),
			@Result(column = "is_finish",property="isFinish",javaType = java.lang.Integer.class)
	})
	Process findByUserIdAndCourseVideoId(@Param("userId") Integer userId, @Param("courseVideoId") Integer courseVideoId,@Param("courseId") Integer courseId);

	@Insert("insert into "+RasConstants.VIDEOPROCESS+" (user_id,course_id,course_video_id,process) value(#{userId},#{courseId},#{courseVideoId},#{currentTime})")
	void insertProcess(@Param("userId") Integer userId,@Param("courseVideoId") Integer courseVideoId,@Param("courseId") Integer courseId,@Param("currentTime") Double currentTime);

	@Update("update "+RasConstants.VIDEOPROCESS+" set process=#{currentTime} where user_id=#{userId} and course_id=#{courseId} and course_video_id=#{courseVideoId}")
	void updateProcess(@Param("userId") Integer userId,@Param("courseVideoId") Integer courseVideoId,@Param("courseId") Integer courseId,@Param("currentTime") Double currentTime);

	@Select("SELECT * FROM "+RasConstants.COURSEVIDEOTABLE+" WHERE parent in (SELECT s.id FROM course_video_table s where parent = 0 and course_id = #{courseId}) and course_id = #{courseId}")
	@Results({
		@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
		@Result(column = "course_id",property="courseId",javaType=java.lang.Integer.class),
		@Result(column = "id",property="captionList",javaType=List.class,
		many=@Many(select="com.hxbd.clp.dao.CaptionDao.selectByVideoId"))
	})
	List<CourseVideo> selectAllVideoByCourseId(@Param("courseId")Integer courseId);

	@Update("update "+RasConstants.VIDEOPROCESS+" set is_finish = 1 where user_id=#{userId} and course_id=#{courseId} and course_video_id=#{courseVideoId}")
	void updateIsFinish(@Param("userId") Integer userId,@Param("courseVideoId") Integer courseVideoId,@Param("courseId") Integer courseId);

	/**
	 * 查询用户对于某课程最近观看的视频进度
	 * @param userId
	 * @param courseId
	 * @return
	 */
	@Select("select * from "+RasConstants.VIDEOPROCESS+" where user_id = #{userId} and course_id = #{courseId} ORDER BY lastStudy_time DESC LIMIT 0,1")
	@Results(value={
			@Result(column = "user_id",property="userId",javaType = java.lang.Integer.class),
			@Result(column = "course_id",property="courseId",javaType = java.lang.Integer.class),
			@Result(column = "course_video_id",property="courseVideoId",javaType = java.lang.Integer.class),
			@Result(column = "is_finish",property="isFinish",javaType = java.lang.Integer.class)
	})
	Process selectLastCourseVideoId(@Param("userId") Integer userId,@Param("courseId") Integer courseId);

	/**
	 * 课程视频总数(绑定主标题页面)
	 * @param params
	 * @return
	 */
	@SelectProvider(type = CourseVideoDynaSqlProvider.class, method = "countCourseVideoVO")
	Integer countCourseVideoVO(Map<String, Object> params);
	/**
	 * 分页查询课程视频记录(绑定主标题页面)
	 * @param params
	 * @return
	 */
	@SelectProvider(type = CourseVideoDynaSqlProvider.class, method = "selectByParentVO")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "course_id",property = "courseId" ,javaType = java.lang.Integer.class),
			@Result(column = "id",property="captionList",javaType=List.class,
			many=@Many(select="com.hxbd.clp.dao.CaptionDao.selectByVideoId"))
	})
	List<CourseVideo> selectByParentVO(Map<String, Object> params);

	/**
	 * 根据课程编号获取主标题
	 * @param courseNo
	 * @return
	 */
	@SelectProvider(type = CourseVideoDynaSqlProvider.class, method = "slectByCourseNo")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "course_id",property = "courseId" ,javaType = java.lang.Integer.class),
			@Result(column = "id",property="captionList",javaType=List.class,
			many=@Many(select="com.hxbd.clp.dao.CaptionDao.selectByVideoId"))
	})
	List<CourseVideo> slectByCourseNo(String courseNo);
	
	}
