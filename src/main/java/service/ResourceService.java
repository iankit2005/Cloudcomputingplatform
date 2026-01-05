package service;

import database.DatabaseManager;
import models.Resource;

import java.util.List;

public class ResourceService {

    public boolean addResource(Resource resource) {
        return DatabaseManager.addResource(resource);
    }

    public List<Resource> getResourcesByUser(int userId) {
        return DatabaseManager.getResourcesByUser(userId);
    }

    // 🔥 REQUIRED FOR ADMIN
    public List<Resource> getAllResources() {
        return DatabaseManager.getAllResources();
    }

    public boolean updateResourceStatus(int resourceId, String status) {
        return DatabaseManager.updateResourceStatus(resourceId, status);
    }
}







