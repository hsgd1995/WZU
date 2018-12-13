package com.hxbd.clp.domain;

/**
 * 简答题
 * @author Administrator
 *
 */
public class ShortAnswer {
	private Integer id;
	private String name;//题目
	private String answer;//答案
	
	public ShortAnswer() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "ShortAnswer [id=" + id + ", name=" + name + ", answer=" + answer + "]";
	}
	
	
}
