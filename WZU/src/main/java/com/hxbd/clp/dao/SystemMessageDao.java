package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.hxbd.clp.dao.provider.SystemMessageDynaSqlProvider;
import com.hxbd.clp.domain.SystemMessage;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 系统消息Dao
 * @author Administrator
 *
 */
public interface SystemMessageDao {
	/**
	 * 插入
	 * @param systemMessage
	 */
	@InsertProvider(type=SystemMessageDynaSqlProvider.class,method="insert")
	void insert(SystemMessage systemMessage);
	
	
	/**
	 * 检索-分页查询记录
	 * @param parmas
	 * @return
	 */
	@SelectProvider(type = SystemMessageDynaSqlProvider.class, method = "selectByParma")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column="user_id",property="user",javaType=com.hxbd.clp.domain.User.class,
			one=@One(select="com.hxbd.clp.dao.UserDao.selectById")),
			@Result(column="send_time",property="sendTime",javaType=java.util.Date.class)
	})
	List<SystemMessage> selectByPage(Map<String, Object> parmas);
	
	/**
	 * 检索-分页查询记录总数
	 * @param parmas
	 * @return
	 */
	@SelectProvider(type = SystemMessageDynaSqlProvider.class, method = "count")
	Integer count(Map<String, Object> parmas);

	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	@Select("select * from "+RasConstants.SYSTEMMESSAGE+" where id = #{id}")
	@Results(value = {
					@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
					@Result(column="user_id",property="user",javaType=com.hxbd.clp.domain.User.class,
					one=@One(select="com.hxbd.clp.dao.UserDao.selectById")),
					@Result(column="send_time",property="sendTime",javaType=java.util.Date.class)
	})
	SystemMessage selectById(@Param("id") Integer id);
	
	/**
	 * 批量删除
	 * @param params
	 */
	@DeleteProvider(type=SystemMessageDynaSqlProvider.class,method="batchDel")
	void batchDel(Map<String, Object> params);


	/**
	 * 已读
	 * @param id
	 */
	@Update("update "+RasConstants.SYSTEMMESSAGE+" set status = 1 where id = #{id} and user_id=#{userId}")
	void updateStatusById(@Param("id") Integer id,@Param("userId") Integer userId);

	/**
	 * 更新用户的全部信息状态为已读
	 * @param userId
	 */
	@Update("update "+RasConstants.SYSTEMMESSAGE+" set status = 1 where user_id = #{userId}")
	void updateStatusByUserId(Integer userId);


	/**
	 * 删除某用户的全部消息
	 * @param userId
	 */
	@Delete("delete from "+RasConstants.SYSTEMMESSAGE+" where user_id = #{userId} ")
	void delMessageByUserId(Integer userId);


	/**
	 * 根据ids和userId删除记录
	 * @param params
	 */
	@DeleteProvider(type=SystemMessageDynaSqlProvider.class,method="delByIdsAndUserId")
	void delByIdsAndUserId(Map<String, Object> params);
}
