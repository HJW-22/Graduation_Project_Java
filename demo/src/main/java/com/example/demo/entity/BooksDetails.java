package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date; // 或者使用 java.time.LocalDateTime;

@Data
@TableName("bookDetails") // 指明映射的表
public class BooksDetails {

    @TableField("detail_id")  // 确保这里指定了数据库列名
    private String detailId;

    @TableField("book_id")  // 确保这里指定了数据库列名
    private String bookId;

    @TableField("status") // 确保这里指定了数据库列名
    private BooksDetailsRole status; // 这里可以是 String，也可以考虑枚举

    @TableField("added_date")  // 确保这里指定了数据库列名
    private Date addedDate; // 或者 LocalDateTime

    @TableField("sequence_number")  // 确保这里指定了数据库列名
    private int sequenceNumber;
}