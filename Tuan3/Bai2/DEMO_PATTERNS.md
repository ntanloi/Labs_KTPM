# 🎯 HƯỚNG DẪN DEMO TỪNG DESIGN PATTERN

## 🚀 Trước khi bắt đầu
1. Chạy ứng dụng: http://localhost:8080
2. Mở **Console/Terminal** để xem log của Observer Pattern
3. Mở **Developer Tools** (F12) để xem network requests

---

## 1. 🏭 **FACTORY METHOD PATTERN**

### 📍 **Vị trí trong code:**
- `src/main/java/fit/iuh/demo/factory/`
- `BookFactory.java` (Abstract Factory)
- `PhysicalBookFactory.java`, `EBookFactory.java`, `AudioBookFactory.java`

### 🎬 **Cách demo:**

#### Bước 1: Vào trang "Thêm sách mới"
- Click menu **"Thêm sách mới"** hoặc truy cập: http://localhost:8080/books/add

#### Bước 2: Quan sát Factory Pattern hoạt động
1. **Chọn "Loại sách"** = **"Sách giấy"**
   - Form sẽ hiển thị thêm: **ISBN**, **Số trang**
   - JavaScript gọi `toggleSpecificFields()` để hiển thị fields riêng

2. **Chọn "Loại sách"** = **"Sách điện tử"**  
   - Form sẽ hiển thị: **Định dạng file**, **Kích thước file (MB)**

3. **Chọn "Loại sách"** = **"Sách nói"**
   - Form sẽ hiển thị: **Thời lượng (phút)**, **Người đọc**

#### Bước 3: Tạo sách để xem Factory hoạt động
**Tạo sách giấy:**
```
Loại sách: Sách giấy
Tên sách: Design Patterns in Java
Tác giả: Gang of Four
Thể loại: Công nghệ
Ngày xuất bản: 2024-01-15
ISBN: 978-0123456789
Số trang: 400
```

#### Bước 4: Kiểm tra kết quả
- Sau khi submit, xem **console log**
- Vào **"Danh sách sách"** để thấy sách mới với thông tin đặc trưng

### 🔍 **Minh chứng Factory Pattern:**
```java
// BookFactoryProvider.java - line 15-25
public BookFactory getFactory(String bookType) {
    switch (bookType.toUpperCase()) {
        case "PHYSICAL":
            return physicalBookFactory;  // Tạo PhysicalBook
        case "EBOOK":
            return eBookFactory;         // Tạo EBook  
        case "AUDIOBOOK":
            return audioBookFactory;     // Tạo AudioBook
    }
}
```

---

## 2. 🎯 **STRATEGY PATTERN**

### 📍 **Vị trí trong code:**
- `src/main/java/fit/iuh/demo/strategy/`
- `SearchStrategy.java` (Interface)
- `SearchByTitleStrategy.java`, `SearchByAuthorStrategy.java`, `SearchByGenreStrategy.java`

### 🎬 **Cách demo:**

#### Bước 1: Vào trang "Tìm kiếm sách"
- Click menu **"Tìm kiếm sách"** hoặc truy cập: http://localhost:8080/search

#### Bước 2: Demo các Strategy khác nhau

**Strategy 1: Tìm theo tên sách**
```
Từ khóa: "Java"
Chiến lược: "Tìm theo tên sách"
→ Kết quả: Tìm tất cả sách có "Java" trong tên
```

**Strategy 2: Tìm theo tác giả**
```
Từ khóa: "Nguyễn"  
Chiến lược: "Tìm theo tác giả"
→ Kết quả: Tìm tất cả sách của tác giả có "Nguyễn"
```

**Strategy 3: Tìm theo thể loại**
```
Từ khóa: "Công nghệ"
Chiến lược: "Tìm theo thể loại"  
→ Kết quả: Tìm tất cả sách thể loại "Công nghệ"
```

#### Bước 3: Quan sát kết quả khác nhau
- Cùng 1 từ khóa nhưng **strategy khác nhau** → **kết quả khác nhau**
- Trang kết quả hiển thị **strategy đã sử dụng**

