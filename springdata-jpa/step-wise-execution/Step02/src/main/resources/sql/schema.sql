-- Schema file for Student Management System

-- Drop table if exists to avoid conflicts
DROP TABLE IF EXISTS student;

-- Create student table
CREATE TABLE student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    date_of_birth DATE,
    gender CHAR(1) CHECK (gender IN ('M', 'F', 'O')),
    major VARCHAR(50),
    gpa DECIMAL(3,2),
    hometown VARCHAR(100),
    graduation_year INT,
    advisor VARCHAR(100),
    address VARCHAR(255),
    phone VARCHAR(15),
    enrollment_date DATE DEFAULT CURRENT_DATE(),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);

-- Add index on commonly searched fields
CREATE INDEX idx_student_name ON student(last_name, first_name);
CREATE INDEX idx_student_email ON student(email);

