package com.tang.mapper;

import com.tang.entity.Book;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * 书籍 Mapper 接口
 * </p>
 *
 * @author tang
 * @since 2019-05-22
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {

}