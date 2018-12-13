package com.hxbd.clp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.dao.NewsDao;
import com.hxbd.clp.domain.News;
import com.hxbd.clp.service.NewsService;
import com.hxbd.clp.utils.tag.PageModel;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service("newsService")
public class NewsServiceImpl implements NewsService{

	@Autowired
	private NewsDao newsDao;
	
	@Transactional(readOnly = true)
	@Override
	public News selectById(Integer newsId) {
		return newsDao.selectById(newsId);
	}

	@Transactional(readOnly = true)
	@Override
	public PageModel<News> findNews(News news, PageModel<News> pageModel) {
		Map<String, Object> params = new HashMap<>();
		params.put("news", news);
		Integer recordCount = newsDao.count(params);
		if(recordCount > 0){
			pageModel.setRecordCount(recordCount);
			params.put("pageModel",pageModel);
		}
		
		// 查询分页记录
		List<News> list  = newsDao.selectByPage(params);
		pageModel.setList(list);
		return pageModel;
	}
	
	@Override
	public Integer addNews(News news) {
		newsDao.addNews(news);
		return news.getId();
	}
	
	@Override
	public void modifyNews(News news) {
		newsDao.updateNews(news);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<News> getNewsByPage(Map<String, Object> parmas) {
		return newsDao.selectByPage(parmas);
	}

	@Override
	public void batchDelNews(Integer[] ids) {
		Map<String, Integer[]> map = new HashMap<>();
		map.put("ids", ids);
		newsDao.batchDelete(map);
	}

}
