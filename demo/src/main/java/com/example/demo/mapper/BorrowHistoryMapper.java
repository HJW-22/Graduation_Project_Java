package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.BooksDetails;
import com.example.demo.entity.BorrowHistory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper // 必须添加此注解
public interface BorrowHistoryMapper extends BaseMapper<BorrowHistory> {

    @Select("SELECT bh.history_id, bh.userid, bh.detail_id, bh.borrow_at, bh.return_at, " +
            "bh.create_at, bh.update_at, b.title AS bookTitle, u.username AS username " +
            "FROM borrowHistory bh " +
            "JOIN bookDetails bd ON bh.detail_id = bd.detail_id " +
            "JOIN books b ON bd.book_id = b.book_id " + // 改为从 books 表中获取 title
            "JOIN users u ON bh.userid = u.userid " +
            "WHERE bh.userid = #{userId}")
    List<BorrowHistoryWithDetailsDTO> selectListByUserId(String userId);

    @Select("SELECT * FROM borrowHistory WHERE history_id = #{id}")
    BorrowHistory selectById(String id);


    @Delete("DELETE FROM borrowHistory WHERE history_id = #{id}")
    int deleteById(String id);

}
