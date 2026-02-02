package fit.iuh.demo.decorator;

import fit.iuh.demo.model.Book;

public interface BorrowService {
    String borrowBook(Book book);
    double calculateFee();
    String getServiceDescription();
}