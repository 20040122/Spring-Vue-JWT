package com.project.springboot_jwt.Repository;

import com.project.springboot_jwt.Enitity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsernameAndPassword(String username, String password);
    Users findByUsername(String username);

}
