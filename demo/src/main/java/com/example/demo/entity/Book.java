package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("books") // 指明映射的表
public class Book {
    @TableId(value = "book_id", type = IdType.AUTO) // 将ID类型设置为自增 ID



    private String bookId; // 与数据库的 book_id 直接对应，改为 Integer 类型

    private String title; // 与数据库的 title 列对应
    private String author; // 与数据库的 author 列对应
    private String publisher; // 与数据库的 publisher 列对应

    @TableField("published_date") // 映射数据库列名 published_date
    private Date publishedDate; // 与数据库的 published_date 列对应

    private String isbn; // 与数据库的 isbn 列对应

    private String category; // 与数据库的 category 列对应

    @TableField("stock_quantity") // 映射数据库列名 stock_quantity
    private Integer stockQuantity; // 与数据库的 stock_quantity 列对应


}