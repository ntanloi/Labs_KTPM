package fit.iuh.demo.observer;

import fit.iuh.demo.model.Book;
import org.springframework.stereotype.Component;

@Component
public class LibrarianObserver implements LibraryObserver {
    
    @Override
    public void onBookAdded(Book book) {
        System.out.println("📚 [Thủ thư] Sách mới đã được thêm: " + book.getTitle());
    }
    
    @Override
    public void onBookBorrowed(Book book) {
        System.out.println("📖 [Thủ thư] Sách đã được mượn: " + book.getTitle());
    }
    
    @Override
    public void onBookReturned(Book book) {
        System.out.println("✅ [Thủ thư] Sách đã được trả: " + book.getTitle());
    }
    
    @Override
    public void onBookOverdue(Book book) {
        System.out.println("⚠️ [Thủ thư] Sách quá hạn: " + book.getTitle());
    }
    
    @Override
    public String getObserverName() {
        return "Thủ thư";
    }
}