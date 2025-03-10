package com.learning.springdatajpa.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for H2 Database operations with improved error handling and additional JDBC utilities.
 */
public class H2DatabaseUtil {
    private static final Logger LOGGER = Logger.getLogger(H2DatabaseUtil.class.getName());
    
    // Database connection properties
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:file:./target/db/studentdb";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";
    
    static {
        try {
            // Load the H2 JDBC driver
            Class.forName(DB_DRIVER);
            LOGGER.info("H2 JDBC driver loaded successfully");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Failed to load H2 JDBC driver", e);
            throw new RuntimeException("Failed to load H2 JDBC driver", e);
        }
    }
    
    /**
     * Gets a database connection.
     * 
     * @return A database connection
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            LOGGER.info("Database connection established successfully");
            return connection;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to establish database connection", e);
            throw e;
        }
    }
    
    /**
     * Executes a SQL query that returns a single result.
     * 
     * @param sql The SQL query to execute
     * @param resultMapper A function that maps a ResultSet to the desired result type
     * @param <T> The type of the result
     * @return An Optional containing the result, or empty if no result was found
     * @throws SQLException if a database access error occurs
     */
    public static <T> Optional<T> executeQuery(String sql, ResultSetMapper<T> resultMapper) throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            
            if (resultSet.next()) {
                return Optional.ofNullable(resultMapper.map(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error executing query: " + sql, e);
            throw e;
        }
    }
    
    /**
     * Executes a SQL query that returns multiple results.
     * 
     * @param sql The SQL query to execute
     * @param resultMapper A function that maps a ResultSet to the desired result type
     * @param <T> The type of the results
     * @return A List of results
     * @throws SQLException if a database access error occurs
     */
    public static <T> List<T> executeQueryList(String sql, ResultSetMapper<T> resultMapper) throws SQLException {
        List<T> results = new ArrayList<>();
        
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            
            while (resultSet.next()) {
                results.add(resultMapper.map(resultSet));
            }
            
            LOGGER.info("Query executed successfully, returned " + results.size() + " results");
            return results;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error executing query: " + sql, e);
            throw e;
        }
    }
    
    /**
     * Executes a SQL query with parameters that returns multiple results.
     * 
     * @param sql The SQL query to execute
     * @param resultMapper A function that maps a ResultSet to the desired result type
     * @param params The parameters to use in the prepared statement
     * @param <T> The type of the results
     * @return A List of results
     * @throws SQLException if a database access error occurs
     */
    public static <T> List<T> executeQueryListWithParams(String sql, ResultSetMapper<T> resultMapper, Object... params) throws SQLException {
        List<T> results = new ArrayList<>();
        
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            // Set parameters
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(resultMapper.map(resultSet));
                }
            }
            
            LOGGER.info("Parameterized query executed successfully, returned " + results.size() + " results");
            return results;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error executing parameterized query: " + sql, e);
            throw e;
        }
    }
    
    /**
     * Executes a SQL update statement (INSERT, UPDATE, DELETE).
     * 
     * @param sql The SQL statement to execute
     * @return The number of rows affected
     * @throws SQLException if a database access error occurs
     */
    public static int executeUpdate(String sql) throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            
            int rowsAffected = statement.executeUpdate(sql);
            LOGGER.info("Update executed successfully, " + rowsAffected + " rows affected");
            return rowsAffected;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error executing update: " + sql, e);
            throw e;
        }
    }
    
    /**
     * Executes a SQL update statement with parameters.
     * 
     * @param sql The SQL statement to execute
     * @param params The parameters to use in the prepared statement
     * @return The number of rows affected
     * @throws SQLException if a database access error occurs
     */
    public static int executeUpdateWithParams(String sql, Object... params) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            // Set parameters
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            
            int rowsAffected = statement.executeUpdate();
            LOGGER.info("Parameterized update executed successfully, " + rowsAffected + " rows affected");
            return rowsAffected;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error executing parameterized update: " + sql, e);
            throw e;
        }
    }
    
    /**
     * Executes a SQL INSERT statement and returns the generated keys.
     * 
     * @param sql The SQL INSERT statement to execute
     * @param keyMapper A function that maps a ResultSet to the desired key type
     * @param params The parameters to use in the prepared statement
     * @param <T> The type of the generated key
     * @return An Optional containing the generated key, or empty if no key was generated
     * @throws SQLException if a database access error occurs
     */
    public static <T> Optional<T> executeInsertWithGeneratedKeys(String sql, ResultSetMapper<T> keyMapper, Object... params) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Set parameters
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected == 0) {
                LOGGER.warning("Insert statement did not generate any rows");
                return Optional.empty();
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    T key = keyMapper.map(generatedKeys);
                    LOGGER.info("Insert executed successfully, generated key retrieved");
                    return Optional.ofNullable(key);
                } else {
                    LOGGER.warning("Insert executed successfully, but no ID was generated");
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error executing insert with generated keys: " + sql, e);
            throw e;
        }
    }
    
    /**
     * Executes a transaction consisting of multiple SQL statements.
     * 
     * @param transaction A function that performs the transaction operations
     * @param <T> The return type of the transaction
     * @return The result of the transaction
     * @throws SQLException if a database access error occurs
     */
    public static <T> T executeTransaction(TransactionOperation<T> transaction) throws SQLException {
        Connection connection = null;
        boolean originalAutoCommit = true;
        
        try {
            connection = getConnection();
            originalAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            
            T result = transaction.execute(connection);
            
            connection.commit();
            LOGGER.info("Transaction committed successfully");
            return result;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    LOGGER.info("Transaction rolled back due to error");
                } catch (SQLException rollbackEx) {
                    LOGGER.log(Level.SEVERE, "Error rolling back transaction", rollbackEx);
                }
            }
            LOGGER.log(Level.SEVERE, "Error executing transaction", e);
            throw e;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(originalAutoCommit);
                    connection.close();
                } catch (SQLException closeEx) {
                    LOGGER.log(Level.WARNING, "Error closing connection", closeEx);
                }
            }
        }
    }
    
    /**
     * Closes a database resource silently, logging any errors.
     * 
     * @param autoCloseable The resource to close
     */
    public static void closeQuietly(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error closing resource", e);
            }
        }
    }
    
    /**
     * Functional interface for mapping a ResultSet to an object.
     *
     * @param <T> The type of object to map to
     */
    @FunctionalInterface
    public interface ResultSetMapper<T> {
        /**
         * Maps the current row of a ResultSet to an object.
         *
         * @param rs The ResultSet to map
         * @return The mapped object
         * @throws SQLException if a database access error occurs
         */
        T map(ResultSet rs) throws SQLException;
    }
    
    /**
     * Functional interface for transaction operations.
     *
     * @param <T> The type of the result of the transaction
     */
    @FunctionalInterface
    public interface TransactionOperation<T> {
        /**
         * Executes a transaction operation.
         *
         * @param connection The database connection to use
         * @return The result of the transaction
         * @throws SQLException if a database access error occurs
         */
        T execute(Connection connection) throws SQLException;
    }
}

