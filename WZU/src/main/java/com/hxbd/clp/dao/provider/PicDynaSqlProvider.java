package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.Pic;
import com.hxbd.clp.utils.common.RasConstants;


public class PicDynaSqlProvider {
	
	public String selectByParma(Map<String, Object> params){
		String sql = new SQL(){
			{
				SELECT("*");
				FROM(RasConstants.PICTABLE);
				Pic pic = (Pic) params.get("pic"); 
				if(pic != null){
					if(pic.getPicName() != null && !pic.getPicName().equals("")){
						WHERE(" pic_name like CONCAT('%',#{pic.picName},'%')");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}
	
	
	
	public String countPic(Map<String, Object> params){
		String sql = new SQL(){
			{
				SELECT("count(*)");
				FROM(RasConstants.PICTABLE);
				Pic pic = (Pic) params.get("pic"); 
				if(pic != null){
					if(pic.getPicName() != null && !pic.getPicName().equals("")){
						WHERE(" pic_name like CONCAT('%',#{pic.picName},'%')");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}
	
	public String savePic(Pic pic){
		return new SQL(){
			{
				INSERT_INTO(RasConstants.PICTABLE);
				if(pic != null){
					if(pic.getPicNo() != null && !pic.getPicNo().equals("")){
						VALUES("pic_no", "#{picNo}");
					}
					if(pic.getPicName() != null && !pic.getPicName().equals("")){
						VALUES("pic_name", "#{picName}");
					}
					if(pic.getPath() != null && !pic.getPath().equals("")){
						VALUES("path", "#{path}");
					}
					VALUES("pageview", "#{pageview}");
					VALUES("remark", "#{remark}");
				}
			}
		}.toString();
	}
	
	public String updatePic(Pic pic){
		return new SQL(){
			{
				UPDATE(RasConstants.PICTABLE);
				if(pic != null){
					if(pic.getPicNo() != null && !pic.getPicNo().equals("")){
						SET("pic_no = #{picNo}");
					}
					if(pic.getPicName() != null && !pic.getPicName().equals("")){
						SET("pic_name = #{picName}");
					}
					if(pic.getPath() != null && !pic.getPath().equals("")){
						SET("path = #{path}");
					}
					SET("pageview = #{pageview}");
					SET("remark = #{remark}");
					WHERE(" id = #{id} ");
				}
			}
		}.toString();
	}
	
	public String batchDelPic(Map<String, Integer[]> ids){
		return new SQL(){
			{
				if(ids != null && ids.get("ids") != null){
					DELETE_FROM(RasConstants.PICTABLE);
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
