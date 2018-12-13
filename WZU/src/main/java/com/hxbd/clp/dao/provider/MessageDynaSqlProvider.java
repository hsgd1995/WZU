package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.Message;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 消息动态语句
 * 
 * @author Administrator
 *
 */
public class MessageDynaSqlProvider {
	public String selectByParma(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(RasConstants.MESSAGETABLE);
				ORDER_BY("id desc");
				Message message = (Message) params.get("message");
				if (message != null) {
					if (message.getTitle() != null && !message.getTitle().equals("")) {
						WHERE(" title like CONCAT('%',#{message.title},'%')");
					}
					if (message.getContent() != null && !message.getContent().equals("")) {
						WHERE(" content like CONCAT('%',#{message.content},'%')");
					}
					if (message.getType() != null) {
						WHERE(" type = #{message.type}");
					}
					if (message.getCreateTime() != null) {
						WHERE(" date_format(create_time,'%Y-%m-%d') = date_format(#{message.createTime},'%Y-%m-%d')");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	public String countMessage(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("count(*)");
				FROM(RasConstants.MESSAGETABLE);
				Message message = (Message) params.get("message");
				if (message != null) {
					if (message.getTitle() != null && !message.getTitle().equals("")) {
						WHERE(" title like CONCAT('%',#{message.title},'%')");
					}
					if (message.getContent() != null && !message.getContent().equals("")) {
						WHERE(" content like CONCAT('%',#{message.content},'%')");
					}
					if (message.getType() != null) {
						WHERE(" type = #{message.type}");
					}
					if (message.getCreateTime() != null) {
						WHERE(" date_format(create_time,'%Y-%m-%d') = date_format(#{message.createTime},'%Y-%m-%d')");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	/**
	 * 插入数据
	 * @param message
	 * @return
	 */
	public String insert(Message message){
		return new SQL(){{
			if(message!=null){
				INSERT_INTO(RasConstants.MESSAGETABLE);
				if(message.getTitle()!=null){
					VALUES("title", "#{title}");
				}
				if(message.getContent()!=null){
					VALUES("content", "#{content}");
				}
				if(message.getCourse()!=null&&message.getCourse().getId()!=null){
					VALUES("course_id", "#{course.id}");
				}
				if(message.getCreater()!=null&&message.getCreater().getId()!=null){
					VALUES("create_manager_id", "#{creater.id}");
				}
				if(message.getType()!=null){
					VALUES("type", "#{type}");
				}
				if(message.getFromName()!=null){
					VALUES("form_name", "#{formName}");
				}
			}
		}}.toString();
	}
	
	/**
	 * 更新数据
	 * @param message
	 * @return
	 */
	public String update(Message message){
		return new SQL(){{
			if(message!=null){
				UPDATE(RasConstants.MESSAGETABLE);
				if(message.getTitle()!=null){
					SET("title = #{title}");
				}
				if(message.getContent()!=null){
					SET("content = #{content}");
				}
				WHERE("id = #{id}");
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
			s.append(RasConstants.MESSAGETABLE).append(" where id in (");
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
