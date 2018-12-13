package com.hxbd.clp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hxbd.clp.dao.provider.ChoiceQuestionDynaSqlProvider;
import com.hxbd.clp.domain.ChoiceQuestion;
import com.hxbd.clp.utils.common.RasConstants;

public interface ChoiceQuestionDao {

	@Select("select * from " + RasConstants.CQTABLE + " where id = #{id}")
	ChoiceQuestion selectById(Integer id);

	@Select("select * from " + RasConstants.CQTABLE + " where courseVideoId = #{courseVideoId}")
	List<ChoiceQuestion> selectByCVId(Integer courseVideoId);

	@SelectProvider(type = ChoiceQuestionDynaSqlProvider.class, method = "selectByParam")
	List<ChoiceQuestion> selectByParams(Map<String, Object> params);

	@SelectProvider(type = ChoiceQuestionDynaSqlProvider.class, method = "count")
	Integer count(Map<String, Object> params);

	@InsertProvider(type = ChoiceQuestionDynaSqlProvider.class, method = "save")
	void save(ChoiceQuestion choiceQuestion);

	@UpdateProvider(type = ChoiceQuestionDynaSqlProvider.class, method = "update")
	void update(ChoiceQuestion choiceQuestion);

	@DeleteProvider(type = ChoiceQuestionDynaSqlProvider.class, method = "batchDel")
	void batchDel(Map<String, Integer[]> ids);

	@Insert(" insert into " + RasConstants.USERACHIEVEMENT
			+ "(user_id, main_title,achievement,user_option) values (#{userId} , #{courseVideoId} , #{fraction} ,#{answer})")
	void addAchievement(@Param("userId")Integer  userId,@Param("courseVideoId")Integer courseVideoId, @Param("fraction")double fraction,@Param("answer")String answer);

	@Select("select user_option from " + RasConstants.USERACHIEVEMENT + " where user_id = #{userId} and main_title = #{courseVideoId}")
	String selectAcswerById(@Param("userId")int userId, @Param("courseVideoId")Integer courseVideoId);
}
