package com.learning.springdatajpa.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Implementation of the StudentRepository interface using JDBC with H2DatabaseUtil.
 */
public class StudentRepositoryImpl implements StudentRepository {
    private static final Logger LOGGER = Logger.getLogger(StudentRepositoryImpl.class.getName());
    
    // SQL statements for student table operations
    private static final String SQL_FIND_BY_ID = "SELECT * FROM student WHERE id = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM student";
    private static final String SQL_FIND_BY_MAJOR = "SELECT * FROM student WHERE major = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT * FROM student WHERE email = ?";
    private static final String SQL_INSERT = "INSERT INTO student (first_name, last_name, email, date_of_birth, gender, major, gpa, hometown, graduation_year, advisor, address, phone, enrollment_date, active, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE student SET first_name = ?, last_name = ?, email = ?, date_of_birth = ?, gender = ?, major = ?, gpa = ?, hometown = ?, graduation_year = ?, advisor = ?, address = ?, phone = ?, enrollment_date = ?, active = ?, updated_at = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM student WHERE id = ?";
    private static final String SQL_COUNT = "SELECT COUNT(*) AS count FROM student";
    private static final String SQL_EXISTS_BY_ID = "SELECT 1 FROM student WHERE id = ?";
    private static final String SQL_UPDATE_ACTIVE_STATUS = "UPDATE student SET active = ?, updated_at = ? WHERE id = ?";
    
    // ResultSet mapper for Student objects
    private final H2DatabaseUtil.ResultSetMapper<Student> studentMapper = rs -> mapResultSetToStudent(rs);
    
    // ResultSet mapper for counting
    private final H2DatabaseUtil.ResultSetMapper<Integer> countMapper = rs -> rs.getInt("count");
    
    // ResultSet mapper for IDs
    private final H2DatabaseUtil.ResultSetMapper<Integer> idMapper = rs -> rs.getInt(1);
    
    /**
     * Maps a ResultSet row to a Student object.
     * 
     * @param rs The ResultSet to map
     * @return A Student object populated with data from the ResultSet
     * @throws SQLException if a database access error occurs
     */
    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setEmail(rs.getString("email"));
        
        // Handle potential null values
        java.sql.Date dobDate = rs.getDate("date_of_birth");
        if (dobDate != null) {
            student.setDateOfBirth(dobDate.toLocalDate());
        }
        
        String genderStr = rs.getString("gender");
        if (genderStr != null && !genderStr.isEmpty()) {
            student.setGender(genderStr.charAt(0));
        }
        
        student.setMajor(rs.getString("major"));
        student.setGpa(rs.getBigDecimal("gpa"));
        student.setHometown(rs.getString("hometown"));
        student.setGraduationYear(rs.getObject("graduation_year", Integer.class));
        student.setAdvisor(rs.getString("advisor"));
        student.setAddress(rs.getString("address"));
        student.setPhone(rs.getString("phone"));
        
        java.sql.Date enrollmentDate = rs.getDate("enrollment_date");
        if (enrollmentDate != null) {
            student.setEnrollmentDate(enrollmentDate.toLocalDate());
        }
        
        student.setActive(rs.getBoolean("active"));
        
        java.sql.Timestamp createdAtTimestamp = rs.getTimestamp("created_at");
        if (createdAtTimestamp != null) {
            student.setCreatedAt(createdAtTimestamp.toLocalDateTime());
        }
        
        java.sql.Timestamp updatedAtTimestamp = rs.getTimestamp("updated_at");
        if (updatedAtTimestamp != null) {
            student.setUpdatedAt(updatedAtTimestamp.toLocalDateTime());
        }
        
