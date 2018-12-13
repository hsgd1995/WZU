package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.JudgmentProblem;
import com.hxbd.clp.utils.common.RasConstants;
import com.hxbd.clp.vo.JudgmentProblemVO;

public class JudgmentProblemDynaSqlProvider {

	public String selectByParams(Map<String, Object> params) {
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(RasConstants.JPTABLE);
				ORDER_BY("id desc");
				JudgmentProblemVO jp = (JudgmentProblemVO) params.get("jpVO");
				if (jp.getCourseName() != null && !jp.getCourseName().equals("")) {
					String sql2 = new SQL(){
						{
							SELECT("id");
							FROM(RasConstants.COURSEVIDEOTABLE);
							WHERE(" parent = 0 and name like CONCAT('%',#{jpVO.courseName},'%')");
						}
					}.toString();
					WHERE("courseVideoId IN (" + sql2 + ")");
				}
				if (jp.getTitle() != null && !jp.getTitle().equals("")) {
					WHERE(" title like CONCAT('%',#{jpVO.title},'%')");
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
				FROM(RasConstants.JPTABLE);
				JudgmentProblemVO jp = (JudgmentProblemVO) params.get("jpVO");
				if (jp.getCourseName() != null && !jp.getCourseName().equals("")) {
					String sql2 = new SQL(){
						{
							SELECT("id");
							FROM(RasConstants.COURSEVIDEOTABLE);
							WHERE(" parent = 0 and name like CONCAT('%',#{jpVO.courseName},'%')");
						}
					}.toString();
					WHERE("courseVideoId IN (" + sql2 + ")");
				}
				if (jp.getTitle() != null && !jp.getTitle().equals("")) {
					WHERE(" title like CONCAT('%',#{jpVO.title},'%')");
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	public String save(JudgmentProblem jp) {
		return new SQL() {
			{
				//System.err.println(jp);
				if (jp != null) {
					INSERT_INTO(RasConstants.JPTABLE);
					VALUES("title", "#{title}");
					VALUES("answer", "#{answer}");
					VALUES("jieshi", "#{jieshi}");
					VALUES("courseVideoId", "#{courseVideoId}");
				}
			}
		}.toString();
	}

	public String update(JudgmentProblem jp) {
		return new SQL() {
			{
				if (jp != null) {
					UPDATE(RasConstants.JPTABLE);
					if (jp.getTitle() != null && !jp.getTitle().equals("")) {
						SET("title = #{title}");
					}
					if (jp.getAnswer() != null && !jp.getAnswer().equals("")) {
						SET("answer = #{answer}");
					}
					if (jp.getJieshi() != null && !jp.getJieshi().equals("")) {
						SET("jieshi = #{jieshi}");
					}
					if (jp.getCourseVideoId() != null && !jp.getCourseVideoId().equals("")) {
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
					DELETE_FROM(RasConstants.JPTABLE);
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
