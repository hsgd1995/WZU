package com.hxbd.clp.domain;

import java.io.Serializable;

/**
 * 字幕类
 * @author hxcl
 *
 */
public class Caption implements Serializable{
	
	private Integer id;
	private String  captionName;//字幕文件名
	private Integer videoId;//视频id
	
	public Caption() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCaptionName() {
		return captionName;
	}

	public void setCaptionName(String captionName) {
		this.captionName = captionName;
	}

	public Integer getVideoId() {
		return videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	@Override
	public String toString() {
		return "Caption [id=" + id + ", captionName=" + captionName + ", videoId=" + videoId + "]";
	}
	
	
}
