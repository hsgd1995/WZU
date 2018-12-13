package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hxbd.clp.dao.provider.NewsPicDynaSqlProvider;
import com.hxbd.clp.domain.NewsPic;
import com.hxbd.clp.domain.Pic;
import com.hxbd.clp.utils.common.RasConstants;

public interface NewsPicDao {
	
	// 新增图片
	@InsertProvider(type = NewsPicDynaSqlProvider.class ,method="saveNewsPic")
	void save(NewsPic newsPic);
	
	// 更新图片
	@InsertProvider(type = NewsPicDynaSqlProvider.class ,method="updatePic")
	void upate(NewsPic newsPic);
	
	//批量删除图片
	@DeleteProvider(type = NewsPicDynaSqlProvider.class , method = "batchDelPic")
	void batchDelPic(Map<String, Integer[]> ids);

	/**
	 * 根据id查找新闻图片
	 * @param id
	 * @return
	 */
	@Select("select * from "+RasConstants.NEWSPICTABLE +" where id = #{id}")
	@Results({
		@Result(column="news_id",property="news",javaType=com.hxbd.clp.domain.News.class,
				one=@One(select="com.hxbd.clp.dao.NewsDao.selectById"))
	})
	Pic selectPicById(Integer id);
	
	/**
	 * 根据新闻id查找图片
	 * @param newsId
	 * @return
	 */
	@Select("select * from "+RasConstants.NEWSPICTABLE +" where news_id = #{newsId}")
	@Results({
		@Result(column="news_id",property="news",javaType=com.hxbd.clp.domain.News.class,
				one=@One(select="com.hxbd.clp.dao.NewsDao.selectById"))
	})
	List<NewsPic> SelectPicByNewsId(Integer newsId);

	@Delete("delete from "+RasConstants.NEWSPICTABLE +" where id = #{id}")
	void delById(Integer id);
}
