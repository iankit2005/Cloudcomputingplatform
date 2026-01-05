package database;

import models.AdminUser;
import models.NormalUser;
import models.Resource;
import models.User;
import models.UsageRecord;
import utils.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    // ================= JDBC DRIVER =================
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException ex) {
            System.err.println("MySQL JDBC Driver not found");
        }
    }

    // ================= CONNECTION =================
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DBConfig.URL,
                DBConfig.USER,
                DBConfig.PASSWORD
        );
    }

    // ================= TEST DB =================
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    // ================= USERS =================
    public static boolean addUser(User user) {
        String sql = "INSERT INTO users (name,email,password,role) VALUES (?,?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());

            if (ps.executeUpdate() == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) user.setId(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("addUser error: " + e.getMessage());
        }
        return false;
    }

    public static List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String role = rs.getString("role");
                User u = "ADMIN".equalsIgnoreCase(role)
                        ? new AdminUser()
                        : new NormalUser();

                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setRole(role);
                u.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(u);
            }
        } catch (SQLException e) {
            System.err.println("getAllUsers error: " + e.getMessage());
        }
        return list;
    }

    // ================= LOGIN =================
    public static User authenticateUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email=? AND password=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                User u = "ADMIN".equalsIgnoreCase(role)
                        ? new AdminUser()
                        : new NormalUser();

                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setRole(role);
                u.setCreatedAt(rs.getTimestamp("created_at"));
                return u;
            }
        } catch (SQLException e) {
            System.err.println("authenticateUser error: " + e.getMessage());
        }
        return null;
    }

    // ================= RESOURCES =================
    public static boolean addResource(Resource r) {
        String sql = "INSERT INTO resources (user_id,name,type,configuration,status) VALUES (?,?,?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, r.getUserId());
            ps.setString(2, r.getName());
            ps.setString(3, r.getType());
            ps.setString(4, r.getConfiguration());
            ps.setString(5, r.getStatus());

            if (ps.executeUpdate() == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) r.setId(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("addResource error: " + e.getMessage());
        }
        return false;
    }

    public static List<Resource> getResourcesByUser(int userId) {
        List<Resource> list = new ArrayList<>();
        String sql = "SELECT * FROM resources WHERE user_id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

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
        } catch (SQLException e) {
            System.err.println("getResourcesByUser error: " + e.getMessage());
        }
        return list;
    }

    public static List<Resource> getAllResources() {
        List<Resource> list = new ArrayList<>();
        String sql = "SELECT * FROM resources ORDER BY id";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
        } catch (SQLException e) {
            System.err.println("getAllResources error: " + e.getMessage());
        }
        return list;
    }

    public static boolean updateResourceStatus(int resourceId, String status) {
        String sql = "UPDATE resources SET status=? WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, resourceId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    // ================= BILLING / USAGE =================
    public static void startUsage(int resourceId) {
        String sql = "INSERT INTO resource_usage (resource_id,start_time) VALUES (?,NOW())";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, resourceId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("startUsage error: " + e.getMessage());
        }
    }

    public static void stopUsage(int resourceId) {
        String sql = """
            UPDATE resource_usage
            SET end_time = NOW(),
                duration_minutes = TIMESTAMPDIFF(MINUTE,start_time,NOW()),
                cost = TIMESTAMPDIFF(MINUTE,start_time,NOW()) * 1.6
            WHERE resource_id=? AND end_time IS NULL
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, resourceId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("stopUsage error: " + e.getMessage());
        }
    }

    // ================= USER BILLING =================
    public static List<String[]> getUserBilling(int userId) {
        List<String[]> list = new ArrayList<>();

        String sql = """
        SELECT r.name,u.start_time,u.end_time,u.duration_minutes,u.cost
        FROM resource_usage u
        JOIN resources r ON u.resource_id=r.id
        WHERE r.user_id=?
        ORDER BY u.start_time DESC
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new String[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                });
            }
        } catch (SQLException e) {
            System.err.println("getUserBilling error: " + e.getMessage());
        }
        return list;
    }

    // ================= ADMIN BILLING REPORT =================
    public static List<String[]> getAdminBillingReport() {
        List<String[]> list = new ArrayList<>();

        String sql = """
        SELECT r.name,r.user_id,
               u.start_time,u.end_time,
               u.duration_minutes,u.cost
        FROM resource_usage u
        JOIN resources r ON u.resource_id=r.id
        ORDER BY u.start_time DESC
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new String[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                });
            }
        } catch (SQLException e) {
            System.err.println("getAdminBillingReport error: " + e.getMessage());
        }
        return list;
    }

    // ================= ADMIN DASHBOARD METRICS =================
    public static double getTotalRevenue() {
        String sql = "SELECT IFNULL(SUM(cost),0) FROM resource_usage";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.err.println("getTotalRevenue error");
        }
        return 0;
    }

    public static int getTotalUsageMinutes() {
        String sql = "SELECT IFNULL(SUM(duration_minutes),0) FROM resource_usage";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("getTotalUsageMinutes error");
        }
        return 0;
    }
    // ================= ADMIN DASHBOARD COUNTERS =================

    // ▶ RUNNING RESOURCES COUNT
    public static int getRunningResourceCount() {
        String sql = "SELECT COUNT(*) FROM resources WHERE status='RUNNING'";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("getRunningResourceCount error: " + e.getMessage());
        }
        return 0;
    }

    // ■ STOPPED RESOURCES COUNT
    public static int getStoppedResourceCount() {
        String sql = "SELECT COUNT(*) FROM resources WHERE status='STOPPED'";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("getStoppedResourceCount error: " + e.getMessage());
        }
        return 0;
    }
// ================= AUDIT LOG =================

    // ➕ ADD LOG
    public static void addAuditLog(String actor, String action) {
        String sql = "INSERT INTO audit_logs (actor, action) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, actor);
            ps.setString(2, action);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("addAuditLog error: " + e.getMessage());
        }
    }

    // 📄 FETCH LOGS
    public static List<String[]> getAuditLogs() {

        List<String[]> list = new ArrayList<>();
        String sql = "SELECT actor, action, timestamp FROM audit_logs ORDER BY timestamp DESC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new String[]{
                        rs.getString("actor"),
                        rs.getString("action"),
                        rs.getString("timestamp")
                });
            }
        } catch (SQLException e) {
            System.err.println("getAuditLogs error: " + e.getMessage());
        }

        return list;
    }

}
