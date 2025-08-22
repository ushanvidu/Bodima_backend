# Postman Login Testing Guide

## Setup Instructions

### 1. Import Collection

1. Open Postman
2. Click "Import"
3. Import the `Bodima_Auth_Collection.json` file

### 2. Environment Setup

Create a new environment with these variables:

- `base_url`: `http://localhost:8080`
- `username`: `testuser`
- `password`: `testpass123`

## Login Testing Scenarios

### 1. Successful Login

**Request:**

- Method: `POST`
- URL: `{{base_url}}/api/auth/login`
- Headers:
  - `Content-Type: application/json`
- Body (raw JSON):

```json
{
  "username": "testuser",
  "password": "testpass123"
}
```

**Expected Response (200 OK):**

```json
{
  "message": "Login successful",
  "userId": 1,
  "username": "testuser",
  "userName": "John Doe",
  "email": "john@example.com",
  "phone": "1234567890"
}
```

### 2. Invalid Username

**Request:**

```json
{
  "username": "nonexistentuser",
  "password": "testpass123"
}
```

**Expected Response (400 Bad Request):**

```json
{
  "error": "Invalid username or password"
}
```

### 3. Invalid Password

**Request:**

```json
{
  "username": "testuser",
  "password": "wrongpassword"
}
```

**Expected Response (400 Bad Request):**

```json
{
  "error": "Invalid username or password"
}
```

### 4. Missing Username

**Request:**

```json
{
  "password": "testpass123"
}
```

**Expected Response (400 Bad Request):**

```json
{
  "error": "Username and password are required"
}
```

### 5. Missing Password

**Request:**

```json
{
  "username": "testuser"
}
```

**Expected Response (400 Bad Request):**

```json
{
  "error": "Username and password are required"
}
```

## Additional Endpoints Testing

### 6. Get User Profile

**Request:**

- Method: `GET`
- URL: `{{base_url}}/api/auth/profile/1`

**Expected Response (200 OK):**

```json
{
  "userId": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "1234567890",
  "joinDate": "2024-01-15"
}
```

### 7. Change Password

**Request:**

- Method: `PUT`
- URL: `{{base_url}}/api/auth/change-password`
- Body:

```json
{
  "username": "testuser",
  "currentPassword": "testpass123",
  "newPassword": "newpass456"
}
```

**Expected Response (200 OK):**

```json
{
  "message": "Password changed successfully"
}
```

### 8. Validate Username

**Request:**

- Method: `POST`
- URL: `{{base_url}}/api/auth/validate-username`
- Body:

```json
{
  "username": "testuser"
}
```

**Expected Response (200 OK):**

```json
{
  "username": "testuser",
  "available": false
}
```

### 9. Logout

**Request:**

- Method: `POST`
- URL: `{{base_url}}/api/auth/logout`

**Expected Response (200 OK):**

```json
{
  "message": "Logout successful"
}
```

## Test Data Setup

Before testing, ensure you have test data in your database:

```sql
-- Insert test user
INSERT INTO user (name, email, phone, password, join_date)
VALUES ('John Doe', 'john@example.com', '1234567890', 'testpass123', '2024-01-15');

-- Insert login credentials (assuming user_id = 1)
INSERT INTO login (user_id, username, password)
VALUES (1, 'testuser', 'testpass123');
```

## Troubleshooting

### Common Issues:

1. **CORS Error**: Ensure `@CrossOrigin(origins = "*")` is present in controller
2. **404 Error**: Check if Spring Boot application is running on port 8080
3. **Database Connection**: Verify database is running and accessible
4. **Invalid JSON**: Ensure request body is valid JSON format

### Debug Steps:

1. Check application logs for errors
2. Verify database connection in `application.properties`
3. Test with curl command:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"testpass123"}'
```
