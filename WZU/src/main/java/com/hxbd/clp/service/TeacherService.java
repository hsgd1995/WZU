package com.hxbd.clp.service;

import java.util.List;
import java.util.Map;

import com.hxbd.clp.domain.Teacher;
import com.hxbd.clp.utils.tag.PageModel;

public interface TeacherService {
	
	// 根据id查询教师
	Teacher selectById(Integer id);
	// 根据教师名查询教师
	Teacher selectByName(String teacherName);
	// 分页查询教师
	PageModel<Teacher> findTeacher(Teacher teacher ,PageModel<Teacher> pageModel);
	// 获取教师列表
	List<Teacher> getTeacherList(Map<String, Object> params);
	// 新增教师
	int addTeacher(Teacher teacher);
	// 修改教师
	void modifyTeacher(Teacher teacher);
	//批量删除教师
	void batchDelTeacher(Integer[] ids);
	//查询4条教师数据
	List<Teacher> findLimitfour();
	//查询map
	Map<Integer, Teacher> findAllMap();
	//根据编号查询教师
	Teacher selectByTeacherNo(String teacherNo);
}
