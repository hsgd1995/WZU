package com.tang.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.tang.entity.Book;

/**
 * <p>
 * 书籍服务类
 * </p>
 *
 * @author tang
 * @since 2019-05-22
 */
public interface IBookService extends IService<Book> {
	/**
	 * 分页
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<Book> selectPage(Integer pageIndex, Integer pageSize,String bookName);
}
