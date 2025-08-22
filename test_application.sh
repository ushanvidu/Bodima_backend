#!/bin/bash

# Bodima Application Test Script
echo "=== Bodima Application Test Script ==="
echo ""

# Check if we're in the right directory
if [ ! -f "pom.xml" ]; then
    echo "❌ Error: pom.xml not found. Please run this script from the project root directory."
    exit 1
fi

echo "✅ Found pom.xml - we're in the right directory"
echo ""

# Check if Maven wrapper exists
if [ ! -f "mvnw" ]; then
    echo "❌ Error: Maven wrapper (mvnw) not found."
    exit 1
fi

echo "✅ Found Maven wrapper"
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed or not in PATH"
    exit 1
fi

echo "✅ Java is installed: $(java -version 2>&1 | head -n 1)"
echo ""

# Check if MySQL is running (basic check)
echo "Checking MySQL connection..."
if mysql -u root -p'Ananda@2002' -e "SELECT 1;" 2>/dev/null; then
    echo "✅ MySQL is running and accessible"
else
    echo "⚠️  Warning: Could not connect to MySQL. Make sure MySQL is running and credentials are correct."
    echo "   You can still test the application, but database operations will fail."
fi
echo ""

# Create database if it doesn't exist
echo "Creating database if it doesn't exist..."
mysql -u root -p'Ananda@2002' -e "CREATE DATABASE IF NOT EXISTS bodima;" 2>/dev/null
if [ $? -eq 0 ]; then
    echo "✅ Database 'bodima' is ready"
else
    echo "⚠️  Warning: Could not create database. You may need to create it manually."
fi
echo ""

# Test compilation
echo "Testing compilation..."
./mvnw compile
if [ $? -eq 0 ]; then
    echo "✅ Compilation successful"
else
    echo "❌ Compilation failed"
    exit 1
fi
echo ""

# Check if port 8090 is available
echo "Checking if port 8090 is available..."
if lsof -Pi :8090 -sTCP:LISTEN -t >/dev/null ; then
    echo "⚠️  Warning: Port 8090 is already in use. The application may not start."
else
    echo "✅ Port 8090 is available"
fi
echo ""

echo "=== Ready to start application ==="
echo ""
echo "To start the application, run:"
echo "  ./mvnw spring-boot:run"
echo ""
echo "To test with Postman:"
echo "  1. Import the Bodima_API_Collection.json file"
echo "  2. Start the application"
echo "  3. Run the test requests in the collection"
echo ""
echo "To validate the database, run:"
echo "  mysql -u root -p'Ananda@2002' < database_validation.sql"
echo ""
echo "For detailed testing instructions, see TESTING_GUIDE.md"
echo ""

