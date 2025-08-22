# Bodima API Testing Guide

## Overview
This guide will help you test your Bodima hostel management system API using Postman and verify your database implementation.

## Prerequisites

### 1. Database Setup
Make sure your MySQL database is running and accessible:
```sql
-- Create database if not exists
CREATE DATABASE IF NOT EXISTS bodima;
USE bodima;
```

### 2. Application Configuration
Your `application.properties` is configured for:
- **Database**: MySQL on localhost:3306
- **Database Name**: bodima
- **Username**: root
- **Password**: Ananda@2002
- **Server Port**: 8090

### 3. Start the Application
```bash
# Navigate to your project directory
cd "Bodima 2"

# Run the Spring Boot application
./mvnw spring-boot:run
```

## Postman Testing

### 1. Import the Collection
1. Open Postman
2. Click "Import" 
3. Select the `Bodima_API_Collection.json` file
4. The collection will be imported with all endpoints

### 2. Environment Setup
The collection uses a variable `{{baseUrl}}` set to `http://localhost:8090`

### 3. Testing Sequence

#### Step 1: Test Database Connection
First, test if your application starts and connects to the database:

1. Start your Spring Boot application
2. Check the console logs for:
   - Database connection success
   - Hibernate DDL generation
   - Application started on port 8090

#### Step 2: Create Test Data (Recommended Order)

**1. Create Admin**
```http
POST http://localhost:8090/api/admins
Content-Type: application/json

{
    "name": "John Admin",
    "email": "admin@bodima.com"
}
```

**2. Create Room**
```http
POST http://localhost:8090/api/rooms
Content-Type: application/json

{
    "roomNumber": "A101",
    "capacity": 2
}
```

**3. Create User**
```http
POST http://localhost:8090/api/users
Content-Type: application/json

{
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "+1234567890",
    "joinDate": "2024-01-15"
}
```

**4. Create Payment**
```http
POST http://localhost:8090/api/payments
Content-Type: application/json

{
    "month": "2024-01-01",
    "monthlyCharge": 500.00,
    "paidAmount": 500.00
}
```

#### Step 3: Test CRUD Operations

For each entity (Admin, Room, User, Payment), test:

1. **GET all** - Verify data is retrieved
2. **GET by ID** - Verify specific record retrieval
3. **PUT** - Update existing records
4. **DELETE** - Remove records

## Database Validation

### 1. Check Database Tables
Connect to your MySQL database and run:

```sql
USE bodima;

-- Check if tables were created
SHOW TABLES;

-- Expected tables:
-- admin
-- room  
-- user
-- login
-- monthly_payment
```

### 2. Verify Table Structure
```sql
-- Check table structures
DESCRIBE admin;
DESCRIBE room;
DESCRIBE user;
DESCRIBE login;
DESCRIBE monthly_payment;
```

### 3. Check Relationships
```sql
-- Verify foreign key relationships
SELECT 
    TABLE_NAME,
    COLUMN_NAME,
    CONSTRAINT_NAME,
    REFERENCED_TABLE_NAME,
    REFERENCED_COLUMN_NAME
FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE REFERENCED_TABLE_SCHEMA = 'bodima';
```

### 4. Test Data Integrity
```sql
-- Check if data was inserted correctly
SELECT * FROM admin;
SELECT * FROM room;
SELECT * FROM user;
SELECT * FROM monthly_payment;

-- Test relationships
SELECT u.name, r.room_number, a.name as admin_name
FROM user u
LEFT JOIN room r ON u.room_id = r.room_id
LEFT JOIN admin a ON u.admin_id = a.admin_id;
```

## Common Issues and Solutions

### 1. Database Connection Issues
**Problem**: Application fails to start with database connection error
**Solution**: 
- Verify MySQL is running
- Check credentials in `application.properties`
- Ensure database `bodima` exists

### 2. JPA/Hibernate Issues
**Problem**: Tables not created automatically
**Solution**:
- Check `spring.jpa.hibernate.ddl-auto=update` in properties
- Look for Hibernate logs in console
- Verify entity annotations are correct

### 3. API Response Issues
**Problem**: 404 or 500 errors
**Solution**:
- Verify controllers are properly annotated with `@RestController`
- Check request mappings
- Ensure service and repository classes are properly injected

### 4. JSON Serialization Issues
**Problem**: Circular reference errors in JSON responses
**Solution**:
- Add `@JsonManagedReference` and `@JsonBackReference` annotations
- Or use `@JsonIgnore` on bidirectional relationships

## Testing Checklist

- [ ] Application starts without errors
- [ ] Database tables are created automatically
- [ ] POST requests create new records
- [ ] GET requests retrieve data correctly
- [ ] PUT requests update existing records
- [ ] DELETE requests remove records
- [ ] Foreign key relationships work correctly
- [ ] JSON responses are properly formatted
- [ ] Error handling works for invalid requests

## Performance Testing

### 1. Load Testing
Use Postman's Runner to test multiple concurrent requests:
1. Create a collection with multiple requests
2. Use Postman Runner with iterations
3. Monitor response times and success rates

### 2. Database Performance
```sql
-- Check query performance
EXPLAIN SELECT * FROM user WHERE room_id = 1;

-- Monitor slow queries
SHOW VARIABLES LIKE 'slow_query_log';
```

## Security Considerations

1. **Input Validation**: Ensure all inputs are validated
2. **SQL Injection**: JPA/Hibernate provides protection, but verify
3. **Authentication**: Consider adding Spring Security
4. **CORS**: Currently set to allow all origins (`*`)

## Next Steps

1. Add input validation using `@Valid` annotations
2. Implement proper error handling with `@ControllerAdvice`
3. Add logging for better debugging
4. Consider adding Spring Security for authentication
5. Add unit tests for services and integration tests for controllers
6. Implement pagination for large datasets
7. Add API documentation using Swagger/OpenAPI
