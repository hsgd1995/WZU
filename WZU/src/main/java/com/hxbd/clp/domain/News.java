package com.hxbd.clp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.hxbd.clp.java.ANote;

public class News implements Serializable {
	
	private static final long serialVersionUID = 8725458955381833708L;
	
	@ANote(value="新闻ID")
	private Integer id;
	
	@ANote(value="新闻标题")
	private String newsName; 
	
	@ANote(value="新闻内容")
	private String content;
	
	@ANote(value="新闻创建时间")
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date createDate;
	
	@ANote(value="新闻图片")
	private String newsPic;
	
	@ANote(value="新闻类型")
	private Integer  newsType;
	
	@ANote(value="标记")
	private String remark;
	
	//一对多
	private List<NewsPic> newPicList;
	
	public News() {
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNewsName() {
		return newsName;
	}

	public void setNewsName(String newsName) {
		this.newsName = newsName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getNewsPic() {
		return newsPic;
	}

	public void setNewsPic(String newsPic) {
		this.newsPic = newsPic;
	}

	public Integer getNewsType() {
		return newsType;
	}

	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<NewsPic> getNewPicList() {
		return newPicList;
	}

	public void setNewPicList(List<NewsPic> newPicList) {
		this.newPicList = newPicList;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", newsName=" + newsName + ", content=" + content + ", createDate=" + createDate
				+ ", newsPic=" + newsPic + ", newsType=" + newsType + ", remark=" + remark + ", newPicList="
				+ newPicList + "]";
	}

}
