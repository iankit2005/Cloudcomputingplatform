package database;

import models.AdminUser;
import models.NormalUser;
import models.Resource;
import models.User;
import utils.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("MySQL JDBC Driver not found. Add connector JAR later.");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
    }

    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // -------- USER OPERATIONS --------
    public static boolean addUser(User user) {
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, user.getName());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getRole());

            int affected = pst.executeUpdate();
            if (affected == 1) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException ex) {
            System.err.println("Error in addUser: " + ex.getMessage());
        }
        return false;
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, email, role, created_at FROM users ORDER BY id";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String role = rs.getString("role");
                User u;

                if ("ADMIN".equalsIgnoreCase(role)) {
                    u = new AdminUser();
                } else {
                    u = new NormalUser();
                }

                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setRole(role);
                u.setCreatedAt(rs.getTimestamp("created_at"));

                users.add(u);     // GENERICS + COLLECTIONS proof
            }
        } catch (SQLException ex) {
            System.err.println("Error in getAllUsers: " + ex.getMessage());
        }
        return users;
    }

    // -------- LOGIN / AUTHENTICATION --------
    public static User authenticateUser(String email, String password) {
        String sql = "SELECT id, name, email, role, created_at FROM users WHERE email = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, email);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    User u;

                    if ("ADMIN".equalsIgnoreCase(role)) {
                        u = new AdminUser();
                    } else {
                        u = new NormalUser();
                    }

                    u.setId(rs.getInt("id"));
                    u.setName(rs.getString("name"));
                    u.setEmail(rs.getString("email"));
                    u.setRole(role);
                    u.setCreatedAt(rs.getTimestamp("created_at"));

                    return u;  // POLYMORPHISM
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error in authenticateUser: " + ex.getMessage());
        }
        return null; // not found / error
    }


    // -------- RESOURCE OPERATIONS --------
    public static boolean addResource(Resource r) {
        String sql = "INSERT INTO resources (user_id, name, type, configuration, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, r.getUserId());
            pst.setString(2, r.getName());
            pst.setString(3, r.getType());
            pst.setString(4, r.getConfiguration());
            pst.setString(5, r.getStatus());

            int affected = pst.executeUpdate();
            if (affected == 1) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        r.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException ex) {
            System.err.println("Error in addResource: " + ex.getMessage());
        }
        return false;
    }

    public static List<Resource> getResourcesByUser(int userId) {
        List<Resource> list = new ArrayList<>();
        String sql = "SELECT id, user_id, name, type, configuration, status, created_at FROM resources WHERE user_id = ? ORDER BY id";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Resource r = new Resource();
                    r.setId(rs.getInt("id"));
                    r.setUserId(rs.getInt("user_id"));
                    r.setName(rs.getString("name"));
                    r.setType(rs.getString("type"));
                    r.setConfiguration(rs.getString("configuration"));
                    r.setStatus(rs.getString("status"));
                    r.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(r);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error in getResourcesByUser: " + ex.getMessage());
        }
        return list;
    }
}
