package com.project.springboot_jwt.Controler;


import com.project.springboot_jwt.Enitity.Admin;
import com.project.springboot_jwt.Enitity.JwtResponseAdmin;
import com.project.springboot_jwt.Enitity.JwtResponseUser;

import com.project.springboot_jwt.Services.AdminService;
import com.project.springboot_jwt.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final JwtUtil jwtUtil;
    @Autowired
    public AdminController(AdminService adminService,JwtUtil jwtUtil) {
        this.adminService = adminService;
        this.jwtUtil = jwtUtil;
    }
    //登录
    @PostMapping("/login")
    public ResponseEntity<JwtResponseAdmin> login(@RequestBody Admin admin) {
        try {
            Admin existingAdmin = adminService.getByCredentials(admin.getName(), admin.getPassword());
            if (existingAdmin != null) {
                String token = jwtUtil.generateToken(existingAdmin.getName());
                JwtResponseAdmin jwtResponseAdmin = new JwtResponseAdmin(token);
                return ResponseEntity.ok(jwtResponseAdmin);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            System.err.println("Error logging in admin: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
