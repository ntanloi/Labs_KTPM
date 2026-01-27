# Adapter Pattern - Sơ đồ thiết kế

## Mô tả
Adapter Pattern cho phép các interface không tương thích làm việc cùng nhau. Trong hệ thống này, chúng ta chuyển đổi giữa XML và JSON formats.

## Sơ đồ UML

```
┌─────────────────────────────────────────────────────────────────┐
│                    Adapter Pattern                              │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│    ┌─────────────────┐                                         │
│    │     Client      │                                         │
│    │                 │                                         │
│    └─────────────────┘                                         │
│            │                                                    │
│            │ uses                                               │
│            ▼                                                    │
│    ┌─────────────────┐                                         │
│    │ DataProcessor   │ (Target Interface)                      │
│    │  <<interface>>  │                                         │
│    ├─────────────────┤                                         │
│    │ + processData() │                                         │
│    └─────────────────┘                                         │
│            △                                                    │
│            │ implements                                         │
│            │                                                    │
│    ┌───────┴────────┐                                          │
│    │                │                                          │
│ ┌──▼──────────┐  ┌──▼──────────┐                              │
│ │ JsonService │  │XmlToJsonAdap│                              │
│ │             │  │ter          │                              │
│ ├─────────────┤  ├─────────────┤                              │
│ │+processData()│  │- xmlService │ ◄─────────┐                 │
│ └─────────────┘  ├─────────────┤           │                 │
│                  │+processData()│           │ uses            │
│                  └─────────────┘           │                 │
│                                            │                 │
│                  ┌─────────────────┐       │                 │
│                  │JsonToXmlAdapter │       │                 │
│                  ├─────────────────┤       │                 │
│                  │- jsonService    │       │                 │
│                  ├─────────────────┤       │                 │
│                  │+ processData()  │       │                 │
│                  └─────────────────┘       │                 │
│                            │               │                 │
│                            │ uses          │                 │
│                            ▼               │                 │
│                    ┌───────┴────────┐      │                 │
│                    │   XmlService   │ ◄────┘                 │
│                    │   (Adaptee)    │                        │
│                    ├────────────────┤                        │
│                    │ + processXml() │                        │
│                    └────────────────┘                        │
└─────────────────────────────────────────────────────────────────┘
```

## Các thành phần chính

### 1. Target Interface - DataProcessor
- **Vai trò**: Interface mà client mong muốn sử dụng
- **Phương thức**: `String processData(String data)`
- **Mục đích**: Cung cấp interface thống nhất cho việc xử lý dữ liệu

### 2. Concrete Target - JsonService
- **Vai trò**: Service xử lý JSON, implement DataProcessor interface
- **Chức năng**:
  - Validate JSON format
  - Format JSON với pretty printing
  - Xử lý lỗi JSON parsing
- **Phương thức**:
  - `processData(String jsonData)`: Xử lý JSON data
  - `isValidJson(String jsonData)`: Kiểm tra JSON hợp lệ

### 3. Adaptee - XmlService
- **Vai trò**: Service xử lý XML với interface riêng (không tương thích với DataProcessor)
- **Chức năng**:
  - Validate XML format
  - Format XML với pretty printing
  - Xử lý lỗi XML parsing
- **Phương thức**:
  - `processXml(String xmlData)`: Xử lý XML data (interface khác)
  - `isValidXml(String xmlData)`: Kiểm tra XML hợp lệ

### 4. Adapters

#### XmlToJsonAdapter
- **Vai trò**: Chuyển đổi XML sang JSON
- **Implement**: DataProcessor interface
- **Thuộc tính**: `XmlService xmlService`
- **Chức năng**:
  - Nhận XML data qua processData()
  - Sử dụng XmlService để validate XML
  - Chuyển đổi XML → Object → JSON
  - Trả về JSON formatted string

#### JsonToXmlAdapter
- **Vai trò**: Chuyển đổi JSON sang XML
- **Implement**: DataProcessor interface
- **Thuộc tính**: `JsonService jsonService`
- **Chức năng**:
  - Nhận JSON data qua processData()
  - Sử dụng JsonService để validate JSON
  - Chuyển đổi JSON → Object → XML
  - Trả về XML formatted string

## Luồng hoạt động

### Scenario 1: Xử lý JSON trực tiếp
1. **Client** gọi `JsonService.processData(jsonString)`
2. **JsonService** validate và format JSON
3. **Return** formatted JSON string

### Scenario 2: Chuyển đổi XML sang JSON
1. **Client** gọi `XmlToJsonAdapter.processData(xmlString)`
2. **XmlToJsonAdapter** sử dụng `XmlService.isValidXml()` để validate
3. **Adapter** chuyển đổi: XML → Object → JSON
4. **Return** JSON formatted string

### Scenario 3: Chuyển đổi JSON sang XML
1. **Client** gọi `JsonToXmlAdapter.processData(jsonString)`
2. **JsonToXmlAdapter** sử dụng `JsonService.isValidJson()` để validate
3. **Adapter** chuyển đổi: JSON → Object → XML
4. **Return** XML formatted string

## Lợi ích

1. **Tái sử dụng code**: Sử dụng lại existing XmlService mà không cần sửa đổi
2. **Tương thích interface**: Làm cho XmlService tương thích với DataProcessor
3. **Separation of concerns**: Logic chuyển đổi tách biệt khỏi business logic
4. **Flexibility**: Dễ dàng thêm adapter cho format khác (YAML, CSV, etc.)
5. **Single Responsibility**: Mỗi adapter chỉ lo một việc chuyển đổi

## Ứng dụng thực tế

- **Data format conversion**: XML ↔ JSON, CSV ↔ JSON
- **Legacy system integration**: Kết nối hệ thống cũ với hệ thống mới
- **Third-party library wrapper**: Bọc external library với interface riêng
- **Database adapters**: Kết nối với các loại database khác nhau
- **Payment gateway integration**: Tích hợp với nhiều payment provider

## Công nghệ sử dụng

### Jackson Libraries
- **jackson-databind**: JSON processing
- **jackson-dataformat-xml**: XML processing
- **jackson-core**: Core functionality

### Conversion Process
1. **Input validation**: Kiểm tra format hợp lệ
2. **Parse to Object**: Chuyển string thành Java Object
3. **Convert to target format**: Object → target format string
4. **Format output**: Pretty printing cho dễ đọc

## Code Structure

```
src/main/java/fit/iuh/demo/adapter/
├── DataProcessor.java         # Target interface
├── JsonService.java           # Concrete target
├── XmlService.java            # Adaptee
├── XmlToJsonAdapter.java      # Adapter XML → JSON
└── JsonToXmlAdapter.java      # Adapter JSON → XML
```

## Error Handling

- **Invalid JSON**: Catch JsonProcessingException
- **Invalid XML**: Catch parsing exceptions
- **Null data**: Handle null input gracefully
- **Empty data**: Validate non-empty input
- **User feedback**: Provide clear error messages

## Extension Points

Dễ dàng mở rộng cho các format khác:
- **YamlToJsonAdapter**: YAML → JSON
- **CsvToJsonAdapter**: CSV → JSON
- **PropertiesToJsonAdapter**: Properties → JSON