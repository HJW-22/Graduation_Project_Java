package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("users") // 指明映射的表
public class User {
    @TableId(value = "userid", type = IdType.INPUT) // 将ID类型改为 INPUT，手动生成
    private String userid; // 改为 String 类型
    private String username;
    private String password;
    private String email;
    // 与数据库的列名进行匹配
    @TableField("phoneNumber")  // 确保这里指定了数据库列名
    private String phoneNumber; // 在 Java 对象中使用实际的字段名

    private UserRole role;

    @TableField("created_at")  // 确保这里指定了数据库列名
    private Date createdAt; // 创建时间

    @TableField("updated_at")  // 确保这里指定了数据库列名
    private Date updatedAt; // 更新时间

}
