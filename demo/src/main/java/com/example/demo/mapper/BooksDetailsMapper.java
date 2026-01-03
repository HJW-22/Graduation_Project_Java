package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.BooksDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper // 必须添加此注解
public interface BooksDetailsMapper extends BaseMapper<BooksDetails> {

    // 根据 bookId 查询多条书籍详情
    @Select("SELECT detail_id, book_id, status, added_date FROM bookDetails WHERE book_id = #{bookId}")
    List<BooksDetails> selectListByBookId(String bookId);

    // 根据 detailId 查询单条书籍详情
    @Select("SELECT detail_id, book_id, status, added_date FROM bookDetails WHERE detail_id = #{detailId}")
    BooksDetails selectById(String detailId);

    @Update("UPDATE bookDetails SET status = #{status} WHERE detail_id = #{detailId}")
    int updateById(BooksDetails bookDetail);
}