### 🔍 **Minh chứng Strategy Pattern:**
```java
// LibraryController.java - line 140-155
SearchStrategy strategy;
switch (searchType) {
    case "title":
        strategy = searchByTitleStrategy;    // Runtime selection
        break;
    case "author":
        strategy = searchByAuthorStrategy;   // Runtime selection
        break;
    case "genre":
        strategy = searchByGenreStrategy;    // Runtime selection
}
List<Book> results = libraryService.searchBooks(keyword, strategy);
```

---

## 3. 👁️ **OBSERVER PATTERN**

### 📍 **Vị trí trong code:**
- `src/main/java/fit/iuh/demo/observer/`
- `LibraryObserver.java` (Interface)
- `LibrarianObserver.java`, `UserNotificationObserver.java`

### 🎬 **Cách demo:**

#### Bước 1: Mở Console để xem thông báo
- **IntelliJ**: Xem tab **Run** console
- **Command line**: Xem terminal đang chạy ứng dụng

#### Bước 2: Thực hiện các hành động để trigger Observer

**Demo 1: Thêm sách mới**
1. Vào **"Thêm sách mới"**
2. Tạo 1 cuốn sách bất kỳ
3. **Quan sát console** sẽ thấy:
```
📚 [Thủ thư] Sách mới đã được thêm: [Tên sách]
🔔 [Thông báo người dùng] Có sách mới: [Tên sách] - [Tác giả]
```

**Demo 2: Mượn sách**
1. Vào **"Danh sách sách"**
2. Click **"Mượn"** một cuốn sách có sẵn
3. **Quan sát console**:
```
📖 [Thủ thư] Sách đã được mượn: [Tên sách]
📱 [Thông báo người dùng] Bạn đã mượn thành công: [Tên sách]
```

**Demo 3: Trả sách**
1. Click **"Trả"** một cuốn sách đã mượn
2. **Quan sát console**:
```
✅ [Thủ thư] Sách đã được trả: [Tên sách]
📱 [Thông báo người dùng] Cảm ơn bạn đã trả sách: [Tên sách]
```

**Demo 4: Sách quá hạn**
1. Vào **"Sách quá hạn"**
2. **Quan sát console** (tự động trigger khi load trang):
```
⚠️ [Thủ thư] Sách quá hạn: [Tên sách]
📱 [Thông báo người dùng] Nhắc nhở: Sách quá hạn trả - [Tên sách]
```

### 🔍 **Minh chứng Observer Pattern:**
```java
// LibraryService.java - line 35-50
private void notifyBookAdded(Book book) {
    observers.forEach(observer -> observer.onBookAdded(book));
}

public Book addBook(Book book) {
    Book savedBook = bookRepository.save(book);
    notifyBookAdded(savedBook);  // Tự động thông báo tất cả observers
    return savedBook;
}
```

---

## 4. 🎨 **DECORATOR PATTERN**

### 📍 **Vị trí trong code:**
- `src/main/java/fit/iuh/demo/decorator/`
- `BorrowService.java` (Interface)
- `BasicBorrowService.java`, `ExtendedBorrowDecorator.java`, `SpecialEditionDecorator.java`

### 🎬 **Cách demo:**

#### Bước 1: Vào "Danh sách sách"
- Truy cập: http://localhost:8080/books

#### Bước 2: Demo Decorator Pattern

**Demo 1: Mượn sách cơ bản (không decorator)**
1. Click **"Mượn"** một cuốn sách
2. **Không chọn** tính năng bổ sung nào
3. Click **"Mượn sách"**
4. **Kết quả**: "Mượn sách cơ bản: [Tên sách]"

**Demo 2: Mượn sách + Gia hạn (ExtendedBorrowDecorator)**
1. Click **"Mượn"** một cuốn sách khác
2. **Gia hạn thêm**: Nhập **7** (ngày)
3. Click **"Mượn sách"**
4. **Kết quả**: "Mượn sách cơ bản: [Tên sách] + Gia hạn 7 ngày - Phí: 35000 VND"

