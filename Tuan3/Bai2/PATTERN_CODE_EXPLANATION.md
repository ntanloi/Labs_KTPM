# 🔍 GIẢI THÍCH CODE CHI TIẾT TỪNG PATTERN

## 1. 🏭 **FACTORY METHOD PATTERN**

### 📁 **Cấu trúc files:**
```
factory/
├── BookFactory.java           # Abstract Factory
├── PhysicalBookFactory.java   # Concrete Factory 1
├── EBookFactory.java          # Concrete Factory 2
├── AudioBookFactory.java      # Concrete Factory 3
└── BookFactoryProvider.java   # Factory Provider
```

### 💻 **Code chính:**

#### BookFactory.java (Abstract Factory)
```java
public abstract class BookFactory {
    public abstract Book createBook();  // Factory Method
    
    public Book orderBook() {
        Book book = createBook();        // Gọi factory method
        return book;
    }
}
```

#### PhysicalBookFactory.java (Concrete Factory)
```java
@Component
public class PhysicalBookFactory extends BookFactory {
    @Override
    public Book createBook() {
        return new PhysicalBook();       // Tạo sản phẩm cụ thể
    }
}
```

#### BookFactoryProvider.java (Factory Selector)
```java
@Service
public class BookFactoryProvider {
    public BookFactory getFactory(String bookType) {
        switch (bookType.toUpperCase()) {
            case "PHYSICAL": return physicalBookFactory;
            case "EBOOK": return eBookFactory;
            case "AUDIOBOOK": return audioBookFactory;
        }
    }
}
```

### 🎯 **Cách hoạt động:**
1. User chọn loại sách trong form
2. Controller gọi `bookFactoryProvider.getFactory(bookType)`
3. Provider trả về factory tương ứng
4. Factory tạo object Book phù hợp
5. Object được populate data và save

---

## 2. 🎯 **STRATEGY PATTERN**

### 📁 **Cấu trúc files:**
```
strategy/
├── SearchStrategy.java          # Strategy Interface
├── SearchByTitleStrategy.java   # Concrete Strategy 1
├── SearchByAuthorStrategy.java  # Concrete Strategy 2
├── SearchByGenreStrategy.java   # Concrete Strategy 3
└── SearchContext.java           # Context Class
```

### 💻 **Code chính:**

#### SearchStrategy.java (Strategy Interface)
```java
public interface SearchStrategy {
    List<Book> search(List<Book> books, String keyword);
    String getStrategyName();
}
```

#### SearchByTitleStrategy.java (Concrete Strategy)
```java
@Component
public class SearchByTitleStrategy implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String keyword) {
        return books.stream()
                .filter(book -> book.getTitle()
                    .toLowerCase()
                    .contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
```

#### SearchContext.java (Context)
```java
@Component
public class SearchContext {
    private SearchStrategy strategy;
    
    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;           // Runtime strategy selection
    }
    
    public List<Book> executeSearch(List<Book> books, String keyword) {
        return strategy.search(books, keyword);  // Delegate to strategy
    }
}
```

### 🎯 **Cách hoạt động:**
1. User chọn search type trong form
2. Controller map search type thành Strategy object
3. Context.setStrategy() được gọi
4. Context.executeSearch() delegate cho strategy
5. Strategy thực hiện thuật toán tìm kiếm riêng

---

## 3. 👁️ **OBSERVER PATTERN**

### 📁 **Cấu trúc files:**
```
observer/
├── LibraryObserver.java         # Observer Interface
├── LibrarianObserver.java       # Concrete Observer 1
└── UserNotificationObserver.java # Concrete Observer 2
```

### 💻 **Code chính:**

#### LibraryObserver.java (Observer Interface)
```java
public interface LibraryObserver {
    void onBookAdded(Book book);
    void onBookBorrowed(Book book);
    void onBookReturned(Book book);
    void onBookOverdue(Book book);
}
```

#### LibrarianObserver.java (Concrete Observer)
```java
@Component
public class LibrarianObserver implements LibraryObserver {
    @Override
    public void onBookAdded(Book book) {
        System.out.println("📚 [Thủ thư] Sách mới: " + book.getTitle());
    }
    
    @Override
    public void onBookBorrowed(Book book) {
        System.out.println("📖 [Thủ thư] Sách đã mượn: " + book.getTitle());
    }
}
```

#### LibraryService.java (Subject)
```java
@Service
public class LibraryService {
    private List<LibraryObserver> observers = new ArrayList<>();
    
    public void addObserver(LibraryObserver observer) {
        observers.add(observer);
    }
    
    private void notifyBookAdded(Book book) {
        observers.forEach(observer -> observer.onBookAdded(book));
    }
    
    public Book addBook(Book book) {
        Book savedBook = bookRepository.save(book);
        notifyBookAdded(savedBook);  // Notify all observers
        return savedBook;
    }
}
```

