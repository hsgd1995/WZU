package com.hxbd.clp.service;

import java.util.List;

import com.hxbd.clp.domain.Caption;

public interface CaptionService {
	/**
	 * 新增字幕
	 * @param caption
	 */
	void add(Caption caption);
	/**
	 * 根据videoId获取字幕
	 * @param videoId
	 * @return
	 */
	List<Caption> getByVideoId(Integer videoId);
	/**
	 * 根据videoId删除字幕
	 * @param videoId
	 */
	void deleteByVideoId(Integer videoId);
}
