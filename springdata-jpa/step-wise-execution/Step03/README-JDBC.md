# JDBC Implementation for Student Management System

This document explains the JDBC implementation pattern used in the Step03 project, including the classes created, their responsibilities, and how to use the application.

## Implementation Pattern

The implementation follows a repository pattern with a clear separation of concerns:

1. **Database Utility** - Provides connection management and query execution utilities
2. **Model Class** - Represents the data entities in the database
3. **Repository Interface** - Defines the contract for CRUD operations
4. **Repository Implementation** - Implements the CRUD operations using JDBC
5. **Console Application** - Provides a user interface for interacting with the repository
6. **Database Initializer** - Sets up the database schema and initial data
7. **Main Application** - Entry point that ties everything together

This pattern resembles a simplified version of what Spring Data JPA offers but using plain JDBC.

## Classes and Responsibilities

### `H2DatabaseUtil`

Utility class that handles database connections and provides reusable methods for JDBC operations.

**Responsibilities:**
- Managing database connections
- Executing SQL queries and updates
- Handling transaction management
- Proper resource cleanup

### `Student`

Model class representing a student entity that maps to the STUDENT table in the database.

**Responsibilities:**
- Storing student data (id, first name, last name, date of birth, gender, etc.)
- Providing getters and setters for all properties
- Supporting object creation through constructors

### `StudentRepository`

Interface that defines the contract for student data operations.

**Responsibilities:**
- Defining CRUD operations (create, read, update, delete)
- Defining special queries (find by name, find all, etc.)

### `StudentRepositoryImpl`

Implementation of the StudentRepository interface using JDBC.

**Responsibilities:**
- Implementing all CRUD operations using JDBC
- Converting result sets to Student objects
- Managing prepared statements and parameters
- Handling SQL exceptions

### `JdbcStudentApp`

Console application that interacts with the StudentRepository to perform operations.

**Responsibilities:**
- Providing a user interface via the console
- Handling user input
- Displaying results
- Delegating operations to the repository

### `JdbcDatabaseInitializer`

Utility class that initializes the database with schema and sample data.

**Responsibilities:**
- Creating tables using schema.sql
- Populating tables using data.sql
- Handling initialization errors

### `JdbcApplication`

Main entry point for the application.

**Responsibilities:**
- Initializing the database
- Setting up the repository
- Launching the application UI

## Database Schema

The application uses an H2 in-memory database with the following schema:

```sql
CREATE TABLE IF NOT EXISTS STUDENT (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    gender VARCHAR(10),
    major VARCHAR(50),
    gpa DECIMAL(3,2),
    hometown VARCHAR(50),
    graduation_year INT,
    advisor VARCHAR(100),
    address VARCHAR(200),
    phone VARCHAR(20),
    enrollment_date DATE,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## How to Use the Application

1. **Run the Application**
   
   Execute the `JdbcApplication` class to start the application. This will:
   - Initialize the database with schema and sample data
   - Launch the console menu

2. **Using the Console Menu**

   The console menu offers the following options:
   - View all students
   - Find student by ID
   - Add a new student
   - Update an existing student
   - Delete a student
   - Exit the application

3. **Examples**

   **Viewing All Students:**
   - Select option 1 from the menu
   - The application will display all students in the database

   **Finding a Student by ID:**
   - Select option 2 from the menu
   - Enter the student ID when prompted
   - The application will display the student details or a "not found" message

   **Adding a New Student:**
   - Select option 3 from the menu
   - Enter the required student information when prompted
   - The application will add the student to the database

   **Updating a Student:**
   - Select option 4 from the menu
   - Enter the student ID to update
   - Enter the new information for the student
   - The application will update the student in the database

   **Deleting a Student:**
   - Select option 5 from the menu
   - Enter the student ID to delete
   - The application will remove the student from the database

## Key JDBC Concepts Demonstrated

1. **Connection Management** - Opening, using, and closing database connections safely
2. **Statement Preparation** - Using PreparedStatement to avoid SQL injection
3. **Parameter Binding** - Setting parameters in SQL statements
4. **Result Set Processing** - Extracting data from query results
5. **Transaction Management** - Using commit and rollback to ensure data integrity
6. **Resource Cleanup** - Properly closing connections, statements, and result sets
7. **Exception Handling** - Handling SQL exceptions gracefully

## Best Practices Implemented

1. **Separation of Concerns** - Different classes handle different responsibilities
2. **Interface-based Design** - Repository interface defines the contract
3. **Resource Management** - Using try-with-resources for proper cleanup
4. **Data Validation** - Validating input before database operations
5. **Error Handling** - Proper exception handling and user feedback
6. **Parameterized Queries** - Using prepared statements to prevent SQL injection
7. **Transaction Control** - Managing transactions for data integrity

