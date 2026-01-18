# 🚀 Hướng dẫn Test Nhanh JWT Authentication

## Bước 1: Khởi động ứng dụng

```bash
# Trong thư mục spring_boot_api_jwt_ad
mvn spring-boot:run
```

Đợi cho đến khi thấy log: `Started SpringBootApiJwtAdApplication`

## Bước 2: Chọn phương pháp test

### 🌐 Option 1: Test bằng Web Interface (Dễ nhất)
1. Mở file `jwt-test.html` trong browser
2. Điền thông tin và click các button để test
3. Xem kết quả trực tiếp trên web

### 📱 Option 2: Test bằng Postman
1. Import file `JWT_Authentication_Tests.postman_collection.json`
2. Chạy collection hoặc từng request riêng lẻ
3. Xem kết quả trong Postman

### 💻 Option 3: Test bằng cURL (Command Line)

#### Test cơ bản:
```bash
# 1. Test public endpoint
curl http://localhost:8080/api/test/public

# 2. Đăng ký user
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'

# 3. Đăng nhập
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'
```

#### Lưu token và test protected endpoints:
```bash
# Lưu response login vào file
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}' \
  -o login_response.json

# Trích xuất access token (Linux/Mac)
ACCESS_TOKEN=$(cat login_response.json | grep -o '"accessToken":"[^"]*"' | cut -d'"' -f4)

# Test protected endpoint
curl -H "Authorization: Bearer $ACCESS_TOKEN" \
  http://localhost:8080/api/test/protected
```

### 🤖 Option 4: Test tự động bằng script
```bash
# Linux/Mac
chmod +x test-script.sh
./test-script.sh

# Windows
test-script.bat
```

## Bước 3: Kết quả mong đợi

### ✅ Các test thành công:
- **Public endpoint**: Trả về 200, không cần token
- **Register**: Trả về 200, tạo user thành công
- **Login**: Trả về 200 + accessToken + refreshToken
- **Protected endpoint**: Trả về 200 với valid token
- **User endpoint**: Trả về 200 (có USER_READ permission)
- **Refresh token**: Trả về 200 + new accessToken

### ❌ Các test thất bại (mong đợi):
- **Admin endpoint**: Trả về 403 (user không có ADMIN role)
- **Invalid token**: Trả về 401
- **No token**: Trả về 401
- **Expired token**: Trả về 401

## Bước 4: Verify JWT Token

### Sử dụng jwt.io:
1. Copy accessToken từ login response
2. Truy cập https://jwt.io
3. Paste token vào Encoded section
4. Xem decoded payload:

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

## Bước 5: Test Advanced Features

### Test Token Refresh:
```bash
# Sử dụng refresh token để lấy access token mới
curl -X POST http://localhost:8080/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{"refreshToken":"YOUR_REFRESH_TOKEN"}'
```

### Test Logout:
```bash
# Revoke refresh tokens
curl -X POST http://localhost:8080/auth/logout \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### Test sau khi logout:
```bash
# Thử refresh token sau logout (should fail)
curl -X POST http://localhost:8080/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{"refreshToken":"YOUR_REFRESH_TOKEN"}'
```

## 🔧 Troubleshooting

### Lỗi "Connection refused":
- Kiểm tra ứng dụng đã khởi động chưa
- Verify port 8080 không bị chiếm

### Lỗi "RSA key not found":
```bash
# Tạo RSA keys
cd spring_boot_api_jwt_ad
# Windows:
generate-keys.bat
# Linux/Mac:
chmod +x generate-keys.sh && ./generate-keys.sh
```

### Lỗi Database:
- Kiểm tra MySQL đã khởi động
- Verify connection string trong application.properties
- Tạo database: `CREATE DATABASE springjwt;`

### Token không hoạt động:
- Kiểm tra format: `Bearer <token>`
- Verify token chưa expired (15 phút)
- Kiểm tra RSA keys đúng

## 📊 Test Results Summary

Sau khi chạy test, bạn sẽ thấy:
- ✅ **8-10 tests PASS**: Các chức năng cơ bản hoạt động
- ❌ **2-3 tests FAIL**: Các security restrictions hoạt động đúng

**Total: ~13 tests** - Tất cả đều quan trọng để verify hệ thống hoạt động chính xác!

## 🎯 Next Steps

Sau khi test thành công:
1. Tích hợp vào frontend application
2. Customize token expiration times
3. Add more roles và permissions
4. Implement token blacklist
5. Add rate limiting
6. Setup HTTPS cho production