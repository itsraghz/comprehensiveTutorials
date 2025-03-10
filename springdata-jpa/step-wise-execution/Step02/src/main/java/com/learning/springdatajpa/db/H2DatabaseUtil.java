package com.learning.springdatajpa.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class for connecting to an H2 in-memory database,
 * creating tables, and managing connections.
 */
public class H2DatabaseUtil {
    
    // JDBC URL for H2 in-memory database
    private static final String JDBC_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    
    private static Connection connection = null;
    
    /**
     * Get a connection to the H2 in-memory database.
     * 
     * @return Connection object to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load the H2 JDBC driver
                Class.forName("org.h2.Driver");
                
                // Create a connection to the in-memory database
                connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                System.out.println("Connected to H2 in-memory database");
            } catch (ClassNotFoundException e) {
                System.err.println("H2 JDBC Driver not found: " + e.getMessage());
                throw new SQLException("H2 JDBC Driver not found", e);
            }
        }
        return connection;
    }
    
    /**
     * Create tables in the database based on provided SQL statements.
     * 
     * @param createTableSQL SQL statement to create a table
     * @throws SQLException if a database access error occurs
     */
    public static void createTable(String createTableSQL) throws SQLException {
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement()) {
            
            statement.execute(createTableSQL);
            System.out.println("Table created successfully");
        }
    }
    
    /**
     * Insert data into a table using prepared statement.
     * 
     * @param insertSQL the SQL insert statement with placeholders
     * @param params the parameters to be set in the prepared statement
     * @throws SQLException if a database access error occurs
     */
    public static void executeUpdate(String sql, Object... params) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            // Set parameters if any
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " row(s) affected");
        }
    }
    
    /**
     * Execute a query and process the result set.
     * 
     * @param querySQL the SQL query to execute
     * @param resultSetProcessor function to process the result set
     * @param params parameters for the prepared statement
     * @throws SQLException if a database access error occurs
     */
    public static void executeQuery(String querySQL, ResultSetProcessor resultSetProcessor, 
                                  Object... params) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(querySQL)) {
            
            // Set parameters if any
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSetProcessor.process(resultSet);
            }
        }
    }
    
    /**
     * Functional interface for processing a ResultSet.
     */
    @FunctionalInterface
    public interface ResultSetProcessor {
        void process(ResultSet rs) throws SQLException;
    }
    
    /**
     * Close the database connection.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed");
                connection = null;
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Create a demo student table.
     * 
     * @throws SQLException if a database access error occurs
     */
    public static void createStudentTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                "name VARCHAR(100) NOT NULL, " +
                                "email VARCHAR(100) UNIQUE, " +
                                "age INT)";
        createTable(createTableSQL);
    }
    
    /**
     * Insert a sample student record.
     * 
     * @throws SQLException if a database access error occurs
     */
    public static void insertSampleStudent(String name, String email, int age) throws SQLException {
        String insertSQL = "INSERT INTO students (name, email, age) VALUES (?, ?, ?)";
        executeUpdate(insertSQL, name, email, age);
    }
    
    /**
     * Print all students in the database.
     * 
     * @throws SQLException if a database access error occurs
     */
    public static void printAllStudents() throws SQLException {
        String query = "SELECT * FROM students";
        executeQuery(query, rs -> {
            System.out.println("\nStudent Records:");
            System.out.println("---------------");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int age = rs.getInt("age");
                
                System.out.printf("ID: %d, Name: %s, Email: %s, Age: %d%n", 
                                 id, name, email, age);
            }
        });
    }
    /**
     * Execute SQL statements from a file.
     * 
     * @param filePath Path to the SQL file
     * @throws SQLException if there is an error executing the SQL
     * @throws IOException if there is an error reading the file
     */
    public static void executeSqlFile(String filePath) throws SQLException, IOException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip comments and empty lines
                if (line.trim().isEmpty() || line.trim().startsWith("--")) {
                    continue;
                }
                sb.append(line);
                
                // Execute when a complete statement is found (ending with semicolon)
                if (line.trim().endsWith(";")) {
                    String sql = sb.toString();
                    stmt.execute(sql);
                    sb.setLength(0); // Clear the string builder
                }
            }
        }
        
        System.out.println("SQL file executed successfully: " + filePath);
        stmt.close();
    }
    
    /**
     * Creates a minimalistic student table with all necessary columns as specified in the schema.
     * 
     * @throws SQLException if a database access error occurs
     */
    public static void createMinimalisticTable() throws SQLException {
        String createTableSQL = 
            "CREATE TABLE IF NOT EXISTS student (" +
            "    id INT AUTO_INCREMENT PRIMARY KEY," +
            "    first_name VARCHAR(50) NOT NULL," +
            "    last_name VARCHAR(50) NOT NULL," +
            "    email VARCHAR(100) UNIQUE NOT NULL," +
            "    age INT," +
            "    major VARCHAR(50)," +
            "    gpa DECIMAL(3,2)," +
            "    hometown VARCHAR(100)," +
            "    graduation_year INT," +
            "    advisor VARCHAR(100)" +
            ")";
        
        createTable(createTableSQL);
    }
    
    /**
     * Creates all necessary database tables for the application.
     * This method serves as the central place to initialize the database schema.
     * 
     * @throws SQLException if a database access error occurs
     */
    public static void createTables() throws SQLException {
        // Create the student table with all columns
        createMinimalisticTable();
        
        // Additional tables can be created here as the application grows
        // For example: createCoursesTable(), createEnrollmentsTable(), etc.
        
        System.out.println("All database tables created successfully");
    }
}
