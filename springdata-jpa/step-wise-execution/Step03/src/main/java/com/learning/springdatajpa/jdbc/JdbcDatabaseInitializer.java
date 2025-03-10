package com.learning.springdatajpa.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JdbcDatabaseInitializer is responsible for initializing the database schema 
 * and loading the initial data on application startup.
 */
public class JdbcDatabaseInitializer {
    
    private static final Logger LOGGER = Logger.getLogger(JdbcDatabaseInitializer.class.getName());
    private static final String SCHEMA_FILE = "/sql/schema.sql";
    private static final String DATA_FILE = "/sql/data.sql";
    
    /**
     * Initializes the database by executing schema.sql and data.sql files.
     * 
     * @return true if initialization was successful, false otherwise
     */
    public static boolean initializeDatabase() {
        LOGGER.info("Initializing database...");
        boolean schemaInitialized = executeSqlFile(SCHEMA_FILE);
        
        if (!schemaInitialized) {
            LOGGER.severe("Failed to initialize database schema. Data loading will be skipped.");
            return false;
        }
        
        boolean dataLoaded = executeSqlFile(DATA_FILE);
        if (!dataLoaded) {
            LOGGER.warning("Failed to load initial data, but schema was created successfully.");
            return false;
        }
        
        LOGGER.info("Database initialized successfully!");
        return true;
    }
    
    /**
     * Executes all SQL statements in the specified file.
     * 
     * @param filePath the path to the SQL file in resources
     * @return true if all statements executed successfully, false otherwise
     */
    private static boolean executeSqlFile(String filePath) {
        List<String> sqlStatements = readSqlFile(filePath);
        
        if (sqlStatements.isEmpty()) {
            LOGGER.warning("No SQL statements found in file: " + filePath);
            return false;
        }
        
        try (Connection connection = H2DatabaseUtil.getConnection()) {
            if (connection == null) {
                LOGGER.severe("Failed to get database connection.");
                return false;
            }
            
            // Disable auto-commit to allow rollback in case of errors
            boolean autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            
            try (Statement statement = connection.createStatement()) {
                for (String sql : sqlStatements) {
                    try {
                        statement.execute(sql);
                        LOGGER.fine("Executed SQL: " + sql);
                    } catch (SQLException e) {
                        LOGGER.log(Level.SEVERE, "Error executing SQL: " + sql, e);
                        connection.rollback();
                        connection.setAutoCommit(autoCommit);
                        return false;
                    }
                }
                connection.commit();
                connection.setAutoCommit(autoCommit);
                LOGGER.info("Successfully executed all SQL statements from " + filePath);
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during initialization", e);
            return false;
        }
    }
    
    /**
     * Reads SQL statements from a file in the resources directory.
     * 
     * @param filePath the path to the SQL file in resources
     * @return a list of SQL statements
     */
    private static List<String> readSqlFile(String filePath) {
        List<String> sqlStatements = new ArrayList<>();
        StringBuilder currentStatement = new StringBuilder();
        
        try (InputStream is = JdbcDatabaseInitializer.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            
            if (is == null) {
                throw new IOException("Resource not found: " + filePath);
            }
            
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip comments and empty lines
                if (line.trim().isEmpty() || line.trim().startsWith("--")) {
                    continue;
                }
                
                currentStatement.append(line).append(" ");
                
                // If the line ends with a semicolon, it's the end of a statement
                if (line.trim().endsWith(";")) {
                    sqlStatements.add(currentStatement.toString());
                    currentStatement = new StringBuilder();
                }
            }
            
            // Add any remaining SQL that might not end with a semicolon
            if (currentStatement.length() > 0) {
                sqlStatements.add(currentStatement.toString());
            }
            
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading SQL file: " + filePath, e);
        }
        
        return sqlStatements;
    }
    
    /**
     * Main method for standalone testing of database initialization.
     */
    public static void main(String[] args) {
        boolean initialized = initializeDatabase();
        System.out.println("Database initialization " + (initialized ? "successful" : "failed"));
    }
}

