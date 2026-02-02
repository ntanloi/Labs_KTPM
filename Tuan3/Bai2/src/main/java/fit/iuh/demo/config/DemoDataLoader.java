package fit.iuh.demo.config;

import fit.iuh.demo.factory.BookFactoryProvider;
import fit.iuh.demo.model.*;
import fit.iuh.demo.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Order(2) // Chạy sau DataLoader
public class DemoDataLoader implements CommandLineRunner {

    @Autowired
    private LibraryService libraryService;
    
    @Autowired
    private BookFactoryProvider bookFactoryProvider;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n🎯 ========================================");
        System.out.println("   DEMO DATA FOR DESIGN PATTERNS");
        System.out.println("🎯 ========================================");
        
        // Tạo thêm dữ liệu để demo patterns
        createDemoBooks();
        
        System.out.println("\n✅ Demo data loaded successfully!");
        System.out.println("📚 Total books: " + libraryService.getAllBooks().size());
        System.out.println("🔍 Ready to demo Design Patterns!");
        System.out.println("\n🎬 DEMO GUIDE:");
        System.out.println("1. 🏭 Factory Pattern: /books/add - Try different book types");
        System.out.println("2. 🎯 Strategy Pattern: /search - Try different search strategies");
        System.out.println("3. 👁️ Observer Pattern: Watch console logs during operations");
        System.out.println("4. 🎨 Decorator Pattern: Borrow books with extra features");
        System.out.println("5. 👑 Singleton Pattern: LibraryService instance shared across app");
        System.out.println("\n🌐 Access: http://localhost:8080");
        System.out.println("🗄️ H2 Console: http://localhost:8080/h2-console");
    }
    
    private void createDemoBooks() {
        // Sách để demo Strategy Pattern - tìm theo tên
        PhysicalBook strategyBook1 = (PhysicalBook) bookFactoryProvider.getFactory("PHYSICAL").createBook();
        strategyBook1.setTitle("Advanced Java Programming");
        strategyBook1.setAuthor("John Smith");
        strategyBook1.setGenre("Programming");
        strategyBook1.setPublicationDate(LocalDate.of(2023, 6, 15));
        strategyBook1.setIsbn("978-1111111111");
        strategyBook1.setPageCount(500);
        libraryService.addBook(strategyBook1);

        // Sách để demo Strategy Pattern - tìm theo tác giả
        EBook strategyBook2 = (EBook) bookFactoryProvider.getFactory("EBOOK").createBook();
        strategyBook2.setTitle("Web Development Guide");
        strategyBook2.setAuthor("Nguyễn Văn Demo");
        strategyBook2.setGenre("Web Technology");
        strategyBook2.setPublicationDate(LocalDate.of(2023, 7, 20));
        strategyBook2.setFileFormat("PDF");
        strategyBook2.setFileSizeMB(25.0);
        libraryService.addBook(strategyBook2);

        // Sách để demo Strategy Pattern - tìm theo thể loại
        AudioBook strategyBook3 = (AudioBook) bookFactoryProvider.getFactory("AUDIOBOOK").createBook();
        strategyBook3.setTitle("Machine Learning Basics");
        strategyBook3.setAuthor("AI Expert");
        strategyBook3.setGenre("Artificial Intelligence");
        strategyBook3.setPublicationDate(LocalDate.of(2023, 8, 10));
        strategyBook3.setDurationMinutes(600);
        strategyBook3.setNarrator("Tech Speaker");
        libraryService.addBook(strategyBook3);

        // Sách để demo Decorator Pattern - có sẵn để mượn
        PhysicalBook decoratorBook1 = (PhysicalBook) bookFactoryProvider.getFactory("PHYSICAL").createBook();
        decoratorBook1.setTitle("Design Patterns Demo Book");
        decoratorBook1.setAuthor("Pattern Master");
        decoratorBook1.setGenre("Software Engineering");
        decoratorBook1.setPublicationDate(LocalDate.of(2023, 9, 1));
        decoratorBook1.setIsbn("978-2222222222");
        decoratorBook1.setPageCount(300);
        libraryService.addBook(decoratorBook1);

        EBook decoratorBook2 = (EBook) bookFactoryProvider.getFactory("EBOOK").createBook();
        decoratorBook2.setTitle("Decorator Pattern in Action");
        decoratorBook2.setAuthor("Code Architect");
        decoratorBook2.setGenre("Software Design");
        decoratorBook2.setPublicationDate(LocalDate.of(2023, 9, 15));
        decoratorBook2.setFileFormat("EPUB");
        decoratorBook2.setFileSizeMB(12.5);
        libraryService.addBook(decoratorBook2);

        System.out.println("📖 Created demo books for Pattern demonstration");
    }
}