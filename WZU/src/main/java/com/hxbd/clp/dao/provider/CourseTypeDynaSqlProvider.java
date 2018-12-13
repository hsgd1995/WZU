package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.CourseType;
import com.hxbd.clp.utils.common.RasConstants;

public class CourseTypeDynaSqlProvider {
	
	public String selectByParma(Map<String, Object> params) {
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(RasConstants.COURSETYPETABLE);
				CourseType courseType  = (CourseType) params.get("courseType");
				if(courseType != null){
					if(courseType.getTypeName() != null &&  !courseType.getTypeName().equals("")){
						WHERE(" type_name like CONCAT('%',#{courseType.typeName},'%')");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	public String countCourseType(Map<String, Object> params) {
		String sql =  new SQL(){
			{
				SELECT("count(*)");
				FROM(RasConstants.COURSETYPETABLE);
				CourseType courseType  = (CourseType) params.get("courseType");
				if(courseType != null){
					if(courseType.getTypeName() != null &&  !courseType.getTypeName().equals("")){
						WHERE(" type_name like CONCAT('%',#{courseType.typeName},'%')");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	public String addCourseType(CourseType courseType) {
		return new SQL(){
			{
				if(courseType != null){
					INSERT_INTO(RasConstants.COURSETYPETABLE);
					if(courseType.getTypeName() != null && !courseType.getTypeName().equals("")){
						VALUES("type_name", "#{typeName}");
					}
					VALUES("remark", "#{remark}");
				}
			}
		}.toString();
	}

	public String updateCourseType(CourseType CourseType) {
		return new SQL(){
			{
				if(CourseType != null){
					UPDATE(RasConstants.COURSETYPETABLE);
					if(CourseType.getTypeName() != null && !CourseType.getTypeName().equals("")){
						SET("type_name = #{typeName}");
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
					DELETE_FROM(RasConstants.COURSETYPETABLE);
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
