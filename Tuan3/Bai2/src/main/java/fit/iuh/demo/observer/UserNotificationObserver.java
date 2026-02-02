package fit.iuh.demo.observer;

import fit.iuh.demo.model.Book;
import org.springframework.stereotype.Component;

@Component
public class UserNotificationObserver implements LibraryObserver {
    
    @Override
    public void onBookAdded(Book book) {
        System.out.println("🔔 [Thông báo người dùng] Có sách mới: " + book.getTitle() + " - " + book.getAuthor());
    }
    
    @Override
    public void onBookBorrowed(Book book) {
        System.out.println("📱 [Thông báo người dùng] Bạn đã mượn thành công: " + book.getTitle());
    }
    
    @Override
    public void onBookReturned(Book book) {
        System.out.println("📱 [Thông báo người dùng] Cảm ơn bạn đã trả sách: " + book.getTitle());
    }
    
    @Override
    public void onBookOverdue(Book book) {
        System.out.println("📱 [Thông báo người dùng] Nhắc nhở: Sách quá hạn trả - " + book.getTitle());
    }
    
    @Override
    public String getObserverName() {
        return "Hệ thống thông báo người dùng";
    }
}