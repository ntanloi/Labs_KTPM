package fit.iuh.demo.decorator;

import fit.iuh.demo.model.Book;

import java.time.LocalDate;

public class ExtendedBorrowDecorator extends BorrowServiceDecorator {
    private int extraDays;
    
    public ExtendedBorrowDecorator(BorrowService borrowService, int extraDays) {
        super(borrowService);
        this.extraDays = extraDays;
    }
    
    @Override
    public String borrowBook(Book book) {
        String result = super.borrowBook(book);
        // Gia hạn thêm thời gian
        book.setReturnDate(book.getReturnDate().plusDays(extraDays));
        return result + " + Gia hạn " + extraDays + " ngày";
    }
    
    @Override
    public double calculateFee() {
        return super.calculateFee() + (extraDays * 5000); // 5000 VND/ngày
    }
    
    @Override
    public String getServiceDescription() {
        return super.getServiceDescription() + " + Gia hạn " + extraDays + " ngày";
    }
}