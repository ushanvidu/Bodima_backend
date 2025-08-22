# Postman Testing Guide for Bodima API

## 🎯 **Complete Postman Testing Workflow**

### **Step 1: Prepare Your Environment**

#### **1.1 Start Your Application**

```bash
# In your terminal, navigate to your project directory
cd "Bodima 2"

# Start the Spring Boot application
./mvnw spring-boot:run
```

**Wait for this message:**

```
Started BodimaApplication in X.XXX seconds
```

#### **1.2 Open Postman**

- Download Postman from: https://www.postman.com/downloads/
- Install and open Postman

### **Step 2: Import the Collection**

#### **2.1 Import the Collection File**

1. In Postman, click **"Import"** button
2. Click **"Upload Files"**
3. Select `Bodima_API_Collection.json` from your project folder
4. Click **"Import"**

#### **2.2 Set Environment Variables**

1. In the imported collection, click on **"Variables"** tab
2. Set `baseUrl` to: `http://localhost:8090`
3. Click **"Save"**

### **Step 3: Test Each Endpoint**

#### **🔧 ADMIN MANAGEMENT TESTS**

**Test 1.1: Create Admin**

- **Method**: `POST`
- **URL**: `{{baseUrl}}/api/admins`
- **Headers**:
  - Key: `Content-Type`
  - Value: `application/json`
- **Body** (raw JSON):

```json
{
  "name": "John Admin",
  "email": "admin@bodima.com"
}
```

- **Expected Response**: `201 Created` with admin data

**Test 1.2: Get All Admins**

- **Method**: `GET`
- **URL**: `{{baseUrl}}/api/admins`
- **Expected Response**: `200 OK` with array of admins

**Test 1.3: Get Admin by ID**

- **Method**: `GET`
- **URL**: `{{baseUrl}}/api/admins/1`
- **Expected Response**: `200 OK` with admin data

**Test 1.4: Update Admin**

- **Method**: `PUT`
- **URL**: `{{baseUrl}}/api/admins/1`
- **Headers**: `Content-Type: application/json`
- **Body** (raw JSON):

```json
{
  "name": "Updated Admin Name",
  "email": "updated@bodima.com"
}
```

- **Expected Response**: `200 OK` with updated admin data

#### **🏠 ROOM MANAGEMENT TESTS**

**Test 2.1: Create Room**

- **Method**: `POST`
- **URL**: `{{baseUrl}}/api/rooms`
- **Headers**: `Content-Type: application/json`
- **Body** (raw JSON):

```json
{
  "roomNumber": "A101",
  "capacity": 2
}
```

- **Expected Response**: `201 Created` with room data

**Test 2.2: Get All Rooms**

- **Method**: `GET`
- **URL**: `{{baseUrl}}/api/rooms`
- **Expected Response**: `200 OK` with array of rooms

**Test 2.3: Get Room by ID**

- **Method**: `GET`
- **URL**: `{{baseUrl}}/api/rooms/1`
- **Expected Response**: `200 OK` with room data

#### **👥 USER MANAGEMENT TESTS**

**Test 3.1: Create User**

