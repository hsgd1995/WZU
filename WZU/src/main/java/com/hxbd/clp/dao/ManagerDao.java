package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.dao.provider.ManagerDynaSqlProvider;
import com.hxbd.clp.domain.Manager;
import com.hxbd.clp.utils.common.RasConstants;

public interface ManagerDao {
	
	@Select("select * from " + RasConstants.MANAGERTABLE + " where username = #{username} and password = #{password}")
	Manager selectManeger(@Param("username") String username ,@Param("password") String password);
	
	// 根据管理员id查询管理员 
	@Select("select * from " + RasConstants.MANAGERTABLE + " where id = #{id}")
	Manager selectById(Integer id);
	
	
	// 获取所有管理员
	@SelectProvider(type = ManagerDynaSqlProvider.class , method = "selectByParam")
	List<Manager> selectByParam(Map<String, Object> params);
	
	// 查询所有管理员总数
	@SelectProvider(type = ManagerDynaSqlProvider.class , method = "countManager")
	Integer countManager(Map<String, Object> params);
	
	// 保存管理员
	@InsertProvider(type = ManagerDynaSqlProvider.class , method = "saveManager")
	void saveManager(Manager manager);
	
	// 更新管理员
	@UpdateProvider(type = ManagerDynaSqlProvider.class , method = "updateManager")
	void updateManager(Manager manager);
	
	// 批量删除管理员
	@DeleteProvider(type = ManagerDynaSqlProvider.class , method = "bathDel")
	void batchDelManager(Map<String, Integer[]> ids);

	@Select(" select  id,username  from "+RasConstants.MANAGERTABLE+" where username <> 'admin' ")
	List<Manager> selectNoAdmin();
}
