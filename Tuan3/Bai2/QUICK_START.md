# 🚀 HƯỚNG DẪN CHẠY NHANH

## ⚡ Cách nhanh nhất

### 🎯 **Tùy chọn 1: Sử dụng IDE (Khuyến nghị)**
1. **Download IntelliJ IDEA Community** (miễn phí): https://www.jetbrains.com/idea/download/
2. **Import project**: Open → Chọn thư mục `Bai2` → Import as Gradle project
3. **Run**: Tìm `Bai2Application.java` → Right-click → Run
4. **Truy cập**: http://localhost:8080

### 🎯 **Tùy chọn 2: Cài đặt JDK (Lâu dài)**
1. **Chạy kiểm tra**: `.\setup-java.bat`
2. **Download JDK**: https://adoptium.net/ (JDK 8 LTS)
3. **Thiết lập JAVA_HOME** và PATH
4. **Chạy**: `.\run.bat`

### 🎯 **Tùy chọn 3: Online IDE (Không cần cài đặt)**
1. **Upload code** lên https://replit.com/
2. **Chọn Java template**
3. **Run** trực tiếp trên web

---

## 🔍 Kiểm tra nhanh

```bash
java -version    # Phải có Java
javac -version   # Cần có để compile (JDK)
```

**Hiện tại**: Chỉ có JRE → Cần JDK hoặc dùng IDE

---

## ❌ Nếu gặp lỗi "tools.jar"

**Nguyên nhân**: Đang dùng JRE thay vì JDK

**Giải pháp nhanh**: Dùng IDE (IntelliJ IDEA có sẵn JDK)

**Giải pháp lâu dài**: Cài JDK từ https://adoptium.net/

---

## 🎯 Demo Design Patterns

Sau khi chạy thành công, bạn có thể demo:

1. **Factory Pattern**: Thêm sách mới → chọn loại sách
2. **Strategy Pattern**: Tìm kiếm → chọn chiến lược
3. **Decorator Pattern**: Mượn sách → chọn tính năng bổ sung
4. **Observer Pattern**: Xem console log khi thực hiện thao tác
5. **Singleton Pattern**: LibraryService được quản lý duy nhất

---

## 📞 Hỗ trợ

Nếu vẫn gặp vấn đề:
1. Chạy `setup-java.bat` để kiểm tra chi tiết
2. Đọc file `README.md` để biết thêm thông tin
3. Kiểm tra console log để debug