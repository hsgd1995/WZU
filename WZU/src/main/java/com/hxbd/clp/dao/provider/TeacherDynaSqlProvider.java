package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.basedata.Teacher;
import com.hxbd.clp.utils.common.RasConstants;

public class TeacherDynaSqlProvider {
	
	public String selectByParma(Map<String, Object> params) {
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(RasConstants.TEACHERTABLE);
				ORDER_BY("id desc");
				Teacher teacher  = (Teacher) params.get("teacher");
				if(teacher != null){
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	public String countTeacher(Map<String, Object> params) {
		String sql =  new SQL(){
			{
				SELECT("count(*)");
				FROM(RasConstants.TEACHERTABLE);
				Teacher teacher  = (Teacher) params.get("teacher");
				if(teacher != null){
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	public String addTeacher(Teacher teacher) {
		return new SQL(){
			{
				if(teacher != null){
					INSERT_INTO(RasConstants.TEACHERTABLE);
					if(teacher.getName()!=null){
						VALUES("name", "#{name}");
					}
					if(teacher.getSex()!=null){
						VALUES("sex", "#{sex}");
					}
					if(teacher.getSc()!=null&&teacher.getSc().getId()!=null){
						VALUES("secondary_college_id", "#{sc.id}");
					}
					if(teacher.getPosition()!=null){
						VALUES("position", "#{position}");
					}
					if(teacher.getJob()!=null){
						VALUES("job", "#{job}");
					}
					if(teacher.getPhone()!=null){
						VALUES("phone", "#{phone}");
					}
					if(teacher.getProjectId()!=null){
						VALUES("project_id", "#{projectId}");
					}
				}
			}
		}.toString();
	}

	public String updateTeacher(Teacher teacher) {
		return new SQL(){
			{
				if(teacher != null){
					UPDATE(RasConstants.TEACHERTABLE);
					if(teacher.getName()!=null){
						SET("name=#{name}");
					}
					if(teacher.getSex()!=null){
						SET("sex=#{sex}");
					}
					if(teacher.getSc()!=null&&teacher.getSc().getId()!=null){
						SET("secondary_college_id=#{sc.id}");
					}
					if(teacher.getIsPosition()!=null){
						SET("position=#{position}");
					}
					if(teacher.getJob()!=null){
						SET("job=#{job}");
					}
					if(teacher.getPhone()!=null){
						SET("phone=#{phone}");
					}
					if(teacher.getProjectId()!=null){
						SET("project_id=#{projectId}");
					}
					WHERE(" id = #{id} ");
				}
			}
		}.toString();
	}
	
	public String batchDel(Map<String, Integer[]> ids){
		return new SQL(){
			{
				if(ids != null && ids.get("ids") != null){
					DELETE_FROM(RasConstants.TEACHERTABLE);
					String inConditions = "id in (";
					Integer[] idsArr = ids.get("ids");
					for (int index = 0; index < idsArr.length; index++) {
						inConditions += idsArr[index];
						if(index != idsArr.length - 1){
							inConditions += ",";
						}
					}
					inConditions +=") ";
					WHERE(inConditions);
				}
			}
		}.toString();
	}
}
