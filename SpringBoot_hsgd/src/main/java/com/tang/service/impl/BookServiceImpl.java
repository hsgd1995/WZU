package com.tang.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tang.entity.Book;
import com.tang.mapper.BookMapper;
import com.tang.service.IBookService;

/**
 * <p>
 * 书籍服务实现类
 * </p>
 *
 * @author tang
 * @since 2019-05-22
 */
@Service("bookService")
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {
	@Autowired
	private BookMapper bookMapper;

	@Override
	public List<Book> selectPage(Integer pageIndex, Integer pageSize,String bookName) {
		Wrapper<Book> wrapper = new EntityWrapper<Book>();
		wrapper.like(Book.BOOK_NAME, bookName);
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		return bookMapper.selectPage(rowBounds, wrapper);
	}

}
