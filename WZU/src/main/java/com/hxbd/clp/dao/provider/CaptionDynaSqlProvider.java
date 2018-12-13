package com.hxbd.clp.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.Caption;
import com.hxbd.clp.utils.common.RasConstants;

public class CaptionDynaSqlProvider {
	
	public String insertCaption(Caption caption){
		return new SQL(){{
			if(caption!=null){
				INSERT_INTO(RasConstants.CAPTION);
				if(caption.getCaptionName()!=null){
					VALUES("caption_name", "#{captionName}");
				}
				if(caption.getVideoId()!=null){
					VALUES("video_id", "#{videoId}");
				}
			}
			
		}}.toString();
	}

}
