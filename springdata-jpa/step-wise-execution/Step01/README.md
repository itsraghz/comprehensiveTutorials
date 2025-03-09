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

