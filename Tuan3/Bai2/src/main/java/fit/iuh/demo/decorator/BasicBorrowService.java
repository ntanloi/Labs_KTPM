package fit.iuh.demo.decorator;

import fit.iuh.demo.model.Book;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BasicBorrowService implements BorrowService {
    
    @Override
    public String borrowBook(Book book) {
        book.setAvailable(false);
        book.setBorrowedDate(LocalDate.now());
        book.setReturnDate(LocalDate.now().plusDays(14)); // 2 tuần
        return "Mượn sách cơ bản: " + book.getTitle();
    }
    
    @Override
    public double calculateFee() {
        return 0.0; // Miễn phí
    }
    
    @Override
    public String getServiceDescription() {
        return "Mượn sách cơ bản (14 ngày)";
    }
}