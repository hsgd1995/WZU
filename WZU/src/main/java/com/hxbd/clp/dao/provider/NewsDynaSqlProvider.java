package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.News;
import com.hxbd.clp.utils.common.RasConstants;

public class NewsDynaSqlProvider {

	public String selectByParma(Map<String, Object> params) {
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(RasConstants.NEWSTABLE);
				ORDER_BY("id desc");
				News news = (News) params.get("news");
				if(news != null){
					if(news.getNewsName()!= null && !news.getNewsName().equals("")){
						WHERE(" news_name like CONCAT('%',#{news.newsName},'%')");
					}
					if(news.getNewsType()!= null && !news.getNewsType().equals("")){
						WHERE(" news_type like CONCAT('%',#{news.newsType},'%')");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;	
	}

	public String countNews(Map<String, Object> params) {
		String sql =  new SQL(){
			{
				SELECT("count(*)");
				FROM(RasConstants.NEWSTABLE);
				News news = (News) params.get("news");
				if(news != null){
					if(news.getNewsName()!= null && !news.getNewsName().equals("")){
						WHERE(" news_name like CONCAT('%',#{news.newsName},'%')");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	public String addNews(News news) {
		return new SQL(){
			{
				if(news != null){
					INSERT_INTO(RasConstants.NEWSTABLE);
					if(news.getNewsName() != null && !news.getNewsName().equals("")){
						VALUES("news_name", "#{newsName}");
					}
					if(news.getContent() != null && !news.getContent().equals("")){
						VALUES("content", "#{content}");
					}
					if(news.getNewsPic() != null && !news.getNewsPic().equals("")){
						VALUES("news_pic","#{newsPic}");
					}
					if(news.getNewsType() != null && !news.getNewsType().equals("")){
						VALUES("news_type", "#{newsType}");
					}
					VALUES("create_date", "#{createDate}");
					VALUES("remark", "#{remark}");
				}
			}
		}.toString();
	}

	public String updateNews(News news) {
		return new SQL(){
			{
				if(news != null){
					UPDATE(RasConstants.NEWSTABLE);
					if(news.getNewsName() != null && !news.getNewsName().equals("")){
						SET("news_name = #{newsName}");
					}
					if(news.getContent() != null && !news.getContent().equals("")){
						SET("content = #{content}");
					}
					if(news.getNewsPic() !=null && !news.getNewsPic().equals("")){
						SET("news_pic = #{newsPic}");
					}
					if(news.getNewsType() !=null && !news.getNewsType().equals("")){
						SET("news_type = #{newsType}");
					}
					SET("create_date = #{createDate}");
					SET("remark = #{remark}");
					WHERE(" id = #{id} ");
				}
			}
		}.toString();
	}
	
	public String batchDelNews(Map<String, Integer[]> ids){
		return new SQL(){
			{
				if(ids != null && ids.get("ids") != null){
					DELETE_FROM(RasConstants.NEWSTABLE);
					String inConditions = "id in (";
					Integer[] idsArr = ids.get("ids");
					for (int index = 0; index < idsArr.length; index++) {
						inConditions += idsArr[index];
						if(index != idsArr.length - 1){
							inConditions += ",";
						}
					}
					inConditions +=") ";
					WHERE(inConditions);
				}
			}
		}.toString();
	}
}
