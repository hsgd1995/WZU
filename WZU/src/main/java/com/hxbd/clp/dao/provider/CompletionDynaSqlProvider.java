package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.Completion;
import com.hxbd.clp.domain.JudgmentProblem;
import com.hxbd.clp.utils.common.RasConstants;
import com.hxbd.clp.vo.CompletionVO;

public class CompletionDynaSqlProvider {
		
	public String selectByParams(Map<String ,Object> params){
		String sql = new SQL(){
			{
				SELECT("*");
				FROM(RasConstants.COMPLETIONTABLE);
				ORDER_BY("id desc");
				CompletionVO completionVO = (CompletionVO)params.get("completionVO");
				if (completionVO.getCourseName() != null && !completionVO.getCourseName().equals("")) {
					String sql2 = new SQL(){
						{
							SELECT("id");
							FROM(RasConstants.COURSEVIDEOTABLE);
							WHERE(" parent = 0 and name like CONCAT('%',#{completionVO.courseName},'%')");
						}
					}.toString();
					WHERE("courseVideoId IN (" + sql2 + ")");
				}
				if (completionVO.getTitle() != null && !completionVO.getTitle().equals("")) {
					WHERE(" title like CONCAT('%',#{completionVO.title},'%')");
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
				FROM(RasConstants.COMPLETIONTABLE);
				CompletionVO completionVO = (CompletionVO)params.get("completionVO");
				if (completionVO.getCourseName() != null && !completionVO.getCourseName().equals("")) {
					String sql2 = new SQL(){
						{
							SELECT("id");
							FROM(RasConstants.COURSEVIDEOTABLE);
							WHERE(" parent = 0 and name like CONCAT('%',#{completionVO.courseName},'%')");
						}
					}.toString();
					WHERE("courseVideoId IN (" + sql2 + ")");
				}
				if (completionVO.getTitle() != null && !completionVO.getTitle().equals("")) {
					WHERE(" title like CONCAT('%',#{completionVO.title},'%')");
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	public String save(Completion completion) {
		return new SQL() {
			{
				//System.err.println(completion);
				if (completion != null) {
					INSERT_INTO(RasConstants.COMPLETIONTABLE);
					VALUES("title", "#{title}");
					VALUES("answer", "#{answer}");
					VALUES("jieshi", "#{jieshi}");
					VALUES("courseVideoId", "#{courseVideoId}");
				}
			}
		}.toString();
	}

	public String update(Completion completion) {
		return new SQL() {
			{
				if (completion != null) {
					UPDATE(RasConstants.COMPLETIONTABLE);
					if (completion.getTitle() != null && !completion.getTitle().equals("")) {
						SET("title = #{title}");
					}
					if (completion.getAnswer() != null && !completion.getAnswer().equals("")) {
						SET("answer = #{answer}");
					}
					if (completion.getJieshi() != null && !completion.getJieshi().equals("")) {
						SET("jieshi = #{jieshi}");
					}
					if (completion.getCourseVideoId() != null && !completion.getCourseVideoId().equals("")) {
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
					DELETE_FROM(RasConstants.COMPLETIONTABLE);
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
