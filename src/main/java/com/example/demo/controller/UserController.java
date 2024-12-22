package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*") // 允许所有来源的跨域请求
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User registeredUser = userService.register(user);
        Map<String, Object> response = new HashMap<>();
        if (registeredUser != null) {
            response.put("message", "注册成功");
            response.put("user", registeredUser);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "注册失败，用户名或手机号已存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 返回错误信息
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        User user = userService.login(username, password);
        Map<String, Object> response = new HashMap<>();
        if (user != null) {
            response.put("message", "登录成功");
            response.put("user", user);
            // 这里应该生成 token 并返回给前端，简化处理，直接返回用户信息
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "用户名或密码错误");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // 返回错误信息
        }
    }
}