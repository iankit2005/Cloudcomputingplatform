package service;

import database.DatabaseManager;
import java.util.List;

public class AdminBillingService {

    public List<String[]> getReport() {
        return DatabaseManager.getAdminBillingReport();
    }

    public double getTotalRevenue() {
        return DatabaseManager.getTotalRevenue();
    }

    public int getTotalUsageMinutes() {
        return DatabaseManager.getTotalUsageMinutes();
    }
}
