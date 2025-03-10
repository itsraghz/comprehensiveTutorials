[INFO] Scanning for projects...
[INFO] 
[INFO] ----------------< com.learning:spring-data-jpa-step03 >-----------------
[INFO] Building spring-data-jpa-step03 0.0.1-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- clean:3.2.0:clean (default-clean) @ spring-data-jpa-step03 ---
[INFO] Deleting /Users/raghavan.muthu/raghs/prfsnl/github-repos/comprehensiveTutorials/springdata-jpa/step-wise-execution/Step03/target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ spring-data-jpa-step03 ---
[INFO] Copying 2 resources from src/main/resources to target/classes
[INFO] 
[INFO] --- compiler:3.10.1:compile (default-compile) @ spring-data-jpa-step03 ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 7 source files to /Users/raghavan.muthu/raghs/prfsnl/github-repos/comprehensiveTutorials/springdata-jpa/step-wise-execution/Step03/target/classes
[INFO] /Users/raghavan.muthu/raghs/prfsnl/github-repos/comprehensiveTutorials/springdata-jpa/step-wise-execution/Step03/src/main/java/com/learning/springdatajpa/jdbc/JdbcStudentApp.java: /Users/raghavan.muthu/raghs/prfsnl/github-repos/comprehensiveTutorials/springdata-jpa/step-wise-execution/Step03/src/main/java/com/learning/springdatajpa/jdbc/JdbcStudentApp.java uses or overrides a deprecated API.
[INFO] /Users/raghavan.muthu/raghs/prfsnl/github-repos/comprehensiveTutorials/springdata-jpa/step-wise-execution/Step03/src/main/java/com/learning/springdatajpa/jdbc/JdbcStudentApp.java: Recompile with -Xlint:deprecation for details.
[INFO] 
[INFO] --- exec:3.1.0:java (default-cli) @ spring-data-jpa-step03 ---
Starting JDBC Student Management Application...
Initializing database...
Database initialization completed successfully.
Launching Student Management Application...
===== JDBC Student Management System =====

===== MENU =====
1. List all students
2. Find student by ID
3. Find students by major
4. Add a new student
5. Update a student
6. Delete a student
7. Display statistics
8. Exit
Enter your choice: 
===== All Students =====

------------------------
ID: 1
Name: John Smith
Email: john.smith@university.edu
Major: Computer Science
Date of Birth: 1998-05-12
GPA: 3.75
Hometown: Chicago
Graduation Year: 2023
Gender: M
------------------------

------------------------
ID: 2
Name: Emily Davis
Email: emily.davis@university.edu
Major: Mathematics
Date of Birth: 1999-11-08
GPA: 3.92
Hometown: Boston
Graduation Year: 2023
Gender: F
------------------------

------------------------
ID: 3
Name: Michael Johnson
Email: michael.j@university.edu
Major: Physics
Date of Birth: 2000-03-24
GPA: 3.45
Hometown: San Francisco
Graduation Year: 2024
Gender: M
------------------------

------------------------
ID: 4
Name: Sophia Garcia
Email: sophia.g@university.edu
Major: Business Administration
Date of Birth: 1997-09-17
GPA: 3.88
Hometown: Miami
Graduation Year: 2022
Gender: F
------------------------

------------------------
ID: 5
Name: David Wilson
Email: david.wilson@university.edu
Major: English Literature
Date of Birth: 2001-01-30
GPA: 3.67
Hometown: Seattle
Graduation Year: 2025
Gender: M
------------------------
Total: 5 students

Press Enter to continue...

===== MENU =====
1. List all students
2. Find student by ID
3. Find students by major
4. Add a new student
5. Update a student
6. Delete a student
7. Display statistics
8. Exit
Enter your choice: Enter student ID: 
===== Student Found =====

------------------------
ID: 3
Name: Michael Johnson
Email: michael.j@university.edu
Major: Physics
Date of Birth: 2000-03-24
GPA: 3.45
Hometown: San Francisco
Graduation Year: 2024
Gender: M
------------------------

Press Enter to continue...

===== MENU =====
1. List all students
2. Find student by ID
3. Find students by major
4. Add a new student
5. Update a student
6. Delete a student
7. Display statistics
8. Exit
Enter your choice: 
===== Student Statistics =====
Total students: 5
Average GPA: 3.73

Distribution by Major:
Computer Science    : 1 students (20.0%)
English Literature  : 1 students (20.0%)
Mathematics         : 1 students (20.0%)
Business Administration: 1 students (20.0%)
Physics             : 1 students (20.0%)

Press Enter to continue...

===== MENU =====
1. List all students
2. Find student by ID
3. Find students by major
4. Add a new student
5. Update a student
6. Delete a student
7. Display statistics
8. Exit
Enter your choice: Enter major: 
===== Students with Major: Computer Science =====

------------------------
ID: 1
Name: John Smith
Email: john.smith@university.edu
Major: Computer Science
Date of Birth: 1998-05-12
GPA: 3.75
Hometown: Chicago
Graduation Year: 2023
Gender: M
------------------------
Total: 1 students

Press Enter to continue...

===== MENU =====
1. List all students
2. Find student by ID
3. Find students by major
4. Add a new student
5. Update a student
6. Delete a student
7. Display statistics
8. Exit
Enter your choice: 
===== Add New Student =====
Enter first name: Enter last name: Enter email: Enter major: Enter date of birth (yyyy-MM-dd) or leave empty: Enter GPA or leave empty: Enter hometown or leave empty: Enter graduation year or leave empty: Enter gender (M/F/O) or leave empty: 
Press Enter to continue...

===== MENU =====
1. List all students
2. Find student by ID
3. Find students by major
4. Add a new student
5. Update a student
6. Delete a student
7. Display statistics
8. Exit
Enter your choice: Exiting application. Goodbye!
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  01:38 min
[INFO] Finished at: 2025-03-10T19:52:04+05:30
[INFO] ------------------------------------------------------------------------
