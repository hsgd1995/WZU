package com.hxbd.clp.utils.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 导入Excel问题类
 * @author Administrator
 *
 */
public class ErrorImport<T> {
	
	private List<T> errorList = new ArrayList<>();//错误记录集合，课程视频主标题不存在的记录
	//private Integer errorListSize=0;//错误记录集合总数
	private Integer failIndex=0;//不合格记录所在行索引
	private List<T> failList= new ArrayList<>();//不合格记录集合，该行记录中有空的单元格
	
	
	
	public ErrorImport() {
		super();
	}
	public List<T> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<T> errorList) {
		this.errorList = errorList;
	}
	public Integer getFailIndex() {
		return failIndex;
	}
	public void setFailIndex(Integer failIndex) {
		this.failIndex = failIndex;
	}
	public List<T> getFailList() {
		return failList;
	}
	public void setFailList(List<T> failList) {
		this.failList = failList;
	}
	/*public Integer getErrorListSize() {
		return errorListSize;
	}
	public void setErrorListSize(Integer errorListSize) {
		this.errorListSize = errorListSize;
	}*/
	
	
	
}
