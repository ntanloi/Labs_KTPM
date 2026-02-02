package fit.iuh.demo.controller;

import fit.iuh.demo.decorator.*;
import fit.iuh.demo.factory.BookFactoryProvider;
import fit.iuh.demo.model.*;
import fit.iuh.demo.observer.LibrarianObserver;
import fit.iuh.demo.observer.UserNotificationObserver;
import fit.iuh.demo.service.LibraryService;
import fit.iuh.demo.strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/")
public class LibraryController {
    
    @Autowired
    private LibraryService libraryService;
    
    @Autowired
    private BookFactoryProvider bookFactoryProvider;
    
    @Autowired
    private SearchByTitleStrategy searchByTitleStrategy;
    
    @Autowired
    private SearchByAuthorStrategy searchByAuthorStrategy;
    
    @Autowired
    private SearchByGenreStrategy searchByGenreStrategy;
    
    @Autowired
    private LibrarianObserver librarianObserver;
    
    @Autowired
    private UserNotificationObserver userNotificationObserver;
    
    @Autowired
    private BasicBorrowService basicBorrowService;
    
    public LibraryController() {
    }
    
    @PostConstruct
    public void init() {
        // Đăng ký observers
        libraryService.addObserver(librarianObserver);
        libraryService.addObserver(userNotificationObserver);
    }
    
    @GetMapping
    public String home(Model model) {
        List<Book> books = libraryService.getAllBooks();
        List<Book> availableBooks = libraryService.getAvailableBooks();
        List<Book> overdueBooks = libraryService.getOverdueBooks();
        
        model.addAttribute("totalBooks", books.size());
        model.addAttribute("availableBooks", availableBooks.size());
        model.addAttribute("borrowedBooks", books.size() - availableBooks.size());
        model.addAttribute("overdueBooks", overdueBooks.size());
        model.addAttribute("recentBooks", books.stream().limit(5).toList());
        
        return "index";
    }
    
    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", libraryService.getAllBooks());
        return "books/list";
    }
    
    @GetMapping("/books/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book() {
            @Override
            public String getBookType() { return ""; }
            @Override
            public String getDisplayInfo() { return ""; }
        });
        return "books/add";
    }
    
    @PostMapping("/books/add")
    public String addBook(@RequestParam String bookType,
                         @RequestParam String title,
                         @RequestParam String author,
                         @RequestParam String genre,
                         @RequestParam String publicationDate,
                         @RequestParam(required = false) String isbn,
                         @RequestParam(required = false) Integer pageCount,
                         @RequestParam(required = false) String fileFormat,
                         @RequestParam(required = false) Double fileSizeMB,
                         @RequestParam(required = false) Integer durationMinutes,
                         @RequestParam(required = false) String narrator,
                         RedirectAttributes redirectAttributes) {
        
        try {
            Book book = bookFactoryProvider.getFactory(bookType).createBook();
            book.setTitle(title);
            book.setAuthor(author);
            book.setGenre(genre);
            book.setPublicationDate(LocalDate.parse(publicationDate));
            
            // Set specific properties based on book type
            if (book instanceof PhysicalBook) {
                PhysicalBook physicalBook = (PhysicalBook) book;
                physicalBook.setIsbn(isbn);
                physicalBook.setPageCount(pageCount != null ? pageCount : 0);
            } else if (book instanceof EBook) {
                EBook eBook = (EBook) book;
                eBook.setFileFormat(fileFormat);
                eBook.setFileSizeMB(fileSizeMB != null ? fileSizeMB : 0.0);
            } else if (book instanceof AudioBook) {
                AudioBook audioBook = (AudioBook) book;
                audioBook.setDurationMinutes(durationMinutes != null ? durationMinutes : 0);
                audioBook.setNarrator(narrator);
            }
            
            libraryService.addBook(book);
            redirectAttributes.addFlashAttribute("success", "Thêm sách thành công!");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm sách: " + e.getMessage());
        }
        
        return "redirect:/books";
    }
    
    @GetMapping("/search")
    public String showSearchForm(Model model) {
        return "books/search";
    }
    
    @PostMapping("/search")
    public String searchBooks(@RequestParam String keyword,
                             @RequestParam String searchType,
                             Model model) {
        
        SearchStrategy strategy;
        switch (searchType) {
            case "title":
                strategy = searchByTitleStrategy;
                break;
            case "author":
                strategy = searchByAuthorStrategy;
                break;
            case "genre":
                strategy = searchByGenreStrategy;
                break;
            default:
                strategy = searchByTitleStrategy;
        }
        
        List<Book> results = libraryService.searchBooks(keyword, strategy);
        model.addAttribute("books", results);
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchType", searchType);
        model.addAttribute("strategyName", strategy.getStrategyName());
        
        return "books/search-results";
    }
    
    @PostMapping("/books/{id}/borrow")
    public String borrowBook(@PathVariable Long id,
                           @RequestParam(required = false) Integer extraDays,
                           @RequestParam(required = false) String specialFeature,
                           RedirectAttributes redirectAttributes) {
        
        try {
            Book book = libraryService.getBookById(id);
            
            // Sử dụng Decorator Pattern để thêm tính năng
            BorrowService borrowService = basicBorrowService;
            
            if (extraDays != null && extraDays > 0) {
                borrowService = new ExtendedBorrowDecorator(borrowService, extraDays);
            }
            
            if (specialFeature != null && !specialFeature.trim().isEmpty()) {
                borrowService = new SpecialEditionDecorator(borrowService, specialFeature);
            }
            
            String result = borrowService.borrowBook(book);
            double fee = borrowService.calculateFee();
            
            libraryService.borrowBook(id);
            
            redirectAttributes.addFlashAttribute("success", 
                result + (fee > 0 ? " - Phí: " + fee + " VND" : ""));
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi mượn sách: " + e.getMessage());
        }
        
        return "redirect:/books";
    }
    
    @PostMapping("/books/{id}/return")
    public String returnBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            libraryService.returnBook(id);
            redirectAttributes.addFlashAttribute("success", "Trả sách thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi trả sách: " + e.getMessage());
        }
        
        return "redirect:/books";
    }
    
    @GetMapping("/overdue")
    public String listOverdueBooks(Model model) {
        List<Book> overdueBooks = libraryService.getOverdueBooks();
        
        // Tính toán thống kê
        long totalOverdue = overdueBooks.size();
        long overdueWithin7Days = overdueBooks.stream()
                .filter(book -> book.getReturnDate() != null)
                .mapToLong(book -> ChronoUnit.DAYS.between(book.getReturnDate(), LocalDate.now()))
                .filter(days -> days <= 7)
                .count();
        long overdueOver7Days = totalOverdue - overdueWithin7Days;
        
        model.addAttribute("books", overdueBooks);
        model.addAttribute("totalOverdue", totalOverdue);
        model.addAttribute("overdueWithin7Days", overdueWithin7Days);
        model.addAttribute("overdueOver7Days", overdueOver7Days);
        
        return "books/overdue";
    }
}