package com.hxbd.clp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.dao.NewsPicDao;
import com.hxbd.clp.dao.PicDao;
import com.hxbd.clp.domain.NewsPic;
import com.hxbd.clp.domain.Pic;
import com.hxbd.clp.service.NewsPicService;
import com.hxbd.clp.service.PicService;
import com.hxbd.clp.utils.tag.PageModel;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service("newsPicService")
public class NewsPicServiceImpl implements NewsPicService{
		
	@Autowired
	private NewsPicDao newsPicDao;

	@Override
	public void saveNewsPic(NewsPic newsPic) {
		newsPicDao.save(newsPic);
	}

	@Override
	public List<NewsPic> getNewsPicByNewsId(Integer newsId) {
		return newsPicDao.SelectPicByNewsId(newsId);
	}
	
	
	@Override
	public void delectById(Integer id) {
		newsPicDao.delById(id);
		
	}

	@Override
	public void updatePicNoFile(Pic pic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batchDeletePic(Integer[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pic selectPicById(Integer id) {
		return newsPicDao.selectPicById(id);
	}

	@Override
	public Pic selectPicByPicNo(String picNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageModel<Pic> findPic(Pic pic, PageModel<Pic> pageModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pic> picList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pic> selcectMaxClickPic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExisPic(String picName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer countPic(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageModel<Pic> findPicList(Pic pic, PageModel<Pic> pageModel) {
		// TODO Auto-generated method stub
		return null;
	}

	

	

	

}