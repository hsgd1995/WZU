package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.dao.provider.MessageDynaSqlProvider;
import com.hxbd.clp.domain.Message;
import com.hxbd.clp.utils.common.RasConstants;


/**
 * 消息Dao层
 * @author Administrator
 *
 */
public interface MessageDao {
	
		/**
		 * 检索-分页查询记录
		 * @param parmas
		 * @return
		 */
		@SelectProvider(type = MessageDynaSqlProvider.class, method = "selectByParma")
		@Results(value = {
				@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
				@Result(column = "create_time",property = "createTime" ,javaType = java.util.Date.class),
				@Result(column = "from_name",property = "fromName" ,javaType = java.lang.String.class),
				@Result(column = "create_manager_id",property = "creater" ,javaType = com.hxbd.clp.domain.Manager.class,
				one = @One(select="com.hxbd.clp.dao.ManagerDao.selectById")),
				@Result(column="course_id",property="course",javaType=com.hxbd.clp.domain.Course.class,
				one = @One(select="com.hxbd.clp.dao.CourseDao.selectById"))
		})
		List<Message> selectByPage(Map<String, Object> parmas);
		
		/**
		 * 检索-分页查询记录总数
		 * @param parmas
		 * @return
		 */
		@SelectProvider(type = MessageDynaSqlProvider.class, method = "countMessage")
		Integer count(Map<String, Object> parmas);

		/**
		 * 根据id查询记录
		 * @param id
		 * @return
		 */
		@Select("select * from "+RasConstants.MESSAGETABLE+" where id = #{id}")
		@Results(value = {
				@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
				@Result(column = "create_time",property = "createTime" ,javaType = java.util.Date.class),
				@Result(column = "from_name",property = "fromName" ,javaType = java.lang.String.class),
				@Result(column = "create_manager_id",property = "creater" ,javaType = com.hxbd.clp.domain.Manager.class,
				one = @One(select="com.hxbd.clp.dao.ManagerDao.selectById")),
				@Result(column="course_id",property="course",javaType=com.hxbd.clp.domain.Course.class,
				one = @One(select="com.hxbd.clp.dao.CourseDao.selectById"))
		})
		Message selectById(@Param("id") Integer id);

		/**
		 * 插入数据
		 * @param message
		 */
		@SelectProvider(type=MessageDynaSqlProvider.class,method="insert")
		void insert(Message message);

		/**
		 * 更新数据
		 * @param message
		 */
		@UpdateProvider(type=MessageDynaSqlProvider.class,method="update")
		void update(Message message);

		/**
		 * 批量删除
		 * @param params
		 */
		@DeleteProvider(type=MessageDynaSqlProvider.class,method="batchDel")
		void batchDel(Map<String, Object> params);
}
