package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import com.hxbd.clp.domain.SystemMessage;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 系统消息动态sql
 * @author Administrator
 *
 */
public class SystemMessageDynaSqlProvider {
	
	/**
	 * 插入
	 * @param systemMessage
	 * @return
	 */
	public String insert(SystemMessage systemMessage){
		return new SQL(){{
			if(systemMessage!=null){
				INSERT_INTO(RasConstants.SYSTEMMESSAGE);
				if(systemMessage.getUser()!=null&&systemMessage.getUser().getId()!=null){
					VALUES("user_id", "#{user.id}");
				}
				if(systemMessage.getTitle()!=null){
					VALUES("title", "#{title}");
				}
				if(systemMessage.getContent()!=null){
					VALUES("content", "#{content}");
				}
			}
		}}.toString();
	}
	
	/**
	 * 按检索-分页查找记录
	 * @param params
	 * @return
	 */
	public String selectByParma(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SystemMessage systemMessage = (SystemMessage) params.get("systemMessage");
				SELECT("s.* ");
				if (systemMessage != null) {
					if(systemMessage.getUser()!=null && systemMessage.getUser().getUsername()!=null){
						FROM(RasConstants.SYSTEMMESSAGE+" s,"+RasConstants.USERTABLE+" u ");
						
					}else{
						FROM(RasConstants.SYSTEMMESSAGE+" s ");
					}
				}else{
					FROM(RasConstants.SYSTEMMESSAGE+" s ");
				}
				
				ORDER_BY(" s.id desc ");
				if (systemMessage != null) {
					if (systemMessage.getTitle() != null && !"".equals(systemMessage.getTitle())) {
						WHERE(" title like CONCAT('%',#{systemMessage.title},'%')");
					}
					if(systemMessage.getUser()!=null && systemMessage.getUser().getUsername()!=null){
						WHERE(" s.user_id=u.id and u.username like CONCAT('%',#{systemMessage.user.username},'%')  ");
					}
					if (systemMessage.getSendTime() != null) {
						WHERE(" date_format(send_time,'%Y-%m-%d') = date_format(#{systemMessage.sendTime},'%Y-%m-%d')");
					}
					if(systemMessage.getUser()!=null&& !StringUtils.isEmpty(systemMessage.getUser().getId())){
						WHERE(" user_id = #{systemMessage.user.id}");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		System.err.println("sql->>"+sql);
		return sql;
	}

	/**
	 * 按检索-分页查找记录总数
	 * @param params
	 * @return
	 */
	public String count(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SystemMessage systemMessage = (SystemMessage) params.get("systemMessage");
				SELECT("count(*)");
				if (systemMessage != null) {
					if(systemMessage.getUser()!=null && systemMessage.getUser().getUsername()!=null){
						FROM(RasConstants.SYSTEMMESSAGE+" s,"+RasConstants.USERTABLE+" u ");
						
					}else{
						FROM(RasConstants.SYSTEMMESSAGE+" s ");
					}
				}else{
					FROM(RasConstants.SYSTEMMESSAGE+" s ");
				}
				ORDER_BY(" s.id desc ");
				
				if (systemMessage != null) {
					if (systemMessage.getTitle() != null && !systemMessage.getTitle().equals("")) {
						WHERE(" title like CONCAT('%',#{systemMessage.title},'%')");
					}
					if(systemMessage.getUser()!=null && systemMessage.getUser().getUsername()!=null){
						WHERE(" s.user_id=u.id and u.username like CONCAT('%',#{systemMessage.user.username},'%')  ");
					}
					if (systemMessage.getSendTime() != null) {
						WHERE(" date_format(send_time,'%Y-%m-%d') = date_format(#{systemMessage.sendTime},'%Y-%m-%d')");
					}
					if(systemMessage.getUser()!=null&& !StringUtils.isEmpty(systemMessage.getUser().getId())){
						WHERE(" user_id = #{systemMessage.user.id}");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		System.err.println("sql->>"+sql);
		return sql;
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
			s.append(RasConstants.SYSTEMMESSAGE).append(" where id in (");
			Integer[] ids = (Integer[]) params.get("ids");
			for (Integer integer : ids) {
				s.append(integer).append(",");
			}
			s.append(")");
			sql = s.toString().replace(",)", ")");
		}
		return sql;
	}
	
	/**
	 * 根据ids和userId删除记录
	 * @param params
	 * @return
	 */
	public String delByIdsAndUserId(Map<String, Object> params){
		String sql = "";
		if(params!=null&&params.get("ids")!=null){
			StringBuffer s = new StringBuffer("delete from ");
			s.append(RasConstants.SYSTEMMESSAGE).append(" where id in (");
			Integer[] ids = (Integer[]) params.get("ids");
			for (Integer integer : ids) {
				s.append(integer).append(",");
			}
			s.append(")");
			sql = s.toString().replace(",)", ")");
		}
		if(params!=null&&params.get("userId")!=null){
			sql+=" and user_id = "+params.get("userId");
		}
		return sql;
	}
}
