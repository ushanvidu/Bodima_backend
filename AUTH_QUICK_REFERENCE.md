# Authentication Quick Reference

## 🚀 **Quick Start**

### **1. Start Application**

```bash
./mvnw spring-boot:run
```

### **2. Import Collection**

- Import `Bodima_Auth_Collection.json` in Postman
- Set `{{baseUrl}}` = `http://localhost:8090`

## 📋 **Authentication Endpoints**

### **🔐 REGISTRATION**

| Method | URL                           | Body                                                                                                                                  | Expected |
| ------ | ----------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- | -------- |
| POST   | `/api/auth/register`          | `{"name":"John","email":"john@test.com","phone":"+1234567890","joinDate":"2024-01-15","username":"johndoe","password":"password123"}` | 200 OK   |
| POST   | `/api/auth/validate-username` | `{"username":"johndoe"}`                                                                                                              | 200 OK   |

### **🔑 LOGIN/LOGOUT**

| Method | URL                | Body                                              | Expected |
| ------ | ------------------ | ------------------------------------------------- | -------- |
| POST   | `/api/auth/login`  | `{"username":"johndoe","password":"password123"}` | 200 OK   |
| POST   | `/api/auth/logout` | -                                                 | 200 OK   |

### **👤 PROFILE**

| Method | URL                   | Body | Expected |
| ------ | --------------------- | ---- | -------- |
| GET    | `/api/auth/profile/1` | -    | 200 OK   |

### **🔒 PASSWORD**

| Method | URL                         | Body                                                                         | Expected |
| ------ | --------------------------- | ---------------------------------------------------------------------------- | -------- |
| PUT    | `/api/auth/change-password` | `{"username":"johndoe","currentPassword":"oldpass","newPassword":"newpass"}` | 200 OK   |

## 🧪 **Testing Flow**

### **1. Register User**

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "+1234567890",
  "joinDate": "2024-01-15",
  "username": "johndoe",
  "password": "password123"
}
```

### **2. Login User**

```json
{
  "username": "johndoe",
  "password": "password123"
}
```

### **3. Get Profile**

```
GET /api/auth/profile/1
```

### **4. Change Password**

```json
{
  "username": "johndoe",
  "currentPassword": "password123",
  "newPassword": "newpassword123"
}
```

## ✅ **Expected Responses**

### **Successful Registration**

```json
{
  "message": "User registered successfully",
  "userId": 1,
  "username": "johndoe"
}
```

### **Successful Login**

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

### **Username Validation**

```json
{
  "username": "johndoe",
  "available": false
}
```

## 🚨 **Error Responses**

### **Missing Fields**

```json
{
  "error": "Missing required fields"
}
```

### **Duplicate Username**

```json
{
  "error": "Username already exists"
}
```

### **Invalid Credentials**

```json
{
  "error": "Invalid username or password"
}
```

## 🗄️ **Database Verification**

### **Check Users**

```sql
USE bodima;
SELECT * FROM user;
```

### **Check Login Credentials**

```sql
SELECT l.username, l.password, u.name, u.email
FROM login l
JOIN user u ON l.user_id = u.user_id;
```

## 🔧 **cURL Examples**

### **Register**

```bash
curl -X POST http://localhost:8090/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","email":"test@test.com","username":"testuser","password":"pass123"}'
```

### **Login**

```bash
curl -X POST http://localhost:8090/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"pass123"}'
```

### **Get Profile**

```bash
curl -X GET http://localhost:8090/api/auth/profile/1
```

## 🎯 **Testing Checklist**

- [ ] Register new user
- [ ] Login with valid credentials
- [ ] Get user profile
- [ ] Change password
- [ ] Login with new password
- [ ] Test error scenarios
- [ ] Verify database data

## 📚 **Required Fields**

### **Registration**

- `name` (required)
- `email` (required)
- `username` (required)
- `password` (required)
- `phone` (optional)
- `joinDate` (optional)

### **Login**

- `username` (required)
- `password` (required)

### **Change Password**

- `username` (required)
- `currentPassword` (required)
- `newPassword` (required)





