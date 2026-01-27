# Hệ thống Demo Design Patterns

## Tổng quan
Dự án này minh họa việc áp dụng ba design patterns quan trọng trong Java Spring Boot:

1. **Composite Pattern** - Hệ thống quản lý thư mục và tập tin
2. **Observer Pattern** - Hệ thống thông báo cho cổ phiếu và quản lý task
3. **Adapter Pattern** - Chuyển đổi dữ liệu giữa XML và JSON

## Cấu trúc dự án

```
src/main/java/fit/iuh/demo/
├── composite/           # Composite Pattern
│   ├── FileSystemComponent.java
│   ├── File.java
│   └── Directory.java
├── observer/            # Observer Pattern
│   ├── Observer.java
│   ├── Subject.java
│   ├── Stock.java
│   ├── TaskManager.java
│   ├── Investor.java
│   └── TeamMember.java
├── adapter/             # Adapter Pattern
│   ├── DataProcessor.java
│   ├── JsonService.java
│   ├── XmlService.java
│   ├── XmlToJsonAdapter.java
│   └── JsonToXmlAdapter.java
└── demo/               # Demo classes
    ├── CompositePatternDemo.java
    ├── ObserverPatternDemo.java
    └── AdapterPatternDemo.java
```

## 1. Composite Pattern - Hệ thống quản lý thư mục và tập tin

### Mô tả
Composite Pattern cho phép xử lý các đối tượng đơn lẻ và nhóm đối tượng một cách thống nhất. Trong hệ thống file:
- **Component**: `FileSystemComponent` - abstract class chung
- **Leaf**: `File` - đại diện cho tập tin
- **Composite**: `Directory` - đại diện cho thư mục chứa files và directories khác

### Tính năng
- Hiển thị cấu trúc cây thư mục với emoji
- Tính toán dung lượng tự động (leaf + composite)
- Thêm/xóa files và directories
- Hiển thị thông tin chi tiết

### Ví dụ output
```
📁 root/ (436 bytes total)
  📄 README.md (49 bytes)
  📄 config.json (33 bytes)
  📁 src/ (217 bytes total)
    📄 Main.java (115 bytes)
    📄 Utils.java (102 bytes)
```

## 2. Observer Pattern - Hệ thống thông báo

### Mô tả
Observer Pattern định nghĩa mối quan hệ một-nhiều giữa các đối tượng. Khi một đối tượng thay đổi trạng thái, tất cả các đối tượng phụ thuộc sẽ được thông báo tự động.

### Các trường hợp sử dụng

#### A. Hệ thống thông báo cổ phiếu
- **Subject**: `Stock` - cổ phiếu với giá và mã
- **Observer**: `Investor` - nhà đầu tư theo dõi cổ phiếu
- **Tính năng**: Thông báo khi giá cổ phiếu thay đổi với tỷ lệ phần trăm

#### B. Hệ thống quản lý task
- **Subject**: `TaskManager` - quản lý công việc
- **Observer**: `TeamMember` - thành viên nhóm với vai trò khác nhau
- **Tính năng**: Thông báo khi trạng thái task hoặc assignee thay đổi

### Ví dụ output
```
📈 Nhà đầu tư Nguyễn Văn A nhận thông báo: Cổ phiếu VN30: Giá thay đổi từ 1250.50 -> 1275.80 (2.02%)
👥 Developer Nguyễn Văn D nhận thông báo: Task 'Phát triển tính năng đăng nhập': Trạng thái thay đổi từ 'TODO' -> 'IN_PROGRESS'
```

## 3. Adapter Pattern - Chuyển đổi XML/JSON

### Mô tả
Adapter Pattern cho phép các interface không tương thích làm việc cùng nhau. Trong hệ thống này, chúng ta chuyển đổi giữa XML và JSON.

### Cấu trúc
- **Target Interface**: `DataProcessor` - interface chung cho xử lý dữ liệu
- **Adaptee**: `XmlService` - service xử lý XML với interface riêng
- **Adapter**: `XmlToJsonAdapter`, `JsonToXmlAdapter` - chuyển đổi giữa các format
- **Client**: `JsonService` - service xử lý JSON implement DataProcessor

### Tính năng
- Chuyển đổi XML sang JSON và ngược lại
- Validation dữ liệu đầu vào
- Xử lý lỗi gracefully
- Format output đẹp mắt
- Sử dụng thống nhất qua interface

### Ví dụ output
```
🔄 Đang chuyển đổi XML sang JSON...
✅ Chuyển đổi XML sang JSON thành công
{
  "id" : "2",
  "name" : "Trần Thị B",
  "email" : "tranthib@example.com"
}
```

## Công nghệ sử dụng

- **Java 17**
- **Spring Boot 4.0.2**
- **Jackson** - JSON/XML processing
- **Lombok** - Reduce boilerplate code
- **Maven** - Build tool

## Cách chạy ứng dụng

1. **Clone repository**
```bash
git clone <repository-url>
cd Bai1
```

2. **Compile và chạy**
```bash
mvn clean compile
mvn spring-boot:run
```

3. **Kết quả**
Ứng dụng sẽ chạy tất cả demo patterns và hiển thị kết quả trong console, sau đó khởi động Spring Boot server trên port 8081.

## Truy cập giao diện web

Sau khi ứng dụng khởi động thành công, truy cập:

- **Trang chủ**: http://localhost:8081
- **Composite Pattern**: http://localhost:8081/composite  
- **Observer Pattern**: http://localhost:8081/observer
- **Adapter Pattern**: http://localhost:8081/adapter

## Sơ đồ thiết kế

Xem file `DESIGN_DIAGRAMS.md` để xem các sơ đồ UML chi tiết cho từng pattern.

## Lợi ích của các Design Patterns

### Composite Pattern
- **Tính nhất quán**: Xử lý file và directory giống nhau
- **Tính mở rộng**: Dễ dàng thêm loại component mới
- **Tính đệ quy**: Tự nhiên với cấu trúc cây

### Observer Pattern
- **Loose coupling**: Subject và Observer độc lập
- **Dynamic relationships**: Có thể thêm/xóa observer runtime
- **Broadcast communication**: Một thay đổi thông báo nhiều observer

### Adapter Pattern
- **Tái sử dụng code**: Sử dụng lại existing classes
- **Tương thích**: Làm việc với incompatible interfaces
- **Separation of concerns**: Logic chuyển đổi tách biệt

## Kết luận

Dự án này minh họa cách áp dụng design patterns vào các tình huống thực tế:
- Quản lý file system với Composite
- Hệ thống notification với Observer  
- Data transformation với Adapter

Mỗi pattern giải quyết một vấn đề cụ thể và có thể kết hợp để tạo ra hệ thống phức tạp hơn.