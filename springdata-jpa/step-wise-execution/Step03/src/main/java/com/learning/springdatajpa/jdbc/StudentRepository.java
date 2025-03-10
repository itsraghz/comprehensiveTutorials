package com.learning.springdatajpa.jdbc;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Student CRUD operations.
 */
public interface StudentRepository {
    /**
     * Finds a student by ID.
     *
     * @param id The ID of the student
     * @return An Optional containing the student if found, empty otherwise
     * @throws SQLException if a database access error occurs
     */
    Optional<Student> findById(int id) throws SQLException;
    
    /**
     * Finds all students.
     *
     * @return A list of all students
     * @throws SQLException if a database access error occurs
     */
    List<Student> findAll() throws SQLException;
    
    /**
     * Finds students by major.
     *
     * @param major The major to search for
     * @return A list of students with the specified major
     * @throws SQLException if a database access error occurs
     */
    List<Student> findByMajor(String major) throws SQLException;
    
    /**
     * Finds a student by email.
     *
     * @param email The email to search for
     * @return An Optional containing the student if found, empty otherwise
     * @throws SQLException if a database access error occurs
     */
    Optional<Student> findByEmail(String email) throws SQLException;
    
    /**
     * Saves a new student or updates an existing one.
     *
     * @param student The student to save
     * @return The saved student with updated ID if it was a new student
     * @throws SQLException if a database access error occurs
     */
    Student save(Student student) throws SQLException;
    
    /**
     * Deletes a student by ID.
     *
     * @param id The ID of the student to delete
     * @return true if the student was deleted, false otherwise
     * @throws SQLException if a database access error occurs
     */
    boolean deleteById(int id) throws SQLException;
    
    /**
     * Counts the total number of students.
     *
     * @return The total number of students
     * @throws SQLException if a database access error occurs
     */
    int count() throws SQLException;
    
    /**
     * Checks if a student with the specified ID exists.
     *
     * @param id The ID to check
     * @return true if a student with the ID exists, false otherwise
     * @throws SQLException if a database access error occurs
     */
    boolean existsById(int id) throws SQLException;
    
    /**
     * Updates a student's active status.
     *
     * @param id The ID of the student
     * @param active The new active status
     * @return true if the student was updated, false otherwise
     * @throws SQLException if a database access error occurs
     */
    boolean updateActiveStatus(int id, boolean active) throws SQLException;
}

