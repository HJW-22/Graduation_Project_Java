package com.example.demo.mapper;

import lombok.Data;

@Data
public class BorrowRequestDto {
    private String userId;
    private String detailId;
    private String borrowDate;
}