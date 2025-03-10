# H2 Database Console Application - Interaction Log

This document shows a complete interaction with the H2 Database Console Application, demonstrating the database initialization, table creation, and CRUD operations on student records.

## Table of Contents
- [Maven Build Process](#maven-build-process)
- [Database Initialization](#database-initialization)
- [Application Session](#application-session)
  - [Finding a Student by ID (Empty Database)](#finding-a-student-by-id-empty-database)
  - [Listing All Students (Empty Database)](#listing-all-students-empty-database)
  - [Adding a New Student](#adding-a-new-student)
  - [Finding the Added Student by ID](#finding-the-added-student-by-id)
  - [Viewing All Students](#viewing-all-students)
  - [Exiting the Application](#exiting-the-application)

## Maven Build Process

The application is built and run using Maven with the following command:

```
$ mvn clean compile exec:java -Dexec.mainClass="com.learning.springdatajpa.H2DatabaseConsoleApp"
[INFO] Scanning for projects...
[INFO] 
[INFO] ---------< com.learning.springdatajpa:spring-data-jpa-step02 >----------
[INFO] Building spring-data-jpa-step02 1.0.0
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- clean:3.2.0:clean (default-clean) @ spring-data-jpa-step02 ---
[INFO] Deleting /Users/raghavan.muthu/raghs/prfsnl/github-repos/comprehensiveTutorials/springdata-jpa/step-wise-execution/Step02/target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ spring-data-jpa-step02 ---
[INFO] Copying 3 resources from src/main/resources to target/classes
[INFO] 
[INFO] --- compiler:3.11.0:compile (default-compile) @ spring-data-jpa-step02 ---
[INFO] Changes detected - recompiling the module! :source
[INFO] Compiling 4 source files with javac [debug target 17] to target/classes
[INFO] 
[INFO] --- exec:3.5.0:java (default-cli) @ spring-data-jpa-step02 ---
```

You can also run the application by executing the compiled class:

```
$ java -cp target/classes com.learning.springdatajpa.H2DatabaseConsoleApp
```

## Database Initialization

When the application starts, it initializes the H2 in-memory database and creates the necessary tables:

```
Initializing H2 database...
Connected to H2 in-memory database
Table created successfully
All database tables created successfully
```

## Application Session

Upon successful initialization, the application displays the main menu:

```
===== STUDENT MANAGEMENT SYSTEM =====
1. Add a new student
2. Find a student by ID
3. View all students
4. Update student information
5. Delete a student
6. Exit
Enter your choice (1-6): 
```

### Finding a Student by ID (Empty Database)

First, we attempted to find a student by ID in an empty database:

```
----- Find Student by ID -----
Enter student ID: 1
Connected to H2 in-memory database
No student found with ID: 1

Press Enter to continue...
```

### Listing All Students (Empty Database)

Next, we listed all students in the empty database:

```
----- All Students -----
Connected to H2 in-memory database
+-----------+--------------------+--------------------+-----+---------------+-------+
| ID        | First Name         | Last Name          | Age | Major         | GPA   |
+-----------+--------------------+--------------------+-----+---------------+-------+
+-----------+--------------------+--------------------+-----+---------------+-------+
No students found in the database.

Press Enter to continue...
```

### Adding a New Student

We proceeded to add a new student:

```
----- Add a New Student -----
Enter first name: Raghavan
Enter last name: Muthu
Enter email: raghs@email.com
Enter age: 43
Enter major (or press Enter to skip): 
Enter GPA (0.0-4.0, or press Enter to skip): 
Connected to H2 in-memory database
Student added successfully with ID: 1

Press Enter to continue...
```

### Finding the Added Student by ID

After adding a student, we searched for it by ID:

```
----- Find Student by ID -----
Enter student ID: 1
Connected to H2 in-memory database

Student Found:
ID: 1
Name: Raghavan Muthu
Email: raghs@email.com
Age: 43

Press Enter to continue...
```

### Viewing All Students

We then listed all students to confirm the new entry:

```
----- All Students -----
Connected to H2 in-memory database
+-----------+--------------------+--------------------+-----+---------------+-------+
| ID        | First Name         | Last Name          | Age | Major         | GPA   |
+-----------+--------------------+--------------------+-----+---------------+-------+
| 1         | Raghavan           | Muthu              | 43  | N/A           | N/A   |
+-----------+--------------------+--------------------+-----+---------------+-------+

Press Enter to continue...
```

### Exiting the Application

Finally, we exited the application:

```
===== STUDENT MANAGEMENT SYSTEM =====
1. Add a new student
2. Find a student by ID
3. View all students
4. Update student information
5. Delete a student
6. Exit
Enter your choice (1-6): 6
Exiting application...
```
The Maven build completed successfully:

```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  03:18 min
[INFO] Finished at: 2025-03-10T01:09:26+05:30
[INFO] ------------------------------------------------------------------------
```

## Alternative Ways to Run the Application

You can also package the application and run it as a JAR file:

```
$ mvn package
$ java -jar target/spring-data-jpa-step02-1.0.0.jar
```

For development and debugging, you might prefer using an IDE like IntelliJ IDEA or Eclipse to run the `H2DatabaseConsoleApp` class directly.
```

