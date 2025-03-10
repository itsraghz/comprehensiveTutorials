package com.learning.springdatajpa;

import java.sql.SQLException;

import com.learning.springdatajpa.db.H2DatabaseUtil;

/**
 * Demo class to demonstrate H2 database connectivity and basic operations
 * using the H2DatabaseUtil class.
 */
public class H2DatabaseDemo {

    public static void main(String[] args) {
        System.out.println("H2 Database Demo - Starting");
        
        try {
            // Create the student table
            H2DatabaseUtil.createStudentTable();
            
            // Insert sample student records
            H2DatabaseUtil.insertSampleStudent("John Doe", "john.doe@example.com", 20);
            H2DatabaseUtil.insertSampleStudent("Jane Smith", "jane.smith@example.com", 22);
            H2DatabaseUtil.insertSampleStudent("Bob Johnson", "bob.johnson@example.com", 21);
            
            // Query and display all student records
            H2DatabaseUtil.printAllStudents();
            
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the database connection - note: no parameters needed
            H2DatabaseUtil.closeConnection();
        }
        
        System.out.println("\nH2 Database Demo - Completed");
    }
}

