package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import com.hxbd.clp.domain.MessageAndUser;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 发送消息动态sql类
 * @author Administrator
 *
 */
public class MessageAndUserDynaSqlProvider {

	/**
	 * 按检索条件和分页条件查询记录总数
	 * @param params
	 * @return
	 */
	public String count(Map<String, Object> params) {
		String sql =  new SQL(){
			{
				MessageAndUser messageAndUser = (MessageAndUser) params.get("messageAndUser");
				SELECT("count(*)");
				String dynaSql = RasConstants.MESSAGEANDUSERTABLE+" s ";
				if (messageAndUser != null) {
					if(messageAndUser.getUser()!=null && !StringUtils.isEmpty(messageAndUser.getUser().getUsername())){
						dynaSql += " , " + RasConstants.USERTABLE+" u ";
					}
					if(messageAndUser.getMessage()!=null && !StringUtils.isEmpty(messageAndUser.getMessage().getTitle())){
						dynaSql += " , " + RasConstants.MESSAGETABLE + " m";
					}
				}
				FROM(dynaSql);
				
				if(messageAndUser != null){
					if(messageAndUser.getStatus()!= null){
						WHERE(" status = #{messageAndUser.status}");
					}
					if(messageAndUser.getUser()!=null&& !StringUtils.isEmpty(messageAndUser.getUser().getId())){
						WHERE(" user_id = #{messageAndUser.user.id}");
					}
					if(messageAndUser.getUser()!=null && !StringUtils.isEmpty(messageAndUser.getUser().getUsername())){
						WHERE(" s.user_id=u.id and u.username like CONCAT('%',#{messageAndUser.user.username},'%')  ");
					}
					if(messageAndUser.getMessage()!=null && !StringUtils.isEmpty(messageAndUser.getMessage().getTitle())){
						WHERE(" s.message_id=m.id and m.title like  CONCAT('%',#{messageAndUser.message.title},'%') ");
					}
					if (messageAndUser.getSendTime() != null) {
						WHERE(" date_format(send_time,'%Y-%m-%d') = date_format(#{messageAndUser.sendTime},'%Y-%m-%d')");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		System.err.println(sql);
		return sql;
	}
	
	/**
	 * 按检索条件和分页条件查询记录
	 * @param params
	 * @return
	 */
	public String selectByPage(Map<String, Object> params) {
		String sql =  new SQL(){
			{
				MessageAndUser messageAndUser = (MessageAndUser) params.get("messageAndUser");
				SELECT("s.*");
				String dynaSql = RasConstants.MESSAGEANDUSERTABLE+" s ";
				if (messageAndUser != null) {
					if(messageAndUser.getUser()!=null && !StringUtils.isEmpty(messageAndUser.getUser().getUsername())){
						dynaSql += " , " + RasConstants.USERTABLE+" u ";
					}
					if(messageAndUser.getMessage()!=null && !StringUtils.isEmpty(messageAndUser.getMessage().getTitle())){
						dynaSql += " , " + RasConstants.MESSAGETABLE + " m";
					}
				}
				FROM(dynaSql);
				ORDER_BY("s.id desc");
				if(messageAndUser != null){
					if(messageAndUser.getStatus()!= null){
						WHERE(" status = #{messageAndUser.status}");
					}
					if(messageAndUser.getUser()!=null&& !StringUtils.isEmpty(messageAndUser.getUser().getId())){
						WHERE(" user_id = #{messageAndUser.user.id}");
					}
					if(messageAndUser.getUser()!=null && !StringUtils.isEmpty(messageAndUser.getUser().getUsername())){
						WHERE(" s.user_id=u.id and u.username like CONCAT('%',#{messageAndUser.user.username},'%')  ");
					}
					if(messageAndUser.getMessage()!=null && !StringUtils.isEmpty(messageAndUser.getMessage().getTitle())){
						WHERE(" s.message_id=m.id and m.title like  CONCAT('%',#{messageAndUser.message.title},'%') ");
					}
					if (messageAndUser.getSendTime() != null) {
						WHERE(" date_format(send_time,'%Y-%m-%d') = date_format(#{messageAndUser.sendTime},'%Y-%m-%d')");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		System.err.println(sql);
		return sql;	
	}
	
	/**
	 * 插入数据
	 * @param messageAndUser
	 * @return
	 */
	public String insert(MessageAndUser messageAndUser){
		return new SQL(){{
			if(messageAndUser!=null){
				INSERT_INTO(RasConstants.MESSAGEANDUSERTABLE);
				if(messageAndUser.getMessage()!=null&&messageAndUser.getMessage().getId()!=null){
					VALUES("message_id", "#{message.id}");
				}
				if(messageAndUser.getUser()!=null&&messageAndUser.getUser().getId()!=null){
					VALUES("user_id", "#{user.id}");
				}
			}
		}}.toString();
	}
	
	/**
	 * 批量删除
	 * @param params
	 * @return
	 */
	public String batchDel(Map<String, Object> params){
		String sql = "";
		if(params!=null&&params.get("ids")!=null){
			StringBuffer s = new StringBuffer("delete from ");
			s.append(RasConstants.MESSAGEANDUSERTABLE).append(" where id in (");
			Integer[] ids = (Integer[]) params.get("ids");
			for (Integer integer : ids) {
				s.append(integer).append(",");
			}
			s.append(")");
			sql = s.toString().replace(",)", ")");
		}
		return sql;
	}
}
