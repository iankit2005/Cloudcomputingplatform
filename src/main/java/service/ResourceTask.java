package service;

import database.DatabaseManager;
import utils.AppLogger;

public class ResourceTask implements Runnable {

    private final int resourceId;
    private final String targetStatus;

    public ResourceTask(int resourceId, String targetStatus) {
        this.resourceId = resourceId;
        this.targetStatus = targetStatus;
    }

    @Override
    public void run() {
        synchronized (ResourceTask.class) { // 🔒 THREAD SAFETY
            try {
                AppLogger.log("THREAD START → Resource ID: "
                        + resourceId + ", Target: " + targetStatus);

                // Simulate cloud delay
                Thread.sleep(3000);

                DatabaseManager.updateResourceStatus(resourceId, targetStatus);

                // 🔥 USAGE + BILLING LOGIC
                if ("RUNNING".equalsIgnoreCase(targetStatus)) {
                    DatabaseManager.startUsage(resourceId);
                } else {
                    DatabaseManager.stopUsage(resourceId);
                }

                AppLogger.log("THREAD COMPLETE → Resource ID: "
                        + resourceId + " updated to " + targetStatus);

            } catch (Exception e) {
                AppLogger.log("THREAD ERROR → " + e.getMessage());
            }
        }
    }
}
