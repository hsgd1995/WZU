package com.hxbd.clp.service;

import java.util.List;
import java.util.Map;

import com.hxbd.clp.domain.NewsPic;
import com.hxbd.clp.domain.Pic;
import com.hxbd.clp.utils.tag.PageModel;


public interface NewsPicService  {
	
	/**
	 * 根据新闻id获取图片
	 * @param newsId
	 */
	List<NewsPic> getNewsPicByNewsId(Integer newsId);
	
	/**
	 * 根据id删除记录
	 * @param id
	 */
	void delectById(Integer id);
	
	// 保存图片
	void saveNewsPic(NewsPic newsPic);
	// 更新图片无文件
	void updatePicNoFile(Pic pic);
	// 根据id删除图片
	void batchDeletePic(Integer[] ids);
	// 根据id查询图片
	Pic selectPicById(Integer id);
	// 根据图片ID查询图片
	Pic selectPicByPicNo(String	 picNo);
	// 分页查询所有图片
	PageModel<Pic> findPic(Pic pic ,PageModel<Pic> pageModel);
	// 获取图片列表
	List<Pic> picList();
	// 查询点击量最多的5张图片
	List<Pic> selcectMaxClickPic();
	// 判断图片是否存在
	boolean isExisPic(String picName);
	// 图片总数
	Integer countPic(Map<String, Object> params);

	PageModel<Pic> findPicList(Pic pic, PageModel<Pic> pageModel);
}
