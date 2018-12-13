package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.ChoiceQuestion;
import com.hxbd.clp.utils.common.RasConstants;
import com.hxbd.clp.vo.ChoiceQuestionVO;

public class ChoiceQuestionDynaSqlProvider {
	public String selectByParam(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(RasConstants.CQTABLE);
				ORDER_BY("id desc");
				ChoiceQuestionVO cq = (ChoiceQuestionVO) params.get("cqVO");
				if (cq.getCourseName() != null && !cq.getCourseName().equals("")) {
					String sql2 = new SQL(){
						{
							SELECT("id");
							FROM(RasConstants.COURSEVIDEOTABLE);
							WHERE(" parent = 0 and name like CONCAT('%',#{cqVO.courseName},'%')");
						}
					}.toString();
					WHERE("courseVideoId IN (" + sql2 + ")");
				}
				if (cq.getTitle() != null && !cq.getTitle().equals("")) {
					WHERE(" title like CONCAT('%',#{cqVO.title},'%')");
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	public String count(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("count(*)");
				FROM(RasConstants.CQTABLE);
				ChoiceQuestionVO cq = (ChoiceQuestionVO) params.get("cqVO");
				if (cq.getCourseName() != null && !cq.getCourseName().equals("")) {
					String sql2 = new SQL(){
						{
							SELECT("id");
							FROM(RasConstants.COURSEVIDEOTABLE);
							WHERE(" parent = 0 and name like CONCAT('%',#{cqVO.courseName},'%')");
						}
					}.toString();
					WHERE("courseVideoId IN (" + sql2 + ")");
				}
				if (cq.getTitle() != null && !cq.getTitle().equals("")) {
					WHERE(" title like CONCAT('%',#{cqVO.title},'%')");
				}
			}
		}.toString();
		return sql;
	}

	public String save(ChoiceQuestion cq) {
		return new SQL() {
			{
				//System.err.println(cq);
				if (cq != null) {
					INSERT_INTO(RasConstants.CQTABLE);
					VALUES("title", "#{title}");
					VALUES("typeChoice", "#{typeChoice}");
					VALUES("optionOne", "#{optionOne}");
					VALUES("optionTwo", "#{optionTwo}");
					VALUES("optionThree", "#{optionThree}");
					VALUES("optionFour", "#{optionFour}");
					VALUES("answer", "#{answer}");
					VALUES("jieshi", "#{jieshi}");
					VALUES("courseVideoId", "#{courseVideoId}");
				}
			}
		}.toString();
	}

	public String update(ChoiceQuestion cq) {
		return new SQL() {
			{
				if (cq != null) {
					UPDATE(RasConstants.CQTABLE);
					if (cq.getTitle() != null && !cq.getTitle().equals("")) {
						SET("title = #{title}");
					}
					if (cq.getTitle() != null && !cq.getTitle().equals("")) {
						SET("typeChoice = #{typeChoice}");
					}
					if (cq.getOptionOne() != null && !cq.getOptionOne().equals("")) {
						SET("optionOne = #{optionOne}");
					}
					if (cq.getOptionTwo() != null && !cq.getOptionTwo().equals("")) {
						SET("optionTwo = #{optionTwo}");
					}
					if (cq.getOptionThree() != null && !cq.getOptionThree().equals("")) {
						SET("optionThree = #{optionThree}");
					}
					if (cq.getOptionFour() != null && !cq.getOptionFour().equals("")) {
						SET("optionFour = #{optionFour}");
					}
					if (cq.getAnswer() != null && !cq.getAnswer().equals("")) {
						SET("answer = #{answer}");
					}
					if (cq.getJieshi() != null && !cq.getJieshi().equals("")) {
						SET("jieshi = #{jieshi}");
					}
					if (cq.getCourseVideoId() != null && !cq.getCourseVideoId().equals("")) {
						SET("courseVideoId = #{courseVideoId}");
					}
					WHERE(" id = #{id} ");
				}
			}
		}.toString();
	}

	public String batchDel(Map<String, Integer[]> ids) {
		return new SQL() {
			{
				if (ids != null && ids.get("ids") != null) {
					DELETE_FROM(RasConstants.CQTABLE);
					String inConditions = "id in (";
					Integer[] idsArr = ids.get("ids");
					for (int index = 0; index < idsArr.length; index++) {
						inConditions += idsArr[index];
						if (index != idsArr.length - 1) {
							inConditions += ",";
						}
					}
					inConditions += ") ";
					WHERE(inConditions);
				}
			}
		}.toString();
	}

}
