package fit.iuh.demo.decorator;

import fit.iuh.demo.model.Book;

public abstract class BorrowServiceDecorator implements BorrowService {
    protected BorrowService borrowService;
    
    public BorrowServiceDecorator(BorrowService borrowService) {
        this.borrowService = borrowService;
    }
    
    @Override
    public String borrowBook(Book book) {
        return borrowService.borrowBook(book);
    }
    
    @Override
    public double calculateFee() {
        return borrowService.calculateFee();
    }
    
    @Override
    public String getServiceDescription() {
        return borrowService.getServiceDescription();
    }
}