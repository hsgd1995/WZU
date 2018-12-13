package com.hxbd.clp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxbd.clp.dao.ChannelDao;
import com.hxbd.clp.domain.CreateSuccess;
import com.hxbd.clp.service.ChannelService;

@Service("channelService")
public class ChannelServiceImpl implements ChannelService{
	@Autowired
	private ChannelDao channelDao;
	@Override
	public void saveChannel(CreateSuccess createSuccess) {
		channelDao.saveChannel(createSuccess);
	}

}
