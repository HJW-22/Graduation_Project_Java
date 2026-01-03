package com.example.demo.service.serviceImpl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User loginService(String username, String password) {
        // 用户登录逻辑
        User user = userMapper.findByUsernameAndPassword(username, password);
        if (user != null) {
            user.setPassword(""); // 清空密码
        }
        return user;
    }

    @Override
    public User registerController(User user) {
        // 用户注册逻辑
        if (userMapper.findByUsername(user.getUsername()) != null) {
            return null; // 用户已存在
        } else {
            // 保存新用户
            int result = userMapper.insert(user); // 插入用户
            return result > 0 ? user : null; // 插入成功返回用户
        }
    }
}