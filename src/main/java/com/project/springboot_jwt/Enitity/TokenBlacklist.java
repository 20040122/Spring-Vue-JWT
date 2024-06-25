package com.project.springboot_jwt.Enitity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tokenblacklist")
public class TokenBlacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryDate() {
        return expiry_date;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiry_date = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String token;

    private Date expiry_date;

    // Constructors, Getters and Setters
    // ...

    public TokenBlacklist() {
    }

    public TokenBlacklist(String token, Date expiryDate) {
        this.token = token;
        this.expiry_date = expiryDate;
    }
}
