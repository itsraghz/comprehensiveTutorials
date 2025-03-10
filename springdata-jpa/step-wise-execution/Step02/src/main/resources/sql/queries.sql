-- Queries.sql - Examples of SQL queries for analyzing student data
-- These queries demonstrate various SQL capabilities using the student table

-- ========== BASIC QUERIES ==========

-- 1. Select all students
SELECT * FROM student;

-- 2. Basic filtering with WHERE
-- Find all Computer Science majors
SELECT id, first_name, last_name, major, gpa 
FROM student 
WHERE major = 'Computer Science';

-- 3. Ordering results
-- List students by GPA in descending order
SELECT id, first_name, last_name, major, gpa 
FROM student 
ORDER BY gpa DESC;

-- 4. Using LIMIT to get top results
-- Get the top 3 students with highest GPA
SELECT id, first_name, last_name, major, gpa 
FROM student 
ORDER BY gpa DESC 
LIMIT 3;

-- 5. Multiple conditions with AND/OR
-- Find all seniors (graduation year 2024) majoring in either Biology or Physics
SELECT * FROM student 
WHERE graduation_year = 2024 
AND (major = 'Biology' OR major = 'Physics');

-- ========== AGGREGATION QUERIES ==========

-- 1. Basic counts
-- Count the number of students
SELECT COUNT(*) AS total_students FROM student;

-- 2. Count by category
-- Count the number of students in each major
SELECT major, COUNT(*) AS student_count 
FROM student 
GROUP BY major 
ORDER BY student_count DESC;

-- 3. Average, minimum, and maximum values
-- Find average, minimum, and maximum GPAs
SELECT 
    AVG(gpa) AS average_gpa,
    MIN(gpa) AS minimum_gpa,
    MAX(gpa) AS maximum_gpa
FROM student;

-- 4. Aggregation with GROUP BY
-- Find average GPA by major
SELECT major, AVG(gpa) AS average_gpa 
FROM student 
GROUP BY major 
ORDER BY average_gpa DESC;

-- 5. Using HAVING to filter aggregation results
-- Find majors with more than 2 students
SELECT major, COUNT(*) AS student_count 
FROM student 
GROUP BY major 
HAVING COUNT(*) > 2;

-- ========== ADVANCED QUERIES ==========

-- 1. Subqueries in WHERE clause
-- Find students with GPA higher than average
SELECT id, first_name, last_name, major, gpa 
FROM student 
WHERE gpa > (SELECT AVG(gpa) FROM student);

-- 2. Subqueries in FROM clause
-- Find the difference between each student's GPA and their major's average GPA
SELECT s.id, s.first_name, s.last_name, s.major, s.gpa,
       s.gpa - avg_gpa AS gpa_difference
FROM student s
JOIN (
    SELECT major, AVG(gpa) AS avg_gpa
    FROM student
    GROUP BY major
) AS major_avg
ON s.major = major_avg.major
ORDER BY gpa_difference DESC;

-- 3. Using CASE statements for conditional logic
-- Classify students by GPA range
SELECT 
    id, first_name, last_name, gpa,
    CASE 
        WHEN gpa >= 3.7 THEN 'Excellent'
        WHEN gpa >= 3.0 THEN 'Good'
        WHEN gpa >= 2.0 THEN 'Average'
        ELSE 'Needs Improvement'
    END AS performance
FROM student
ORDER BY gpa DESC;

-- 4. Counting students by graduation year
SELECT graduation_year, COUNT(*) AS student_count
FROM student
GROUP BY graduation_year
ORDER BY graduation_year;

-- 5. Finding the distribution of advisors
SELECT advisor, COUNT(*) AS student_count
FROM student
GROUP BY advisor
ORDER BY student_count DESC;

-- ========== ANALYTICAL QUERIES ==========

-- 1. Find top student in each major
SELECT s.id, s.first_name, s.last_name, s.major, s.gpa
FROM student s
JOIN (
    SELECT major, MAX(gpa) AS max_gpa
    FROM student
    GROUP BY major
) AS max_gpas
ON s.major = max_gpas.major AND s.gpa = max_gpas.max_gpa
ORDER BY s.major;

-- 2. Hometown distribution with percentage
SELECT 
    hometown, 
    COUNT(*) AS student_count,
    ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM student), 2) AS percentage
FROM student
GROUP BY hometown
ORDER BY student_count DESC;

-- 3. Cumulative student count by major and graduation year
SELECT 
    major,
    graduation_year,
    COUNT(*) AS students_in_year,
    SUM(COUNT(*)) OVER (PARTITION BY major ORDER BY graduation_year) AS cumulative_count
FROM student
GROUP BY major, graduation_year
ORDER BY major, graduation_year;

-- 4. Students whose GPA is in the top quartile overall
SELECT id, first_name, last_name, major, gpa
FROM student
WHERE gpa >= (
    SELECT MIN(gpa)
    FROM (
        SELECT gpa
        FROM student
        ORDER BY gpa DESC
        LIMIT (SELECT CEILING(COUNT(*) * 0.25) FROM student)
    ) AS top_quartile
);

-- 5. Rank students by GPA within each major
SELECT 
    id, 
    first_name, 
    last_name, 
    major, 
    gpa,
    RANK() OVER(PARTITION BY major ORDER BY gpa DESC) AS major_rank
FROM student;

