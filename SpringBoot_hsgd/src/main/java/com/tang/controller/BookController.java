package com.tang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.entity.Book;
import com.tang.service.IBookService;

/**
 * <p>
 * 书籍前端控制器
 * </p>
 *
 * @author tang
 * @since 2019-05-22
 */
@RestController
@RequestMapping("/book")
public class BookController {
	@Autowired
	private IBookService bookService;

	@RequestMapping("getBookById")
	public Object getBookById(Integer id) {
		return bookService.selectById(id);
	}

	@RequestMapping("selectPage")
	public List<Book> selectPage(Integer pageIndex, Integer pageSize,String bookName) {
		return bookService.selectPage(pageIndex, pageSize,bookName);
	}
}
