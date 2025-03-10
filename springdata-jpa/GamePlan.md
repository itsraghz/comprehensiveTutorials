# Game Plan

Let us have a step by step explanation with an easy to follow/use repository at every stage that helps the learners progressively pick up the workspace and continue at any/every stage.

Each step will have two different snapshot of repositories with a suffix, "_start" and  "_end". For example, for Step 1, we will have two different branches namely, "Step1_start" and "Step1_end" to indicate the different snapshots.

A separate HTML file with the Bootstrap compliant UI layout is added to represent each steps and the corresponding  branches in a HTML table structure with a meaningful remark. 

## Steps 

### Step 01 - Helloworld Java Maven 

> No start, and end. Only one step.

A basic Java project with a maven setup, with Java 17, and a HelloWorld.java program to begin the stage. 

### Step 02 - install H2 in the pom.xml

> No start, and end. Only one step.

* Install the latest version of the H2 database by specifying the artifacts in the pom.xml
* A separate ReadMe.md file that explains the installation of H2 database, and basic usage of it, including the Web Console
* Run the command 'mvn clean install' in the terminal to pull out the h2 repository
* SQL 
  * Create a set of SQL files 
     * "Student-DDL.sql" that creates a schema named 'student-mgmt-system' and a table named 'Student' with the different attributes Id, FirstName, LastName, Age, gender, EmailAddress, ContactNo, Address etc., 
     * "Student-DML.sql" with 20+ records with different permutations of gender, course, age, name etc,. to play around with the SQL aggregration commands. 
     * "Student-Queries.sql" with different set of SQL SELECT queries starting from a simple SELECT queries, gradually progressing with filtering with conditions, usage of Distinct, and then progressing with the aggregration queries. 
  * Let us have all of them in a different folder named 'sql'.

### Step 03 - JDBC Program 

#### Step 03 - Start 

Define the objective as follows. 

* Let us have a JDBC Client Application with the different set of interfaces and implementation classes, and a main class "JDBCDemo.java" that can take input from the Users on a command line menu that offers different operations, and perform the CRUD operations and print the output in the Console itself in a well structure layout.  

#### Step 03 - End 

The actual implementation of the program. 
Let us capture the output of a full blown execution in a meaningful text file or a .md file "ConsoleOutput-JDBCDemo.md" in a different folder named "consoleOutputs"
 
