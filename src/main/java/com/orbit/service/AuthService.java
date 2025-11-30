package com.orbit.service;

import com.orbit.dao.UserDao;
import com.orbit.model.User;

public class AuthService {

    private UserDao userDao;
    private static User currentUser; 

    public AuthService() {
        this.userDao = new UserDao();
    }

    public boolean login(String username, String password) {
        User user = userDao.findByUsername(username);
        
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user; 
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isAdmin() {
        return currentUser != null && "ADMIN".equalsIgnoreCase(currentUser.getRole());
    }
}