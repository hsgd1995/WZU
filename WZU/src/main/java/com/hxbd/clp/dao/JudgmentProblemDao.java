package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.dao.provider.JudgmentProblemDynaSqlProvider;
import com.hxbd.clp.domain.ChoiceQuestion;
import com.hxbd.clp.domain.JudgmentProblem;
import com.hxbd.clp.utils.common.RasConstants;

public interface JudgmentProblemDao {

	@Select("select * from " + RasConstants.JPTABLE + " where id = #{id}")
	JudgmentProblem seleceById(Integer id);
	
	@Select("select * from " + RasConstants.JPTABLE + " where courseVideoId = #{courseVideoId}")
	List<JudgmentProblem> selectByCVId(Integer courseVideoId);
	
	@SelectProvider(type= JudgmentProblemDynaSqlProvider.class,method="selectByParams")
	List<JudgmentProblem> selectByParams(Map<String , Object> params);
	
	@SelectProvider(type = JudgmentProblemDynaSqlProvider.class, method = "count")
	Integer count(Map<String, Object> params);
	
	@InsertProvider(type = JudgmentProblemDynaSqlProvider.class, method = "save")
	void save(JudgmentProblem judgmentProblem);
	
	@UpdateProvider(type = JudgmentProblemDynaSqlProvider.class, method = "update")
	void update(JudgmentProblem judgmentProblem);
	
	@DeleteProvider(type = JudgmentProblemDynaSqlProvider.class, method = "batchDel")
	void batchDel(Map<String, Integer[]> ids);
}
