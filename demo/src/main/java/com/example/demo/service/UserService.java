package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User loginService(String username, String password);
    User registerController(User user); // 确保这里的命名一致
}