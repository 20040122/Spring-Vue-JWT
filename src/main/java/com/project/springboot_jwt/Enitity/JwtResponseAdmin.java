package com.project.springboot_jwt.Enitity;

public class JwtResponseAdmin {
    private String token;

    public JwtResponseAdmin(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

}
