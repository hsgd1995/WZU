package com.hxbd.clp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hxbd.clp.dao.provider.CaptionDynaSqlProvider;
import com.hxbd.clp.domain.Caption;
import com.hxbd.clp.utils.common.RasConstants;

public interface CaptionDao {
	/**
	 * 根据videoId查询字幕
	 * @param id
	 * @return
	 */
	@Select("select * from "+RasConstants.CAPTION +" where video_id = #{id}")
	@Results({
		@Result(column="id",property="id",javaType=java.lang.Integer.class),
		@Result(column="caption_name",property="captionName",javaType=java.lang.String.class),
		@Result(column="video_id",property="videoId",javaType=java.lang.Integer.class)
	})
	List<Caption> selectByVideoId(Integer id);
	
	/**
	 * 新增字幕
	 * @param caption
	 */
	@InsertProvider(type=CaptionDynaSqlProvider.class,method="insertCaption")
	void addCaption(Caption caption);
	
	/**
	 * 根据videoId删除字幕
	 * @param id
	 */
	@Delete("delete from "+RasConstants.CAPTION+" where video_id = #{id}")
	void deleteByVideoId(Integer id);
}
