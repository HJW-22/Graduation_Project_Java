package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper // 必须添加此注解
public interface UserMapper extends BaseMapper<User> {

    // 根据用户名查找用户
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

    // 根据用户名和密码查找用户
    @Select("SELECT * FROM users WHERE username = #{username} AND password = #{password}")
    User findByUsernameAndPassword(String username, String password);


    /**
     * 根据搜索条件筛选书籍
     * @param search 查询字符串，可以是书名、作者、出版社、ISBN等
     * @return 符合条件的书籍列表
     */
    @Select({
            "<script>",
            "SELECT * FROM users",
            "WHERE userid LIKE CONCAT('%', #{search}, '%')",
            "   OR username LIKE CONCAT('%', #{search}, '%')",
            "   OR password LIKE CONCAT('%', #{search}, '%')",
            "   OR email LIKE CONCAT('%', #{search}, '%')",
            "   OR phoneNumber LIKE CONCAT('%', #{search}, '%')",
            "   OR role LIKE CONCAT('%', #{search}, '%')",
            "   OR created_at LIKE CONCAT('%', #{search}, '%')",
            "   OR updated_at LIKE CONCAT('%', #{search}, '%')",
            "</script>"
    })
    List<User> selectBySearch(String search);
}