- **Method**: `POST`
- **URL**: `{{baseUrl}}/api/users`
- **Headers**: `Content-Type: application/json`
- **Body** (raw JSON):

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "+1234567890",
  "joinDate": "2024-01-15"
}
```

- **Expected Response**: `201 Created` with user data

**Test 3.2: Get All Users**

- **Method**: `GET`
- **URL**: `{{baseUrl}}/api/users`
- **Expected Response**: `200 OK` with array of users

**Test 3.3: Get Users by Room**

- **Method**: `GET`
- **URL**: `{{baseUrl}}/api/users/room/1`
- **Expected Response**: `200 OK` with users in room 1

#### **💰 PAYMENT MANAGEMENT TESTS**

**Test 4.1: Create Payment**

- **Method**: `POST`
- **URL**: `{{baseUrl}}/api/payments`
- **Headers**: `Content-Type: application/json`
- **Body** (raw JSON):

```json
{
  "month": "2024-01-01",
  "monthlyCharge": 500.0,
  "paidAmount": 500.0
}
```

- **Expected Response**: `201 Created` with payment data

**Test 4.2: Get All Payments**

- **Method**: `GET`
- **URL**: `{{baseUrl}}/api/payments`
- **Expected Response**: `200 OK` with array of payments

**Test 4.3: Get Payments by User**

- **Method**: `GET`
- **URL**: `{{baseUrl}}/api/payments/user/1`
- **Expected Response**: `200 OK` with payments for user 1

### **Step 4: Understanding Responses**

#### **✅ Successful Response Examples**

**Create Admin Response (201 Created):**

```json
{
  "adminId": 1,
  "name": "John Admin",
  "email": "admin@bodima.com"
}
```

**Get All Admins Response (200 OK):**

```json
[
  {
    "adminId": 1,
    "name": "John Admin",
    "email": "admin@bodima.com"
  }
]
```

#### **❌ Error Response Examples**

**404 Not Found:**

```json
{
  "timestamp": "2024-01-15T10:30:00.000+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Admin not found with id: 999"
}
```

**400 Bad Request:**

```json
{
  "timestamp": "2024-01-15T10:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid JSON format"
}
```

### **Step 5: Database Verification**

After testing with Postman, verify your database:

#### **5.1 Connect to MySQL**

```bash
mysql -u root -p'Ananda@2002'
```

#### **5.2 Check Database**

```sql
USE bodima;

-- Check if tables exist
SHOW TABLES;

-- Check data in tables
SELECT * FROM admin;
SELECT * FROM room;
SELECT * FROM user;
SELECT * FROM monthly_payment;

-- Check relationships
SELECT
    u.name as user_name,
    r.room_number,
    a.name as admin_name
FROM user u
LEFT JOIN room r ON u.room_id = r.room_id
LEFT JOIN admin a ON u.admin_id = a.admin_id;
```

### **Step 6: Troubleshooting**

#### **Common Issues and Solutions**

**Issue 1: "Connection refused"**

- **Solution**: Make sure your application is running on port 8090
- **Check**: Look for "Started BodimaApplication" in console

**Issue 2: "404 Not Found"**

- **Solution**: Check if the URL is correct
- **Check**: Verify `{{baseUrl}}` is set to `http://localhost:8090`

**Issue 3: "500 Internal Server Error"**

- **Solution**: Check application logs for detailed error
- **Check**: Look at the console where you started the application

**Issue 4: "Invalid JSON"**

- **Solution**: Check JSON syntax in request body
- **Check**: Use JSON validator to verify format

### **Step 7: Advanced Testing**

#### **7.1 Test Error Scenarios**

- Try creating admin without required fields
- Try getting non-existent admin (ID 999)
- Try updating non-existent admin

#### **7.2 Test Relationships**

- Create admin, room, user, and payment
- Verify relationships work correctly
- Test cascade operations

#### **7.3 Performance Testing**

- Use Postman Runner to send multiple requests
- Test with different data sets
- Monitor response times

### **Step 8: Save and Share Results**

#### **8.1 Export Test Results**

- In Postman, go to **"Runner"**
- Select your collection
- Run all tests
- Export results as JSON

#### **8.2 Document Findings**

- Note any errors or issues
- Document response times
- Save successful request/response examples

### **🎯 Testing Checklist**

- [ ] Application starts successfully
- [ ] All POST requests create records
- [ ] All GET requests retrieve data
- [ ] All PUT requests update records
- [ ] All DELETE requests remove records
- [ ] Database tables are created
- [ ] Relationships work correctly
- [ ] Error handling works properly
- [ ] Response formats are correct

### **📚 Next Steps**

1. **Add Validation**: Test with invalid data
2. **Security Testing**: Test authentication/authorization
3. **Load Testing**: Test with multiple concurrent requests
4. **Integration Testing**: Test with frontend application
5. **Automated Testing**: Create automated test suites

This comprehensive testing approach will help you understand how your API works and identify any issues!

