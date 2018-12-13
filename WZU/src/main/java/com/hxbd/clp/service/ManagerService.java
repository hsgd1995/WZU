package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.Manager;
import com.hxbd.clp.utils.tag.PageModel;

public interface ManagerService {
	
	Manager validateManager(String username , String password);
	
	// 保存管理员
	void saveManager(Manager manager);
	// 更新管理员
	void updateManager(Manager manager);
	// 批量删除管理员
	void batchDelManager(Integer[] ids);
	// 根据id查询管理员
	Manager selectById(Integer id);
	// 分页查询管理员
	PageModel<Manager> findManager(Manager manager,PageModel<Manager> pageModel);

	/**
	 * 获取非admin的管理员
	 * @return
	 */
	List<Manager> getOrdinaryManager();
}
