package com.hxbd.clp.service;

import java.util.List;
import java.util.Map;

import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.domain.Process;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.CourseVideoVO;


public interface CourseVideoService {
	
	// 根据课程视频id查询课程视频
	CourseVideo selectById(Integer courseVideoId);
	// 动态查询课程视频
	PageModel<CourseVideo> findCourseVideo(CourseVideo courseVideo, PageModel<CourseVideo> pageModel);
	PageModel<CourseVideoVO> findCourseVideo(CourseVideo courseVideo, PageModel<CourseVideoVO> pageModel,Map<Integer, Course> courseMap);
	//后台绑定视频页面用的查询方法
	PageModel<CourseVideoVO> findCourseVideoVO(CourseVideoVO courseVideoVO, PageModel<CourseVideoVO> pageModel,Map<Integer, Course> courseMap);
	// 分页查询获取课程视频
	List<CourseVideo> getCourseVideoByPage(Map<String, Object> parmas);
	// 新增课程视频
	Integer addCourseVideo(CourseVideo courseVideo);
	// 更新课程视频
	void modifyCourseVideo(CourseVideo courseVideo);
	// 根据课程视频id删除课程视频
	void batchDelCourseVideo(Integer[] ids);
	// 根据课程ID查询课程视频
	List<CourseVideo> findCourseVideoByCourseId(Integer courseId);
	//查出所有list
	List<CourseVideo> findCourseVideoTree(Integer courseId);
	//根据关键字查出所有list
	List<CourseVideo> findCourseVideoTree(Integer courseId, String keyword);
	
	//根据课程ID和父节点0 查询数据
	CourseVideo findCourseVideoByCourseIdAndParent(Integer courseId, String name,int i);
	//查询父节点列表
	List<CourseVideo> findCourseVideoByParent(int i);
	//查询课程视频   params : ( courseId , parent , name  )
	CourseVideo findByCourseIdAndParentAndName(Integer courseId, Integer parent, String name);
	//根据课程Id 和父节点0 查询课程视频
	List<CourseVideo> findCourseVideoByCourseIdAndParent(Integer courseId, int i);
	//查询Map
	Map<Integer,CourseVideo> findAllMap();
	
	/**
	 * 保存用户观看的视频进度
	 * @param userId
	 * @param courseVideoId
	 * @param currentTime
	 */
	void saveVideoProcess(Integer userId, Integer courseVideoId, Integer courseId,Double currentTime);
	/**
	 * 获取视频进度
	 * @param userId
	 * @param courseVideoId
	 * @param courseId
	 * @return
	 */
	Process getVideoProcess(Integer userId, Integer courseVideoId, Integer courseId);
	/**
	 * 完成学习视频
	 * @param userId
	 * @param courseVideoId
	 * @param courseId
	 */
	void finishStudyVideo(Integer userId, Integer courseVideoId, Integer courseId);
	/**
	 * 获得用户对于某课程最新观看的视频
	 * @param userId
	 * @param courseId
	 * @return
	 */
	CourseVideo getLastStudyVideo(Integer userId, Integer courseId);
	
	/**
	 * 根据课程编号获取主标题
	 * @param courseNo
	 * @return
	 */
	List<CourseVideo> findCourseVideoByCourseNo(String courseNo);
	
	
	
}
