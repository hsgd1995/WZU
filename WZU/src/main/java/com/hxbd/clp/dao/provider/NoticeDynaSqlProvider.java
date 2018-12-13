package com.hxbd.clp.dao.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hxbd.clp.domain.Notice;
import com.hxbd.clp.utils.common.RasConstants;
import com.hxbd.clp.vo.NoticeVO;

public class NoticeDynaSqlProvider {

	public String selectByParma(Map<String, Object> params) {
		String sql =  new SQL(){
			{
				SELECT("*");
				FROM(RasConstants.NOTICETABLE);
				ORDER_BY("id desc");
				NoticeVO noticeVO = (NoticeVO) params.get("noticeVO");
				if(noticeVO != null){
					if(noticeVO.getCourseName() != null && !noticeVO.getCourseName().equals("")){
						String sql2 = new SQL(){
							{	
								SELECT("id");
								FROM(RasConstants.COURSETABLE);
								WHERE(" course_name like CONCAT('%',#{noticeVO.courseName},'%')");
							}
						}.toString();
						WHERE(" course_id IN (" + sql2 +")");
					}
//					if(noticeVO.getId() != null && !noticeVO.getId().equals("") && noticeVO.getId() != 0 ){
//						WHERE(" id = #{noticeVO.id}");
//					}
					if(noticeVO.getContent() != null && !noticeVO.getContent().equals("") ){
						WHERE(" content like CONCAT('%',#{noticeVO.content},'%')");
					}
					if(noticeVO.getTitle() != null && !noticeVO.getTitle().equals("") ){
						WHERE(" title like CONCAT('%',#{noticeVO.title},'%')");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;	
	}

	public String countNotice(Map<String, Object> params) {
		String sql =  new SQL(){
			{
				SELECT("count(*)");
				FROM(RasConstants.NOTICETABLE);
				NoticeVO noticeVO = (NoticeVO) params.get("noticeVO");
				if(noticeVO != null){
					if(noticeVO.getCourseName() != null && !noticeVO.getCourseName().equals("")){
						String sql2 = new SQL(){
							{	
								SELECT("id");
								FROM(RasConstants.COURSETABLE);
								WHERE(" course_name like CONCAT('%',#{noticeVO.courseName},'%')");
							}
						}.toString();
						WHERE(" course_id IN (" + sql2 +")");
					}
//					if(noticeVO.getId() != null && !noticeVO.getId().equals("") && noticeVO.getId() != 0 ){
//						WHERE(" id = #{noticeVO.id}");
//					}
					if(noticeVO.getContent() != null && !noticeVO.getContent().equals("") ){
						WHERE(" content like CONCAT('%',#{noticeVO.content},'%')");
					}
					if(noticeVO.getTitle() != null && !noticeVO.getTitle().equals("") ){
						WHERE(" title like CONCAT('%',#{noticeVO.title},'%')");
					}
				}
			}
		}.toString();
		if (params.get("pageModel") != null) {
			sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize}";
		}
		return sql;
	}

	public String addNotice(Notice notice) {
		return new SQL(){
			{
				if(notice != null){
					INSERT_INTO(RasConstants.NOTICETABLE);
					if(notice.getTitle() != null && !notice.getTitle().equals("")){
						VALUES("title","#{title}");
					}
					if(notice.getContent() != null && !notice.getContent().equals("")){
						VALUES("content","#{content}");
					}
					VALUES("course_id","#{courseId}");
					VALUES("release_time", "#{releaseTime}");
				}
			}
		}.toString();
	}

	public String updateNotice(Notice notice) {
		return new SQL(){
			{
				if(notice != null){
					UPDATE(RasConstants.NOTICETABLE);
					if(notice.getTitle() != null && !notice.getTitle().equals("")){
						SET("title = #{title}");
					}
					if(notice.getContent() != null && !notice.getContent().equals("")){
						SET("content = #{content}");
					}
					if(notice.getCourseId() != 0){
						SET("course_id = #{courseId}");
					}

					SET("release_time = #{releaseTime}");
					WHERE(" id = #{id} ");
				}
			}
		}.toString();
	}
	
	public String batchDelNotice(Map<String, Integer[]> ids){
		return new SQL(){
			{
				if(ids != null && ids.get("ids") != null){
					DELETE_FROM(RasConstants.NOTICETABLE);
					String inConditions = "id in (";
					Integer[] idsArr = ids.get("ids");
					for (int index = 0; index < idsArr.length; index++) {
						inConditions += idsArr[index];
						if(index != idsArr.length - 1){
							inConditions += ",";
						}
					}
					inConditions +=") ";
					WHERE(inConditions);
				}
			}
		}.toString();
	}
}
