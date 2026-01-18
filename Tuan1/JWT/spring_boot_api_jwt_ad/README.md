# Spring Boot JWT Authentication với RSA và OAuth Resource Server

## Tổng quan về JWT

**JWT (JSON Web Token)** là một chuẩn mở (RFC 7519) để truyền thông tin an toàn giữa các bên dưới dạng JSON object. JWT bao gồm 3 phần được phân tách bởi dấu chấm (.):

### Cấu trúc JWT: `header.payload.signature`

1. **Header**: Chứa thông tin về thuật toán mã hóa
2. **Payload**: Chứa các claims (thông tin về user)
3. **Signature**: Chữ ký để xác thực tính toàn vẹn

## Access Token vs Refresh Token

### Access Token
- **Thời gian sống ngắn**: 15-30 phút
- **Mục đích**: Truy cập các API được bảo vệ
- **Chứa thông tin**: User ID, username, authorities/roles
- **Bảo mật**: Nếu bị lộ, tác động hạn chế do thời gian sống ngắn

### Refresh Token
- **Thời gian sống dài**: 7-30 ngày
- **Mục đích**: Tạo Access Token mới khi hết hạn
- **Lưu trữ**: Database để có thể revoke
- **Bảo mật**: Tăng cường trải nghiệm người dùng, giảm số lần đăng nhập

## Cách tạo và kiểm tra Token hợp lệ

### 1. Tạo Token (RSAJwtUtil.java)

```java
// Tạo Access Token
public String generateAccessToken(UserPrincipal userPrincipal) {
    Instant now = Instant.now();
    
    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("spring-boot-jwt-app")
            .issuedAt(now)
            .expiresAt(now.plus(15, ChronoUnit.MINUTES)) // 15 phút
            .subject(userPrincipal.getUsername())
            .claim("userId", userPrincipal.getUserId())
            .claim("authorities", userPrincipal.getAuthorities())
            .claim("tokenType", "access")
            .build();
            
    return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
}
```

### 2. Kiểm tra Token hợp lệ

```java
// Xác thực token
public Jwt validateToken(String token) {
    try {
        return jwtDecoder.decode(token); // Tự động verify signature và expiration
    } catch (JwtException e) {
        throw new RuntimeException("Invalid JWT token", e);
    }
}
```

## OAuth Resource Server với RSA

### Ưu điểm của RSA so với HMAC:
- **Asymmetric**: Public key để verify, Private key để sign
- **Scalable**: Nhiều service có thể verify token mà không cần share secret
- **Secure**: Private key chỉ ở Authorization Server

### Cấu hình Security (SecurityConfig.java)

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.getPublicKey()).build();
    }
    
    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.getPublicKey())
                .privateKey(rsaKeys.getPrivateKey())
                .build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt
                .decoder(jwtDecoder())
                .jwtAuthenticationConverter(jwtAuthenticationConverter())
            )
        );
    }
}
```

## API Endpoints

### 1. Đăng ký
```
POST /auth/register
Content-Type: application/json

{
    "username": "testuser",
    "password": "password123"
}
```

### 2. Đăng nhập
```
POST /auth/login
Content-Type: application/json

{
    "username": "testuser",
    "password": "password123"
}

Response:
{
    "accessToken": "eyJhbGciOiJSUzI1NiJ9...",
    "refreshToken": "eyJhbGciOiJSUzI1NiJ9...",
    "tokenType": "Bearer",
    "expiresIn": 900,
    "scope": "read write"
}
```

### 3. Refresh Token
```
POST /auth/refresh
Content-Type: application/json

{
    "refreshToken": "eyJhbGciOiJSUzI1NiJ9..."
}
```

### 4. Truy cập API được bảo vệ
```
GET /auth/protected
Authorization: Bearer eyJhbGciOiJSUzI1NiJ9...
```

### 5. Đăng xuất
```
POST /auth/logout
Authorization: Bearer eyJhbGciOiJSUzI1NiJ9...
```

## Cách chạy ứng dụng

### 1. Tạo RSA Key Pair

**Windows:**
```bash
generate-keys.bat
```

**Linux/Mac:**
```bash
chmod +x generate-keys.sh
./generate-keys.sh
```

**Hoặc sử dụng Java:**
```bash
java -cp src/main/java com.example.spring_boot_api_jwt_ad.util.RSAKeyGenerator
```

### 2. Cấu hình Database
Cập nhật `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/springjwt
spring.datasource.username=root
spring.datasource.password=password
```

### 3. Chạy ứng dụng
```bash
mvn spring-boot:run
```

## Kiểm tra Token với JWT.io

1. Copy token từ response
2. Truy cập https://jwt.io
3. Paste token vào Encoded section
4. Xem thông tin decoded trong Payload section

## Bảo mật Best Practices

1. **Thời gian sống ngắn cho Access Token** (15-30 phút)
2. **Lưu trữ Refresh Token trong database** để có thể revoke
3. **Sử dụng HTTPS** cho tất cả API calls
4. **Validate token signature** trước khi tin tưởng claims
5. **Implement token blacklist** cho logout
6. **Rotate RSA keys** định kỳ
7. **Không lưu sensitive data** trong JWT payload

## Cấu trúc dự án

```
src/main/java/com/example/spring_boot_api_jwt_ad/
├── config/
│   ├── SecurityConfig.java          # OAuth Resource Server config
│   └── RSAKeyProperties.java        # RSA key properties
├── controller/
│   └── AuthControllerRSA.java       # Authentication endpoints
├── dto/
│   ├── TokenResponse.java           # Token response DTO
│   └── RefreshTokenRequest.java     # Refresh token request DTO
├── entity/
│   └── RefreshToken.java            # Refresh token entity
├── service/
│   ├── RefreshTokenService.java     # Refresh token service interface
│   └── RefreshTokenServiceImpl.java # Refresh token service implementation
└── util/
    ├── RSAJwtUtil.java              # RSA JWT utilities
    └── RSAKeyGenerator.java         # RSA key generator utility
```

## Dependencies cần thiết

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```

Dự án này demonstrate cách implement JWT authentication với RSA signing, Access/Refresh token pattern, và OAuth Resource Server trong Spring Boot.