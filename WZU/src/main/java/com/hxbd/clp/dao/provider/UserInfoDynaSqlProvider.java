package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.User;
import com.hxbd.clp.domain.UserInfo;
import com.hxbd.clp.utils.common.RasConstants;

public class UserInfoDynaSqlProvider {

	
	
	public String saveUserInfo(UserInfo userInfo){
		String sql =  new SQL(){
			{
				INSERT_INTO(RasConstants.PERSONAL_SETTINGS);
				if(userInfo != null){
					if(userInfo.getSchool() != null && !userInfo.getSchool().equals("")){
						VALUES("universities_colleges", "#{school}");
					}
					if(userInfo.getMajor() != null && !userInfo.getMajor().equals("")){
						VALUES("major", "#{major}");
					}
					if(userInfo.getGrade() != null && !userInfo.getGrade().equals("")){
						VALUES("grade", "#{grade}");
					}
					if(userInfo.getPersonalProfile() != null && !userInfo.getPersonalProfile().equals("")){
						VALUES("personal_profile", "#{personalProfile}");
					}
					if(userInfo.getEntrance() != null && !userInfo.getEntrance().equals("")){
						VALUES("entrance", "#{entrance}");
					}
					VALUES("user_id", "#{userId}");
					VALUES("privacy", "#{privacy}");
					VALUES("at_school", "#{atSchool}");
					VALUES("address", "#{address}");
					VALUES("sex", "#{sex}");
					VALUES("education", "#{education}");
					VALUES("birthday", "#{birthday}");
				}
			}
		}.toString();
		return sql;
	}
	
	public String updateUserInfo(UserInfo userInfo){
		String sql =  new SQL(){
			{
				UPDATE(RasConstants.PERSONAL_SETTINGS);
				if(userInfo != null){
					if(userInfo.getPersonalProfile() != null && !userInfo.getPersonalProfile().equals("")){
						SET("personal_profile = #{personalProfile}");
					}
					SET("entrance = #{entrance}");
					SET("universities_colleges = #{school}");
					SET("grade = #{grade}");
					SET("major = #{major}");
					SET("privacy = #{privacy}");
					SET("at_school = #{atSchool}");
					SET("address = #{address}");
					SET("sex = #{sex}");
					SET("education = #{education}");
					SET("birthday = #{birthday}");
					WHERE(" user_id = #{userId} ");
				}
			}
		}.toString();
		System.err.println(sql);
		return sql;
	}
	
	
	public String selectByParam(Map<String, Object> params){
		String sql = new SQL(){
			{
				SELECT("*");
				FROM(RasConstants.PERSONAL_SETTINGS);
				UserInfo userInfo = (UserInfo) params.get("userInfo");
				ORDER_BY("id Desc");
				if(userInfo != null){
					if(userInfo.getId() != null && !userInfo.getId().equals("")){
						WHERE(" id = #{userInfo.id}");
					}
					if(userInfo.getUserId() != null && !userInfo.getUserId().equals("")){
						WHERE(" user_id = #{userInfo.userId}");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}
	
	public String countUser(Map<String, Object> params){
		String sql = new SQL(){
			{
				SELECT("count(*)");
				FROM(RasConstants.PERSONAL_SETTINGS);
				UserInfo userInfo = (UserInfo) params.get("userInfo");
				if(userInfo != null){
					if(userInfo.getId() != null && !userInfo.getId().equals("")){
						WHERE(" id = #{userInfo.id}");
					}
					if(userInfo.getUserId() != null && !userInfo.getUserId().equals("")){
						WHERE(" user_id = #{userInfo.userId}");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}
	
	public String batchDelete(Map<String, Integer[]> ids){
		return new SQL(){
			{
				if(ids != null && ids.get("ids") != null){
					DELETE_FROM(RasConstants.PERSONAL_SETTINGS);
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
