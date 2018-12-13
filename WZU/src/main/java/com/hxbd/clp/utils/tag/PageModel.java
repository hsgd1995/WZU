package com.hxbd.clp.utils.tag;

import java.util.List;

import com.hxbd.clp.utils.common.RasConstants;


/**
 * 分页实体类
 * 
 * @author entor83
 *
 */
public class PageModel<T> {

	private int firstLimitParam;// 偏移量
	private int recordCount;// 分页总数据条数
	private int pageIndex;// 当前页
	private int pageSize = RasConstants.PAGE_DEFAULT_SIZE;// 每页记录条数，默认为8
	private int totalSize;// 总页数
	private List<T> list;// 分页数据

	public int getRecordCount() {
		this.recordCount = this.recordCount <= 0 ? 0 : this.recordCount;
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getPageIndex() {
		// 如果当前页码小于0，重置为1
		this.pageIndex = this.pageIndex <= 0 ? 1 : this.pageIndex;
		// 如果当前页码大于总页数，重置为最大页数
		this.pageIndex = this.pageIndex >= this.getTotalSize() ? this.getTotalSize() : this.pageIndex;
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		// 如果每页显示记录数小于默认分页数，重置为默认分页数
		this.pageSize = this.pageSize <= RasConstants.PAGE_DEFAULT_SIZE ? RasConstants.PAGE_DEFAULT_SIZE
				: this.pageSize;
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalSize() {
		// 如果没有记录，也就没有分页
		if (this.getRecordCount() <= 0) {
			this.totalSize = 0;
		} else {
			// 计算总页数
			this.totalSize = (this.getRecordCount() - 1) / this.getPageSize() + 1;
		}
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	/**
	 * 分页偏移量（limit的第1个参数）
	 * 
	 * @return
	 */
	public int getFirstLimitParam() {
		this.firstLimitParam = (this.getPageIndex() - 1) * this.pageSize;
		return firstLimitParam;
	}

	public void setFirstLimitParam(int firstLimitParam) {
		this.firstLimitParam = firstLimitParam;
	}

}
