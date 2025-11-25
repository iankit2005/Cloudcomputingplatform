import database.DatabaseManager;
import models.User;
import models.Resource;

import java.util.List;

public class TestDB {
    public static void main(String[] args) {
        System.out.println("Testing DB connection...");
        boolean ok = DatabaseManager.testConnection();
        System.out.println("DB connection test: " + (ok ? "SUCCESS" : "FAILED"));

        // Example user
        User admin = new User("Admin User", "admin@example.com", "admin123", "ADMIN");
        boolean addedAdmin = DatabaseManager.addUser(admin);
        System.out.println("Admin added: " + addedAdmin + " (id=" + admin.getId() + ")");

        // Example normal user
        User u = new User("Test User", "user1@example.com", "user123", "USER");
        boolean addedUser = DatabaseManager.addUser(u);
        System.out.println("User added: " + addedUser + " (id=" + u.getId() + ")");

        if (addedUser) {
            Resource r = new Resource(u.getId(), "Test VM", "VM", "2CPU, 4GB RAM");
            boolean resAdded = DatabaseManager.addResource(r);
            System.out.println("Resource added: " + resAdded + " (id=" + r.getId() + ")");
        }

        List<User> users = DatabaseManager.getAllUsers();
        System.out.println("All users in DB:");
        for (User user : users) {
            System.out.println(user);
        }
    }
}
