package service;

import database.DatabaseManager;
import java.util.List;

public class BillingService {

    // 👤 USER BILLING
    public List<String[]> getUserBilling(int userId) {
        return DatabaseManager.getUserBilling(userId);
    }

    // 👑 ADMIN BILLING (ALL USERS)
    public List<String[]> getAdminBillingReport() {
        return DatabaseManager.getAdminBillingReport();
    }
}
