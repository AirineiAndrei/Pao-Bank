package audit;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.File;
public class AuditService {
    private static final String CSV_FILE_PATH = "audit.csv";
    private static final String CSV_SEPARATOR = ",";
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static AuditService instance;

    private AuditService() {
        // private constructor to enforce singleton pattern
        createAuditFileIfNotExists();
    }

    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public void writeAuditEntry(String actionName) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);

        String auditEntry = actionName + CSV_SEPARATOR + timestamp;

        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE_PATH, true))) {
            writer.println(auditEntry);
        } catch (IOException e) {
            System.out.println("Failed to write audit entry: " + e.getMessage());
        }
    }

    private void createAuditFileIfNotExists() {
        File file = new File(CSV_FILE_PATH);
        if (!file.exists()) {
            try {
                boolean exist = file.createNewFile();
                if(!exist)
                    throw new RuntimeException("Unable to create audit file...");
            } catch (IOException e) {
                System.out.println("Failed to create audit file: " + e.getMessage());
            }
        }
    }
}

