package com.hxbd.clp.dao.provider;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import com.hxbd.clp.domain.User;
import com.hxbd.clp.utils.common.RasConstants;

public class UserDynaSqlProvider {
	
	public String selectByParam(Map<String, Object> params){
		String sql = new SQL(){
			{
				SELECT("*");
				FROM(RasConstants.USERTABLE);
				User user = (User) params.get("user");
				ORDER_BY("id Desc");
				if(user != null){
					if(user.getLoginName() != null && !user.getLoginName().equals("")){
						WHERE(" login_name like CONCAT('%',#{user.loginName},'%')");
					}
					if(user.getId() != null && !user.getId().equals("")){
						WHERE(" id like CONCAT('%',#{user.id},'%')");
					}
					if(user.getUsername() != null && !user.getUsername().equals("")){
						WHERE(" username like CONCAT('%',#{user.username},'%')");
					}
					if(user.getCardId() != null && !user.getCardId().equals("")){
						WHERE(" card_id like CONCAT('%',#{user.cardId},'%')");
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
				FROM(RasConstants.USERTABLE);
				User user = (User) params.get("user");
				if(user != null){
					if(user.getLoginName() != null && !user.getLoginName().equals("")){
						WHERE(" login_name like CONCAT('%',#{user.loginName},'%')");
					}
					if(user.getId() != null && !user.getId().equals("")){
						WHERE(" id like CONCAT('%',#{user.id},'%')");
					}
					if(user.getUsername() != null && !user.getUsername().equals("")){
						WHERE(" username like CONCAT('%',#{user.username},'%')");
					}
					if(user.getCardId() != null && !user.getCardId().equals("")){
						WHERE(" card_id like CONCAT('%',#{user.cardId},'%')");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}
	
	public String saveUser(User user){
		String sql =  new SQL(){
			{
				INSERT_INTO(RasConstants.USERTABLE);
				if(user != null){
					if(user.getLoginName() != null && !user.getLoginName().equals("")){
						VALUES("login_name", "#{loginName}");
					}
					if(user.getUsername() != null && !user.getUsername().equals("")){
						VALUES("username", "#{username}");
					}
					if(user.getPassword() != null && !user.getPassword().equals("")){
						VALUES("password", "#{password}");
					}
					if(user.getPhoneNumber() != null && !user.getPhoneNumber().equals("")){
					//	VALUES("phone_number", "#{phoneNumber}");
					}
					if(user.getEmail() != null && !user.getEmail().equals("")){
						VALUES("email", "#{email}");
					}
/*					if(user.getCardId() != null){
						VALUES("card_id", "#{cardId}");
					}*/
					if(user.getArea() != null && !user.getArea().equals("")){
						VALUES("area", "#{area}");
					}
					if(user.getUserPic() != null && !user.getUserPic().equals("")){
						VALUES("user_pic", "#{userPic}");
					}
					if(!StringUtils.isEmpty(user.getRemark())){
						VALUES("remark", "#{remark}");
					}
					VALUES("card_id", "#{cardId}");
					VALUES("login_time", "#{loginTime}");
					VALUES("logout_time", "#{logoutTime}");
				}
			}
		}.toString();
		return sql;
	}
	
	public String updateUser(User user){
		String sql =  new SQL(){
			{
				UPDATE(RasConstants.USERTABLE);
				if(user != null){
					if(user.getLoginName() != null && !user.getLoginName().equals("")){
						SET("login_name = #{loginName}");
					}
					if(user.getUsername() != null && !user.getUsername().equals("")){
						SET("username = #{username}");
					}
					if(user.getPassword() != null && !user.getPassword().equals("")){
						SET("password = #{password}");
					}
					if(user.getPhoneNumber() != null && !user.getPhoneNumber().equals("")){
						SET("phone_number = #{phoneNumber}");
					}
					if(user.getEmail() != null && !user.getEmail().equals("")){
						SET("email = #{email}");
					}
/*					if(user.getCardId() != null && !user.getCardId().equals("")){
						SET("card_id = #{cardId}");
					}*/
					if(user.getArea() != null && !user.getArea().equals("")){
						SET("area = #{area}");
					}
					if(user.getUserPic() != null && !user.getUserPic().equals("")){
						SET("user_pic = #{userPic}");
					}
					if(user.getRemark() != null && user.getRemark().equals("")){
						SET("remark = #{remark}");
					}
						SET("card_id = #{cardId}");
						SET("subscribe = #{subscribe}");
						SET("login_time = #{loginTime}");
						SET("logout_time = #{logoutTime}");
					WHERE(" id = #{id} ");
				}
			}
		}.toString();
		System.err.println(sql);
		return sql;
	}
	
	public String bathDel(Map<String, Integer[]> ids){
		return new SQL(){
			{
				if(ids != null && ids.get("ids") != null){
					DELETE_FROM(RasConstants.USERTABLE);
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
	
	public String batchUpdate(Map<String, List<String>> params){
		List<String> list = params.get("list");
		String newRemark="bbb";
		String sql = "";
		sql += " UPDATE user_table SET remark = '"+newRemark+"' WHERE email in(";
		for (String string : list) {
			sql += "'"+string+"'"+",";
		}
		sql += ")";
		sql = sql.replace(",)", ")");
		System.out.println(sql);
		return sql;
	}
}
