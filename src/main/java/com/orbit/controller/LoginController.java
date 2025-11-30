package com.orbit.controller;

import com.orbit.service.AuthService;
import javax.swing.JOptionPane;

public class LoginController {

    private AuthService authService;

    public LoginController() {
        this.authService = new AuthService();
    }

    public boolean handleLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username and Password cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        boolean isSuccess = authService.login(username, password);

        if (isSuccess) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Username or Password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}