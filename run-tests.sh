#!/bin/bash

# Bodima Test Runner Script
# This script helps you run tests and see the red/green cycle

set -e

echo "🚀 Bodima Test Runner"
echo "====================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    local status=$1
    local message=$2
    case $status in
        "success")
            echo -e "${GREEN}✅ $message${NC}"
            ;;
        "error")
            echo -e "${RED}❌ $message${NC}"
            ;;
        "warning")
            echo -e "${YELLOW}⚠️  $message${NC}"
            ;;
        "info")
            echo -e "${BLUE}ℹ️  $message${NC}"
            ;;
    esac
}

# Check if Maven is available (either installed or via wrapper)
if command -v mvn &> /dev/null; then
    MVN_CMD="mvn"
elif [ -f "./mvnw" ]; then
    MVN_CMD="./mvnw"
    print_status "info" "Using Maven wrapper"
else
    print_status "error" "Maven is not installed and Maven wrapper not found. Please install Maven first."
    exit 1
fi

# Set test environment
export SPRING_PROFILES_ACTIVE=test
export SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
export SPRING_DATASOURCE_USERNAME=sa
export SPRING_DATASOURCE_PASSWORD=password

print_status "info" "Setting up test environment..."

# Clean and compile
print_status "info" "Cleaning and compiling project..."
$MVN_CMD clean compile

# Run unit tests
print_status "info" "Running unit tests..."
if $MVN_CMD test -Dtest="*Test" -DfailIfNoTests=false; then
    print_status "success" "Unit tests passed!"
else
    print_status "error" "Unit tests failed!"
    exit 1
fi

# Run Cucumber BDD tests
print_status "info" "Running Cucumber BDD tests..."
if $MVN_CMD test -Dtest="CucumberRunnerTest" -DfailIfNoTests=false; then
    print_status "success" "Cucumber BDD tests passed!"
else
    print_status "error" "Cucumber BDD tests failed!"
    exit 1
fi

# Generate reports
print_status "info" "Generating test reports..."
$MVN_CMD test-compile
$MVN_CMD surefire-report:report-only

# Display results
echo ""
print_status "info" "Test Results Summary:"
echo "========================"

if [ -d "target/surefire-reports" ]; then
    PASSED=$(find target/surefire-reports -name "*.xml" -exec grep -l "<testcase" {} \; | xargs grep -c "<testcase" | awk '{sum+=$1} END {print sum+0}')
    FAILED=$(find target/surefire-reports -name "*.xml" -exec grep -c "<failure" {} \; | awk '{sum+=$1} END {print sum+0}')
    SKIPPED=$(find target/surefire-reports -name "*.xml" -exec grep -c "<skipped" {} \; | awk '{sum+=$1} END {print sum+0}')
    
    echo -e "${GREEN}✅ Passed: $PASSED${NC}"
    echo -e "${RED}❌ Failed: $FAILED${NC}"
    echo -e "${YELLOW}⏭️  Skipped: $SKIPPED${NC}"
    
    if [ "$FAILED" -gt 0 ]; then
        print_status "error" "Some tests failed! Check the reports for details."
        exit 1
    else
        print_status "success" "All tests passed! 🎉"
    fi
else
    print_status "error" "No test reports found"
    exit 1
fi

# Show report locations
echo ""
print_status "info" "Test Reports Generated:"
if [ -f "target/site/surefire-report.html" ]; then
    echo "📄 HTML Report: target/site/surefire-report.html"
else
    echo "⚠️  HTML Report: Not generated (target/site/surefire-report.html)"
fi

if [ -f "target/cucumber-reports/cucumber.html" ]; then
    echo "🥒 Cucumber Report: target/cucumber-reports/cucumber.html"
else
    echo "⚠️  Cucumber Report: Not generated (target/cucumber-reports/cucumber.html)"
fi

if [ -d "target/surefire-reports" ]; then
    echo "📊 XML Reports: target/surefire-reports/"
    echo "   Number of test files: $(find target/surefire-reports -name "*.xml" | wc -l)"
else
    echo "⚠️  XML Reports: Not generated (target/surefire-reports/)"
fi

# Open reports in browser (optional)
if command -v open &> /dev/null; then
    echo ""
    read -p "Would you like to open the test reports in your browser? (y/n): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        if [ -f "target/site/surefire-report.html" ]; then
            open target/site/surefire-report.html
            print_status "info" "Opened HTML report in browser"
        else
            print_status "warning" "HTML report not found"
        fi
        if [ -f "target/cucumber-reports/cucumber.html" ]; then
            open target/cucumber-reports/cucumber.html
            print_status "info" "Opened Cucumber report in browser"
        else
            print_status "warning" "Cucumber report not found"
        fi
    fi
fi

print_status "success" "Test run completed successfully!"
