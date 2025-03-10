# Spring Data JPA - Step 02: H2 Database Integration

## Overview

This project represents Step 02 in our Spring Data JPA learning journey. In this step, we've integrated the H2 in-memory database with our Java application and implemented a comprehensive console application that demonstrates CRUD (Create, Read, Update, Delete) operations.

## What's Implemented

- H2 in-memory database integration
- Java utility class for database connection and operations
- Interactive console application with menu-driven interface
- Complete CRUD operations for student records
- SQL scripts for schema creation, data insertion, and example queries

## Key Features

### H2 Database Utility
- Connection management
- Table creation and initialization
- SQL execution utilities
- Resource management

### Console Application
- Interactive menu system
- Create new student records with validation
- Read student data by ID or list all students
- Update existing student information
- Delete student records with confirmation
- Formatted table output for data display

## Database Connection Issue & Resolution

During development, we encountered a connection management issue where the database connection was being prematurely closed. 

**Issue:**
- The application obtained a connection at startup
- The utility methods internally created and closed connections
- This caused "The object is already closed" errors when attempting subsequent database operations

**Solution:**
- Modified the console application to obtain a fresh connection for each database operation
- Ensured proper closing of resources using try-with-resources
- Implemented connection scope management within each CRUD method

This approach follows best practices for resource management in database applications, preventing connection leaks while ensuring the connection is available when needed.

## Running the Application

### Prerequisites
- Java 17 or higher
- Maven

### Command to Run
```bash
mvn clean compile exec:java -Dexec.mainClass="com.learning.springdatajpa.H2DatabaseConsoleApp"
```

### Using the Application
1. The application will display a menu with options 1-6
2. Select an option by entering the corresponding number
3. Follow the prompts to perform database operations
4. Select option 6 to exit the application

## Available Documentation

This project includes comprehensive documentation:

- **[Console Output Example](consoleOutputs/consoleOutput-H2DatabaseConsoleApp.md)**: Sample interaction with the application
- **[H2DatabaseUtil Explanation](docs/explanation/ReadMe-Explanation-H2DatabaseUtil.md)**: Details about the database utility class
- **[H2DatabaseConsoleApp Explanation](docs/explanation/ReadMe-Explanation-H2DatabaseConsoleApp.md)**: Documentation for the console application
- **SQL Files**: 
  - `src/main/resources/sql/schema.sql`: Table definitions
  - `src/main/resources/sql/data.sql`: Sample data
  - `src/main/resources/sql/queries.sql`: Example queries

## Next Steps

After mastering the basics of H2 database connectivity and CRUD operations, you'll be prepared to move on to Step 03, where we'll introduce Spring Data JPA to streamline database operations further.

# Step 02: Learning H2 Database Connection

This step focuses on learning how to connect to and work with an H2 database in a Java application. H2 is a lightweight, in-memory database written in Java that provides a convenient way to prototype and test database applications.

## Purpose

The main objectives of this step are:
- Learn how to add H2 database dependency to a Java project
- Create database connections using JDBC
- Execute SQL statements to create tables, insert data, and query results
- Understand how to use the H2 Web Console for database management

## Project Structure

```
Step02/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── learning/
│   │   │           └── springdatajpa/
│   │   │               ├── H2DatabaseDemo.java
│   │   │               ├── HelloWorld.java
│   │   │               └── db/
│   │   │                   └── H2DatabaseUtil.java
│   │   └── resources/
│   │       └── sql/
│   │           ├── schema.sql
│   │           ├── data.sql
│   │           └── queries.sql
│   └── test/
├── consoleOutputs/
├── pom.xml
└── README.md
```

## Running the H2DatabaseDemo

To run the H2DatabaseDemo class:

1. Make sure you have JDK 17 installed
2. Navigate to the Step02 directory
3. Build the project with Maven:
   ```bash
   mvn clean package
   ```
4. Run the H2DatabaseDemo class:
   ```bash
   java -cp target/spring-data-jpa-step02-1.0.0.jar com.learning.springdatajpa.H2DatabaseDemo
   ```

The program will create a connection to an H2 in-memory database, create a student table, insert sample records, and display the query results.

## Accessing and Using the H2 Web Console

H2 database comes with a built-in web console that allows you to interact with the database using a browser interface.

### Starting the H2 Web Console

1. Download the H2 database JAR file from [the H2 website](http://www.h2database.com/html/download.html) if you don't already have it
2. Run the H2 console by executing:
   ```bash
   java -jar h2-2.2.224.jar
   ```
   This will start the H2 console and open it in your default web browser.

### Connecting to the Database

1. In the login page that appears in your browser, set the following parameters:
   - **JDBC URL**: `jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1` (for an in-memory database)
   - **Username**: sa
   - **Password**: (leave empty)
2. Click "Connect" to log in to the database

### Using the Web Console

Once connected:
1. You'll see a SQL execution panel on the left where you can enter and execute SQL statements
2. Results will be displayed in the right panel
3. The console also includes schema browsers and other tools to help manage your database

## Executing SQL Files in the H2 Web Console

You can execute the SQL files provided in this project through the H2 Web Console:

1. **schema.sql**: Creates the student table structure
   - Open the `Step02/src/main/resources/sql/schema.sql` file
   - Copy its contents
   - Paste into the SQL execution panel in the H2 Web Console
   - Click "Run" (or press Ctrl+Enter)

2. **data.sql**: Populates the student table with sample data
   - After executing schema.sql, open `Step02/src/main/resources/sql/data.sql`
   - Copy its contents
   - Paste into the SQL execution panel
   - Click "Run"

3. **queries.sql**: Contains various queries to analyze the student data
   - After executing data.sql, open `Step02/src/main/resources/sql/queries.sql`
   - You can execute each query separately by selecting it and clicking "Run"
   - Observe the results for each query in the right panel

## Key Features Demonstrated

- Setting up H2 database connection in a Java application
- Creating and populating tables programmatically
- Querying database data using JDBC
- Using the H2 Web Console for direct SQL execution

## Next Steps

After completing this step, you will have a solid understanding of how to work with an H2 database. This knowledge will be essential when we move to Spring Data JPA in the next steps, as H2 is often used as the underlying database for Spring Data JPA applications during development.

# Step 01: Basic Java Maven Project

This directory contains a basic Java Maven project with JDK 17 and a simple HelloWorld program. This is the first step in our learning journey for Spring Data JPA.

## Project Structure

```
Step01/
├── pom.xml                 # Maven configuration file
└── src/
    └── main/
        └── java/
            └── com/
                └── learning/
                    └── springdatajpa/
                        └── HelloWorld.java   # Simple HelloWorld program
```

## Technologies Used

- Java 17
- Maven 3.9+

## How to Run

To compile and run the HelloWorld program, execute the following commands from the Step01 directory:

```bash
# Compile the project
mvn clean compile

# Run the HelloWorld class
mvn exec:java -Dexec.mainClass="com.learning.springdatajpa.HelloWorld"
```

## Purpose

This simple project serves as the foundation for learning Spring Data JPA. It demonstrates:

1. Basic Maven project setup
2. Java 17 configuration
3. Simple Java program execution

In the next steps, we will build upon this foundation by adding database connectivity and implementing JDBC operations.

