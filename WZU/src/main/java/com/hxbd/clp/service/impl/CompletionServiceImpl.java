package com.hxbd.clp.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxbd.clp.dao.CompletionDao;
import com.hxbd.clp.dao.CourseVideoDao;
import com.hxbd.clp.domain.ChoiceQuestion;
import com.hxbd.clp.domain.Completion;
import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.domain.JudgmentProblem;
import com.hxbd.clp.service.CompletionService;
import com.hxbd.clp.service.JudgmentProblemService;
import com.hxbd.clp.utils.common.ErrorImport;
import com.hxbd.clp.utils.common.ExcelUtil;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.ChoiceQuestionVO;
import com.hxbd.clp.vo.CompletionVO;
import com.hxbd.clp.vo.JudgmentProblemVO;

@Service("CompletionService")
public class CompletionServiceImpl implements CompletionService{
	
	@Autowired
	private CompletionDao completionDao;
	
	@Autowired
	private CourseVideoDao courseVideoDao;

	
	//导入Excel写入数据库完成,0表示未遍历，1表示全部记录已遍历，2表示存在不符合格式的行（如：该行的某个单元格为空）
	private Integer importStatus = 0;
	

	@Override
	public Completion selectById(Integer id) {
		return completionDao.seleceById(id);
	}

	@Override
	public PageModel<CompletionVO> selectByPageModel(CompletionVO completionVO, PageModel<CompletionVO> pageModel,
			Map<Integer, CourseVideo> courseMap) {
		Map<String, Object> params = new HashMap<>();
		params.put("completionVO", completionVO);
		Integer recordCount = completionDao.count(params);
		if (recordCount > 0) {
			pageModel.setRecordCount(recordCount);
			params.put("pageModel", pageModel);
		}
		List<Completion> listt = completionDao.selectByParams(params);
		List<CompletionVO> list = listt.stream().map(m ->getVO(m,courseMap.get(m.getCourseVideoId()))).collect(Collectors.toList());
		pageModel.setList(list);
		return pageModel;
	}
	
	public CompletionVO getVO(Completion completion,CourseVideo courseVideo){
		CompletionVO vo = new CompletionVO();
		vo.setId(completion.getId());
		vo.setTitle(completion.getTitle());
		vo.setAnswer(completion.getAnswer());
		vo.setJieshi(completion.getJieshi());
		vo.setCourseName(courseVideo.getName());
		return vo;
	}

	@Override
	public void saveCompletion(Completion completion) {
		completionDao.save(completion);
		
	}

	@Override
	public void updateCompletion(Completion completion) {
		completionDao.update(completion);
		
	}

	@Override
	public void delCompletion(Integer[] ids) {
		Map<String, Integer[]> map = new HashMap<>();
		map.put("ids", ids);
		completionDao.batchDel(map);
		
	}

	@Override
	public ErrorImport<CourseVideo> batchAddQuestion(InputStream in, String originalFilename) {
		//错误列表，Excel表中课程视频主标题不存在的记录
		ErrorImport<CourseVideo> errorImport = new ErrorImport<>();
		//遍历索引，用于记录不合格记录在第几行
		int index=1;
		try{
			//页
			List<List<Object>> rowList = ExcelUtil.getBankListByExcel(in, originalFilename);
			//遍历Excel的记录，从第二行开始读，第一行为表头
			for(int i=1;i<rowList.size();i++){
				//行
				List<Object> ob = rowList.get(i);
				//ob 下标从0开始
				//从每行的第2个单元格开始取，对应下标为1，
				//第1个单元格为序号，2为课程视频主标题，3为题目，4为答案，5为解释 
				
				Completion completion = new Completion();
				
				index = i+1;
				//取第二个单元格，判断课程视频主标题是否存在
				List<CourseVideo> cvList = courseVideoDao.selectByName(String.valueOf(ob.get(1)));
				//若课程视频主标题不存在则将返回  的列表
				if(cvList == null || cvList.size()==0){
					CourseVideo cv = new CourseVideo();
					cv.setId(Integer.parseInt((String)ob.get(0)));
					cv.setName(String.valueOf(ob.get(1)));
					//将主标题不存在的记录的序号和主标题放入错误列表
					errorImport.getErrorList().add(cv);
					continue;
				}
				//如果存在课程视频主标题同名，则取第一条
				completion.setCourseVideoId(cvList.get(0).getId());
				if(String.valueOf(ob.get(2))==null || "".equals(String.valueOf(ob.get(2)))){
					throw new NullPointerException("课程视频主标题为空！");
				}
				completion.setTitle(String.valueOf(ob.get(2)));
				completion.setAnswer(String.valueOf(ob.get(3)));
				completion.setJieshi(String.valueOf(ob.get(4)));
				//保存选择题
				completionDao.save(completion);
			}
			importStatus = 1;
		}catch(Exception e){
			e.printStackTrace();
			
			errorImport.setFailIndex(index);
			importStatus = 2;
		}
		return errorImport;
	}

	public Integer getImportStatus() {
		return importStatus;
	}

}