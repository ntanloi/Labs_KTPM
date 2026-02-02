package fit.iuh.demo.service;

import fit.iuh.demo.model.Book;
import fit.iuh.demo.observer.LibraryObserver;
import fit.iuh.demo.repository.BookRepository;
import fit.iuh.demo.strategy.SearchContext;
import fit.iuh.demo.strategy.SearchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private SearchContext searchContext;
    
    private List<LibraryObserver> observers = new ArrayList<>();
    
    // Singleton pattern được đảm bảo bởi Spring Framework với @Service annotation
    
    public void addObserver(LibraryObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(LibraryObserver observer) {
        observers.remove(observer);
    }
    
    private void notifyBookAdded(Book book) {
        observers.forEach(observer -> observer.onBookAdded(book));
    }
    
    private void notifyBookBorrowed(Book book) {
        observers.forEach(observer -> observer.onBookBorrowed(book));
    }
    
    private void notifyBookReturned(Book book) {
        observers.forEach(observer -> observer.onBookReturned(book));
    }
    
    private void notifyBookOverdue(Book book) {
        observers.forEach(observer -> observer.onBookOverdue(book));
    }
    
    public Book addBook(Book book) {
        Book savedBook = bookRepository.save(book);
        notifyBookAdded(savedBook);
        return savedBook;
    }
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableTrue();
    }
    
    public Book borrowBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách"));
        
        if (!book.isAvailable()) {
            throw new RuntimeException("Sách đã được mượn");
        }
        
        book.setAvailable(false);
        book.setBorrowedDate(LocalDate.now());
        book.setReturnDate(LocalDate.now().plusDays(14));
        
        Book savedBook = bookRepository.save(book);
        notifyBookBorrowed(savedBook);
        return savedBook;
    }
    
    public Book returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách"));
        
        if (book.isAvailable()) {
            throw new RuntimeException("Sách chưa được mượn");
        }
        
        book.setAvailable(true);
        book.setBorrowedDate(null);
        book.setReturnDate(null);
        
        Book savedBook = bookRepository.save(book);
        notifyBookReturned(savedBook);
        return savedBook;
    }
    
    public List<Book> searchBooks(String keyword, SearchStrategy strategy) {
        searchContext.setStrategy(strategy);
        List<Book> allBooks = getAllBooks();
        return searchContext.executeSearch(allBooks, keyword);
    }
    
    public List<Book> getOverdueBooks() {
        List<Book> overdueBooks = bookRepository.findOverdueBooks(LocalDate.now());
        overdueBooks.forEach(this::notifyBookOverdue);
        return overdueBooks;
    }
    
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách"));
    }
}