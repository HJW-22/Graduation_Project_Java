package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book> {
    /**
     * 根据搜索条件筛选书籍
     * @param search 查询字符串，可以是书名、作者、出版社、ISBN等
     * @return 符合条件的书籍列表
     */
    @Select({
            "<script>",
            "SELECT * FROM books",
            "WHERE title LIKE CONCAT('%', #{search}, '%')",
            "   OR author LIKE CONCAT('%', #{search}, '%')",
            "   OR publisher LIKE CONCAT('%', #{search}, '%')",
            "   OR isbn LIKE CONCAT('%', #{search}, '%')",
            "   OR book_id LIKE CONCAT('%', #{search}, '%')",
            "   OR published_date LIKE CONCAT('%', #{search}, '%')",
            "   OR category LIKE CONCAT('%', #{search}, '%')",
            "</script>"
    })
    List<Book> selectBySearch(String search);

}