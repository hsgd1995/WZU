package com.hxbd.clp.dao;

import org.apache.ibatis.annotations.InsertProvider;

import com.hxbd.clp.dao.provider.ChannelDynaSqlProvider;
import com.hxbd.clp.domain.CreateSuccess;

public interface ChannelDao {
	
	/**
	 * 保存频道信息
	 * @param createSuccess
	 */
	@InsertProvider(type = ChannelDynaSqlProvider.class, method = "saveChannel")
	void saveChannel(CreateSuccess createSuccess);

}