**Demo 3: Mượn sách + Phiên bản đặc biệt (SpecialEditionDecorator)**
1. Click **"Mượn"** một cuốn sách khác
2. **Phiên bản đặc biệt**: Chọn **"Sách chữ nổi"**
3. Click **"Mượn sách"**
4. **Kết quả**: "Mượn sách cơ bản: [Tên sách] + Phiên bản đặc biệt: Chữ nổi - Phí: 10000 VND"

**Demo 4: Mượn sách + Cả 2 decorator**
1. Click **"Mượn"** một cuốn sách khác
2. **Gia hạn thêm**: **5** ngày
3. **Phiên bản đặc biệt**: **"Bìa cứng"**
4. Click **"Mượn sách"**
5. **Kết quả**: "Mượn sách cơ bản: [Tên sách] + Gia hạn 5 ngày + Phiên bản đặc biệt: Bìa cứng - Phí: 35000 VND"

### 🔍 **Minh chứng Decorator Pattern:**
```java
// LibraryController.java - line 175-185
BorrowService borrowService = basicBorrowService;

if (extraDays != null && extraDays > 0) {
    borrowService = new ExtendedBorrowDecorator(borrowService, extraDays);
}

if (specialFeature != null && !specialFeature.trim().isEmpty()) {
    borrowService = new SpecialEditionDecorator(borrowService, specialFeature);
}

String result = borrowService.borrowBook(book);  // Decorated behavior
```

---

## 5. 👑 **SINGLETON PATTERN**

### 📍 **Vị trí trong code:**
- `src/main/java/fit/iuh/demo/service/LibraryService.java`
- Annotation `@Service` đảm bảo Spring tạo singleton

### 🎬 **Cách demo:**

#### Bước 1: Kiểm tra trong code
- Mở `LibraryService.java`
- Thấy annotation `@Service` → Spring quản lý như singleton

#### Bước 2: Demo thực tế
1. **Thêm sách** từ trang "Thêm sách mới"
2. **Xem danh sách** từ trang "Danh sách sách" 
3. **Tìm kiếm** từ trang "Tìm kiếm"
4. **Tất cả đều thấy sách vừa thêm** → Cùng 1 instance LibraryService

#### Bước 3: Kiểm tra thống kê
- Vào **Trang chủ**
- Thấy **thống kê cập nhật** sau mỗi thao tác
- **Tất cả controller đều dùng chung 1 LibraryService instance**

### 🔍 **Minh chứng Singleton Pattern:**
```java
// LibraryService.java - line 15
@Service  // Spring đảm bảo chỉ có 1 instance duy nhất
public class LibraryService {
    // Tất cả @Autowired LibraryService đều trỏ đến cùng 1 object
}
```

---

## 🎯 **TỔNG KẾT DEMO**

### ✅ **Checklist hoàn thành:**
- [ ] **Factory Pattern**: Tạo 3 loại sách khác nhau
- [ ] **Strategy Pattern**: Tìm kiếm với 3 chiến lược khác nhau  
- [ ] **Observer Pattern**: Xem 4 loại thông báo trong console
- [ ] **Decorator Pattern**: Mượn sách với 4 cách khác nhau
- [ ] **Singleton Pattern**: Kiểm tra cùng 1 instance được dùng

### 📊 **Bằng chứng Pattern hoạt động:**
1. **Factory**: Form thay đổi theo loại sách
2. **Strategy**: Kết quả tìm kiếm khác nhau với cùng từ khóa
3. **Observer**: Console log hiển thị thông báo tự động
4. **Decorator**: Phí và mô tả thay đổi theo tính năng
5. **Singleton**: Dữ liệu nhất quán trên tất cả trang

### 🎬 **Video demo gợi ý:**
1. **Mở ứng dụng** → Giới thiệu trang chủ
2. **Factory Pattern** → Thêm 3 loại sách
3. **Strategy Pattern** → Tìm kiếm với 3 chiến lược
4. **Observer Pattern** → Thực hiện thao tác, xem console
5. **Decorator Pattern** → Mượn sách với các tùy chọn
6. **Singleton Pattern** → Giải thích và kiểm tra code

**Thời gian demo**: ~10-15 phút cho tất cả patterns