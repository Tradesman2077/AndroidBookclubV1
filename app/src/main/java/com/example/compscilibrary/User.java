package com.example.compscilibrary;

public class User {
    private String email;
    private String password;
    private String id;

    public User(){}

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
