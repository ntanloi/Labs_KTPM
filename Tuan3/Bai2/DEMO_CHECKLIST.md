# ✅ CHECKLIST DEMO DESIGN PATTERNS

## 🚀 Chuẩn bị
- [ ] Ứng dụng chạy tại: http://localhost:8080
- [ ] Console/Terminal mở để xem Observer logs
- [ ] Browser Developer Tools mở (F12)

---

## 1. 🏭 **FACTORY METHOD PATTERN**
**Trang**: `/books/add`

### Demo Steps:
- [ ] Chọn "Sách giấy" → Thấy fields: ISBN, Số trang
- [ ] Chọn "Sách điện tử" → Thấy fields: Định dạng, Kích thước
- [ ] Chọn "Sách nói" → Thấy fields: Thời lượng, Người đọc
- [ ] Tạo 1 sách bất kỳ → Xem console log

**Minh chứng**: Form thay đổi theo loại sách được chọn

---

## 2. 🎯 **STRATEGY PATTERN**  
**Trang**: `/search`

### Demo Steps:
- [ ] Tìm "Java" theo "Tên sách" → Ghi nhận kết quả
- [ ] Tìm "Java" theo "Tác giả" → So sánh kết quả khác
- [ ] Tìm "Công nghệ" theo "Thể loại" → Thấy strategy khác

**Minh chứng**: Cùng từ khóa, strategy khác → kết quả khác

---

## 3. 👁️ **OBSERVER PATTERN**
**Quan sát**: Console logs

### Demo Steps:
- [ ] Thêm sách mới → Xem log: "📚 [Thủ thư] Sách mới..."
- [ ] Mượn sách → Xem log: "📖 [Thủ thư] Sách đã được mượn..."
- [ ] Trả sách → Xem log: "✅ [Thủ thư] Sách đã được trả..."
- [ ] Vào trang "Sách quá hạn" → Xem log: "⚠️ [Thủ thư] Sách quá hạn..."

**Minh chứng**: Mỗi hành động tự động trigger thông báo

---

## 4. 🎨 **DECORATOR PATTERN**
**Trang**: `/books` → Click "Mượn"

### Demo Steps:
- [ ] Mượn cơ bản → "Mượn sách cơ bản: [Tên]"
- [ ] Mượn + Gia hạn 7 ngày → "...+ Gia hạn 7 ngày - Phí: 35000 VND"
- [ ] Mượn + Chữ nổi → "...+ Phiên bản đặc biệt: Chữ nổi - Phí: 10000 VND"
- [ ] Mượn + Cả 2 → "...+ Gia hạn + Phiên bản đặc biệt - Phí: 45000 VND"

**Minh chứng**: Tính năng được "decorate" lên service cơ bản

---

## 5. 👑 **SINGLETON PATTERN**
**Kiểm tra**: Code + Behavior

### Demo Steps:
- [ ] Thêm sách ở trang A → Thấy ở trang B (cùng instance)
- [ ] Thống kê trang chủ cập nhật sau mỗi thao tác
- [ ] Giải thích `@Service` annotation trong code

**Minh chứng**: Tất cả controller dùng chung 1 LibraryService

---

## 🎬 **DEMO SCRIPT (5 phút)**

### Phút 1: Giới thiệu
"Đây là hệ thống quản lý thư viện demo 5 Design Patterns..."

### Phút 2: Factory + Strategy
"Factory Pattern tạo sách theo loại... Strategy Pattern tìm kiếm..."

### Phút 3: Observer
"Observer Pattern thông báo tự động... Xem console..."

### Phút 4: Decorator
"Decorator Pattern thêm tính năng... Xem phí thay đổi..."

### Phút 5: Singleton + Tổng kết
"Singleton đảm bảo 1 instance... Tất cả patterns hoạt động..."

---

## 📊 **KẾT QUẢ MONG ĐỢI**

✅ **Factory**: 3 loại form khác nhau  
✅ **Strategy**: 3 kết quả tìm kiếm khác nhau  
✅ **Observer**: 4+ dòng log thông báo  
✅ **Decorator**: 4 cách mượn sách khác nhau  
✅ **Singleton**: Dữ liệu nhất quán toàn app  

---

## 🆘 **BACKUP PLAN**

Nếu demo live gặp lỗi:
- [ ] Có screenshots sẵn của từng pattern
- [ ] Giải thích code trực tiếp
- [ ] Sử dụng slides backup
- [ ] Demo offline với video recording