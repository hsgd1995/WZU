package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.hxbd.clp.dao.provider.MessageAndUserDynaSqlProvider;
import com.hxbd.clp.domain.MessageAndUser;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 发送消息Dao
 *
 */
public interface MessageAndUserDao {

	/**
	 * 按检索条件和分页条件查询记录总数
	 * @param params
	 * @return
	 */
	@SelectProvider(type=MessageAndUserDynaSqlProvider.class,method="count")
	int count(Map<String, Object> params);

	/**
	 * 按检索条件和分页条件查询记录
	 * @param params
	 * @return
	 */
	@SelectProvider(type=MessageAndUserDynaSqlProvider.class,method="selectByPage")
	@Results({
		@Result(column="message_id",property="message",javaType=com.hxbd.clp.domain.Message.class,
				one=@One(select="com.hxbd.clp.dao.MessageDao.selectById")),
		@Result(column="user_id",property="user",javaType=com.hxbd.clp.domain.User.class,
		one=@One(select="com.hxbd.clp.dao.UserDao.selectById")),
		@Result(column="is_clear",property="isClear",javaType=java.lang.Integer.class),
		@Result(column="send_time",property="sendTime",javaType= java.util.Date.class)
	})
	List<MessageAndUser> selectByPage(Map<String, Object> params);

	/**
	 * 根据id查询记录
	 * @param messageAndUser
	 * @return
	 */
	@Select("select * from "+RasConstants.MESSAGEANDUSERTABLE+" where id = #{id}")
	@Results({
		@Result(column="message_id",property="message",javaType=com.hxbd.clp.domain.MessageAndUser.class,
				one=@One(select="com.hxbd.clp.dao.MessageDao.selectById")),
		@Result(column="user_id",property="user",javaType=com.hxbd.clp.domain.User.class,
		one=@One(select="com.hxbd.clp.dao.UserDao.selectById")),
		@Result(column="is_clear",property="isClear",javaType=java.lang.Integer.class),
		@Result(column="send_time",property="sendTime",javaType= java.util.Date.class)
	})
	MessageAndUser selectById(Integer id);
	
	/**
	 * 插入数据
	 * @param message
	 */
	@SelectProvider(type=MessageAndUserDynaSqlProvider.class,method="insert")
	void insert(MessageAndUser messageAndUser);
	
	/**
	 * 批量删除
	 * @param params
	 */
	@DeleteProvider(type=MessageAndUserDynaSqlProvider.class,method="batchDel")
	void batchDel(Map<String, Object> params);

	@Select("select * from "+RasConstants.MESSAGEANDUSERTABLE+" where user_id = #{userId}")
	@Results({
		@Result(column="message_id",property="message",javaType=com.hxbd.clp.domain.MessageAndUser.class,
				one=@One(select="com.hxbd.clp.dao.MessageDao.selectById")),
		@Result(column="user_id",property="user",javaType=com.hxbd.clp.domain.User.class,
		one=@One(select="com.hxbd.clp.dao.UserDao.selectById")),
		@Result(column="is_clear",property="isClear",javaType=java.lang.Integer.class),
		@Result(column="send_time",property="sendTime",javaType= java.util.Date.class)
	})
	List<MessageAndUser> selectByUserId(Integer userId);

	/**
	 * 更新用户的全部信息状态为已读
	 * @param userId
	 */
	@Update("update "+RasConstants.MESSAGEANDUSERTABLE+" set status = 1 where user_id = #{userId}")
	void updateStatusByUserId(Integer userId);

	/**
	 * 删除某用户的全部消息
	 * @param userId
	 */
	@Delete("delete from "+RasConstants.MESSAGEANDUSERTABLE+" where user_id = #{userId} ")
	void delMessageByUserId(Integer userId);

	/**
	 * 已读
	 * @param messageId
	 * @param userId
	 */
	@Update("update "+RasConstants.MESSAGEANDUSERTABLE+" set status = 1 where id = #{id}")
	void updateStatusById(@Param("id") Integer id);
}
