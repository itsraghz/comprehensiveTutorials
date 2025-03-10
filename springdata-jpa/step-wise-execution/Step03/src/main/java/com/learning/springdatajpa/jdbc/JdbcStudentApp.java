package com.learning.springdatajpa.jdbc;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Console application that demonstrates JDBC functionality for Student management.
 */
public class JdbcStudentApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentRepository studentRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    static {
        try {
            // Initialize the StudentRepository implementation
            studentRepository = new StudentRepositoryImpl();
        } catch (Exception e) {
            System.err.println("Failed to initialize the application: " + e.getMessage());
            throw new RuntimeException("Application initialization failed", e);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("===== JDBC Student Management System =====");
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1:
                        listAllStudents();
                        break;
                    case 2:
                        findStudentById();
                        break;
                    case 3:
                        findStudentsByMajor();
                        break;
                    case 4:
                        addNewStudent();
                        break;
                    case 5:
                        updateStudent();
                        break;
                    case 6:
                        deleteStudent();
                        break;
                    case 7:
                        displayStatistics();
                        break;
                    case 8:
                        running = false;
                        System.out.println("Exiting application. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
            
            // Pause before showing menu again
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. List all students");
        System.out.println("2. Find student by ID");
        System.out.println("3. Find students by major");
        System.out.println("4. Add a new student");
        System.out.println("5. Update a student");
        System.out.println("6. Delete a student");
        System.out.println("7. Display statistics");
        System.out.println("8. Exit");
    }
    
    private static void listAllStudents() throws SQLException {
        System.out.println("\n===== All Students =====");
        List<Student> students = studentRepository.findAll();
        
        if (students.isEmpty()) {
            System.out.println("No students found in the database.");
            return;
        }
        
        for (Student student : students) {
            displayStudentDetails(student);
        }
        System.out.println("Total: " + students.size() + " students");
    }
    
    private static void findStudentById() throws SQLException {
        int id = getIntInput("Enter student ID: ");
        Optional<Student> studentOpt = studentRepository.findById(id);
        
        if (studentOpt.isPresent()) {
            System.out.println("\n===== Student Found =====");
            displayStudentDetails(studentOpt.get());
        } else {
            System.out.println("No student found with ID: " + id);
        }
    }
    
    private static void findStudentsByMajor() throws SQLException {
        String major = getStringInput("Enter major: ");
        List<Student> students = studentRepository.findByMajor(major);
        
        if (students.isEmpty()) {
            System.out.println("No students found with major: " + major);
            return;
        }
        
        System.out.println("\n===== Students with Major: " + major + " =====");
        for (Student student : students) {
            displayStudentDetails(student);
        }
        System.out.println("Total: " + students.size() + " students");
    }
    
    private static void addNewStudent() throws SQLException {
        System.out.println("\n===== Add New Student =====");
        
        Student student = new Student();
        
        student.setFirstName(getStringInput("Enter first name: "));
        student.setLastName(getStringInput("Enter last name: "));
        student.setEmail(getStringInput("Enter email: "));
        student.setMajor(getStringInput("Enter major: "));
        
        String dobStr = getStringInput("Enter date of birth (yyyy-MM-dd) or leave empty: ");
        if (!dobStr.isEmpty()) {
            try {
                student.setDateOfBirth(LocalDate.parse(dobStr, DATE_FORMATTER));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Using default value.");
            }
        }
        
        String gpaStr = getStringInput("Enter GPA or leave empty: ");
        if (!gpaStr.isEmpty()) {
            try {
                student.setGpa(new BigDecimal(gpaStr));
            } catch (NumberFormatException e) {
                System.out.println("Invalid GPA format. Using default value.");
            }
        }
        
        student.setHometown(getStringInput("Enter hometown or leave empty: "));
        
        String gradYearStr = getStringInput("Enter graduation year or leave empty: ");
        if (!gradYearStr.isEmpty()) {
            try {
                student.setGraduationYear(Integer.parseInt(gradYearStr));
            } catch (NumberFormatException e) {
                System.out.println("Invalid graduation year format. Using default value.");
            }
        }
        
        String genderStr = getStringInput("Enter gender (M/F/O) or leave empty: ");
        if (!genderStr.isEmpty() && genderStr.length() == 1) {
            student.setGender(genderStr.charAt(0));
        }
        
        Student savedStudent = studentRepository.save(student);
        System.out.println("Student added successfully with ID: " + savedStudent.getId());
    }
    
    private static void updateStudent() throws SQLException {
        int id = getIntInput("Enter student ID to update: ");
        
        if (!studentRepository.existsById(id)) {
            System.out.println("No student found with ID: " + id);
            return;
        }
        
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (!studentOpt.isPresent()) {
            System.out.println("Failed to retrieve student with ID: " + id);
            return;
        }
        
        Student student = studentOpt.get();
        System.out.println("\n===== Update Student (ID: " + id + ") =====");
        System.out.println("Current details:");
        displayStudentDetails(student);
        
        System.out.println("\nEnter new values (leave empty to keep current value):");
        
        String firstName = getStringInput("First name [" + student.getFirstName() + "]: ");
        if (!firstName.isEmpty()) {
            student.setFirstName(firstName);
        }
        
        String lastName = getStringInput("Last name [" + student.getLastName() + "]: ");
        if (!lastName.isEmpty()) {
            student.setLastName(lastName);
        }
        
        String email = getStringInput("Email [" + student.getEmail() + "]: ");
        if (!email.isEmpty()) {
            student.setEmail(email);
        }
        
        String major = getStringInput("Major [" + student.getMajor() + "]: ");
        if (!major.isEmpty()) {
            student.setMajor(major);
        }
        
        String gpaStr = getStringInput("GPA [" + student.getGpa() + "]: ");
        if (!gpaStr.isEmpty()) {
            try {
                student.setGpa(new BigDecimal(gpaStr));
            } catch (NumberFormatException e) {
                System.out.println("Invalid GPA format. Keeping current value.");
            }
        }
        
        Student updatedStudent = studentRepository.save(student);
        System.out.println("Student updated successfully.");
    }
    
    private static void deleteStudent() throws SQLException {
        int id = getIntInput("Enter student ID to delete: ");
        
        if (!studentRepository.existsById(id)) {
            System.out.println("No student found with ID: " + id);
            return;
        }
        
        System.out.print("Are you sure you want to delete this student? (y/n): ");
        String confirmation = scanner.next().trim().toLowerCase();
        
        if (confirmation.equals("y")) {
            studentRepository.deleteById(id);
            System.out.println("Student with ID " + id + " has been deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    private static void displayStatistics() throws SQLException {
        System.out.println("\n===== Student Statistics =====");
        
        // Get total count
        List<Student> allStudents = studentRepository.findAll();
        int totalCount = allStudents.size();
        
        if (totalCount == 0) {
            System.out.println("No students in the database.");
            return;
        }
        
        // Calculate average GPA
        BigDecimal totalGpa = BigDecimal.ZERO;
        int gpaCount = 0;
        for (Student student : allStudents) {
            if (student.getGpa() != null) {
                totalGpa = totalGpa.add(student.getGpa());
                gpaCount++;
            }
        }
        
        BigDecimal avgGpa = gpaCount > 0 
            ? totalGpa.divide(new BigDecimal(gpaCount), 2, BigDecimal.ROUND_HALF_UP) 
            : BigDecimal.ZERO;
        
        // Count students by major
        System.out.println("Total students: " + totalCount);
        System.out.println("Average GPA: " + avgGpa);
        
        // Find distribution by major
        System.out.println("\nDistribution by Major:");
        List<Student> students = studentRepository.findAll();
        
        // Create a map to count students by major
        java.util.Map<String, Integer> majorCounts = new java.util.HashMap<>();
        for (Student student : students) {
            String major = student.getMajor();
            majorCounts.put(major, majorCounts.getOrDefault(major, 0) + 1);
        }
        
        // Display distribution
        for (java.util.Map.Entry<String, Integer> entry : majorCounts.entrySet()) {
            System.out.printf("%-20s: %d students (%.1f%%)\n", 
                entry.getKey(), 
                entry.getValue(), 
                (entry.getValue() * 100.0 / totalCount));
        }
    }
    
    private static void displayStudentDetails(Student student) {
        System.out.println("\n------------------------");
        System.out.println("ID: " + student.getId());
        System.out.println("Name: " + student.getFirstName() + " " + student.getLastName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Major: " + student.getMajor());
        
        if (student.getDateOfBirth() != null) {
            System.out.println("Date of Birth: " + student.getDateOfBirth().format(DATE_FORMATTER));
        }
        
        if (student.getGpa() != null) {
            System.out.println("GPA: " + student.getGpa());
        }
        
        if (student.getHometown() != null && !student.getHometown().isEmpty()) {
            System.out.println("Hometown: " + student.getHometown());
        }
        
        if (student.getGraduationYear() != null) {
            System.out.println("Graduation Year: " + student.getGraduationYear());
        }
        
        if (student.getGender() != null) {
            System.out.println("Gender: " + student.getGender());
        }
        
        System.out.println("------------------------");
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
