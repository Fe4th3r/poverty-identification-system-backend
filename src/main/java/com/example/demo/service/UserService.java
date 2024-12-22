package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        // 简单的注册逻辑, 仅做演示，实际项目中需要加密密码等操作
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return null; // 用户名已存在
        }
        if (userRepository.findByPhoneNumber(user.getPhoneNumber())!= null){
            return null; // 手机号已存在
        }
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        // 简单的登录逻辑，仅做演示
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User findByPhoneNumber(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    // ... 其他关于用户操作的方法，例如找回密码等，此处省略
}