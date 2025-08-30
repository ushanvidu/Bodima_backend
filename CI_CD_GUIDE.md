# CI/CD Pipeline & Testing Guide - Bodima App

## 🚀 Overview

This guide explains how to use the CI/CD pipeline and testing setup for the Bodima application. The pipeline provides a **red/green cycle** for continuous integration and deployment.

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Git
- GitHub account

## 🧪 Testing Setup

### Local Testing

1. **Run the test script** (recommended):

   ```bash
   ./run-tests.sh
   ```

2. **Manual testing**:

   ```bash
   # Set test environment
   export SPRING_PROFILES_ACTIVE=test

   # Run unit tests
   mvn test -Dtest="*Test"

   # Run Cucumber BDD tests
   mvn test -Dtest="CucumberRunnerTest"

   # Generate reports
   mvn surefire-report:report
   ```

### Test Types

#### 1. Unit Tests (`UserServiceTest.java`)

- Tests individual service methods
- Uses Spring Boot Test with H2 in-memory database
- Provides fast feedback for code changes

#### 2. Cucumber BDD Tests (`CucumberRunnerTest.java`)

- Behavior-driven development tests
- Tests user stories and scenarios
- Provides business-readable test reports

### Test Reports

After running tests, you'll find reports in:

- **HTML Report**: `target/site/surefire-report.html`
- **Cucumber Report**: `target/cucumber-reports/cucumber.html`
- **XML Reports**: `target/surefire-reports/`

## 🔄 CI/CD Pipeline

### GitHub Actions Workflow

The pipeline is defined in `.github/workflows/ci-pipeline.yml` and runs on:

- Push to `main`, `master`, or `develop` branches
- Pull requests to `main` or `master` branches

### Pipeline Steps

1. **Setup**: Checkout code and setup Java 17
2. **Build**: Compile the project
3. **Unit Tests**: Run JUnit tests
4. **BDD Tests**: Run Cucumber tests
5. **Reports**: Generate test reports
6. **Package**: Build JAR file
7. **Artifacts**: Upload test results and JAR

### Red/Green Cycle

The pipeline provides clear **red/green** feedback:

#### 🟢 Green (Success)

- All tests pass
- Build successful
- JAR file created
- Reports generated

#### 🔴 Red (Failure)

- Tests fail
- Build errors
- Missing dependencies
- Configuration issues

### Pipeline Output

The pipeline provides detailed feedback:

```
📋 Test Results Summary:
========================
✅ Passed: 8
❌ Failed: 0
⏭️  Skipped: 0
🟢 All tests passed!
```

## 🛠️ Troubleshooting

### Common Issues

1. **Tests failing locally but passing in CI**

   - Check environment variables
   - Ensure H2 database is configured
   - Verify test profile is active

2. **Cucumber tests not running**

   - Check feature file syntax
   - Verify step definitions match
   - Ensure proper package structure

3. **Build failures**
   - Check Java version (requires 17+)
   - Verify Maven dependencies
   - Check for compilation errors

### Debugging Steps

1. **Check test logs**:

   ```bash
   mvn test -X
   ```

2. **Run specific test**:

   ```bash
   mvn test -Dtest=UserServiceTest#testCreateUser
   ```

3. **Check GitHub Actions logs**:
   - Go to your repository on GitHub
   - Click "Actions" tab
   - Select the failed workflow
   - Review the detailed logs

## 📊 Monitoring

### GitHub Actions Dashboard

Monitor your pipeline at:

```
https://github.com/[username]/[repository]/actions
```

### Test Coverage

To check test coverage:

```bash
mvn jacoco:report
```

Coverage report will be available at:

```
target/site/jacoco/index.html
```

## 🔧 Configuration

### Environment Variables

The pipeline uses these environment variables:

- `SPRING_PROFILES_ACTIVE=test`
- `SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb`
- `SPRING_DATASOURCE_USERNAME=sa`
- `SPRING_DATASOURCE_PASSWORD=password`

### Test Configuration

Test configuration is in:

- `src/test/resources/application-test.properties`
- `src/test/resources/application-ci.properties`

## 🎯 Best Practices

1. **Write tests first** (TDD approach)
2. **Keep tests fast and reliable**
3. **Use descriptive test names**
4. **Test both happy path and edge cases**
5. **Maintain test data isolation**
6. **Review test reports regularly**

## 📈 Continuous Improvement

### Adding New Tests

1. **Unit Tests**: Add to existing test classes
2. **BDD Tests**: Create new feature files and step definitions
3. **Integration Tests**: Add to separate test classes

### Pipeline Enhancements

Consider adding:

- Code coverage reporting
- Security scanning
- Performance testing
- Deployment to staging/production

## 🆘 Support

If you encounter issues:

1. Check the troubleshooting section above
2. Review GitHub Actions logs
3. Run tests locally to reproduce issues
4. Check test configuration files
5. Verify dependencies in `pom.xml`

---

**Happy Testing! 🧪✨**
