package models;

import java.sql.Timestamp;

/**
 * Base User model class.
 * This will be extended by AdminUser and NormalUser
 * to demonstrate inheritance & polymorphism.
 */
public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String role = "USER"; // ADMIN or USER
    private Timestamp createdAt;

    public User() {
    }

    // Default constructor for normal user (role = USER)
    public User(String name, String email, String password) {
        this(name, email, password, "USER");
    }

    // Full constructor with role (used by subclasses also)
    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Virtual-style method – subclasses (AdminUser, NormalUser)
     * can override this for polymorphic behaviour if needed.
     */
    public String getUserDescription() {
        return role + " - " + name + " (" + email + ")";
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
