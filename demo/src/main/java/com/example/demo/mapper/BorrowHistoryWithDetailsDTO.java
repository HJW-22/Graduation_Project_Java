package com.example.demo.mapper;

import lombok.Data;

import java.util.Date;

@Data
public class BorrowHistoryWithDetailsDTO {
    private String historyId;
    private String userId;
    private String detailId;
    private Date borrowAt;
    private Date returnAt;
    private Date createAt;
    private Date updateAt;
    private String bookTitle;   // 书名
    private String username;     // 用户名


}