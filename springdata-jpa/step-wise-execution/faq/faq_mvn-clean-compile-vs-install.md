# Maven Commands: `mvn clean compile` vs `mvn clean install`

These are Maven commands that perform different levels of build operations:

## `mvn clean compile`
- **clean**: Deletes the target directory with all built artifacts
- **compile**: Compiles the source code (just the compilation phase)

This command only compiles your code to verify it builds correctly. It does not run tests or create a packaged artifact (like a JAR or WAR file).

## `mvn clean install`
- **clean**: Deletes the target directory with all built artifacts
- **install**: Runs the full build lifecycle up through the install phase:
  1. Compile code
  2. Run tests
  3. Package the compiled code (e.g., create JAR/WAR)
  4. Install the package into your local Maven repository (~/.m2/repository)

The key difference is that `mvn clean install` does significantly more: it runs all tests, creates the distributable package, and makes it available in your local repository for other projects to use as a dependency.

Use `mvn clean compile` when you just want to check if your code compiles.
Use `mvn clean install` when you want to build the complete artifact and make it available locally.

