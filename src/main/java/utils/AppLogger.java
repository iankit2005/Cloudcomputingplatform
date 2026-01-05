package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AppLogger {

    private static final String LOG_FILE = "cloud-platform.log";

    // 🔒 synchronized → thread safe
    public static synchronized void log(String message) {
        String logMessage =
                "[" + LocalDateTime.now() + "] " + message;

        // Console
        System.out.println(logMessage);

        // File
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(logMessage + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Logger error: " + e.getMessage());
        }
    }
}
