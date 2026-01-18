# Hướng dẫn Test JWT Authentication System

## Bước 1: Chuẩn bị môi trường

### 1.1 Khởi động MySQL Database
```bash
# Tạo database
mysql -u root -p
CREATE DATABASE springjwt;
USE springjwt;
```

### 1.2 Cập nhật application.properties
Đảm bảo thông tin database đúng:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/springjwt
spring.datasource.username=root
spring.datasource.password=your_password
```

### 1.3 Khởi động ứng dụng
```bash
cd spring_boot_api_jwt_ad
mvn spring-boot:run
```

## Bước 2: Test với Postman hoặc cURL

### 2.1 Test Public Endpoint (Không cần authentication)
```bash
curl -X GET http://localhost:8080/api/test/public
```

**Expected Response:**
```json
{
    "message": "This is a public endpoint - no authentication required",
    "timestamp": 1642678800000
}
```

### 2.2 Đăng ký User mới
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

**Expected Response:**
```
User registered successfully
```

### 2.3 Đăng nhập để lấy Tokens
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

**Expected Response:**
```json
{
    "accessToken": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzcHJpbmctYm9vdC1qd3QtYXBwIiwiaWF0IjoxNjQyNjc4ODAwLCJleHAiOjE2NDI2Nzk3MDAsInN1YiI6InRlc3R1c2VyIiwidXNlcklkIjoxLCJhdXRob3JpdGllcyI6WyJVU0VSX1JFQUQiXSwidG9rZW5UeXBlIjoiYWNjZXNzIn0...",
    "refreshToken": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzcHJpbmctYm9vdC1qd3QtYXBwIiwiaWF0IjoxNjQyNjc4ODAwLCJleHAiOjE2NDI2Nzg4MDAsInN1YiI6InRlc3R1c2VyIiwidXNlcklkIjoxLCJ0b2tlblR5cGUiOiJyZWZyZXNoIiwianRpIjoiYWJjZC0xMjM0In0...",
    "tokenType": "Bearer",
    "expiresIn": 900,
    "scope": "read write"
}
```

### 2.4 Test Protected Endpoint
```bash
# Thay YOUR_ACCESS_TOKEN bằng accessToken từ response trên
curl -X GET http://localhost:8080/api/test/protected \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

**Expected Response:**
```json
{
    "message": "This is a protected endpoint - authentication required",
    "user": "testuser",
    "authorities": ["USER_READ"],
    "timestamp": 1642678800000
}
```

### 2.5 Test User Endpoint (cần USER_READ permission)
```bash
curl -X GET http://localhost:8080/api/test/user \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### 2.6 Test Admin Endpoint (sẽ fail vì user không có ADMIN role)
```bash
curl -X GET http://localhost:8080/api/test/admin \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

**Expected Response:**
```json
{
    "error": "Forbidden",
    "message": "Access Denied"
}
```

### 2.7 Refresh Access Token
```bash
curl -X POST http://localhost:8080/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{
    "refreshToken": "YOUR_REFRESH_TOKEN"
  }'
```

### 2.8 Logout (Revoke Refresh Tokens)
```bash
curl -X POST http://localhost:8080/auth/logout \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### 2.9 Test với token đã logout (should fail)
```bash
curl -X GET http://localhost:8080/api/test/protected \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

## Bước 3: Test với Postman

### 3.1 Import Collection
1. Mở Postman
2. Import file `test-api.http` hoặc tạo collection mới
3. Tạo các request theo mẫu trên

### 3.2 Setup Environment Variables
Tạo environment với variables:
- `baseUrl`: `http://localhost:8080`
- `accessToken`: (sẽ được set từ login response)
- `refreshToken`: (sẽ được set từ login response)

### 3.3 Auto-extract Tokens
Trong login request, thêm vào Tests tab:
```javascript
if (pm.response.code === 200) {
    const response = pm.response.json();
    pm.environment.set("accessToken", response.accessToken);
    pm.environment.set("refreshToken", response.refreshToken);
}
```

## Bước 4: Verify JWT Token

### 4.1 Sử dụng JWT.io
1. Copy accessToken từ response
2. Truy cập https://jwt.io
3. Paste token vào Encoded section
4. Verify signature bằng public key

### 4.2 Decode Token Content
Token sẽ chứa:
```json
{
  "iss": "spring-boot-jwt-app",
  "iat": 1642678800,
  "exp": 1642679700,
  "sub": "testuser",
  "userId": 1,
  "authorities": ["USER_READ"],
  "tokenType": "access"
}
```

## Bước 5: Test Error Cases

### 5.1 Invalid Token
```bash
curl -X GET http://localhost:8080/api/test/protected \
  -H "Authorization: Bearer invalid_token"
```

### 5.2 Expired Token
Đợi 15 phút sau khi login, sau đó test lại protected endpoint.

### 5.3 Wrong Credentials
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "wrong_password"
  }'
```

### 5.4 Missing Authorization Header
```bash
curl -X GET http://localhost:8080/api/test/protected
```

## Bước 6: Test Database

### 6.1 Kiểm tra Refresh Tokens trong DB
```sql
SELECT * FROM t_refresh_token;
```

### 6.2 Kiểm tra User và Roles
```sql
SELECT u.username, r.role_name, p.permission_name 
FROM t_user u
JOIN t_user_role ur ON u.id = ur.user_id
JOIN t_role r ON ur.role_id = r.id
JOIN t_role_permission rp ON r.id = rp.role_id
JOIN t_permission p ON rp.permission_id = p.id;
```

## Troubleshooting

### Lỗi thường gặp:

1. **"RSA key not found"**
   - Chạy `generate-keys.bat` để tạo RSA keys
   - Kiểm tra file `private.pem` và `public.pem` trong `src/main/resources/certs/`

2. **"Database connection failed"**
   - Kiểm tra MySQL đã khởi động
   - Verify thông tin database trong `application.properties`

3. **"Access Denied"**
   - Kiểm tra user có đúng roles/permissions không
   - Verify token chưa expired

4. **"Invalid JWT token"**
   - Kiểm tra format Authorization header: `Bearer <token>`
   - Verify token chưa bị tamper

## Expected Test Results

✅ **Successful Tests:**
- Public endpoint accessible without token
- User registration successful
- Login returns valid tokens
- Protected endpoints accessible with valid token
- Token refresh works correctly
- Logout revokes refresh tokens

❌ **Expected Failures:**
- Admin endpoint fails for regular user
- Expired tokens rejected
- Invalid tokens rejected
- Requests without tokens rejected