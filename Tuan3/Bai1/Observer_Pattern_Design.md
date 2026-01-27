# Observer Pattern - Sơ đồ thiết kế

## Mô tả
Observer Pattern định nghĩa mối quan hệ một-nhiều giữa các đối tượng. Khi một đối tượng thay đổi trạng thái, tất cả các đối tượng phụ thuộc sẽ được thông báo tự động.

## Sơ đồ UML

```
┌─────────────────────────────────────────────────────────────────┐
│                    Observer Pattern                             │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│    ┌─────────────────┐                                         │
│    │    Subject      │                                         │
│    │   <<abstract>>  │                                         │
│    ├─────────────────┤                                         │
│    │ - observers     │                                         │
│    │   List<>        │                                         │
│    ├─────────────────┤                                         │
│    │ + attach()      │                                         │
│    │ + detach()      │                                         │
│    │ + notify()      │                                         │
│    └─────────────────┘                                         │
│            △                                                    │
│            │                                                    │
│    ┌───────┴────────┐                                          │
│    │                │                                          │
│ ┌──▼──────────┐  ┌──▼──────────┐                              │
│ │   Stock     │  │ TaskManager │                              │
│ ├─────────────┤  ├─────────────┤                              │
│ │ - price     │  │ - status    │                              │
│ │ - symbol    │  │ - taskName  │                              │
│ ├─────────────┤  ├─────────────┤                              │
│ │ + setPrice()│  │ + setStatus()│                             │
│ └─────────────┘  └─────────────┘                              │
│                                                                 │
│    ┌─────────────────┐                                         │
│    │    Observer     │                                         │
│    │  <<interface>>  │                                         │
│    ├─────────────────┤                                         │
│    │ + update()      │                                         │
│    └─────────────────┘                                         │
│            △                                                    │
│            │                                                    │
│    ┌───────┴────────┐                                          │
│    │                │                                          │
│ ┌──▼──────────┐  ┌──▼──────────┐                              │
│ │  Investor   │  │ TeamMember  │                              │
│ ├─────────────┤  ├─────────────┤                              │
│ │ - name      │  │ - name      │                              │
│ ├─────────────┤  ├─────────────┤                              │
│ │ + update()  │  │ + update()  │                              │
│ └─────────────┘  └─────────────┘                              │
└─────────────────────────────────────────────────────────────────┘
```

## Các thành phần chính

### 1. Subject (Abstract Class)
- **Vai trò**: Quản lý danh sách observers và thông báo khi có thay đổi
- **Thuộc tính**: `List<Observer> observers`
- **Phương thức**:
  - `attach(Observer)`: Đăng ký observer
  - `detach(Observer)`: Hủy đăng ký observer
  - `notifyObservers(String)`: Thông báo cho tất cả observers

### 2. Concrete Subjects

#### Stock (Cổ phiếu)
- **Thuộc tính**:
  - `String symbol`: Mã cổ phiếu
  - `double price`: Giá hiện tại
- **Phương thức**:
  - `setPrice(double)`: Cập nhật giá và thông báo observers

#### TaskManager (Quản lý công việc)
- **Thuộc tính**:
  - `String taskName`: Tên công việc
  - `String status`: Trạng thái (TODO, IN_PROGRESS, TESTING, DONE)
  - `String assignee`: Người được giao
- **Phương thức**:
  - `setStatus(String)`: Cập nhật trạng thái và thông báo
  - `setAssignee(String)`: Thay đổi người được giao và thông báo

### 3. Observer Interface
- **Phương thức**: `update(String message)`: Nhận thông báo từ subject

### 4. Concrete Observers

#### Investor (Nhà đầu tư)
- **Thuộc tính**: `String name`
- **Chức năng**: Nhận thông báo khi giá cổ phiếu thay đổi

#### TeamMember (Thành viên nhóm)
- **Thuộc tính**: 
  - `String name`: Tên
  - `String role`: Vai trò (Developer, Tester, PM, etc.)
- **Chức năng**: Nhận thông báo khi trạng thái task thay đổi

## Luồng hoạt động

### Scenario 1: Thay đổi giá cổ phiếu
1. **Đăng ký**: Investors đăng ký theo dõi Stock
2. **Thay đổi**: Stock.setPrice() được gọi
3. **Thông báo**: Stock gọi notifyObservers()
4. **Cập nhật**: Tất cả Investors nhận được thông báo qua update()

### Scenario 2: Thay đổi trạng thái task
1. **Đăng ký**: TeamMembers đăng ký theo dõi TaskManager
2. **Thay đổi**: TaskManager.setStatus() được gọi
3. **Thông báo**: TaskManager gọi notifyObservers()
4. **Cập nhật**: Tất cả TeamMembers nhận được thông báo qua update()

## Lợi ích

1. **Loose Coupling**: Subject và Observer độc lập với nhau
2. **Dynamic Relationships**: Có thể thêm/xóa observer runtime
3. **Broadcast Communication**: Một thay đổi thông báo nhiều observer
4. **Open/Closed Principle**: Dễ dàng thêm observer mới mà không sửa code cũ

## Ứng dụng thực tế

- **Hệ thống thông báo**: Email, SMS, push notifications
- **Stock market**: Theo dõi giá cổ phiếu
- **Task management**: Theo dõi tiến độ công việc
- **Event handling**: GUI events, system events
- **Model-View architectures**: MVC, MVP, MVVM

## Code Structure

```
src/main/java/fit/iuh/demo/observer/
├── Observer.java              # Observer interface
├── Subject.java               # Abstract Subject class
├── Stock.java                 # Concrete Subject - Cổ phiếu
├── TaskManager.java           # Concrete Subject - Quản lý task
├── Investor.java              # Concrete Observer - Nhà đầu tư
└── TeamMember.java            # Concrete Observer - Thành viên nhóm
```