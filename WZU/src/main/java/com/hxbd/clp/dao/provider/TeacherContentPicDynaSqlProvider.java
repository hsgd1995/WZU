package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.TeacherContentPic;
import com.hxbd.clp.utils.common.RasConstants;

public class TeacherContentPicDynaSqlProvider {
	public String saveTeacherContentPic(TeacherContentPic teacherContent){
		return new SQL(){
			{
				INSERT_INTO(RasConstants.TEACHERCONTENTPIC);
				if(teacherContent != null){
					if(teacherContent.getUrl() != null && !teacherContent.getUrl().equals("")){
						VALUES("url", "#{url}");
					}
					if(teacherContent.getTeacher()!=null){
						VALUES("teacher_id", "#{teacher.id}");
					}
				}
			}
		}.toString();
	}
	
	
	
	public String batchDelPic(Map<String, Integer[]> ids){
		return new SQL(){
			{
				if(ids != null && ids.get("ids") != null){
					DELETE_FROM(RasConstants.TEACHERCONTENTPIC);
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
