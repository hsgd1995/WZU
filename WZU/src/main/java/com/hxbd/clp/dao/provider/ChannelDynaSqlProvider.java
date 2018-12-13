package com.hxbd.clp.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.CreateSuccess;
import com.hxbd.clp.utils.common.RasConstants;

public class ChannelDynaSqlProvider {
	public String saveChannel(CreateSuccess createSuccess){
		String sql = new SQL(){
			{
			INSERT_INTO(RasConstants.CHANNELTABLE);
			if(createSuccess != null){
				if(createSuccess.getCid() != null && createSuccess.getCid().equals("")){
					VALUES("cid", "#{cid}");
				}
				if(createSuccess.getCtime() != 0){
					VALUES("ctime", "#{ctime}");
				}
				if(createSuccess.getPushurl() != null && createSuccess.getPushurl().equals("")){
					VALUES("pushurl", "#{pushurl}");
				}
				if(createSuccess.getHttpPullUrl() != null && createSuccess.getHttpPullUrl().equals("")){
					VALUES("httpPullUrl", "#{httpPullUrl}");
				}
				if(createSuccess.getHlsPullUrl() !=null && createSuccess.equals("")){
					VALUES("hlsPullUrl", "#{hlsPullUrl}");
				}
				if(createSuccess.getRtmpPullUrl() != null && createSuccess.getRtmpPullUrl().equals("")){
					VALUES("rtmpPullUrl", "#{rtmpPullUrl}");
				}
				if(createSuccess.getName() != null && createSuccess.getName().equals("")){
					VALUES("name", "#{name}");
				}
				VALUES("code", "#{code}");
				VALUES("type", "#{type}");
			}
			}
			
		}.toString();
		return sql;		
	}
}
