package com.project.springboot_jwt.Services;

import com.project.springboot_jwt.Enitity.Admin;
import com.project.springboot_jwt.Repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    public Admin getByCredentials(String name, String password) {
        return adminRepository.findBynameAndPassword(name, password);
    }
}
