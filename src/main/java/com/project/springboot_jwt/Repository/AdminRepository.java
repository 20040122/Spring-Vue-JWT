package com.project.springboot_jwt.Repository;

import com.project.springboot_jwt.Enitity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findBynameAndPassword(String name, String password);
}
