# Testing Your API Without Postman - Complete Guide

## 🚀 Method 1: Using cURL (Command Line)

### Start Your Application First
```bash
./mvnw spring-boot:run
```

### Test Admin Endpoints

#### Create Admin
```bash
curl -X POST http://localhost:8090/api/admins \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Admin",
    "email": "admin@bodima.com"
  }'
```

#### Get All Admins
```bash
curl -X GET http://localhost:8090/api/admins
```

#### Get Admin by ID
```bash
curl -X GET http://localhost:8090/api/admins/1
```

#### Update Admin
```bash
curl -X PUT http://localhost:8090/api/admins/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Admin",
    "email": "updated@bodima.com"
  }'
```

#### Delete Admin
```bash
curl -X DELETE http://localhost:8090/api/admins/1
```

### Test Room Endpoints

#### Create Room
```bash
curl -X POST http://localhost:8090/api/rooms \
  -H "Content-Type: application/json" \
  -d '{
    "roomNumber": "A101",
    "capacity": 2
  }'
```

#### Get All Rooms
```bash
curl -X GET http://localhost:8090/api/rooms
```

#### Get Room by ID
```bash
curl -X GET http://localhost:8090/api/rooms/1
```

### Test User Endpoints

#### Create User
```bash
curl -X POST http://localhost:8090/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "+1234567890",
    "joinDate": "2024-01-15"
  }'
```

#### Get All Users
```bash
curl -X GET http://localhost:8090/api/users
```

#### Get Users by Room
```bash
curl -X GET http://localhost:8090/api/users/room/1
```

### Test Payment Endpoints

#### Create Payment
```bash
curl -X POST http://localhost:8090/api/payments \
  -H "Content-Type: application/json" \
  -d '{
    "month": "2024-01-01",
    "monthlyCharge": 500.00,
    "paidAmount": 500.00
  }'
```

#### Get All Payments
```bash
curl -X GET http://localhost:8090/api/payments
```

#### Get Payments by User
```bash
curl -X GET http://localhost:8090/api/payments/user/1
```

## 🛠️ Method 2: Using HTTPie (Simpler than cURL)

### Install HTTPie
```bash
# On macOS
brew install httpie

# On Ubuntu/Debian
sudo apt install httpie

# On Windows
pip install httpie
```

### Test with HTTPie

#### Create Admin
```bash
http POST localhost:8090/api/admins name="John Admin" email="admin@bodima.com"
```

#### Get All Admins
```bash
http GET localhost:8090/api/admins
```

#### Create Room
```bash
http POST localhost:8090/api/rooms roomNumber="A101" capacity:=2
```

## 🌐 Method 3: Using Web Browser

### Simple GET Requests
Open these URLs in your browser:

- `http://localhost:8090/api/admins` - Get all admins
- `http://localhost:8090/api/rooms` - Get all rooms
- `http://localhost:8090/api/users` - Get all users
- `http://localhost:8090/api/payments` - Get all payments

## 🗄️ Method 4: Direct Database Testing

### Connect to MySQL
```bash
mysql -u root -p'Ananda@2002'
```

### Check Database
```sql
-- Use the database
USE bodima;

-- Check if tables exist
SHOW TABLES;

-- Check table structures
DESCRIBE admin;
DESCRIBE room;
DESCRIBE user;
DESCRIBE monthly_payment;

-- Check if data exists
SELECT * FROM admin;
SELECT * FROM room;
SELECT * FROM user;
SELECT * FROM monthly_payment;
```

### Test Relationships
```sql
-- User with room and admin info
SELECT 
    u.name as user_name,
    r.room_number,
    a.name as admin_name
FROM user u
LEFT JOIN room r ON u.room_id = r.room_id
LEFT JOIN admin a ON u.admin_id = a.admin_id;

-- User with payments
SELECT 
    u.name,
    mp.month,
    mp.monthly_charge,
    mp.paid_amount
FROM user u
LEFT JOIN monthly_payment mp ON u.user_id = mp.user_id;
```

## 📊 Method 5: Using wget

### GET Requests
```bash
wget -qO- http://localhost:8090/api/admins
wget -qO- http://localhost:8090/api/rooms
```

