#!/bin/bash

# Bodima API Testing Script using cURL
echo "=== Bodima API Testing Script ==="
echo ""

BASE_URL="http://localhost:8090/api"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    if [ $1 -eq 0 ]; then
        echo -e "${GREEN}✅ $2${NC}"
    else
        echo -e "${RED}❌ $2${NC}"
    fi
}

# Function to test endpoint
test_endpoint() {
    local method=$1
    local endpoint=$2
    local data=$3
    local description=$4
    
    echo -e "${YELLOW}Testing: $description${NC}"
    
    if [ "$method" = "GET" ]; then
        response=$(curl -s -w "%{http_code}" -X GET "$BASE_URL$endpoint")
    elif [ "$method" = "POST" ]; then
        response=$(curl -s -w "%{http_code}" -X POST "$BASE_URL$endpoint" \
            -H "Content-Type: application/json" \
            -d "$data")
    elif [ "$method" = "PUT" ]; then
        response=$(curl -s -w "%{http_code}" -X PUT "$BASE_URL$endpoint" \
            -H "Content-Type: application/json" \
            -d "$data")
    elif [ "$method" = "DELETE" ]; then
        response=$(curl -s -w "%{http_code}" -X DELETE "$BASE_URL$endpoint")
    fi
    
    # Extract status code (last 3 characters)
    status_code=${response: -3}
    # Extract response body (everything except last 3 characters)
    response_body=${response%???}
    
    echo "Status Code: $status_code"
    echo "Response: $response_body"
    echo ""
    
    # Check if status code is 2xx
    if [[ $status_code -ge 200 && $status_code -lt 300 ]]; then
        print_status 0 "Success"
    else
        print_status 1 "Failed"
    fi
    
    echo "----------------------------------------"
}

# Check if application is running
echo "Checking if application is running..."
if curl -s "$BASE_URL/admins" > /dev/null 2>&1; then
    print_status 0 "Application is running"
else
    print_status 1 "Application is not running. Please start it with: ./mvnw spring-boot:run"
    exit 1
fi

echo ""
echo "Starting API tests..."
echo ""

# Test Admin endpoints
echo "=== ADMIN TESTS ==="

# Create Admin
test_endpoint "POST" "/admins" '{"name":"Test Admin","email":"test@bodima.com"}' "Create Admin"

# Get All Admins
test_endpoint "GET" "/admins" "" "Get All Admins"

# Get Admin by ID
test_endpoint "GET" "/admins/1" "" "Get Admin by ID"

# Update Admin
test_endpoint "PUT" "/admins/1" '{"name":"Updated Admin","email":"updated@bodima.com"}' "Update Admin"

echo ""
echo "=== ROOM TESTS ==="

# Create Room
test_endpoint "POST" "/rooms" '{"roomNumber":"A101","capacity":2}' "Create Room"

# Get All Rooms
test_endpoint "GET" "/rooms" "" "Get All Rooms"

# Get Room by ID
test_endpoint "GET" "/rooms/1" "" "Get Room by ID"

# Update Room
test_endpoint "PUT" "/rooms/1" '{"roomNumber":"A102","capacity":3}' "Update Room"

echo ""
echo "=== USER TESTS ==="

# Create User
test_endpoint "POST" "/users" '{"name":"John Doe","email":"john@example.com","phone":"+1234567890","joinDate":"2024-01-15"}' "Create User"

# Get All Users
test_endpoint "GET" "/users" "" "Get All Users"

# Get User by ID
test_endpoint "GET" "/users/1" "" "Get User by ID"

# Get Users by Room
test_endpoint "GET" "/users/room/1" "" "Get Users by Room"

# Update User
test_endpoint "PUT" "/users/1" '{"name":"John Updated","email":"john.updated@example.com","phone":"+1234567891"}' "Update User"

echo ""
echo "=== PAYMENT TESTS ==="

# Create Payment
test_endpoint "POST" "/payments" '{"month":"2024-01-01","monthlyCharge":500.00,"paidAmount":500.00}' "Create Payment"

# Get All Payments
test_endpoint "GET" "/payments" "" "Get All Payments"

# Get Payment by ID
test_endpoint "GET" "/payments/1" "" "Get Payment by ID"

# Get Payments by User
test_endpoint "GET" "/payments/user/1" "" "Get Payments by User"

# Update Payment
test_endpoint "PUT" "/payments/1" '{"month":"2024-01-01","monthlyCharge":550.00,"paidAmount":550.00}' "Update Payment"

echo ""
echo "=== CLEANUP TESTS ==="

# Delete Payment
test_endpoint "DELETE" "/payments/1" "" "Delete Payment"

# Delete User
test_endpoint "DELETE" "/users/1" "" "Delete User"

# Delete Room
test_endpoint "DELETE" "/rooms/1" "" "Delete Room"

# Delete Admin
test_endpoint "DELETE" "/admins/1" "" "Delete Admin"

echo ""
echo "=== TESTING COMPLETED ==="
echo ""
echo "To verify database changes, run:"
echo "mysql -u root -p'Ananda@2002' -e \"USE bodima; SELECT * FROM admin; SELECT * FROM room; SELECT * FROM user; SELECT * FROM monthly_payment;\""
echo ""

