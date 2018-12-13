package com.hxbd.clp.service;

import java.io.InputStream;
import java.util.Map;

import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.domain.JudgmentProblem;
import com.hxbd.clp.utils.common.ErrorImport;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.JudgmentProblemVO;

public interface JudgmentProblemService {

	JudgmentProblem selectById(Integer id);
	
	PageModel<JudgmentProblemVO> selectByPageModel(JudgmentProblemVO jp, PageModel<JudgmentProblemVO> pageModel,Map<Integer,CourseVideo> courseMap);
	
	void saveJP(JudgmentProblem jp);
	
	void updateeJP(JudgmentProblem jp);
	
	void delJP(Integer[] ids);
	
	/**
	 * 批量增加选择题
	 * <br>可能存在三种情况，1：全部导入完成，2：存在错误记录，即课程视频主标题不存在的记录，3：存在空的单元格
	 * <br>第2种情况和第3种情况可能并存
	 * @param in
	 * @param OriginalFilename
	 * @return 返回Excel表中课程视频主标题不存在的记录和不合格记录所在的行的索引
	 */
	ErrorImport<CourseVideo> batchAddQuestion(InputStream in, String originalFilename);
	
	//是否完成导入
	Integer getImportStatus();
}
