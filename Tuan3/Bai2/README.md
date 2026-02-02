# Hệ thống Quản lý Thư viện - Design Patterns Demo

## Mô tả
Đây là một hệ thống quản lý thư viện được xây dựng bằng Spring Boot, minh họa việc sử dụng 5 Design Patterns chính:

## 🎯 Design Patterns được triển khai

### 1. **Singleton Pattern**
- **Class**: `LibraryService`
- **Mô tả**: Đảm bảo chỉ có một instance duy nhất của thư viện trong hệ thống
- **Implementation**: Sử dụng Spring `@Service` annotation để quản lý singleton

### 2. **Factory Method Pattern**
- **Classes**: `BookFactory`, `PhysicalBookFactory`, `EBookFactory`, `AudioBookFactory`
- **Mô tả**: Tạo ra các loại sách khác nhau (sách giấy, sách điện tử, sách nói)
- **Usage**: Trong form thêm sách, chọn loại sách để tạo đối tượng tương ứng

### 3. **Strategy Pattern**
- **Classes**: `SearchStrategy`, `SearchByTitleStrategy`, `SearchByAuthorStrategy`, `SearchByGenreStrategy`
- **Mô tả**: Các chiến lược tìm kiếm khác nhau có thể được chọn tại runtime
- **Usage**: Trong trang tìm kiếm, chọn chiến lược tìm kiếm phù hợp

### 4. **Observer Pattern**
- **Classes**: `LibraryObserver`, `LibrarianObserver`, `UserNotificationObserver`
- **Mô tả**: Thông báo tự động khi có sự kiện (thêm sách, mượn/trả sách, quá hạn)
- **Usage**: Xem console log khi thực hiện các thao tác

### 5. **Decorator Pattern**
- **Classes**: `BorrowService`, `ExtendedBorrowDecorator`, `SpecialEditionDecorator`
- **Mô tả**: Thêm tính năng bổ sung khi mượn sách (gia hạn, phiên bản đặc biệt)
- **Usage**: Trong modal mượn sách, chọn các tính năng bổ sung

## 🚀 Cách chạy ứng dụng

### ⚠️ Lỗi thường gặp: "Could not find tools.jar"

Lỗi này xảy ra khi hệ thống sử dụng JRE thay vì JDK. Để khắc phục:

#### Cách 1: Sử dụng script tự động (Windows)
```bash
# Chạy script kiểm tra và hướng dẫn cài đặt
setup-java.bat

# Sau khi cài đặt JDK, chạy ứng dụng
run.bat
```

#### Cách 2: Cài đặt thủ công

**Bước 1: Cài đặt JDK**
1. Download JDK 8+ từ:
   - [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
   - [OpenJDK](https://adoptium.net/)
2. Cài đặt JDK (không phải JRE)

**Bước 2: Thiết lập biến môi trường**
1. Mở Control Panel > System > Advanced System Settings
2. Click "Environment Variables"
3. Thêm biến mới:
   - Variable name: `JAVA_HOME`
   - Variable value: `C:\Program Files\Java\jdk-8` (đường dẫn JDK của bạn)
4. Sửa biến PATH, thêm: `%JAVA_HOME%\bin`

**Bước 3: Kiểm tra cài đặt**
```bash
java -version    # Phải hiển thị JDK version
javac -version   # Phải có Java Compiler
```

**Bước 4: Chạy ứng dụng**
```bash
./gradlew bootRun
```

### Yêu cầu hệ thống
- ✅ Java JDK 8 hoặc cao hơn (KHÔNG phải JRE)
- ✅ JAVA_HOME được thiết lập đúng
- ✅ Port 8080 chưa bị sử dụng

### Bước 3: Truy cập ứng dụng
- **Ứng dụng chính**: http://localhost:8080
- **H2 Database Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:library`
  - Username: `sa`
  - Password: (để trống)

## 📱 Tính năng chính

### 🏠 Trang chủ
- Thống kê tổng quan thư viện
- Thao tác nhanh
- Hiển thị sách mới nhất
- Thông tin về Design Patterns

### 📚 Quản lý sách
- **Xem danh sách**: Hiển thị tất cả sách với thông tin chi tiết
- **Thêm sách mới**: Sử dụng Factory Pattern để tạo các loại sách
- **Mượn sách**: Sử dụng Decorator Pattern để thêm tính năng
- **Trả sách**: Cập nhật trạng thái và thông báo

### 🔍 Tìm kiếm
- **Strategy Pattern**: Chọn chiến lược tìm kiếm
  - Tìm theo tên sách
  - Tìm theo tác giả  
  - Tìm theo thể loại

### ⚠️ Sách quá hạn
- **Observer Pattern**: Tự động thông báo sách quá hạn
- Thống kê chi tiết
- Thao tác trả sách

## 🗂️ Cấu trúc dự án

```
src/main/java/fit/iuh/demo/
├── config/              # Cấu hình Spring
├── controller/          # REST Controllers
├── decorator/           # Decorator Pattern
├── factory/             # Factory Method Pattern
├── model/               # Entity classes
├── observer/            # Observer Pattern
├── repository/          # JPA Repositories
├── service/             # Business Logic (Singleton)
└── strategy/            # Strategy Pattern

src/main/resources/
├── templates/           # Thymeleaf templates
└── application.properties
```

## 🎨 Giao diện

Ứng dụng sử dụng:
- **Bootstrap 5**: Framework CSS responsive
- **Font Awesome**: Icons
- **Thymeleaf**: Template engine
- **Gradient Design**: Giao diện hiện đại

## 📊 Dữ liệu mẫu

Ứng dụng tự động tạo dữ liệu mẫu khi khởi động:
- 7 cuốn sách với các loại khác nhau
- 1 sách quá hạn để demo Observer Pattern
- Thông tin chi tiết cho từng loại sách

## 🔧 Troubleshooting

### ❌ Lỗi "Could not find tools.jar"
**Nguyên nhân**: Hệ thống sử dụng JRE thay vì JDK
**Giải pháp**:
1. Chạy `setup-java.bat` để kiểm tra và hướng dẫn
2. Cài đặt JDK (không phải JRE)
3. Thiết lập JAVA_HOME đúng đường dẫn JDK
4. Thêm `%JAVA_HOME%\bin` vào PATH

### ❌ Lỗi port 8080 đã được sử dụng
**Giải pháp**: Thay đổi port trong `application.properties`:
```properties
server.port=8081
```

### ❌ Lỗi Gradle version
**Giải pháp**: Dự án đã cấu hình Gradle 7.6.4 tương thích Java 8

### ❌ Lỗi database
**Giải pháp**: 
- H2 database chạy in-memory, sẽ reset khi restart ứng dụng
- Kiểm tra H2 console tại http://localhost:8080/h2-console

### ✅ Kiểm tra cài đặt thành công
```bash
java -version     # Hiển thị JDK version
javac -version    # Hiển thị Java Compiler version
echo %JAVA_HOME%  # Hiển thị đường dẫn JDK
```

## 👨‍💻 Tác giả

Dự án được phát triển như một bài tập minh họa Design Patterns trong Java Spring Boot.

## 📝 Ghi chú

- Ứng dụng sử dụng H2 in-memory database nên dữ liệu sẽ mất khi restart
- Security đã được disable để dễ dàng demo
- Console log sẽ hiển thị các thông báo từ Observer Pattern
- Tất cả Design Patterns đều có giao diện tương tác trực quan