package com.hxbd.clp.domain;

import java.io.Serializable;


/**
 * 选择题
 * @author Administrator
 *
 */
public class ChoiceQuestion implements Serializable{

	private static final long serialVersionUID = -8806658620096129411L;

	private Integer id;
	private String title; // 题目
	private String typeChoice;//类型
	private String optionOne; // 选项1
	private String optionTwo; // 选项2
	private String optionThree; // 选项3
	private String optionFour; // 选项4
	private String answer; // 答案
	private String jieshi; // 解释 
	private Integer courseVideoId;	//课程视频ID
	
	public ChoiceQuestion() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTypeChoice() {
		return typeChoice;
	}

	public void setTypeChoice(String typeChoice) {
		this.typeChoice = typeChoice;
	}


	public String getOptionOne() {
		return optionOne;
	}

	public void setOptionOne(String optionOne) {
		this.optionOne = optionOne;
	}

	public String getOptionTwo() {
		return optionTwo;
	}

	public void setOptionTwo(String optionTwo) {
		this.optionTwo = optionTwo;
	}

	public String getOptionThree() {
		return optionThree;
	}

	public void setOptionThree(String optionThree) {
		this.optionThree = optionThree;
	}

	public String getOptionFour() {
		return optionFour;
	}

	public void setOptionFour(String optionFour) {
		this.optionFour = optionFour;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getJieshi() {
		return jieshi;
	}

	public void setJieshi(String jieshi) {
		this.jieshi = jieshi;
	}

	public Integer getCourseVideoId() {
		return courseVideoId;
	}

	public void setCourseVideoId(Integer courseVideoId) {
		this.courseVideoId = courseVideoId;
	}

	@Override
	public String toString() {
		return "ChoiceQuestion [id=" + id + ", title=" + title + ", optionOne=" + optionOne + ", optionTwo=" + optionTwo
				+ ", optionThree=" + optionThree + ", optionFour=" + optionFour + ", answer=" + answer + ", jieshi="
				+ jieshi + ", courseVideoId=" + courseVideoId + "]";
	}


}