## 🔧 Method 6: Using Python Scripts

### Create a Python Test Script
```python
#!/usr/bin/env python3
import requests
import json

BASE_URL = "http://localhost:8090/api"

def test_api():
    # Test creating admin
    admin_data = {
        "name": "Python Admin",
        "email": "python@bodima.com"
    }
    
    response = requests.post(f"{BASE_URL}/admins", json=admin_data)
    print(f"Create Admin: {response.status_code}")
    print(f"Response: {response.json()}")
    
    # Test getting all admins
    response = requests.get(f"{BASE_URL}/admins")
    print(f"Get Admins: {response.status_code}")
    print(f"Response: {response.json()}")

if __name__ == "__main__":
    test_api()
```

### Run Python Script
```bash
python3 test_api.py
```

## 🧪 Method 7: Using JavaScript/Node.js

### Create a Node.js Test Script
```javascript
const axios = require('axios');

const BASE_URL = 'http://localhost:8090/api';

async function testAPI() {
    try {
        // Test creating admin
        const adminData = {
            name: "Node Admin",
            email: "node@bodima.com"
        };
        
        const createResponse = await axios.post(`${BASE_URL}/admins`, adminData);
        console.log('Create Admin:', createResponse.status);
        console.log('Response:', createResponse.data);
        
        // Test getting all admins
        const getResponse = await axios.get(`${BASE_URL}/admins`);
        console.log('Get Admins:', getResponse.status);
        console.log('Response:', getResponse.data);
        
    } catch (error) {
        console.error('Error:', error.response?.data || error.message);
    }
}

testAPI();
```

### Run Node.js Script
```bash
node test_api.js
```

## 📝 Method 8: Manual Testing Checklist

### Step-by-Step Manual Testing

1. **Start Application**
   ```bash
   ./mvnw spring-boot:run
   ```

2. **Check Application Logs**
   - Look for "Started BodimaApplication" message
   - Check for database connection success
   - Verify no errors in console

3. **Test Database Connection**
   ```bash
   mysql -u root -p'Ananda@2002' -e "USE bodima; SHOW TABLES;"
   ```

4. **Test Each Endpoint Manually**
   - Use cURL or browser for GET requests
   - Use cURL for POST/PUT/DELETE requests
   - Check response status codes
   - Verify response data

5. **Verify Database Changes**
   ```bash
   mysql -u root -p'Ananda@2002' -e "USE bodima; SELECT * FROM admin;"
   ```

## 🎯 Complete Testing Workflow

### 1. Start Everything
```bash
# Terminal 1: Start application
./mvnw spring-boot:run

# Terminal 2: Monitor logs
tail -f logs/application.log
```

### 2. Test Database
```bash
# Terminal 3: Database testing
mysql -u root -p'Ananda@2002' < database_validation.sql
```

### 3. Test API Endpoints
```bash
# Terminal 4: API testing
curl -X GET http://localhost:8090/api/admins
curl -X POST http://localhost:8090/api/admins -H "Content-Type: application/json" -d '{"name":"Test","email":"test@test.com"}'
```

### 4. Verify Results
```bash
# Check database after API calls
mysql -u root -p'Ananda@2002' -e "USE bodima; SELECT * FROM admin;"
```

## 🔍 Debugging Tips

### Check Application Status
```bash
# Check if application is running
lsof -i :8090

# Check application logs
tail -f target/logs/spring.log
```

### Check Database Status
```bash
# Check MySQL status
sudo systemctl status mysql

# Check database connection
mysql -u root -p'Ananda@2002' -e "SELECT 1;"
```

### Common Issues and Solutions

1. **Port 8090 in use**: `lsof -i :8090` then `kill -9 <PID>`
2. **Database connection failed**: Check MySQL is running
3. **404 errors**: Check if controllers are properly annotated
4. **500 errors**: Check application logs for detailed error messages

## 📚 Learning Resources

- **cURL Documentation**: https://curl.se/docs/
- **HTTPie Documentation**: https://httpie.io/docs
- **MySQL Documentation**: https://dev.mysql.com/doc/
- **Spring Boot Testing**: https://spring.io/guides/gs/testing-web/

This approach gives you a deep understanding of how APIs work and how to test them manually!

