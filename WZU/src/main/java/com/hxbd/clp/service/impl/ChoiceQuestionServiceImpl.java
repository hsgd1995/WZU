package com.hxbd.clp.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hxbd.clp.dao.ChoiceQuestionDao;
import com.hxbd.clp.dao.CompletionDao;
import com.hxbd.clp.dao.CourseVideoDao;
import com.hxbd.clp.dao.JudgmentProblemDao;
import com.hxbd.clp.domain.ChoiceQuestion;
import com.hxbd.clp.domain.Completion;
import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.domain.JudgmentProblem;
import com.hxbd.clp.service.ChoiceQuestionService;
import com.hxbd.clp.utils.common.ErrorImport;
import com.hxbd.clp.utils.common.ExcelUtil;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.ChoiceQuestionVO;

@Service("choiceQuestionService")
public class ChoiceQuestionServiceImpl implements ChoiceQuestionService {

	@Autowired
	private ChoiceQuestionDao cqDao;

	@Autowired
	private CourseVideoDao courseVideoDao;

	@Autowired
	private JudgmentProblemDao juProblemDao;

	@Autowired
	private CompletionDao completionDao;

	// 导入Excel写入数据库完成,0表示未遍历，1表示全部记录已遍历，2表示存在不符合格式的行（如：该行的某个单元格为空）
	private Integer importStatus = 0;

