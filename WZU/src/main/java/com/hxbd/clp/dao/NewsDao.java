package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.dao.provider.NewsDynaSqlProvider;
import com.hxbd.clp.domain.News;
import com.hxbd.clp.utils.common.RasConstants;

public interface NewsDao {
	
	//根据id查询新闻
	@Select("select * from " + RasConstants.NEWSTABLE + " where id = #{id}")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "news_name",property = "newsName" ,javaType = java.lang.String.class),
			@Result(column = "create_date",property = "createDate" ,javaType = java.util.Date.class),
			@Result(column = "news_pic",property = "newsPic" ,javaType = java.lang.String.class),
			@Result(column = "news_type",property = "newsType" ,javaType = java.lang.Integer.class)
	})
	News selectById(Integer id);
	
	// 分页查询新闻
	@SelectProvider(type = NewsDynaSqlProvider.class, method = "selectByParma")
	@Results(value = {
			@Result(column = "id",property = "id" ,javaType = java.lang.Integer.class),
			@Result(column = "news_name",property = "newsName" ,javaType = java.lang.String.class),
			@Result(column = "create_date",property = "createDate" ,javaType = java.util.Date.class),
			@Result(column = "news_pic",property = "newsPic" ,javaType = java.lang.String.class),
			@Result(column = "news_type",property = "newsType" ,javaType = java.lang.Integer.class)
	})
	List<News> selectByPage(Map<String, Object> parmas);
	
	// 查询新闻总数
	@SelectProvider(type = NewsDynaSqlProvider.class, method = "countNews")
	Integer count(Map<String, Object> parmas);
	
	// 新增新闻
	@InsertProvider(type = NewsDynaSqlProvider.class, method = "addNews")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void addNews(News news);
	
	// 更新新闻
	@UpdateProvider(type = NewsDynaSqlProvider.class, method = "updateNews")
	void updateNews(News news);
	
	// 根据id删除新闻
	@DeleteProvider(type = NewsDynaSqlProvider.class, method = "batchDelNews")
	void batchDelete(Map<String, Integer[]>  ids);
	
	
}
