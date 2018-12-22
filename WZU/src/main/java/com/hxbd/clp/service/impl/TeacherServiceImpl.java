package com.hxbd.clp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxbd.clp.dao.TeacherDao;
import com.hxbd.clp.domain.basedata.Teacher;
import com.hxbd.clp.service.TeacherService;
import com.hxbd.clp.utils.tag.PageModel;

@Service("teacherService")
public class TeacherServiceImpl implements TeacherService{

	@Autowired
	private TeacherDao teacherDao;
	
	@Override
	public Teacher selectById(Integer id) {
		return teacherDao.selectTeacherById(id);
	}

	@Override
	public Teacher selectByName(String teacherName) {
		return teacherDao.selectTeacherByName(teacherName);
	}

	@Override
	public List<Teacher> getTeacherList(Map<String, Object> params) {
		return teacherDao.selectTeacherList(params);
	}

	@Override
	public int addTeacher(Teacher teacher) {
		teacherDao.saveTeacher(teacher);
		return teacher.getId();
	}

	@Override
	public void modifyTeacher(Teacher teacher) {
		teacherDao.updateTeacher(teacher);
	}

	@Override
	public void batchDelTeacher(Integer[] ids) {
		Map<String, Integer[]> map = new HashMap<>();
		map.put("ids", ids);
		teacherDao.batchDelTeacher(map);
	}

	@Override
	public PageModel<Teacher> findTeacher(Teacher teacher, PageModel<Teacher> pageModel) {
		Map<String, Object> params  = new HashMap<>();
		params.put("teacher", teacher);
		Integer recordCount = teacherDao.count(params);
		//System.out.println(recordCount);
		if(recordCount > 0){
			pageModel.setRecordCount(recordCount);
			params.put("pageModel", pageModel);
		}
		
		List<Teacher> list = teacherDao.selectTeacherList(params);
		//System.out.println(list);
		pageModel.setList(list);
		return pageModel;
	}

	@Override
	public List<Teacher> findLimitfour() {
		return teacherDao.findLimitFour();
	}
	
	
	//查询map  ------用来映射关联ID查询
	@Override
	public Map<Integer,Teacher> findAllMap(){
		List<Teacher> list = teacherDao.findAllList();
		Map<Integer,Teacher> map = new HashMap<>();
		for(int i = 0; i<list.size();i++){
			map.put(list.get(i).getId(), list.get(i));
		}
		return map;
	}

	@Override
	public Teacher selectByTeacherNo(String teacherNo) {
		return teacherDao.selectTeacherByNo(teacherNo);
	}

}
