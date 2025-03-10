-- Drop the STUDENT table if it exists
DROP TABLE IF EXISTS STUDENT;

-- Create the STUDENT table
CREATE TABLE STUDENT (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    date_of_birth DATE,
    gender VARCHAR(10),
    major VARCHAR(100),
    gpa DECIMAL(3,2),
    hometown VARCHAR(100),
    graduation_year INT,
    advisor VARCHAR(100),
    address VARCHAR(255),
    phone VARCHAR(20),
    enrollment_date DATE,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create an index on the email column for faster lookups
CREATE INDEX idx_student_email ON STUDENT(email);

