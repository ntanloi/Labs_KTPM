# 🚀 HƯỚNG DẪN CHẠY BẰNG IDE

Vì hệ thống chỉ có JRE (không có JDK), cách dễ nhất là sử dụng IDE có sẵn JDK.

## 📱 **Cách 1: IntelliJ IDEA (Khuyến nghị)**

### Bước 1: Download IntelliJ IDEA
- **Community Edition (Miễn phí)**: https://www.jetbrains.com/idea/download/
- IntelliJ IDEA đã tích hợp sẵn JDK

### Bước 2: Import Project
1. Mở IntelliJ IDEA
2. Chọn **Open or Import**
3. Chọn thư mục dự án (`Bai2`)
4. Chọn **Import project from external model** → **Gradle**
5. Chọn **Use Gradle from: 'gradle-wrapper.properties' file**
6. Click **Finish**

### Bước 3: Cấu hình JDK
1. File → Project Structure → Project
2. Project SDK: Chọn JDK có sẵn trong IntelliJ (thường là JDK 17+)
3. Project language level: Chọn **8**

### Bước 4: Chạy ứng dụng
1. Tìm file `Bai2Application.java`
2. Click chuột phải → **Run 'Bai2Application'**
3. Truy cập: http://localhost:8080

---

## 📱 **Cách 2: Eclipse IDE**

### Bước 1: Download Eclipse IDE
- **Eclipse IDE for Java Developers**: https://www.eclipse.org/downloads/
- Chọn phiên bản có sẵn JDK

### Bước 2: Import Project
1. Mở Eclipse
2. File → Import → Existing Gradle Project
3. Chọn thư mục dự án
4. Click **Finish**

### Bước 3: Chạy ứng dụng
1. Tìm `Bai2Application.java` trong Package Explorer
2. Right-click → Run As → Java Application
3. Truy cập: http://localhost:8080

---

## 📱 **Cách 3: Visual Studio Code**

### Bước 1: Cài đặt VS Code và Extensions
1. Download VS Code: https://code.visualstudio.com/
2. Cài đặt extensions:
   - **Extension Pack for Java**
   - **Spring Boot Extension Pack**

### Bước 2: Mở Project
1. File → Open Folder → Chọn thư mục dự án
2. VS Code sẽ tự động detect Gradle project

### Bước 3: Chạy ứng dụng
1. Mở `Bai2Application.java`
2. Click **Run** ở trên main method
3. Truy cập: http://localhost:8080

---

## 📱 **Cách 4: Online IDE (Không cần cài đặt)**

### GitPod (Miễn phí)
1. Tạo repository trên GitHub với code này
2. Truy cập: `https://gitpod.io/#https://github.com/your-username/your-repo`
3. GitPod sẽ tự động setup môi trường và chạy

### Replit (Miễn phí)
1. Truy cập: https://replit.com/
2. Tạo new repl → Import from GitHub
3. Chọn Java template

---

## 🎯 **Khuyến nghị**

**Cho người mới bắt đầu**: IntelliJ IDEA Community Edition
- Miễn phí
- Tích hợp sẵn JDK
- Hỗ trợ Spring Boot tốt
- Dễ sử dụng

**Cho người có kinh nghiệm**: VS Code với Java extensions
- Nhẹ
- Nhiều tính năng
- Hỗ trợ debugging tốt

---

## ✅ **Sau khi chạy thành công**

Bạn sẽ thấy:
1. **Console log** hiển thị thông báo từ Observer Pattern
2. **Web interface** tại http://localhost:8080
3. **H2 Console** tại http://localhost:8080/h2-console

### Demo các Design Patterns:
1. **Factory Pattern**: Thêm sách → chọn loại sách
2. **Strategy Pattern**: Tìm kiếm → chọn chiến lược
3. **Decorator Pattern**: Mượn sách → chọn tính năng bổ sung
4. **Observer Pattern**: Xem console log
5. **Singleton Pattern**: LibraryService được quản lý duy nhất

---

## 🆘 **Nếu vẫn gặp vấn đề**

1. **Lỗi JDK**: IDE thường tích hợp sẵn JDK, không cần cài thêm
2. **Lỗi dependencies**: IDE sẽ tự động download
3. **Lỗi port**: Thay đổi port trong `application.properties`
4. **Lỗi database**: H2 chạy in-memory, tự động tạo

**Liên hệ hỗ trợ**: Gửi screenshot lỗi để được hỗ trợ cụ thể.