### 🎯 **Cách hoạt động:**
1. LibraryService khởi tạo với danh sách observers
2. Observers được register trong @PostConstruct
3. Khi có event (add/borrow/return), service notify tất cả observers
4. Mỗi observer xử lý event theo cách riêng (log, email, etc.)

---

## 4. 🎨 **DECORATOR PATTERN**

### 📁 **Cấu trúc files:**
```
decorator/
├── BorrowService.java           # Component Interface
├── BasicBorrowService.java      # Concrete Component
├── BorrowServiceDecorator.java  # Base Decorator
├── ExtendedBorrowDecorator.java # Concrete Decorator 1
└── SpecialEditionDecorator.java # Concrete Decorator 2
```

### 💻 **Code chính:**

#### BorrowService.java (Component Interface)
```java
public interface BorrowService {
    String borrowBook(Book book);
    double calculateFee();
    String getServiceDescription();
}
```

#### BasicBorrowService.java (Concrete Component)
```java
@Component
public class BasicBorrowService implements BorrowService {
    @Override
    public String borrowBook(Book book) {
        book.setAvailable(false);
        book.setBorrowedDate(LocalDate.now());
        book.setReturnDate(LocalDate.now().plusDays(14));
        return "Mượn sách cơ bản: " + book.getTitle();
    }
    
    @Override
    public double calculateFee() {
        return 0.0;  // Free basic service
    }
}
```

#### BorrowServiceDecorator.java (Base Decorator)
```java
public abstract class BorrowServiceDecorator implements BorrowService {
    protected BorrowService borrowService;
    
    public BorrowServiceDecorator(BorrowService borrowService) {
        this.borrowService = borrowService;
    }
    
    @Override
    public String borrowBook(Book book) {
        return borrowService.borrowBook(book);  // Delegate to wrapped service
    }
}
```

#### ExtendedBorrowDecorator.java (Concrete Decorator)
```java
public class ExtendedBorrowDecorator extends BorrowServiceDecorator {
    private int extraDays;
    
    @Override
    public String borrowBook(Book book) {
        String result = super.borrowBook(book);  // Call wrapped service
        book.setReturnDate(book.getReturnDate().plusDays(extraDays));
        return result + " + Gia hạn " + extraDays + " ngày";
    }
    
    @Override
    public double calculateFee() {
        return super.calculateFee() + (extraDays * 5000);  // Add extra fee
    }
}
```

### 🎯 **Cách hoạt động:**
1. User chọn tính năng bổ sung trong modal
2. Controller tạo BasicBorrowService
3. Nếu có extraDays → wrap với ExtendedBorrowDecorator
4. Nếu có specialFeature → wrap với SpecialEditionDecorator
5. Gọi borrowBook() trên decorator cuối cùng
6. Decorator chain thực hiện từng tính năng

---

## 5. 👑 **SINGLETON PATTERN**

### 📁 **File chính:**
```
service/LibraryService.java
```

### 💻 **Code chính:**

#### LibraryService.java (Singleton)
```java
@Service  // Spring annotation đảm bảo Singleton
public class LibraryService {
    
    @Autowired
    private BookRepository bookRepository;  // Dependency injection
    
    // Spring tạo và quản lý duy nhất 1 instance
    // Tất cả @Autowired LibraryService đều trỏ đến cùng object này
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }
}
```

#### Controller sử dụng Singleton
```java
@Controller
public class LibraryController {
    
    @Autowired
    private LibraryService libraryService;  // Same instance everywhere
    
    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", libraryService.getAllBooks());
        return "books/list";
    }
}
```

### 🎯 **Cách hoạt động:**
1. Spring Boot khởi động
2. Spring container tạo 1 instance duy nhất của LibraryService
3. Tất cả @Autowired LibraryService đều inject cùng instance này
4. Mọi thao tác đều thông qua instance duy nhất
5. Đảm bảo data consistency và shared state

---

## 🔗 **TƯƠNG TÁC GIỮA CÁC PATTERNS**

### 🎯 **Flow hoàn chỉnh:**
1. **Singleton** LibraryService được inject vào Controller
2. **Factory** tạo Book object theo loại
3. **Observer** được notify khi có thay đổi
4. **Strategy** được sử dụng khi tìm kiếm
5. **Decorator** được áp dụng khi mượn sách

### 📊 **Dependency Graph:**
```
LibraryController (uses Singleton LibraryService)
    ├── BookFactoryProvider (Factory Pattern)
    ├── SearchContext (Strategy Pattern)  
    ├── LibraryService (Observer Subject + Singleton)
    └── BorrowService (Decorator Pattern)
```

Tất cả patterns hoạt động độc lập nhưng tương tác hài hòa trong 1 hệ thống hoàn chỉnh!