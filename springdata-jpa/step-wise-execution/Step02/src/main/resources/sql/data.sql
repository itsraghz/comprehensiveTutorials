-- Sample data for student table with South Indian names and patterns for aggregation queries

-- Computer Science majors
INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa) 
VALUES (1, 'Arjun', 'Kumar', 'arjun.kumar@example.com', '2000-01-15', 'M', 'Computer Science', 3.85);

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa) 
VALUES (2, 'Priya', 'Venkatesh', 'priya.v@example.com', '2001-03-22', 'F', 'Computer Science', 3.92);

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa) 
VALUES (3, 'Karthik', 'Raman', 'karthik.r@example.com', '1999-07-10', 'M', 'Computer Science', 3.70);

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa) 
VALUES (4, 'Lakshmi', 'Narayan', 'lakshmi.n@example.com', '2002-05-05', 'F', 'Computer Science', 3.65);

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa) 
VALUES (5, 'Ganesh', 'Subramanian', 'ganesh.s@example.com', '2000-11-30', 'M', 'Computer Science', 3.50);

-- Mathematics majors
INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa) 
VALUES (6, 'Aishwarya', 'Iyer', 'aishwarya.i@example.com', '2001-09-18', 'F', 'Mathematics', 3.95);

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa) 
VALUES (7, 'Ramesh', 'Krishnan', 'ramesh.k@example.com', '1999-04-25', 'M', 'Mathematics', 3.40);

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa) 
VALUES (8, 'Divya', 'Chandran', 'divya.c@example.com', '2002-08-12', 'F', 'Mathematics', 3.80);

-- Engineering majors
INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa) 
VALUES (9, 'Suresh', 'Menon', 'suresh.m@example.com', '2000-06-20', 'M', 'Engineering', 3.75);

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa) 
VALUES (10, 'Meena', 'Sundaram', 'meena.s@example.com', '2001-12-03', 'F', 'Engineering', 3.60);

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa) 
VALUES (11, 'Vijay', 'Raghavan', 'vijay.r@example.com', '2000-02-15', 'M', 'Engineering', 3.90);

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa) 
VALUES (12, 'Ananya', 'Patel', 'ananya.p@example.com', '2001-05-22', 'F', 'Engineering', 3.82);

-- Biology majors (all from the same hometown - Chennai)
INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa, hometown) 
VALUES (13, 'Rajesh', 'Nair', 'rajesh.n@example.com', '1999-03-10', 'M', 'Biology', 3.70, 'Chennai');

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa, hometown) 
VALUES (14, 'Sunita', 'Pillai', 'sunita.p@example.com', '2002-07-05', 'F', 'Biology', 3.65, 'Chennai');

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa, hometown) 
VALUES (15, 'Mohan', 'Rao', 'mohan.r@example.com', '2000-09-30', 'M', 'Biology', 3.50, 'Chennai');

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa, hometown) 
VALUES (16, 'Riya', 'Sharma', 'riya.s@example.com', '2001-11-18', 'F', 'Biology', 3.75, 'Chennai');

-- History majors (all with same graduation year)
INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa, graduation_year) 
VALUES (17, 'Vimal', 'Joshi', 'vimal.j@example.com', '2000-04-25', 'M', 'History', 3.40, 2023);

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa, graduation_year) 
VALUES (18, 'Deepa', 'Gopal', 'deepa.g@example.com', '2002-08-12', 'F', 'History', 3.80, 2023);

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa, graduation_year) 
VALUES (19, 'Prakash', 'Shetty', 'prakash.s@example.com', '2000-11-20', 'M', 'History', 3.55, 2023);

-- Psychology majors (similar GPAs)
INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa) 
VALUES (20, 'Kavya', 'Reddy', 'kavya.r@example.com', '2001-10-03', 'F', 'Psychology', 3.90);

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa) 
VALUES (21, 'Arun', 'Nambiar', 'arun.n@example.com', '2000-05-15', 'M', 'Psychology', 3.91);

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa) 
VALUES (22, 'Shreya', 'Menon', 'shreya.m@example.com', '2001-07-22', 'F', 'Psychology', 3.89);

-- Physics major (all with same advisor)
INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa, advisor) 
VALUES (23, 'Harish', 'Iyengar', 'harish.i@example.com', '1999-12-10', 'M', 'Physics', 3.75, 'Dr. Ramanujan');

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa, advisor) 
VALUES (24, 'Nithya', 'Swaminathan', 'nithya.s@example.com', '2002-02-05', 'F', 'Physics', 3.82, 'Dr. Ramanujan');

INSERT INTO student (id, first_name, last_name, email, date_of_birth, gender, major, gpa, advisor) 
VALUES (25, 'Venkat', 'Iyer', 'venkat.i@example.com', '2000-08-30', 'M', 'Physics', 3.60, 'Dr. Ramanujan');

