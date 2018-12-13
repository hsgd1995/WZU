package com.hxbd.clp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxbd.clp.dao.CourseAndUserDao;
import com.hxbd.clp.dao.CourseDao;
import com.hxbd.clp.dao.SystemMessageDao;
import com.hxbd.clp.dao.UserDao;
import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.CourseAndUser;
import com.hxbd.clp.domain.SystemMessage;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.service.SystemMessageService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 系统消息业务层实现类
 * @author Administrator
 *
 */
@Service("systemMessageService")
public class SystemMessageServiceImpl implements SystemMessageService {
	
	@Autowired
	private SystemMessageDao systemMessageDao;
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private CourseAndUserDao courseAndUserDao;
	@Autowired
	private UserDao userDao;
	
	@Override
	public void examStudyWarn() {
		System.out.println("开始考试时间提醒");
		List<Course> warnCourseList = courseDao.selectByExamTime();
		if(warnCourseList.size()>0){
			for(int i=0;i<warnCourseList.size();i++){
				//找出已开始学习该课程的用户
				List<CourseAndUser> courseAndUserList =  courseAndUserDao.selectByCourseIdAndJoincourse(warnCourseList.get(i).getId());
				User user = null;
				SystemMessage sysMessage = null;
				//遍历集合，找出对应用户
				if(courseAndUserList.size()>0){
					for (int j = 0; j < courseAndUserList.size(); j++) {
						user = userDao.selectById(courseAndUserList.get(j).getUserId());
						sysMessage = new SystemMessage();
						sysMessage.setUser(user);
						sysMessage.setCourse(warnCourseList.get(i));
						sysMessage.setTitle("考试时间提醒，课程：《"+warnCourseList.get(i).getCourseName()+"》");
						sysMessage.setContent("《"+warnCourseList.get(i).getCourseName()+"》 考试开始时间为："+formatDate(warnCourseList.get(i).getExamStartTime())+"，考试结束时间为："+formatDate(warnCourseList.get(i).getExamEndTime())+"，请不要错过考试时间！");
						systemMessageDao.insert(sysMessage);
					}
				}
			}
		}
		
	}
	
	public String formatDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(date==null){
			return "无";
		}
		return sdf.format(date);
	}
	

	@Override
	public PageModel<SystemMessage> getByPage(SystemMessage systemMessage, Integer pageIndex, Integer pageSize) {
		//1.整理参数
		Map<String,Object> params = new HashMap<>();
		params.put("systemMessage", systemMessage);
		//2.根据检索条件查询记录总数
		int recordCount = systemMessageDao.count(params);
		PageModel<SystemMessage> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		pageModel.setPageSize(pageSize);
		pageModel.setRecordCount(recordCount);
		if(recordCount>0){
			params.put("pageModel", pageModel);
			//3.根据检索和分页条件查询记录，并保存到分页对象中
			pageModel.setList(systemMessageDao.selectByPage(params));
		}
		return pageModel;
	}

	@Override
	public SystemMessage getById(Integer id) {
		return systemMessageDao.selectById(id);
	}

	@Override
	public void batchDel(Integer[] ids) {
		Map<String, Object> params = new HashMap<>();
		params.put("ids", ids);
		systemMessageDao.batchDel(params);		
	}

	@Override
	public void read(Integer id,Integer userId) {
		systemMessageDao.updateStatusById(id,userId);		
	}
	
	@Override
	public void allRead(Integer userId) {
		systemMessageDao.updateStatusByUserId(userId);
	}

	@Override
	public void allClear(Integer userId) {
		systemMessageDao.delMessageByUserId(userId);		
	}

	@Override
	public void UserDelSysMessage(Integer[] ids, Integer userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("ids", ids);
		params.put("userId", userId);
		systemMessageDao.delByIdsAndUserId(params);
	}
	
	
}
