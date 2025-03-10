# Console Output: Running the HelloWorld Program

This document shows the expected console output when building and running the HelloWorld Java program using Maven.

## Maven Build Output

```
$ mvn clean compile
[INFO] Scanning for projects...
[INFO]
[INFO] -----------< com.learning.springdatajpa:spring-data-jpa-step01 >-----------
[INFO] Building spring-data-jpa-step01 1.0.0
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- maven-clean-plugin:3.1.0:clean (default-clean) @ spring-data-jpa-step01 ---
[INFO] Deleting /path/to/project/step-wise-execution/Step01/target
[INFO]
[INFO] --- maven-resources-plugin:3.2.0:resources (default-resources) @ spring-data-jpa-step01 ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Using 'UTF-8' encoding to copy filtered properties files.
[INFO] Copying 0 resource
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ spring-data-jpa-step01 ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /path/to/project/step-wise-execution/Step01/target/classes
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.342 s
[INFO] Finished at: 2025-03-09T20:30:45Z
[INFO] ------------------------------------------------------------------------
```

## Executing the Program

```
$ mvn exec:java -Dexec.mainClass="com.learning.springdatajpa.HelloWorld"
[INFO] Scanning for projects...
[INFO]
[INFO] -----------< com.learning.springdatajpa:spring-data-jpa-step01 >-----------
[INFO] Building spring-data-jpa-step01 1.0.0
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- exec-maven-plugin:3.0.0:java (default-cli) @ spring-data-jpa-step01 ---
Hello, World! Welcome to Spring Data JPA Learning!
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.548 s
[INFO] Finished at: 2025-03-09T20:30:12Z
[INFO] ------------------------------------------------------------------------
```

## Alternative Execution Method

After compiling the code with `mvn compile`, you can also run the program directly using the Java command:

```
$ java -cp target/classes com.learning.springdatajpa.HelloWorld
Hello, World! Welcome to Spring Data JPA Learning!
```

This completes Step 01 of our Spring Data JPA learning journey, showing a basic Java application built with Maven.
