package com.tang.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 书籍
 * </p>
 *
 * @author tang
 * @since 2019-05-22
 */
public class Book extends Model<Book> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("book_name")
	private String bookName;


	public Integer getId() {
		return id;
	}

	public Book setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getBookName() {
		return bookName;
	}

	public Book setBookName(String bookName) {
		this.bookName = bookName;
		return this;
	}

	public static final String ID = "id";

	public static final String BOOK_NAME = "book_name";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Book{" +
			", id=" + id +
			", bookName=" + bookName +
			"}";
	}
}
