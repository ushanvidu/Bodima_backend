# Bodima Authentication System - Testing Guide

## 🎯 **Overview**

Your Bodima application now has a complete authentication system with:

- User registration
- User login/logout
- Password management
- Profile management
- Username validation

## 🚀 **Quick Start**

### **1. Start Your Application**

```bash
./mvnw spring-boot:run
```

### **2. Import Authentication Collection**

- Open Postman
- Import `Bodima_Auth_Collection.json`
- Set `{{baseUrl}}` = `http://localhost:8090`

## 📋 **Available Authentication Endpoints**

### **🔐 User Registration**

- `POST /api/auth/register` - Register new user
- `POST /api/auth/validate-username` - Check username availability

### **🔑 User Login/Logout**

- `POST /api/auth/login` - User login
- `POST /api/auth/logout` - User logout

### **👤 User Profile**

- `GET /api/auth/profile/{userId}` - Get user profile

### **🔒 Password Management**

- `PUT /api/auth/change-password` - Change password

## 🧪 **Testing Scenarios**

### **Scenario 1: Complete User Registration & Login Flow**

#### **Step 1: Register New User**

```http
POST http://localhost:8090/api/auth/register
Content-Type: application/json

{
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "+1234567890",
    "joinDate": "2024-01-15",
    "username": "johndoe",
    "password": "password123"
}
```

**Expected Response (200 OK):**

```json
{
  "message": "User registered successfully",
  "userId": 1,
  "username": "johndoe"
}
```

#### **Step 2: Login with Registered User**

```http
POST http://localhost:8090/api/auth/login
Content-Type: application/json

{
    "username": "johndoe",
    "password": "password123"
}
```

**Expected Response (200 OK):**

```json
{
  "message": "Login successful",
  "userId": 1,
  "username": "johndoe",
  "userName": "John Doe",
  "email": "john@example.com",
  "phone": "+1234567890"
}
```

#### **Step 3: Get User Profile**

```http
GET http://localhost:8090/api/auth/profile/1
```

**Expected Response (200 OK):**

```json
{
  "userId": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "+1234567890",
  "joinDate": "2024-01-15"
}
```

### **Scenario 2: Error Handling Tests**

#### **Test 1: Register with Missing Fields**

```http
POST http://localhost:8090/api/auth/register
Content-Type: application/json

{
    "name": "John Doe",
    "email": "john@example.com"
}
```

**Expected Response (400 Bad Request):**

```json
{
  "error": "Missing required fields"
}
```

#### **Test 2: Register with Duplicate Username**

```http
POST http://localhost:8090/api/auth/register
Content-Type: application/json

{
    "name": "Jane Doe",
    "email": "jane@example.com",
    "username": "johndoe",
    "password": "password123"
}
```

**Expected Response (400 Bad Request):**

```json
{
  "error": "Username already exists"
}
```

#### **Test 3: Login with Invalid Credentials**

```http
POST http://localhost:8090/api/auth/login
Content-Type: application/json

{
    "username": "johndoe",
    "password": "wrongpassword"
}
```

**Expected Response (400 Bad Request):**

```json
{
  "error": "Invalid username or password"
}
```

### **Scenario 3: Password Management**

#### **Step 1: Change Password**

```http
PUT http://localhost:8090/api/auth/change-password
Content-Type: application/json

{
    "username": "johndoe",
    "currentPassword": "password123",
    "newPassword": "newpassword123"
}
```

**Expected Response (200 OK):**

```json
{
  "message": "Password changed successfully"
}
```

#### **Step 2: Login with New Password**

```http
POST http://localhost:8090/api/auth/login
Content-Type: application/json

{
    "username": "johndoe",
    "password": "newpassword123"
}
```

**Expected Response (200 OK):**

```json
{
  "message": "Login successful",
  "userId": 1,
  "username": "johndoe",
  "userName": "John Doe",
  "email": "john@example.com",
  "phone": "+1234567890"
}
```

### **Scenario 4: Username Validation**

