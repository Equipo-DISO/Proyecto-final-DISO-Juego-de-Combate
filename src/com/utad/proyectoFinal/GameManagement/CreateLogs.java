package com.utad.proyectoFinal.GameManagement; // Assuming this package

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class for managing application logs.
 * Limits the number of logs stored in memory and provides a method to write them to a file.
 */
public class CreateLogs {
    // Static initialization:
    private static final CreateLogs instance = new CreateLogs();

    private static final int MAX_LINEAS = 1000;
    private static final String FILE_NAME = "logs.txt";
    // Static final fields can be accessed directly
    private static final String PATH_TO_LOGS_FILE = FILE_NAME;

    // Instance fields
    private final List<String> lineas = new ArrayList<>(); // Use diamond operator and final
    private int lineasCount = 0; // Use int primitive

    // Private constructor to prevent external instantiation
    private CreateLogs() {
    }

    /**
     * Returns the singleton instance of CreateLogs.
     *
     * @return The singleton instance.
     */
    public static CreateLogs getInstance() {
        return instance;
    }

    /**
     * Adds a log entry. If the maximum number of lines is reached,
     * the oldest entry is removed.
     *
     * @param newLog The log message to add.
     */
    public synchronized void addLog(String newLog) { // Added synchronized for thread safety
        if (newLog != null) {
            if (this.lineasCount >= MAX_LINEAS) {
                if (!this.lineas.isEmpty()) { // Ensure list is not empty before removing
                    this.lineas.remove(0);
                    // lineasCount doesn't need decrementing as we add one right after
                }
            } else {
                this.lineasCount++; // Increment only if we are below the limit
            }

            System.out.println("Log added: " + newLog); // More informative debug message
            this.lineas.add(newLog);
        } else {
            System.err.println("Error: Cannot add a null log entry."); // Use System.err for errors
        }
    }

    /**
     * Writes all currently stored log entries to the log file and clears the in-memory store.
     *
     * @return The number of lines written to the file.
     */
    public synchronized int printLogs() { // Added synchronized for thread safety
        int printedLines = 0; // Use int primitive

        // Use try-with-resources for automatic closing of FileWriter
        try (FileWriter writer = new FileWriter(PATH_TO_LOGS_FILE, true)) { // Append mode

            writer.write("\n\n--- Log Dump [" + java.time.LocalDateTime.now() + "] ---\n\n"); // Add timestamp

            for (String log : this.lineas) {
                writer.write(log + "\n"); // Use platform-independent newline
                printedLines++;
            }

            writer.write("\n--- End Log Dump ---\n\n");

            // Clear the logs after successfully writing them
            this.lineas.clear();
            this.lineasCount = 0;

            System.out.println("Successfully wrote " + printedLines + " logs to " + PATH_TO_LOGS_FILE);

        } catch (FileNotFoundException e) {
            System.err.println("Error: Log file not found: " + PATH_TO_LOGS_FILE);
            e.printStackTrace(); // Print stack trace for debugging
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + PATH_TO_LOGS_FILE);
            e.printStackTrace(); // Print stack trace for debugging
        }

        return printedLines;
    }
}