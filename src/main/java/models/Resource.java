package models;

import java.sql.Timestamp;

/**
 * Resource model class representing a cloud resource
 * (VM, Database, Storage, etc.) owned by a User.
 */
public class Resource {

    private int id;
    private int userId;
    private String name;
    private String type;
    private String configuration;
    private String status = "STOPPED"; // e.g., RUNNING, STOPPED
    private Timestamp createdAt;

    public Resource() {
    }

    public Resource(int userId, String name, String type, String configuration) {
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.configuration = configuration;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getConfiguration() {
        return configuration;
    }
    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