#### **Test Available Username**

```http
POST http://localhost:8090/api/auth/validate-username
Content-Type: application/json

{
    "username": "newuser"
}
```

**Expected Response (200 OK):**

```json
{
  "username": "newuser",
  "available": true
}
```

#### **Test Existing Username**

```http
POST http://localhost:8090/api/auth/validate-username
Content-Type: application/json

{
    "username": "johndoe"
}
```

**Expected Response (200 OK):**

```json
{
  "username": "johndoe",
  "available": false
}
```

## 🗄️ **Database Verification**

### **Check Authentication Data**

```sql
USE bodima;

-- Check users table
SELECT * FROM user;

-- Check login credentials
SELECT l.login_id, l.username, l.password, u.name, u.email
FROM login l
JOIN user u ON l.user_id = u.user_id;

-- Check user-login relationship
SELECT
    u.user_id,
    u.name,
    u.email,
    l.username,
    l.password
FROM user u
LEFT JOIN login l ON u.user_id = l.user_id;
```

## 🔧 **Testing with cURL**

### **Register User**

```bash
curl -X POST http://localhost:8090/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "phone": "+1234567890",
    "joinDate": "2024-01-15",
    "username": "testuser",
    "password": "password123"
  }'
```

### **Login User**

```bash
curl -X POST http://localhost:8090/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

### **Get User Profile**

```bash
curl -X GET http://localhost:8090/api/auth/profile/1
```

### **Change Password**

```bash
curl -X PUT http://localhost:8090/api/auth/change-password \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "currentPassword": "password123",
    "newPassword": "newpassword123"
  }'
```

## 🎯 **Testing Checklist**

### **Registration Tests**

- [ ] Register with valid data
- [ ] Register with missing required fields
- [ ] Register with duplicate username
- [ ] Validate username availability

### **Login Tests**

- [ ] Login with valid credentials
- [ ] Login with invalid username
- [ ] Login with invalid password
- [ ] Login with missing fields

### **Profile Tests**

- [ ] Get existing user profile
- [ ] Get non-existent user profile

### **Password Tests**

- [ ] Change password with correct current password
- [ ] Change password with incorrect current password
- [ ] Login with new password

### **Database Tests**

- [ ] Verify user data in database
- [ ] Verify login credentials in database
- [ ] Verify relationships between user and login

## 🚨 **Common Issues and Solutions**

### **Issue 1: "User not found" during login**

**Solution**: Check if user was properly registered
**Verification**: `SELECT * FROM user; SELECT * FROM login;`

### **Issue 2: "Username already exists"**

**Solution**: Use a different username or delete existing user
**Verification**: `SELECT username FROM login;`

### **Issue 3: "Missing required fields"**

**Solution**: Ensure all required fields are provided
**Required**: name, email, username, password

### **Issue 4: Database relationship issues**

**Solution**: Check foreign key constraints
**Verification**: `DESCRIBE login; DESCRIBE user;`

## 🔒 **Security Considerations**

### **Current Implementation**

- Passwords are stored in plain text (for learning purposes)
- No session management
- No JWT tokens

### **Production Recommendations**

- Hash passwords using BCrypt
- Implement JWT token authentication
- Add session management
- Add rate limiting
- Add input validation and sanitization

## 📚 **Next Steps**

1. **Add Password Hashing**: Implement BCrypt password hashing
2. **JWT Authentication**: Add JWT token-based authentication
3. **Session Management**: Implement proper session handling
4. **Role-Based Access**: Add user roles and permissions
5. **Password Reset**: Implement password reset functionality
6. **Email Verification**: Add email verification for registration

## 🎉 **Success Indicators**

- ✅ User registration creates both user and login records
- ✅ Login validates credentials correctly
- ✅ Profile retrieval works for valid users
- ✅ Password changes are persisted
- ✅ Username validation works correctly
- ✅ Error handling provides meaningful messages
- ✅ Database relationships are maintained correctly

This authentication system provides a solid foundation for your Bodima hostel management application!




