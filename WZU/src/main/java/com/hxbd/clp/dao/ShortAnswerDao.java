package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.dao.provider.ShortAnswerDynaSqlProvider;
import com.hxbd.clp.domain.ShortAnswer;
import com.hxbd.clp.utils.common.RasConstants;

/**
 * 简答题 数据访问接口
 * @author Administrator
 *
 */
public interface ShortAnswerDao {
	/**
	 * 检索-分页查询记录
	 * @param parmas
	 * @return
	 */
	@SelectProvider(type = ShortAnswerDynaSqlProvider.class, method = "selectByParma")
	List<ShortAnswer> selectByPage(Map<String, Object> parmas);
	
	/**
	 * 检索-分页查询记录总数
	 * @param parmas
	 * @return
	 */
	@SelectProvider(type = ShortAnswerDynaSqlProvider.class, method = "count")
	Integer count(Map<String, Object> parmas);

	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	@Select("select * from "+RasConstants.SATABLE+" where id = #{id}")
	ShortAnswer selectById(@Param("id") Integer id);

	/**
	 * 插入数据
	 * @param sa
	 */
	@SelectProvider(type=ShortAnswerDynaSqlProvider.class,method="insert")
	void insert(ShortAnswer sa);

	/**
	 * 更新数据
	 * @param sa
	 */
	@UpdateProvider(type=ShortAnswerDynaSqlProvider.class,method="update")
	void update(ShortAnswer sa);

	/**
	 * 批量删除
	 * @param params
	 */
	@DeleteProvider(type=ShortAnswerDynaSqlProvider.class,method="batchDel")
	void batchDel(Map<String, Object> params);
}
