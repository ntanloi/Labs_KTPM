package fit.iuh.demo.config;

import fit.iuh.demo.factory.BookFactoryProvider;
import fit.iuh.demo.model.*;
import fit.iuh.demo.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private LibraryService libraryService;
    
    @Autowired
    private BookFactoryProvider bookFactoryProvider;

    @Override
    public void run(String... args) throws Exception {
        // Tạo sách giấy
        PhysicalBook physicalBook1 = (PhysicalBook) bookFactoryProvider.getFactory("PHYSICAL").createBook();
        physicalBook1.setTitle("Lập trình Java cơ bản");
        physicalBook1.setAuthor("Nguyễn Văn A");
        physicalBook1.setGenre("Công nghệ");
        physicalBook1.setPublicationDate(LocalDate.of(2023, 1, 15));
        physicalBook1.setIsbn("978-0123456789");
        physicalBook1.setPageCount(350);
        libraryService.addBook(physicalBook1);

        PhysicalBook physicalBook2 = (PhysicalBook) bookFactoryProvider.getFactory("PHYSICAL").createBook();
        physicalBook2.setTitle("Design Patterns trong Java");
        physicalBook2.setAuthor("Trần Thị B");
        physicalBook2.setGenre("Công nghệ");
        physicalBook2.setPublicationDate(LocalDate.of(2023, 3, 20));
        physicalBook2.setIsbn("978-0987654321");
        physicalBook2.setPageCount(420);
        libraryService.addBook(physicalBook2);

        // Tạo sách điện tử
        EBook ebook1 = (EBook) bookFactoryProvider.getFactory("EBOOK").createBook();
        ebook1.setTitle("Spring Boot Thực Hành");
        ebook1.setAuthor("Lê Văn C");
        ebook1.setGenre("Công nghệ");
        ebook1.setPublicationDate(LocalDate.of(2023, 5, 10));
        ebook1.setFileFormat("PDF");
        ebook1.setFileSizeMB(15.5);
        libraryService.addBook(ebook1);

        EBook ebook2 = (EBook) bookFactoryProvider.getFactory("EBOOK").createBook();
        ebook2.setTitle("Cấu trúc dữ liệu và giải thuật");
        ebook2.setAuthor("Phạm Thị D");
        ebook2.setGenre("Khoa học máy tính");
        ebook2.setPublicationDate(LocalDate.of(2023, 2, 28));
        ebook2.setFileFormat("EPUB");
        ebook2.setFileSizeMB(8.2);
        libraryService.addBook(ebook2);

        // Tạo sách nói
        AudioBook audiobook1 = (AudioBook) bookFactoryProvider.getFactory("AUDIOBOOK").createBook();
        audiobook1.setTitle("Tư duy lập trình");
        audiobook1.setAuthor("Hoàng Văn E");
        audiobook1.setGenre("Phát triển bản thân");
        audiobook1.setPublicationDate(LocalDate.of(2023, 4, 5));
        audiobook1.setDurationMinutes(480);
        audiobook1.setNarrator("Nguyễn Minh F");
        libraryService.addBook(audiobook1);

        AudioBook audiobook2 = (AudioBook) bookFactoryProvider.getFactory("AUDIOBOOK").createBook();
        audiobook2.setTitle("Clean Code");
        audiobook2.setAuthor("Robert C. Martin");
        audiobook2.setGenre("Công nghệ");
        audiobook2.setPublicationDate(LocalDate.of(2008, 8, 1));
        audiobook2.setDurationMinutes(720);
        audiobook2.setNarrator("John Smith");
        libraryService.addBook(audiobook2);

        // Tạo một số sách đã mượn và quá hạn để demo
        PhysicalBook overdueBook = (PhysicalBook) bookFactoryProvider.getFactory("PHYSICAL").createBook();
        overdueBook.setTitle("Sách quá hạn demo");
        overdueBook.setAuthor("Demo Author");
        overdueBook.setGenre("Demo");
        overdueBook.setPublicationDate(LocalDate.of(2023, 1, 1));
        overdueBook.setIsbn("978-0000000000");
        overdueBook.setPageCount(200);
        overdueBook.setAvailable(false);
        overdueBook.setBorrowedDate(LocalDate.now().minusDays(20));
        overdueBook.setReturnDate(LocalDate.now().minusDays(5)); // Quá hạn 5 ngày
        libraryService.addBook(overdueBook);

        System.out.println("✅ Đã tải dữ liệu mẫu thành công!");
        System.out.println("📚 Tổng số sách: " + libraryService.getAllBooks().size());
        System.out.println("🔍 Truy cập ứng dụng tại: http://localhost:8080");
        System.out.println("🗄️ H2 Console: http://localhost:8080/h2-console");
    }
}