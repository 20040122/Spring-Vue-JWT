package com.project.springboot_jwt.Services;

import com.project.springboot_jwt.Repository.UserRepository;
import com.project.springboot_jwt.Enitity.Users;
import org.springframework.stereotype.Service;

@Service
public class UserServices {
    private final UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Users getUserByCredentials(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
    public Users getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
//注册
    public Users createUser(Users user) {

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            // 可以记录日志或者抛出自定义异常
            e.printStackTrace();
            return null;
        }
    }
}
