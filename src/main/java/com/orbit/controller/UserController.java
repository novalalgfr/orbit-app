package com.orbit.controller;

import com.orbit.dao.UserDao;
import com.orbit.model.User;
import java.util.List;
import javax.swing.JOptionPane;

public class UserController {

    private UserDao userDao;

    public UserController() {
        this.userDao = new UserDao();
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public boolean addUser(String fullName, String username, String password, String role) {
        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required!");
            return false;
        }
        try {
            if (userDao.findByUsername(username) != null) {
                JOptionPane.showMessageDialog(null, "Username already taken!");
                return false;
            }

            User user = new User(username, password, fullName, role);
            userDao.save(user);
            JOptionPane.showMessageDialog(null, "User Added Successfully!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(int id, String fullName, String username, String password, String role) {
        try {
            User user = userDao.findAll().stream().filter(u -> u.getId() == id).findFirst().orElse(null);
            
            if (user != null) {
                user.setFullName(fullName);
                user.setUsername(username);
                user.setRole(role);
                
                if (!password.isEmpty()) {
                    user.setPassword(password);
                }

                userDao.update(user);
                JOptionPane.showMessageDialog(null, "User Updated Successfully!");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(int id) {
        try {
            userDao.delete(id);
            JOptionPane.showMessageDialog(null, "User Deleted Successfully!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting user. Maybe they are assigned to tickets?");
            return false;
        }
    }
}