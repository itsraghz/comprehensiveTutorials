# Step 02: H2 Database Integration

This document provides detailed explanations of the components created in Step 02, which focuses on integrating H2 database into our Java project.

## Table of Contents

1. [H2DatabaseUtil.java](#h2databaseutiljava)
2. [H2DatabaseDemo.java](#h2databasedemojava)
3. [SQL Files](#sql-files)
4. [H2 Database Memory vs File Mode](#h2-database-memory-vs-file-mode)
5. [Benefits of H2 for Development and Testing](#benefits-of-h2-for-development-and-testing)

## H2DatabaseUtil.java

### Purpose

The `H2DatabaseUtil` class serves as a utility for managing H2 database connections within the application. It provides methods for establishing a connection, closing it properly, and executing SQL statements from files. This utility class follows a common pattern in Java applications where database connectivity code is encapsulated in a separate class for reusability and maintainability.

### Class Structure

```java
package com.learning.springdatajpa.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2DatabaseUtil {
    private static Connection connection;
    
    // Methods: getConnection(), closeConnection(), executeSqlFile()
}
```

### Method Details

#### 1. getConnection()

```java
public static Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
            System.out.println("Connected to H2 in-memory database.");
        } catch (ClassNotFoundException e) {
            System.err.println("H2 JDBC Driver not found: " + e.getMessage());
            throw new SQLException("H2 JDBC Driver not found.", e);
        }
    }
    return connection;
}
```

**Explanation:**
- This method implements a singleton pattern for database connections
- It first checks if a connection already exists and is open
- If not, it:
  - Loads the H2 JDBC driver class
  - Establishes a connection to an in-memory H2 database named "testdb"
  - Uses the default username "sa" with an empty password
  - Sets `DB_CLOSE_DELAY=-1` to keep the database alive as long as the JVM is running
- Returns the connection object for use in database operations

#### 2. closeConnection()

```java
public static void closeConnection() {
    if (connection != null) {
        try {
            connection.close();
            System.out.println("H2 database connection closed.");
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        } finally {
            connection = null;
        }
    }
}
```

**Explanation:**
- Safely closes the database connection if it exists
- Sets the connection to null to free up resources
- Uses a try-catch-finally block to ensure proper handling of exceptions
- Provides feedback about the connection status

#### 3. executeSqlFile()

```java
public static void executeSqlFile(Connection conn, String filePath) throws SQLException, IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
         Statement stmt = conn.createStatement()) {
        
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            // Skip comments and empty lines
            if (line.trim().startsWith("--") || line.trim().isEmpty()) {
                continue;
            }
            sb.append(line);
            
            // Execute when semicolon is found (end of statement)
            if (line.trim().endsWith(";")) {
                stmt.execute(sb.toString());
                sb.setLength(0);
            }
        }
        
        // Execute any remaining SQL
        if (sb.length() > 0) {
            stmt.execute(sb.toString());
        }
        
        System.out.println("Executed SQL file: " + filePath);
    }
}
```

**Explanation:**
- Takes a database connection and a file path as parameters
- Uses try-with-resources to ensure proper resource management
- Reads the SQL file line by line
- Skips comments (lines starting with --) and empty lines
- Builds SQL statements until a semicolon is found
- Executes each statement as it's completed
- Handles any remaining SQL that might not end with a semicolon
- Provides feedback on successful execution

### Overall Design

The utility class demonstrates several good design principles:
- **Encapsulation** - Database connectivity details are hidden
- **Reusability** - Methods can be used throughout the application
- **Single Responsibility** - Each method performs a specific task
- **Resource Management** - Properly handles opening and closing resources

## H2DatabaseDemo.java

### Purpose

The `H2DatabaseDemo` class serves as a practical demonstration of how to use the H2DatabaseUtil to perform basic database operations. It shows the complete lifecycle of database interaction, from connection establishment to execution of various SQL operations and proper cleanup.

### Execution Flow

```java
package com.learning.springdatajpa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.learning.springdatajpa.db.H2DatabaseUtil;

public class H2DatabaseDemo {
    public static void main(String[] args) {
        try {
            // Get connection
            Connection conn = H2DatabaseUtil.getConnection();
            
            // Create tables
            createStudentTable(conn);
            
            // Insert data
            insertStudentData(conn);
            
            // Query data
            queryStudentData(conn);
            
        } catch (Exception e) {
            System.err.println("Database operation failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close connection
            H2DatabaseUtil.closeConnection();
        }
    }
    
    // Other methods: createStudentTable(), insertStudentData(), queryStudentData()
}
```

#### Main Method Flow

1. **Connection Establishment**: Obtains a database connection using `H2DatabaseUtil.getConnection()`
2. **Table Creation**: Calls `createStudentTable()` to set up the database schema
3. **Data Insertion**: Calls `insertStudentData()` to populate the table
4. **Data Querying**: Calls `queryStudentData()` to retrieve and display data
5. **Connection Cleanup**: Uses `finally` block to ensure `closeConnection()` is called regardless of exceptions

#### Helper Methods

**1. createStudentTable()**
```java
private static void createStudentTable(Connection conn) throws SQLException {
    try (Statement stmt = conn.createStatement()) {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "name VARCHAR(100) NOT NULL, " +
                     "email VARCHAR(100) UNIQUE, " +
                     "age INT)";
        stmt.execute(sql);
        System.out.println("Student table created successfully.");
    }
}
```

This method:
- Creates a Statement object using the connection
- Defines a SQL statement to create a students table with id, name, email, and age columns
- Uses try-with-resources to ensure the Statement is closed properly
- Provides feedback upon successful execution

**2. insertStudentData()**
```java
private static void insertStudentData(Connection conn) throws SQLException {
    try (Statement stmt = conn.createStatement()) {
        stmt.execute("INSERT INTO students (name, email, age) VALUES ('John Doe', 'john@example.com', 20)");
        stmt.execute("INSERT INTO students (name, email, age) VALUES ('Jane Smith', 'jane@example.com', 22)");
        stmt.execute("INSERT INTO students (name, email, age) VALUES ('Bob Johnson', 'bob@example.com', 21)");
        System.out.println("Sample student data inserted successfully.");
    }
}
```

This method:
- Creates a Statement object
- Executes multiple INSERT statements to add sample data
- Uses try-with-resources for proper resource management
- Provides confirmation message upon completion

**3. queryStudentData()**
```java
private static void queryStudentData(Connection conn) throws SQLException {
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {
        
        System.out.println("\nStudent Records:");
        System.out.println("ID\tName\t\tEmail\t\t\tAge");
        System.out.println("------------------------------------------------");
        
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            int age = rs.getInt("age");
            
            System.out.printf("%d\t%s\t%s\t%d\n", id, name, email, age);
        }
    }
}
```

This method:
- Creates a Statement object and executes a SELECT query
- Uses a ResultSet to process the query results
- Iterates through the result set using `rs.next()`
- Retrieves data using typed getters (getInt, getString)
- Formats and displays the data in a tabular format
- Uses try-with-resources to manage both Statement and ResultSet resources

### Key Learning Points

The demo showcases several key JDBC concepts:
- Connection management
- Statement creation and execution
- SQL DDL (Data Definition Language) for table creation
- SQL DML (Data Manipulation Language) for data insertion
- SQL DQL (Data Query Language) for data retrieval
- ResultSet processing
- Exception handling in database operations
- Resource cleanup with try-with-resources

## SQL Files

Three SQL files have been created to demonstrate different database operations:

### 1. schema.sql

This file contains Data Definition Language (DDL) statements that define the database structure.

```sql
-- Drop the table if it exists to ensure a clean state
DROP TABLE IF EXISTS students;

-- Create the students table with all required columns
CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    age INT,
    enrollment_date DATE,
    major VARCHAR(50),
    gpa DECIMAL(3,2),
    hometown VARCHAR(100),
    graduation_year INT,
    advisor VARCHAR(100)
);

-- Create index for improved query performance
CREATE INDEX idx_student_major ON students(major);
CREATE INDEX idx_student_graduation_year ON students(graduation_year);
```

**Key Features:**
- Table cleanup with DROP TABLE IF EXISTS
- Table creation with various data types (INT, VARCHAR, DATE, DECIMAL)
- Constraints (PRIMARY KEY, NOT NULL, UNIQUE)
- Index creation for query optimization
- Comments for clarity

### 2. data.sql

This file contains Data Manipulation Language (DML) statements to populate the database with sample data.

```sql
-- Insert sample student data
INSERT INTO students (name, email, age, enrollment_date, major, gpa, hometown, graduation_year, advisor) 
VALUES ('John Doe', 'john@example.com', 20, '2020-09-01', 'Computer Science', 3.75, 'Boston', 2024, 'Dr. Smith');

INSERT INTO students (name, email, age, enrollment_date, major, gpa, hometown, graduation_year, advisor) 
VALUES ('Jane Smith', 'jane@example.com', 22, '2019-09-01', 'Mathematics', 3.90, 'New York', 2023, 'Dr. Johnson');

INSERT INTO students (name, email, age, enrollment_date, major, gpa, hometown, graduation_year, advisor) 
VALUES ('Bob Johnson', 'bob@example.com', 21, '2020-09-01', 'Physics', 3.45, 'Chicago', 2024, 'Dr. Wilson');

INSERT INTO students (name, email, age, enrollment_date, major, gpa, hometown, graduation_year, advisor) 
VALUES ('Alice Brown', 'alice@example.com', 19, '2021-09-01', 'Computer Science', 3.85, 'San Francisco', 2025, 'Dr. Smith');

INSERT INTO students (name, email, age, enrollment_date, major, gpa, hometown, graduation_year, advisor) 
VALUES ('Charlie Davis', 'charlie@example.com', 23, '2019-09-01', 'Mathematics', 3.20, 'Los Angeles', 2023, 'Dr. Johnson');

INSERT INTO students (name, email, age, enrollment_date, major, gpa, hometown, graduation_year, advisor) 
VALUES ('Eva Wilson', 'eva@example.com', 20, '2021-09-01', 'Biology', 3.95, 'Seattle', 2025, 'Dr. Brown');

INSERT INTO students (name, email, age, enrollment_date, major, gpa, hometown, graduation_year, advisor) 
VALUES ('Frank Miller', 'frank@example.com', 22, '2020-09-01', 'Chemistry', 3.60, 'Portland', 2024, 'Dr. Davis');

INSERT INTO students (name, email, age, enrollment_date, major, gpa, hometown, graduation_year, advisor) 
VALUES ('Grace Taylor', 'grace@example.com', 21, '2021-09-01', 'Computer Science', 3.70, 'Denver', 2025, 'Dr. Smith');

INSERT INTO students (name, email, age, enrollment_date, major, gpa, hometown, graduation_year, advisor) 
VALUES ('Henry Clark', 'henry@example.com', 24, '2018-09-01', 'Physics', 3.30, 'Miami', 2022, 'Dr. Wilson');

INSERT INTO students (name, email, age, enrollment_date, major, gpa, hometown, graduation_year, advisor) 
VALUES ('Ivy Martin', 'ivy@example.com', 20, '2021-09-01', 'Mathematics', 4.00, 'Dallas', 2025, 'Dr. Johnson');
```

**Key Features:**
- Multiple INSERT statements with complete student records
- Varied data across different majors, ages, and enrollment years
- Consistent data patterns for meaningful analysis in queries
- Comments for context

### 3. queries.sql

This file demonstrates various SQL queries for data retrieval and analysis.

```sql
-- Basic SELECT queries
-- Get all students
SELECT * FROM students;

-- Filter by a specific major
SELECT * FROM students WHERE major = 'Computer Science';

-- Order students by GPA (descending)
SELECT * FROM students ORDER BY gpa DESC;

-- Aggregation queries
-- Count students by major
SELECT major, COUNT(*) as student_count 
FROM students 
GROUP BY major 
ORDER BY student_count DESC;

-- Calculate average GPA by major
SELECT major, AVG(gpa) as average_gpa 
FROM students 
GROUP BY major 
ORDER BY average_gpa DESC;

-- Find high-performing majors (using HAVING)
SELECT major, AVG(gpa) as average_gpa 
FROM students 
GROUP BY major 
HAVING AVG(gpa) > 3.5 
ORDER BY average_gpa DESC;

-- Advanced queries
-- Find top 3 students by GPA
SELECT name, major, gpa 
FROM students 
ORDER BY gpa DESC 
LIMIT 3;

-- Find students graduating this year with high GPAs
SELECT name, major, gpa 
FROM students 
WHERE graduation_year = 2023 AND gpa >= 3.5 
ORDER BY gpa DESC;

-- Subquery example: Students with above-average GPA
SELECT name, major, gpa 
FROM students 
WHERE gpa > (SELECT AVG(gpa) FROM students);

-- Conditional aggregation: Count of students by graduation year
SELECT 
    graduation_year,
    COUNT(*) as total_students,
    SUM(CASE WHEN major = 'Computer Science' THEN 1 ELSE 0 END) as cs_students,
    SUM(CASE WHEN major = 'Mathematics' THEN 1 ELSE 0 END) as math_students
FROM students
GROUP BY graduation_year
ORDER BY graduation_year;
```

**Key Features:**
- Basic SELECT with WHERE and ORDER BY clauses for simple filtering and sorting
- Aggregation functions (COUNT, AVG) with GROUP BY for summarizing data
- HAVING clause to filter grouped results
- LIMIT clause to restrict the number of returned rows
- Subqueries for more complex conditions
- Conditional expressions with CASE statements
- Sorting and ordering of results
- Multi-condition filtering with AND/OR operators

These queries demonstrate how to extract meaningful information from the database, from simple lookups to complex analytical queries.

## H2 Database Memory vs File Mode

H2 database can operate in two primary modes: in-memory and file-based (persistent). Understanding the differences between these modes is crucial for deciding which to use in your applications.

### In-Memory Mode

In-memory mode is configured using a JDBC URL like:
```
jdbc:h2:mem:testdb
```

**Characteristics:**
- Database exists entirely in RAM
- Very fast performance (no disk I/O)
- Data is lost when the JVM terminates unless `DB_CLOSE_DELAY=-1` is specified
- Ideal for testing, development, and temporary data storage
- No file system access is required

**When to use:**
- Unit and integration testing
- Temporary data caching
- Scenarios where data persistence is not required
- Performance-critical applications with transient data

### File Mode

File mode is configured using a JDBC URL like:
```
jdbc:h2:file:./data/sampledb
```

**Characteristics:**
- Data is stored on disk in files
- Data persists between application restarts
- Slower than in-memory but still quite fast compared to other databases
- Supports database recovery in case of crashes
- Creates physical database files in the specified location

**When to use:**
- Applications that need data persistence
- Development environments that need to retain data
- Small to medium-sized applications with modest data storage needs
- Embedded database scenarios

### Switching Between Modes

One advantage of H2 is that the application code often doesn't need to change when switching between modes. Only the JDBC URL needs to be updated. This makes it easy to use in-memory mode for testing and file mode for development or production.

**Example:**
```java
// For in-memory
String jdbcUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

// For file-based
String jdbcUrl = "jdbc:h2:file:./data/testdb";

// The rest of the connection code remains the same
Connection conn = DriverManager.getConnection(jdbcUrl, "sa", "");
```

## Benefits of H2 for Development and Testing

H2 database offers numerous advantages that make it an excellent choice for development and testing environments:

### 1. Lightweight and Fast Setup

- **Zero Installation**: H2 is just a JAR file - no installation required
- **Small Footprint**: Minimal resource consumption (< 2MB JAR file)
- **Quick Startup**: Initializes in milliseconds, perfect for tests
- **Simple Configuration**: Minimal setup required to get started

### 2. Developer-Friendly Features

- **Web Console**: Built-in browser-based database management interface
- **Multiple Connection Modes**: In-memory, file, server, or mixed modes
- **SQL Standard Compliance**: Supports standard SQL syntax
- **Rich Data Types**: Supports all standard SQL types plus additional ones
- **Compatibility Modes**: Can emulate other databases like MySQL, PostgreSQL

### 3. Testing Advantages

- **Isolation**: Each test can have its own isolated database instance
- **Speed**: In-memory operation provides fast test execution
- **Deterministic**: Tests can start with a clean database state each time
- **No External Dependencies**: Tests don't require external database services
- **Scriptable**: Easy to initialize with SQL scripts

### 4. Production-Like Capabilities

- **ACID Compliance**: Supports transactions with full ACID properties
- **Referential Integrity**: Supports foreign keys and constraints
- **Indexes**: Supports various index types for performance optimization
- **Triggers and Views**: Allows for complex database behaviors
- **Encryption**: Supports database encryption for sensitive data

### 5. Integration Benefits

- **Spring Boot Integration**: First-class support in Spring Boot
- **ORM Compatibility**: Works well with JPA, Hibernate, and other ORMs
- **Connection Pooling**: Compatible with HikariCP and other connection pools
- **Migration Tools**: Works with Flyway, Liquibase for schema migrations

H2 provides an excellent balance between simplicity and capability, making development faster and more productive while reducing the overhead of database setup and maintenance during the development process.
