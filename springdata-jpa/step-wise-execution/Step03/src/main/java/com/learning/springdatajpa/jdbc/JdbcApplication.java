package com.learning.springdatajpa.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Main application class that initializes the database and launches the JdbcStudentApp.
 * This class serves as the entry point for the JDBC-based student management system.
 */
public class JdbcApplication {

    public static void main(String[] args) {
        System.out.println("Starting JDBC Student Management Application...");
        
        try {
            // Initialize the database schema and sample data
            initializeDatabase();
            
            // Start the Student Management Application
            launchStudentApp();
            
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Initializes the database by creating schema and loading sample data
     */
    private static void initializeDatabase() {
        System.out.println("Initializing database...");
        
        // Call the static method from JdbcDatabaseInitializer
        boolean success = JdbcDatabaseInitializer.initializeDatabase();
        
        if (success) {
            System.out.println("Database initialization completed successfully.");
        } else {
            String errorMessage = "Database initialization failed";
            System.err.println(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }
    
    /**
     * Launches the Student Management Application
     */
    private static void launchStudentApp() {
        System.out.println("Launching Student Management Application...");
        
        try {
            // Call the JdbcStudentApp's main method to start the application
            String[] args = new String[0]; // empty args array
            JdbcStudentApp.main(args);
            
        } catch (Exception e) {
            System.err.println("Error launching Student Management Application: " + e.getMessage());
            throw new RuntimeException("Failed to launch application", e);
        }
    }
}

