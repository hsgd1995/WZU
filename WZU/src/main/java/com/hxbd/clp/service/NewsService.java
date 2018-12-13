package com.hxbd.clp.service;

import java.util.List;
import java.util.Map;

import com.hxbd.clp.domain.News;
import com.hxbd.clp.utils.tag.PageModel;


public interface NewsService {
	
	// 根据新闻id查询新闻
	News selectById(Integer newsId);
	// 动态查询新闻
	PageModel<News> findNews(News news, PageModel<News> pageModel);
	// 分页查询获取新闻
	List<News> getNewsByPage(Map<String, Object> parmas);
	// 新增新闻
	Integer addNews(News news);
	// 更新新闻
	void modifyNews(News news);
	// 根据新闻id删除新闻
	void batchDelNews(Integer[] ids);
}
