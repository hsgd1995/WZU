package com.hxbd.clp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.dao.PicDao;
import com.hxbd.clp.domain.Pic;
import com.hxbd.clp.service.PicService;
import com.hxbd.clp.utils.tag.PageModel;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service("picService")
public class PicServiceImpl implements PicService{
		
	@Autowired
	private PicDao picDao;

	@Override
	public void savePic(Pic pic) {
		pic.setRemark("新增");
		picDao.save(pic);
	}


	@Override
	public void batchDeletePic(Integer[] ids) {
		Map<String, Integer[] > map = new HashMap<>();
		map.put("ids", ids);
		picDao.batchDelPic(map);
	}

	@Override
	public Pic selectPicById(Integer id) {
		return picDao.selectById(id);
	}

	@Override
	public PageModel<Pic> findPic(Pic pic, PageModel<Pic> pageModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pic> picList() {
		return picDao.selectByPage(new HashMap<String ,Object>());
	}

	@Override
	public List<Pic> selcectMaxClickPic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExisPic(String picName) {
		
		if( picDao.selectByPicName(picName) != null){
			return true;
		}
		return false;
	}

	@Override
	public Pic selectPicByPicNo(String picNo) {
		return picDao.selectByPicNo(picNo);
	}

	@Override
	public void updatePicNoFile(Pic pic) {
		pic.setPath("/" + pic.getPicName() + ".jpg");
		pic.setRemark("更新");
		pic.setId(pic.getId());
		picDao.upate(pic);
	}


	@Override
	public Integer countPic(Map<String, Object> params) {
		return picDao.countPic(new HashMap<String, Object>());
	}


	@Override
	public PageModel<Pic> findPicList(Pic pic, PageModel<Pic> pageModel) {
		Map<String, Object> params  = new HashMap<>();
		params.put("pic", pic);
		Integer recordCount = picDao.countPic(params);
		if(recordCount > 0){
			pageModel.setRecordCount(recordCount);
			params.put("pageModel", pageModel);
		}
		
		List<Pic> list = picDao.selectByPage(params);
		pageModel.setList(list);
		return pageModel;
	}

}
