package com.hxbd.clp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxbd.clp.dao.CaptionDao;
import com.hxbd.clp.domain.Caption;
import com.hxbd.clp.service.CaptionService;
@Service("captionService")
public class CaptionServiceImpl implements CaptionService{
	
	@Autowired
	private CaptionDao captionDao;

	@Override
	public void add(Caption caption) {
		captionDao.addCaption(caption);
	}

	@Override
	public List<Caption> getByVideoId(Integer videoId) {
		return captionDao.selectByVideoId(videoId);
	}

	@Override
	public void deleteByVideoId(Integer videoId) {
		captionDao.deleteByVideoId(videoId);
	}
	
}
