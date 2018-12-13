package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.hxbd.clp.dao.provider.PicDynaSqlProvider;
import com.hxbd.clp.domain.Pic;
import com.hxbd.clp.utils.common.RasConstants;

public interface PicDao {
	
	/**
	 * 根据id查询图片
	 * 
	 * @param id
	 * @return
	 */
	@Select("select * from " + RasConstants.PICTABLE + " where id = #{id}")
	@Results(value = {@Result(column = "pic_name" , property = "picName" , javaType = java.lang.String.class),
			@Result(column = "pic_no" , property = "picNo", javaType = java.lang.String.class)})
	Pic selectById(Integer id); 
	
	/**
	 * 根据图片编号查询图片
	 * 
	 * @param picNo
	 * @return
	 */
	@Select("select * from " + RasConstants.PICTABLE + " where pic_no = #{picNo}")
	@Results(value = {@Result(column = "pic_name" , property = "picName" , javaType = java.lang.String.class),
			@Result(column = "pic_no" , property = "picNo", javaType = java.lang.String.class)})
	Pic selectByPicNo(String picNo); 
	
	/**
	 * 根据图片名查询图片
	 * 
	 * @param picName
	 * @return
	 */
	@Select("select * from " + RasConstants.PICTABLE + " where pic_name = #{picName}")
	@Results(value = {@Result(column = "pic_name" , property = "picName" , javaType = java.lang.String.class),
			@Result(column = "pic_no" , property = "picNo", javaType = java.lang.String.class)})
	Pic selectByPicName(String picName); 
	
	/**
	 * 动态查询图片
	 * 
	 * @return
	 */
	@SelectProvider(type = PicDynaSqlProvider.class, method = "selectByParma")
	@Results(value = {@Result(column = "pic_name" , property = "picName" , javaType = java.lang.String.class),
			@Result(column = "pic_no" , property = "picNo", javaType = java.lang.String.class)})
	List<Pic> selectByPage(Map<String, Object> params);
	
	/**
	 * 查询图片总数
	 * 
	 * @param parmas
	 * @return
	 */
	@SelectProvider(type = PicDynaSqlProvider.class, method = "countPic")
	Integer countPic(Map<String, Object> parmas);
	
	/**
	 * 新增图片
	 * 
	 * @param pic
	 */
	@InsertProvider(type = PicDynaSqlProvider.class ,method="savePic")
	void save(Pic pic);
	
	/**
	 * 更新图片
	 * 
	 * @param pic
	 */
	@InsertProvider(type = PicDynaSqlProvider.class ,method="updatePic")
	void upate(Pic pic);
	
	/**
	 * 批量删除图片
	 * 
	 * @param ids
	 */
	@DeleteProvider(type = PicDynaSqlProvider.class , method = "batchDelPic")
	void batchDelPic(Map<String, Integer[]> ids);
}
