package service;

import database.DatabaseManager;
import models.User;

import java.util.List;

public class UserService {

    // 🔹 Login
    public User login(String email, String password) {
        return DatabaseManager.authenticateUser(email, password);
    }

    // 🔹 Register user (already present)
    public boolean registerUser(User user) {
        return DatabaseManager.addUser(user);
    }

    // 🔹 ADD USER (Admin CRUD – NEW)
    public boolean addUser(User user) {
        return DatabaseManager.addUser(user);
    }

    // 🔹 Fetch all users (Admin)
    public List<User> getAllUsers() {
        return DatabaseManager.getAllUsers();
    }
}
