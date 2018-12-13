package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.dao.provider.CompletionDynaSqlProvider;
import com.hxbd.clp.domain.Completion;
import com.hxbd.clp.utils.common.RasConstants;

public interface CompletionDao {

	@Select("select * from " + RasConstants.COMPLETIONTABLE + " where id = #{id}")
	Completion seleceById(Integer id);
	
	@Select("select * from " + RasConstants.COMPLETIONTABLE + " where courseVideoId = #{courseVideoId}")
	List<Completion> selectByCVId(Integer courseVideoId);
	
	@SelectProvider(type= CompletionDynaSqlProvider.class,method="selectByParams")
	List<Completion> selectByParams(Map<String , Object> params);
	
	@SelectProvider(type = CompletionDynaSqlProvider.class, method = "count")
	Integer count(Map<String, Object> params);
	
	@InsertProvider(type = CompletionDynaSqlProvider.class, method = "save")
	void save(Completion completion);
	
	@UpdateProvider(type = CompletionDynaSqlProvider.class, method = "update")
	void update(Completion completion);
	
	@DeleteProvider(type = CompletionDynaSqlProvider.class, method = "batchDel")
	void batchDel(Map<String, Integer[]> ids);
}
