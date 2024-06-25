package com.project.springboot_jwt.Controler;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.springboot_jwt.Enitity.JwtResponseUser;
import com.project.springboot_jwt.Enitity.Users;
import com.project.springboot_jwt.Repository.UserRepository;
import com.project.springboot_jwt.Services.UserServices;
import com.project.springboot_jwt.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserServices userService;
    private final JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UsersController(UserServices userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    //注册
    @PostMapping("/register")
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        if(userService.getUserByUsername(user.getUsername()) != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        try {
            Users savedUser = userService.createUser(user);
            if (savedUser != null) {
                return ResponseEntity.status(HttpStatus.CREATED).build();

            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            // 记录详细的日志信息
            System.err.println("Error creating user: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/checkName")
    public ResponseEntity<Boolean> checkName(@RequestBody String username) {
        if(userService.getUserByUsername(username) != null){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }

    //登录
    @PostMapping("/login")
    public ResponseEntity<JwtResponseUser> login(@RequestBody Users user) {
        try {
            Users existingUser = userService.getUserByCredentials(user.getUsername(), user.getPassword());
            if (existingUser != null) {
                existingUser.setPassword(null);
                String token = jwtUtil.generateToken(existingUser.getUsername());
                JwtResponseUser jwtResponseUser = new JwtResponseUser(token,existingUser);
                return ResponseEntity.ok(jwtResponseUser);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            System.err.println("Error logging in user: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
  //测试拦截器
    @GetMapping("/test")
    public ResponseEntity<String> test(HttpServletRequest request) {
        return ResponseEntity.ok("Test successful");
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("token");
        if (authorizationHeader != null ) {
            // 将 token 加入黑名单
            jwtUtil.addToBlacklist(authorizationHeader);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/updateUserProfile")
    public ResponseEntity<String> updateUserProfile(HttpServletRequest request, @RequestBody Users new_user) {
        String authorizationHeader = request.getHeader("token");
        if (authorizationHeader != null && jwtUtil.validateToken(authorizationHeader)) {
            String userName = jwtUtil.extractUserName(authorizationHeader);
            Users user = userService.getUserByUsername(userName);
            user.setUsername(new_user.getUsername());
            user.setDefault_address(new_user.getDefault_address());
            user.setPhone_number(new_user.getPhone_number());
            userRepository.save(user);
            return ResponseEntity.ok("Update success");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/updateUserPassword")
    public ResponseEntity<String> updateUserPassword(HttpServletRequest request, @RequestBody JsonNode passwords) {
        String new_password = passwords.path("newPassword").asText();
        String authorizationHeader = request.getHeader("token");

        if (authorizationHeader != null && jwtUtil.validateToken(authorizationHeader)) {
            String userName = jwtUtil.extractUserName(authorizationHeader);
            Users user = userService.getUserByUsername(userName);
            String password = user.getPassword(); // 获取原密码

            if(new_password == null || password.equals(new_password)){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            user.setPassword(new_password);
            userRepository.save(user);
            return ResponseEntity.ok("Update success");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

