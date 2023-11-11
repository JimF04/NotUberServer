package com.apiproyect.NotUberServer.Model;

public class LogIn {
    private String email;
    private String password;

    public LogIn() {
    }

    public LogIn(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }
}
