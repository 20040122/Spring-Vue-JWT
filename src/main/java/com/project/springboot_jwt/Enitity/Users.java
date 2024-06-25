package com.project.springboot_jwt.Enitity;
import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(generator = "increment")
    private Integer userId;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private  String password;
    @Column(nullable = true)
    private String default_address;
    @Column(nullable = false)
    private String phone_number;

    public String getDefault_address() {
        return default_address;
    }
    public void setDefault_address(String default_address) {
        this.default_address = default_address;
    }

    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
