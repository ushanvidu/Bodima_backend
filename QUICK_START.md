# Bodima API - Quick Start Guide

## 🚀 Quick Setup

### 1. Start Your Application

```bash
./mvnw spring-boot:run
```

### 2. Test with Postman

1. **Import Collection**: Open Postman → Import → Select `Bodima_API_Collection.json`
2. **Set Environment**: The collection uses `{{baseUrl}}` = `http://localhost:8090`
3. **Start Testing**: Run the requests in order

## 📋 Available Endpoints

### Admin Management

- `POST /api/admins` - Create admin
- `GET /api/admins` - Get all admins
- `GET /api/admins/{id}` - Get admin by ID
- `PUT /api/admins/{id}` - Update admin
- `DELETE /api/admins/{id}` - Delete admin

### Room Management

- `POST /api/rooms` - Create room
- `GET /api/rooms` - Get all rooms
- `GET /api/rooms/{id}` - Get room by ID
- `PUT /api/rooms/{id}` - Update room
- `DELETE /api/rooms/{id}` - Delete room

### User Management

- `POST /api/users` - Create user
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/room/{roomId}` - Get users by room
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Payment Management

- `POST /api/payments` - Create payment
- `GET /api/payments` - Get all payments
- `GET /api/payments/{id}` - Get payment by ID
- `GET /api/payments/user/{userId}` - Get payments by user
- `PUT /api/payments/{id}` - Update payment
- `DELETE /api/payments/{id}` - Delete payment

## 🗄️ Database Validation

### Check Database

```bash
mysql -u root -p'Ananda@2002' < database_validation.sql
```

### Manual Database Check

```sql
USE bodima;
SHOW TABLES;
SELECT * FROM admin;
SELECT * FROM room;
SELECT * FROM user;
SELECT * FROM monthly_payment;
```

## 🔧 Configuration

**Database**: MySQL on localhost:3306
**Database Name**: bodima
**Username**: root
**Password**: Ananda@2002
**Server Port**: 8090

## 📝 Sample Test Data

### Create Admin

```json
{
  "name": "John Admin",
  "email": "admin@bodima.com"
}
```

### Create Room

```json
{
  "roomNumber": "A101",
  "capacity": 2
}
```

### Create User

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "+1234567890",
  "joinDate": "2024-01-15"
}
```

### Create Payment

```json
{
  "month": "2024-01-01",
  "monthlyCharge": 500.0,
  "paidAmount": 500.0
}
```

## ⚠️ Common Issues

1. **MySQL Connection**: Make sure MySQL is running
2. **Port 8090**: Ensure port is not in use
3. **Database**: Create database `bodima` if it doesn't exist
4. **JSON Format**: Ensure proper JSON format in request bodies

## 📚 Full Documentation

- **Testing Guide**: `TESTING_GUIDE.md`
- **Database Script**: `database_validation.sql`
- **Postman Collection**: `Bodima_API_Collection.json`

## 🎯 Testing Checklist

- [ ] Application starts on port 8090
- [ ] Database tables are created
- [ ] POST requests create records
- [ ] GET requests retrieve data
- [ ] PUT requests update records
- [ ] DELETE requests remove records
- [ ] Relationships work correctly

