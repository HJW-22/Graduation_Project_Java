package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // 引入 BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<User> loginService(@RequestParam String username, @RequestParam String password) {
        User user = userService.loginService(username, password);
        if (user != null) {
            return ResponseEntity.ok(user); // 登录成功，返回用户信息
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        user.setUserid(generateUserId()); // 生成并设置新的 userid
        User newUser = userService.registerController(user);
        if (newUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("注册成功");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("用户名已存在");
        }
    }

    private String generateUserId() {
        String prefix = "CD";
        String date = new SimpleDateFormat("yyMMdd").format(new Date());
        String uniqueId = UUID.randomUUID().toString().substring(0, 4); // 使用 UUID 的前 4 位
        return prefix + date + uniqueId; // 合成最终的 userid
    }

//    // 查询全部用户
//    @GetMapping
//    public List<User> list() {
//        return userMapper.selectList(null);
//    }

    // 根据 userid 查询用户
    @GetMapping("/{userid}")
    public ResponseEntity<User> get(@PathVariable String userid) {
        User user = userMapper.selectById(userid); // 使用 userid 作为参数，确保类型一致
        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 添加新用户
    @PostMapping
    public ResponseEntity<String> add(@RequestBody User user) {
        user.setUserid(generateUserId()); // 生成并设置新的 userid
        int result = userMapper.insert(user);
        return result > 0
                ? ResponseEntity.status(HttpStatus.CREATED).body("添加成功")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("添加失败");
    }

    // 删除用户
    @DeleteMapping("/{userid}")
    public ResponseEntity<String> delete(@PathVariable String userid) {
        int result = userMapper.deleteById(userid); // 使用 userid 作为参数，确保类型一致
        return result > 0
                ? ResponseEntity.ok("删除成功")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("删除失败");
    }

    @PutMapping("/{userid}")
    public ResponseEntity<String> update(@PathVariable String userid, @RequestBody User user) {
        // 检查 user 对象是否包含必要的信息
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("缺少必要的参数: username");
        }
        // 可以添加其他字段检查
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("缺少必要的参数: email");
        }
        // 设置传入的 userid
        user.setUserid(userid); // 确保设置 userid
        try {
            int result = userMapper.updateById(user); // 更新用户信息
            return result > 0
                    ? ResponseEntity.ok("更新成功")
                    : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新失败，用户未找到或数据未改变");
        } catch (Exception e) {
            // 在这里可以记录异常日志以便后续排查
            e.printStackTrace(); // 或使用日志记录框架
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器发生错误，请稍后重试");
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> list(@RequestParam(required = false) String search) {
        List<User> users;

        if (search != null && !search.isEmpty()) {
            users = userMapper.selectBySearch(search); // 你需要实现这个方法来过滤
        } else {
            users = userMapper.selectList(null);
        }

        return ResponseEntity.ok(users);
    }



}