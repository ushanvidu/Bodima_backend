# Postman Quick Reference Card

## 🚀 **Quick Start**

### **1. Start Application**

```bash
./mvnw spring-boot:run
```

### **2. Import Collection**

- Open Postman → Import → Select `Bodima_API_Collection.json`
- Set `{{baseUrl}}` = `http://localhost:8090`

## 📋 **Test Endpoints**

### **ADMIN TESTS**

| Method | URL             | Body                                            | Expected    |
| ------ | --------------- | ----------------------------------------------- | ----------- |
| POST   | `/api/admins`   | `{"name":"Admin","email":"admin@test.com"}`     | 201 Created |
| GET    | `/api/admins`   | -                                               | 200 OK      |
| GET    | `/api/admins/1` | -                                               | 200 OK      |
| PUT    | `/api/admins/1` | `{"name":"Updated","email":"updated@test.com"}` | 200 OK      |
| DELETE | `/api/admins/1` | -                                               | 200 OK      |

### **ROOM TESTS**

| Method | URL            | Body                                 | Expected    |
| ------ | -------------- | ------------------------------------ | ----------- |
| POST   | `/api/rooms`   | `{"roomNumber":"A101","capacity":2}` | 201 Created |
| GET    | `/api/rooms`   | -                                    | 200 OK      |
| GET    | `/api/rooms/1` | -                                    | 200 OK      |
| PUT    | `/api/rooms/1` | `{"roomNumber":"A102","capacity":3}` | 200 OK      |
| DELETE | `/api/rooms/1` | -                                    | 200 OK      |

### **USER TESTS**

| Method | URL                 | Body                                                                                    | Expected    |
| ------ | ------------------- | --------------------------------------------------------------------------------------- | ----------- |
| POST   | `/api/users`        | `{"name":"John","email":"john@test.com","phone":"+1234567890","joinDate":"2024-01-15"}` | 201 Created |
| GET    | `/api/users`        | -                                                                                       | 200 OK      |
| GET    | `/api/users/1`      | -                                                                                       | 200 OK      |
| GET    | `/api/users/room/1` | -                                                                                       | 200 OK      |
| PUT    | `/api/users/1`      | `{"name":"John Updated","email":"john.updated@test.com"}`                               | 200 OK      |
| DELETE | `/api/users/1`      | -                                                                                       | 200 OK      |

### **PAYMENT TESTS**

| Method | URL                    | Body                                                                | Expected    |
| ------ | ---------------------- | ------------------------------------------------------------------- | ----------- |
| POST   | `/api/payments`        | `{"month":"2024-01-01","monthlyCharge":500.00,"paidAmount":500.00}` | 201 Created |
| GET    | `/api/payments`        | -                                                                   | 200 OK      |
| GET    | `/api/payments/1`      | -                                                                   | 200 OK      |
| GET    | `/api/payments/user/1` | -                                                                   | 200 OK      |
| PUT    | `/api/payments/1`      | `{"month":"2024-01-01","monthlyCharge":550.00,"paidAmount":550.00}` | 200 OK      |
| DELETE | `/api/payments/1`      | -                                                                   | 200 OK      |

## 🔧 **Headers Required**

For POST/PUT requests, always include:

```
Content-Type: application/json
```

## ✅ **Expected Response Codes**

- **200 OK**: Successful GET, PUT, DELETE
- **201 Created**: Successful POST
- **400 Bad Request**: Invalid data
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Server error

## 🗄️ **Database Verification**

After testing, check your database:

```bash
mysql -u root -p'Ananda@2002' -e "USE bodima; SELECT * FROM admin; SELECT * FROM room; SELECT * FROM user; SELECT * FROM monthly_payment;"
```

## 🚨 **Common Issues**

| Issue              | Solution                              |
| ------------------ | ------------------------------------- |
| Connection refused | Check if app is running on port 8090  |
| 404 Not Found      | Verify URL and `{{baseUrl}}` variable |
| 500 Error          | Check application console logs        |
| Invalid JSON       | Validate JSON syntax                  |

## 📝 **Testing Order**

1. **Create** (POST) - Admin → Room → User → Payment
2. **Read** (GET) - All endpoints
3. **Update** (PUT) - All endpoints
4. **Delete** (DELETE) - Payment → User → Room → Admin

## 🎯 **Success Indicators**

- ✅ Status codes 200/201
- ✅ JSON response with data
- ✅ Database tables created
- ✅ Data persists in database
- ✅ Relationships work correctly

