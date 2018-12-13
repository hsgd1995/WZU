package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.Teacher;
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
					if(teacher.getTeacherName() != null &&  !teacher.getTeacherName().equals("")){
						WHERE(" teacher_name like CONCAT('%',#{teacher.teacherName},'%')");
					}
					if(teacher.getTeacherNo() != null &&  !teacher.getTeacherNo().equals("")){
						WHERE(" teacher_no like CONCAT('%',#{teacher.teacherNo},'%')");
					}
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
					if(teacher.getTeacherName() != null &&  !teacher.getTeacherName().equals("")){
						WHERE(" teacher_name like CONCAT('%',#{teacher.teacherName},'%')");
					}
					if(teacher.getTeacherNo() != null &&  !teacher.getTeacherNo().equals("")){
						WHERE(" teacher_no like CONCAT('%',#{teacher.teacherNo},'%')");
					}
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
					if(teacher.getTeacherNo() != null && !teacher.getTeacherNo().equals("")){
						VALUES("teacher_no", "#{teacherNo}");
					}
					if(teacher.getTeacherName() != null && !teacher.getTeacherName().equals("")){
						VALUES("teacher_name", "#{teacherName}");
					}
					if(teacher.getPosition() != null && !teacher.getPosition().equals("")){
						VALUES("position", "#{position}");
					}
					if(teacher.getIntroduce() != null && !teacher.getIntroduce().equals("")){
						VALUES("introduce", "#{introduce}");
					}
					if(teacher.getContent() != null && !teacher.getContent().equals("")){
						VALUES("content", "#{content}");
					}
					if(teacher.getTeacherPic() != null && !teacher.getTeacherPic().equals("")){
						VALUES("teacher_pic", "#{teacherPic}");
					}
					VALUES("remark", "#{remark}");
				}
			}
		}.toString();
	}

	public String updateTeacher(Teacher teacher) {
		return new SQL(){
			{
				if(teacher != null){
					UPDATE(RasConstants.TEACHERTABLE);
					if(teacher.getTeacherNo() != null && !teacher.getTeacherNo().equals("")){
						SET("teacher_no = #{teacherNo}");
					}
					if(teacher.getTeacherName() != null && !teacher.getTeacherName().equals("")){
						SET("teacher_name = #{teacherName}");
					}
					if(teacher.getPosition() != null && !teacher.getPosition().equals("")){
						SET("position = #{position}");
					}
					if(teacher.getIntroduce() != null && !teacher.getIntroduce().equals("")){
						SET("introduce = #{introduce}");
					}
					if(teacher.getContent() != null && !teacher.getContent().equals("")){
						SET("content = #{content}");
					}
					if(teacher.getTeacherPic() != null && !teacher.getTeacherPic().equals("")){
						SET("teacher_pic = #{teacherPic}");
					}
					SET("remark = #{remark}");
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
