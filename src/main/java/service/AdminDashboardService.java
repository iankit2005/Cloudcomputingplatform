package service;

import database.DatabaseManager;

public class AdminDashboardService {

    public double getTotalRevenue() {
        return DatabaseManager.getTotalRevenue();
    }

    public int getTotalUsageMinutes() {
        return DatabaseManager.getTotalUsageMinutes();
    }

    public int getRunningResources() {
        return DatabaseManager.getRunningResourceCount();
    }

    public int getStoppedResources() {
        return DatabaseManager.getStoppedResourceCount();
    }
}
