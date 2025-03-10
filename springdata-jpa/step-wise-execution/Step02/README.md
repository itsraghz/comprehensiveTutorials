# Spring Data JPA - Step 02: H2 Database Integration

## Overview

This project represents Step 02 in our Spring Data JPA learning journey. In this step, we focus on adding H2 database to our Java Maven project and learning how to use the H2 Web Console for database operations.

## What's Implemented

- H2 in-memory database integration via Maven dependency
- SQL scripts for schema creation, data insertion, and example queries
- H2 Web Console access and usage instructions
- Documentation for H2 database features and capabilities

## Key Features

### H2 Database
- Lightweight, in-memory database
- Embedded mode for development and testing
- Fast startup and execution
- SQL standard compliance

### SQL Scripts
- Schema definitions (DDL)
- Data population scripts (DML)
- Example queries for analysis
- Educational SQL examples

## Available Documentation

This project includes comprehensive documentation:

- **[H2 Web Console Guide](docs/H2-Web-Console-Guide.md)**: Detailed guide for using the H2 Web Console
- **SQL Files**: 
  - `src/main/resources/sql/schema.sql`: Table definitions
  - `src/main/resources/sql/data.sql`: Sample data
  - `src/main/resources/sql/queries.sql`: Example queries

## Using the H2 Web Console

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

## Next Steps

After completing Step 02, you will have a solid understanding of how to work with an H2 database using the Web Console. In Step 03, we will expand on this knowledge by implementing JDBC operations to programmatically interact with the H2 database.
