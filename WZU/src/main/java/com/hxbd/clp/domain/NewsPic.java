package com.hxbd.clp.domain;

import java.io.Serializable;

public class NewsPic implements Serializable {
	
	private static final long serialVersionUID = 3670624245708675604L;
	
	private Integer id;
	private String url; //图片url
	private News news;//新闻id
	
	public NewsPic() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	@Override
	public String toString() {
		return "NewsPic [id=" + id + ", url=" + url + ", news=" + news + "]";
	}

	

}