	@Override
	public ChoiceQuestion selectById(Integer id) {
		return cqDao.selectById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public PageModel<ChoiceQuestionVO> selectByPageModel(ChoiceQuestionVO cqVO, PageModel<ChoiceQuestionVO> pageModel,
			Map<Integer, CourseVideo> courseMap) {
		Map<String, Object> params = new HashMap<>();
		params.put("cqVO", cqVO);
		Integer recordCount = cqDao.count(params);
		if (recordCount > 0) {
			pageModel.setRecordCount(recordCount);
			params.put("pageModel", pageModel);
		}
		List<ChoiceQuestion> listt = cqDao.selectByParams(params);
		List<ChoiceQuestionVO> list = listt.stream().map(m -> getVO(m, courseMap.get(m.getCourseVideoId())))
				.collect(Collectors.toList());
		pageModel.setList(list);
		return pageModel;
	}

	public ChoiceQuestionVO getVO(ChoiceQuestion choiceQuestion, CourseVideo courseVideo) {
		ChoiceQuestionVO vo = new ChoiceQuestionVO();
		vo.setId(choiceQuestion.getId());
		vo.setTitle(choiceQuestion.getTitle());
		vo.setTypeChoice(choiceQuestion.getTypeChoice());
		vo.setOptionOne(choiceQuestion.getOptionOne());
		vo.setOptionTwo(choiceQuestion.getOptionTwo());
		vo.setOptionThree(choiceQuestion.getOptionThree());
		vo.setOptionFour(choiceQuestion.getOptionFour());
		vo.setAnswer(choiceQuestion.getAnswer());
		vo.setJieshi(choiceQuestion.getJieshi());
		vo.setCourseName(courseVideo.getName());
		return vo;
	}

	@Override
	public void saveCQ(ChoiceQuestion cq) {
		cqDao.save(cq);
	}

	@Override
	public void updateeCQ(ChoiceQuestion cq) {
		cq.setId(cq.getId());
		cqDao.update(cq);

	}

	@Override
	public void delCQ(Integer[] ids) {
		Map<String, Integer[]> map = new HashMap<>();
		map.put("ids", ids);
		cqDao.batchDel(map);
	}

	@Override
	public ErrorImport<CourseVideo> batchAddQuestion(InputStream in, String originalFilename) {
		// 错误列表，Excel表中课程视频主标题不存在的记录
		ErrorImport<CourseVideo> errorImport = new ErrorImport<>();
		// 遍历索引，用于记录不合格记录在第几行
		int index = 1;
		try {
			// 页
			List<List<Object>> rowList = ExcelUtil.getBankListByExcel(in, originalFilename);
			// 遍历Excel的记录，从第二行开始读，第一行为表头
			for (int i = 1; i < rowList.size(); i++) {
				// 行
				List<Object> ob = rowList.get(i);
				// ob 下标从0开始
				// 从每行的第2个单元格开始取，对应下标为1，
				// 第1个单元格为序号，2为课程视频主标题，3为题目，4为选项类型,5为选项1，6为选项2，7为选项3，8为选项4,9为答案，10为解释

				ChoiceQuestion cq = new ChoiceQuestion();

				index = i + 1;
				// 取第二个单元格，判断课程视频主标题是否存在
				List<CourseVideo> cvList = courseVideoDao.selectByName(String.valueOf(ob.get(1)));
				// 若课程视频主标题不存在则将返回 的列表
				if (cvList == null || cvList.size() == 0) {
					CourseVideo cv = new CourseVideo();
					cv.setId(Integer.parseInt((String) ob.get(0)));
					cv.setName(String.valueOf(ob.get(1)));
					// 将主标题不存在的记录的序号和主标题放入错误列表
					errorImport.getErrorList().add(cv);
					continue;
				}
				// 如果存在课程视频主标题同名，则取第一条
				cq.setCourseVideoId(cvList.get(0).getId());
				if (String.valueOf(ob.get(2)) == null || "".equals(String.valueOf(ob.get(2)))) {
					throw new NullPointerException("课程视频主标题为空！");
				}
				cq.setTitle(String.valueOf(ob.get(2)));
				cq.setTypeChoice(String.valueOf(ob.get(3)));
				cq.setOptionOne(String.valueOf(ob.get(4)));
				cq.setOptionTwo(String.valueOf(ob.get(5)));
				cq.setOptionThree(String.valueOf(ob.get(6)));
				cq.setOptionFour(String.valueOf(ob.get(7)));
				cq.setAnswer(String.valueOf(ob.get(8)));
				cq.setJieshi(String.valueOf(ob.get(9)));
				// 保存选择题
				cqDao.save(cq);
			}
			importStatus = 1;
		} catch (Exception e) {
			e.printStackTrace();

			errorImport.setFailIndex(index);
			importStatus = 2;
		}
		return errorImport;
	}

	public Integer getImportStatus() {
		return importStatus;
	}

	@Override
	public Map<String, Object> selectByCourseVideoId(Integer courseVideoId) {
		List<ChoiceQuestion> listCQ = cqDao.selectByCVId(courseVideoId);
		List<JudgmentProblem> listJP = juProblemDao.selectByCVId(courseVideoId);
		List<Completion> listCL = completionDao.selectByCVId(courseVideoId);
		Map<String, Object> questionsMap = new HashMap<String, Object>();
		questionsMap.put("listCQ", listCQ);
		questionsMap.put("listJP", listJP);
		questionsMap.put("listCL", listCL);
		return questionsMap;
	}

	@Override
	public Map<String, Object> verificationAnswer(Integer courseVideoId, JSONObject jo) {
		JSONObject answerCQ = jo.getJSONObject("answerCQ");
		JSONObject answerJP = jo.getJSONObject("answerJP");
		JSONObject answerCL = jo.getJSONObject("answerCL");
		List<ChoiceQuestion> listCQ = cqDao.selectByCVId(courseVideoId);
		List<JudgmentProblem> listJP = juProblemDao.selectByCVId(courseVideoId);
		List<Completion> listCL = completionDao.selectByCVId(courseVideoId);
		JSONObject errorAnsewrCQ = matchingAnswerCQ(answerCQ, listCQ);
		JSONObject errorAnsewrJP = matchingAnswerJP(answerJP, listJP);
		JSONObject errorAnsewrCL = matchingAnswerCL(answerCL, listCL);
		Map<String, Object> errorAnsewrMap = new HashMap<String, Object>();
		errorAnsewrMap.put("errorAnsewrCQ", errorAnsewrCQ.toString());
		errorAnsewrMap.put("errorAnsewrJP", errorAnsewrJP.toString());
		errorAnsewrMap.put("errorAnsewrCL", errorAnsewrCL.toString());
		// 计算分数
		double error = errorAnsewrCQ.length() + errorAnsewrJP.length() + errorAnsewrCL.length();
		double totalQuestions = listCQ.size() + listJP.size() + listCL.size();
		double fraction = (totalQuestions - error) / totalQuestions;
		errorAnsewrMap.put("fraction", fraction*100);
		return errorAnsewrMap;
	}

	public JSONObject matchingAnswerCQ(JSONObject json, List<ChoiceQuestion> list) {
		JSONObject errorAnsewr = new JSONObject();
		for (int i = 0; i < list.size(); i++) {
			ChoiceQuestion cq = list.get(i);
			if (!json.get(String.valueOf(cq.getId())).equals(cq.getAnswer())) {
				errorAnsewr.put(String.valueOf(cq.getId()), json.get(String.valueOf(cq.getId())));
			}
		}
		return errorAnsewr;
	}

	public JSONObject matchingAnswerJP(JSONObject json, List<JudgmentProblem> list) {
		JSONObject errorAnsewr = new JSONObject();
		for (int i = 0; i < list.size(); i++) {
			JudgmentProblem jp = list.get(i);
			if (!json.get(String.valueOf(jp.getId())).equals(String.valueOf(jp.getAnswer()))) {
				errorAnsewr.put(String.valueOf(jp.getId()), json.get(String.valueOf(jp.getId())));
			}
		}
		return errorAnsewr;
	}

	public JSONObject matchingAnswerCL(JSONObject json, List<Completion> list) {
		JSONObject errorAnsewr = new JSONObject();
		for (int i = 0; i < list.size(); i++) {
			Completion cq = list.get(i);
			if (!json.get(String.valueOf(cq.getId())).equals(cq.getAnswer())) {
				errorAnsewr.put(String.valueOf(cq.getId()), json.get(String.valueOf(cq.getId())));
			}
		}
		return errorAnsewr;
	}

	@Override
	public void addAchievementByUserId(Integer id, Integer courseVideoId, double fraction, String answer) {
		cqDao.addAchievement(id, courseVideoId, fraction,answer);
	}

	@Override
	public String selectAcswerByUserIdAndCouId(int userId, Integer courseVideoId) {
		return cqDao.selectAcswerById(userId,courseVideoId);
	}

}
