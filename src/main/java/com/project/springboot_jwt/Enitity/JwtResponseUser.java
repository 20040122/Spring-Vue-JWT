package com.project.springboot_jwt.Enitity;

public class JwtResponseUser {
    public class resUser{
        private String username;
        private String default_address;
        private String phone_number;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

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

        public resUser(String username, String default_address, String phone_number) {
            this.username = username;
            this.default_address = default_address;
            this.phone_number = phone_number;
        }
    }
    private String token;
    private  resUser user;

    public JwtResponseUser(String token, Users user) {
        this.token = token;
        this.user = new resUser(user.getUsername(),user.getDefault_address(),user.getPhone_number());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public resUser getUser() {
        return user;
    }

    public void setUser(resUser user) {
        this.user = user;
    }
}
