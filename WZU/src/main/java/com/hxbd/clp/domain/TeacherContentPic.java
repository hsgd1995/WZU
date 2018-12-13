package com.hxbd.clp.domain;

/**
 * 教师详细介绍图片类
 * @author Administrator
 *
 */
public class TeacherContentPic {
	private Integer id;
	private String url;
	private Teacher teacher;
	
	public TeacherContentPic() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Override
	public String toString() {
		return "TeacherContentPic [id=" + id + ", url=" + url + ", teacher=" + teacher + "]";
	}

	
	
}