        return student;
    }
    
    @Override
    public Optional<Student> findById(int id) throws SQLException {
        LOGGER.info("Finding student with ID: " + id);
        return H2DatabaseUtil.executeQuery(
            SQL_FIND_BY_ID.replace("?", String.valueOf(id)),
            studentMapper
        );
    }
    
    @Override
    public List<Student> findAll() throws SQLException {
        LOGGER.info("Finding all students");
        return H2DatabaseUtil.executeQueryList(SQL_FIND_ALL, studentMapper);
    }
    
    @Override
    public List<Student> findByMajor(String major) throws SQLException {
        LOGGER.info("Finding students with major: " + major);
        return H2DatabaseUtil.executeQueryListWithParams(SQL_FIND_BY_MAJOR, studentMapper, major);
    }
    
    @Override
    public Optional<Student> findByEmail(String email) throws SQLException {
        LOGGER.info("Finding student with email: " + email);
        return H2DatabaseUtil.executeQuery(
            SQL_FIND_BY_EMAIL.replace("?", "'" + email + "'"),
            studentMapper
        );
    }
    
    @Override
    public Student save(Student student) throws SQLException {
        LOGGER.info("Saving student: " + student);
        
        // Set updated timestamp
        student.setUpdatedAt(LocalDateTime.now());
        
        // If the student has an ID, update it, otherwise insert a new record
        if (student.getId() != null) {
            int rowsAffected = H2DatabaseUtil.executeUpdateWithParams(
                SQL_UPDATE,
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getDateOfBirth(),
                student.getGender(),
                student.getMajor(),
                student.getGpa(),
                student.getHometown(),
                student.getGraduationYear(),
                student.getAdvisor(),
                student.getAddress(),
                student.getPhone(),
                student.getEnrollmentDate(),
                student.getActive(),
                student.getUpdatedAt(),
                student.getId()
            );
            
            if (rowsAffected == 0) {
                throw new SQLException("Failed to update student, no rows affected");
            }
            
            return student;
        } else {
            // For new students, set created timestamp if not already set
            if (student.getCreatedAt() == null) {
                student.setCreatedAt(LocalDateTime.now());
            }
            
            // Set default enrollment date if not provided
            if (student.getEnrollmentDate() == null) {
                student.setEnrollmentDate(LocalDate.now());
            }
            
            // Set active status to true if not specified
            if (student.getActive() == null) {
                student.setActive(true);
            }
            
            Optional<Integer> generatedId = H2DatabaseUtil.executeInsertWithGeneratedKeys(
                SQL_INSERT,
                idMapper,
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getDateOfBirth(),
                student.getGender(),
                student.getMajor(),
                student.getGpa(),
                student.getHometown(),
                student.getGraduationYear(),
                student.getAdvisor(),
                student.getAddress(),
                student.getPhone(),
                student.getEnrollmentDate(),
                student.getActive(),
                student.getCreatedAt(),
                student.getUpdatedAt()
            );
            
            if (generatedId.isPresent()) {
                student.setId(generatedId.get());
                LOGGER.info("Student inserted with ID: " + student.getId());
            } else {
                throw new SQLException("Failed to insert student, no ID generated");
            }
            
            return student;
        }
    }
    
    @Override
    public boolean deleteById(int id) throws SQLException {
        LOGGER.info("Deleting student with ID: " + id);
        int rowsAffected = H2DatabaseUtil.executeUpdateWithParams(SQL_DELETE_BY_ID, id);
        return rowsAffected > 0;
    }
    
    @Override
    public int count() throws SQLException {
        LOGGER.info("Counting total number of students");
        Optional<Integer> count = H2DatabaseUtil.executeQuery(SQL_COUNT, countMapper);
        return count.orElse(0);
    }
    
    @Override
    public boolean existsById(int id) throws SQLException {
        LOGGER.info("Checking if student exists with ID: " + id);
        Optional<Integer> result = H2DatabaseUtil.executeQuery(
            SQL_EXISTS_BY_ID.replace("?", String.valueOf(id)),
            rs -> 1
        );
        return result.isPresent();
    }
    
    @Override
    public boolean updateActiveStatus(int id, boolean active) throws SQLException {
        LOGGER.info("Updating active status for student with ID: " + id + " to " + active);
        LocalDateTime updatedAt = LocalDateTime.now();
        int rowsAffected = H2DatabaseUtil.executeUpdateWithParams(
            SQL_UPDATE_ACTIVE_STATUS,
            active,
            updatedAt,
            id
        );
        return rowsAffected > 0;
    }
}
