package com.example.demo.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("borrowHistory") // 指明映射的表
public class BorrowHistory {

    @TableField("history_id")  // 确保这里指定了数据库列名
    private String historyId;


    private String userid;
    @TableField("detail_id")  // 确保这里指定了数据库列名
    private String detailId;
    @TableField("borrow_at")  // 确保这里指定了数据库列名
    private Date borrowAt;
    @TableField("return_at")  // 确保这里指定了数据库列名
    private Date returnAt;

    @TableField("create_at")  // 确保这里指定了数据库列名
    private Date createAt;

    @TableField("update_at")  // 确保这里指定了数据库列名
    private Date updateAt;

}