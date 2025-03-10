package com.learning.springdatajpa;

import com.learning.springdatajpa.db.H2DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

/**
 * A console application that provides a menu-driven interface for managing student records
 * in an H2 database. This application demonstrates CRUD operations.
 */
public class H2DatabaseConsoleApp {
    
    private static Scanner scanner;
    
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        
        try {
            System.out.println("Initializing H2 database...");
            
            // Create tables if they don't exist
            H2DatabaseUtil.createTables();
            
            boolean exit = false;
            while (!exit) {
                displayMenu();
                int choice = getUserChoice();
                
                switch (choice) {
                    case 1:
                        createStudent();
                        break;
                    case 2:
                        readStudent();
                        break;
                    case 3:
                        readAllStudents();
                        break;
                    case 4:
                        updateStudent();
                        break;
                    case 5:
                        deleteStudent();
                        break;
                    case 6:
                        exit = true;
                        System.out.println("Exiting application...");
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
                
                // Pause before showing menu again
                if (!exit) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            if (scanner != null) {
                scanner.close();
            }
        }
    }
    
    /**
     * Displays the main menu options.
     */
    private static void displayMenu() {
        System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
        System.out.println("1. Add a new student");
        System.out.println("2. Find a student by ID");
        System.out.println("3. View all students");
        System.out.println("4. Update student information");
        System.out.println("5. Delete a student");
        System.out.println("6. Exit");
        System.out.print("Enter your choice (1-6): ");
    }
    
    /**
     * Gets the user's menu choice and validates it.
     * 
     * @return The validated menu choice
     */
    private static int getUserChoice() {
        int choice = 0;
        boolean validInput = false;
        
        while (!validInput) {
            try {
                String input = scanner.nextLine();
                choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 6) {
                    validInput = true;
                } else {
                    System.out.print("Please enter a number between 1 and 6: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number between 1 and 6: ");
            }
        }
        
        return choice;
    }
    
    /**
     * Creates a new student record in the database.
     * 
     * Prompts the user for student information, validates the input,
     * and inserts the new student record into the database.
     */
    private static void createStudent() throws SQLException {
        System.out.println("\n----- Add a New Student -----");
        
        // Get student information from user
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine().trim();
        
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine().trim();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        
        // Validate and get age
        int age = 0;
        boolean validAge = false;
        while (!validAge) {
            System.out.print("Enter age: ");
            try {
                age = Integer.parseInt(scanner.nextLine().trim());
                if (age > 0 && age < 120) {
                    validAge = true;
                } else {
                    System.out.println("Please enter a valid age (1-119).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number for age.");
            }
        }
        
        // Optional fields
        System.out.print("Enter major (or press Enter to skip): ");
        String major = scanner.nextLine().trim();
        major = major.isEmpty() ? null : major;
        
        // Validate and get GPA if provided
        Double gpa = null;
        System.out.print("Enter GPA (0.0-4.0, or press Enter to skip): ");
        String gpaInput = scanner.nextLine().trim();
        if (!gpaInput.isEmpty()) {
            try {
                gpa = Double.parseDouble(gpaInput);
                if (gpa < 0.0 || gpa > 4.0) {
                    System.out.println("Invalid GPA range. Setting GPA to null.");
                    gpa = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid GPA format. Setting GPA to null.");
            }
        }
        
        // Prepare SQL statement
        String sql = "INSERT INTO STUDENT (first_name, last_name, email, age, major, gpa) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = H2DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setInt(4, age);
            
            // Handle optional fields
            if (major != null) {
                pstmt.setString(5, major);
            } else {
                pstmt.setNull(5, Types.VARCHAR);
            }
            
            if (gpa != null) {
                pstmt.setDouble(6, gpa);
            } else {
                pstmt.setNull(6, Types.DOUBLE);
            }
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        System.out.println("Student added successfully with ID: " + generatedKeys.getLong(1));
                    } else {
                        System.out.println("Student added but failed to get the student ID.");
                    }
                }
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("unique constraint") || e.getMessage().contains("Unique index")) {
                System.out.println("Error: A student with this email already exists.");
            } else {
                throw e;
            }
        }
    }
    
    /**
     * Finds and displays a student by ID.
     * 
     * Prompts the user for a student ID, queries the database,
     * and displays the student's information if found.
     */
    private static void readStudent() throws SQLException {
        System.out.println("\n----- Find Student by ID -----");
        
        // Get student ID from user
        System.out.print("Enter student ID: ");
        long studentId = 0;
        try {
            studentId = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a numeric value.");
            return;
        }
        
        // Query database for student
        String sql = "SELECT * FROM STUDENT WHERE id = ?";
        try (Connection conn = H2DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, studentId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Display student information
                    System.out.println("\nStudent Found:");
                    System.out.println("ID: " + rs.getLong("id"));
                    System.out.println("Name: " + rs.getString("first_name") + " " + rs.getString("last_name"));
                    System.out.println("Email: " + rs.getString("email"));
                    System.out.println("Age: " + rs.getInt("age"));
                    
                    // Display optional fields if they exist
                    String major = rs.getString("major");
                    if (major != null) {
                        System.out.println("Major: " + major);
                    }
                    
                    double gpa = rs.getDouble("gpa");
                    if (!rs.wasNull()) {
                        System.out.println("GPA: " + String.format("%.2f", gpa));
                    }
                } else {
                    System.out.println("No student found with ID: " + studentId);
                }
            }
        }
    }
    
    /**
     * Helper method to truncate strings for display formatting.
     * 
     * @param value The string to truncate
     * @param length The maximum length
     * @return The truncated string
     */
    private static String truncate(String value, int length) {
        if (value != null && value.length() > length) {
            return value.substring(0, length - 3) + "...";
        }
        return value;
    }
    
    /**
     * Displays all students in the database.
     * 
     * Queries the database for all students and displays them
     * in a formatted table. Handles the case where no students
     * are found in the database.
     */
    private static void readAllStudents() throws SQLException {
        System.out.println("\n----- All Students -----");
        
        // Query database for all students
        String sql = "SELECT * FROM STUDENT ORDER BY id";
        try (Connection conn = H2DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            boolean hasStudents = false;
            
            // Print table header
            System.out.println("+-----------+--------------------+--------------------+-----+---------------+-------+");
            System.out.println("| ID        | First Name         | Last Name          | Age | Major         | GPA   |");
            System.out.println("+-----------+--------------------+--------------------+-----+---------------+-------+");
            
            // Display each student
            while (rs.next()) {
                hasStudents = true;
                
                long id = rs.getLong("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int age = rs.getInt("age");
                String major = rs.getString("major");
                double gpa = rs.getDouble("gpa");
                boolean gpaIsNull = rs.wasNull();
                
                // Format each field to fit in the table
                System.out.printf("| %-9d | %-18s | %-18s | %-3d | %-15s | %-5s |%n",
                        id, 
                        firstName != null ? truncate(firstName, 18) : "",
                        lastName != null ? truncate(lastName, 18) : "",
                        age,
                        major != null ? truncate(major, 15) : "N/A",
                        !gpaIsNull ? String.format("%.2f", gpa) : "N/A");
            }
            
            System.out.println("+-----------+--------------------+--------------------+-----+---------------+-------+");
            
            // Handle case where no students were found
            if (!hasStudents) {
                System.out.println("No students found in the database.");
            }
        }
    }
    
    /**
     * Updates an existing student's information.
     * 
     * Prompts the user for a student ID, verifies the student exists,
     * displays current information, and allows the user to update specific
     * fields or all fields with new values.
     */
    private static void updateStudent() throws SQLException {
        System.out.println("\n----- Update Student Information -----");
        
        // Get student ID to update
        System.out.print("Enter student ID to update: ");
        long studentId = 0;
        try {
            studentId = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a numeric value.");
            return;
        }
        
        // Check if student exists and get current data
        String checkSql = "SELECT * FROM STUDENT WHERE id = ?";
        try (Connection conn = H2DatabaseUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setLong(1, studentId);
            
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No student found with ID: " + studentId);
                return;
            }
            
            // Store current values
            String currentFirstName = rs.getString("first_name");
            String currentLastName = rs.getString("last_name");
            String currentEmail = rs.getString("email");
            int currentAge = rs.getInt("age");
            String currentMajor = rs.getString("major");
            double currentGpa = rs.getDouble("gpa");
            boolean gpaIsNull = rs.wasNull();
            
            // Display current student information
            System.out.println("\nCurrent Student Information:");
            System.out.println("ID: " + studentId);
            System.out.println("1. First Name: " + currentFirstName);
            System.out.println("2. Last Name: " + currentLastName);
            System.out.println("3. Email: " + currentEmail);
            System.out.println("4. Age: " + currentAge);
            System.out.println("5. Major: " + (currentMajor != null ? currentMajor : "N/A"));
            System.out.println("6. GPA: " + (!gpaIsNull ? String.format("%.2f", currentGpa) : "N/A"));
            
            // Ask which field to update
            System.out.println("\nWhich field would you like to update?");
            System.out.println("1. First Name");
            System.out.println("2. Last Name");
            System.out.println("3. Email");
            System.out.println("4. Age");
            System.out.println("5. Major");
            System.out.println("6. GPA");
            System.out.println("7. All Fields");
            System.out.println("0. Cancel Update");
            
            System.out.print("\nEnter your choice (0-7): ");
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice < 0 || choice > 7) {
                    System.out.println("Invalid option. Update canceled.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Update canceled.");
                return;
            }
            
            if (choice == 0) {
                System.out.println("Update canceled.");
                return;
            }
            
            // Variables to hold updated values
            String newFirstName = currentFirstName;
            String newLastName = currentLastName;
            String newEmail = currentEmail;
            int newAge = currentAge;
            String newMajor = currentMajor;
            Double newGpa = gpaIsNull ? null : currentGpa;
            
            // Update selected field(s)
            if (choice == 1 || choice == 7) {
                System.out.print("Enter new first name: ");
                String input = scanner.nextLine().trim();
                if (!input.isEmpty()) {
                    newFirstName = input;
                }
            }
            
            if (choice == 2 || choice == 7) {
                System.out.print("Enter new last name: ");
                String input = scanner.nextLine().trim();
                if (!input.isEmpty()) {
                    newLastName = input;
                }
            }
            
            if (choice == 3 || choice == 7) {
                System.out.print("Enter new email: ");
                String input = scanner.nextLine().trim();
                if (!input.isEmpty()) {
                    newEmail = input;
                }
            }
            
            if (choice == 4 || choice == 7) {
                boolean validAge = false;
                while (!validAge) {
                    System.out.print("Enter new age (current: " + currentAge + "): ");
                    String input = scanner.nextLine().trim();
                    if (input.isEmpty()) {
                        validAge = true; // Keep current age
                    } else {
                        try {
                            int age = Integer.parseInt(input);
                            if (age > 0 && age < 120) {
                                newAge = age;
                                validAge = true;
                            } else {
                                System.out.println("Please enter a valid age (1-119).");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number for age.");
                        }
                    }
                }
            }
            
            if (choice == 5 || choice == 7) {
                System.out.print("Enter new major (leave empty to set as NULL): ");
                String input = scanner.nextLine().trim();
                newMajor = input.isEmpty() ? null : input;
            }
            
            if (choice == 6 || choice == 7) {
                boolean validGpa = false;
                while (!validGpa) {
                    System.out.print("Enter new GPA (0.0-4.0, leave empty to set as NULL): ");
                    String input = scanner.nextLine().trim();
                    if (input.isEmpty()) {
                        newGpa = null;
                        validGpa = true;
                    } else {
                        try {
                            double gpa = Double.parseDouble(input);
                            if (gpa >= 0.0 && gpa <= 4.0) {
                                newGpa = gpa;
                                validGpa = true;
                            } else {
                                System.out.println("Please enter a valid GPA between 0.0 and 4.0.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number for GPA.");
                        }
                    }
                }
            }
            
            // Prepare and execute the update SQL statement
            String updateSql = "UPDATE STUDENT SET first_name=?, last_name=?, email=?, age=?, major=?, gpa=? WHERE id=?";
            try (Connection updateConn = H2DatabaseUtil.getConnection();
                 PreparedStatement updateStmt = updateConn.prepareStatement(updateSql)) {
                updateStmt.setString(1, newFirstName);
                updateStmt.setString(2, newLastName);
                updateStmt.setString(3, newEmail);
                updateStmt.setInt(4, newAge);
                
                // Handle nullable fields
                if (newMajor != null) {
                    updateStmt.setString(5, newMajor);
                } else {
                    updateStmt.setNull(5, Types.VARCHAR);
                }
                
                if (newGpa != null) {
                    updateStmt.setDouble(6, newGpa);
                } else {
                    updateStmt.setNull(6, Types.DOUBLE);
                }
                
                updateStmt.setLong(7, studentId);
                
                int affectedRows = updateStmt.executeUpdate();
                
                if (affectedRows > 0) {
                    System.out.println("Student information updated successfully!");
                } else {
                    System.out.println("Failed to update student information.");
                }
            } catch (SQLException e) {
                if (e.getMessage().contains("unique constraint") || e.getMessage().contains("Unique index")) {
                    System.out.println("Error: A student with this email already exists.");
                } else {
                    throw e;
                }
            }
        }
    }
    
    /**
     * Deletes a student from the database.
     * 
     * Prompts the user for a student ID, verifies the student exists,
     * displays the student's information, asks for confirmation,
     * and deletes the student if confirmed.
     */
    private static void deleteStudent() throws SQLException {
        System.out.println("\n----- Delete Student -----");
        
        // Get student ID to delete
        System.out.print("Enter student ID: ");
        long studentId = 0;
        try {
            studentId = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a numeric value.");
            return;
        }
        
        // Check if student exists and display information
        String checkSql = "SELECT * FROM STUDENT WHERE id = ?";
        try (Connection conn = H2DatabaseUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setLong(1, studentId);
            
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("No student found with ID: " + studentId);
                    return;
                }
                
                // Display student information
                System.out.println("\nStudent Found:");
                System.out.println("ID: " + rs.getLong("id"));
                System.out.println("Name: " + rs.getString("first_name") + " " + rs.getString("last_name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Age: " + rs.getInt("age"));
                
                // Display optional fields if they exist
                String major = rs.getString("major");
                if (major != null) {
                    System.out.println("Major: " + major);
                }
                
                double gpa = rs.getDouble("gpa");
                if (!rs.wasNull()) {
                    System.out.println("GPA: " + String.format("%.2f", gpa));
                }
                
                // Ask for confirmation
                System.out.print("\nAre you sure you want to delete this student? (y/n): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();
                
                if (confirmation.equals("y") || confirmation.equals("yes")) {
                    // Proceed with deletion
                    String deleteSql = "DELETE FROM STUDENT WHERE id = ?";
                    try (Connection deleteConn = H2DatabaseUtil.getConnection();
                         PreparedStatement deleteStmt = deleteConn.prepareStatement(deleteSql)) {
                        deleteStmt.setLong(1, studentId);
                        
                        int affectedRows = deleteStmt.executeUpdate();
                        
                        if (affectedRows > 0) {
                            System.out.println("Student deleted successfully!");
                        } else {
                            System.out.println("Failed to delete student. Please try again.");
                        }
                    }
                } else {
                    System.out.println("Deletion canceled.");
                }
            }
        }
    }
}
