# 🔧 Khắc phục lỗi IntelliJ IDEA

## ✅ Các bước đã thực hiện:
1. Cập nhật `gradle.properties` - loại bỏ `MaxPermSize`
2. Cập nhật Spring Boot 3.2.2 cho Java 17
3. Cập nhật dependencies Jakarta EE
4. Cập nhật source code sử dụng `jakarta.*`

## 🚀 Các bước tiếp theo trong IntelliJ:

### Bước 1: Refresh Gradle
1. Mở tab **Gradle** (bên phải IDE)
2. Click biểu tượng **Refresh** 🔄
3. Hoặc nhấn `Ctrl + Shift + O`

### Bước 2: Clean Project
1. **Build** → **Clean**
2. **Build** → **Rebuild Project**

### Bước 3: Invalidate Caches
1. **File** → **Invalidate Caches and Restart**
2. Chọn **Invalidate and Restart**

### Bước 4: Kiểm tra JDK
1. **File** → **Project Structure** → **Project**
2. **Project SDK**: Đảm bảo chọn JDK 17
3. **Project language level**: Chọn **17**

### Bước 5: Chạy ứng dụng
1. Tìm `src/main/java/fit/iuh/demo/Bai2Application.java`
2. Right-click → **Run 'Bai2Application'**

## 🎯 Nếu vẫn lỗi:

### Tùy chọn A: Tạo Run Configuration mới
1. **Run** → **Edit Configurations**
2. Click **+** → **Application**
3. **Main class**: `fit.iuh.demo.Bai2Application`
4. **JRE**: Chọn JDK 17
5. **Apply** → **OK** → **Run**

### Tùy chọn B: Sử dụng Gradle Task
1. Mở tab **Gradle**
2. **Tasks** → **application** → **bootRun**
3. Double-click để chạy

### Tùy chọn C: Terminal trong IDE
```bash
./gradlew clean bootRun
```

## ✅ Kết quả mong đợi:
- Console hiển thị: "Started Bai2Application"
- Truy cập: http://localhost:8080
- Thấy trang chủ với thống kê thư viện

## 🆘 Nếu vẫn không được:
1. Chụp screenshot lỗi mới
2. Kiểm tra **Event Log** trong IntelliJ
3. Xem **Build** output để biết lỗi cụ thể