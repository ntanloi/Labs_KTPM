package fit.iuh.demo.decorator;

import fit.iuh.demo.model.Book;

public class SpecialEditionDecorator extends BorrowServiceDecorator {
    private String specialFeature;
    
    public SpecialEditionDecorator(BorrowService borrowService, String specialFeature) {
        super(borrowService);
        this.specialFeature = specialFeature;
    }
    
    @Override
    public String borrowBook(Book book) {
        String result = super.borrowBook(book);
        return result + " + Phiên bản đặc biệt: " + specialFeature;
    }
    
    @Override
    public double calculateFee() {
        return super.calculateFee() + 10000; // 10000 VND cho phiên bản đặc biệt
    }
    
    @Override
    public String getServiceDescription() {
        return super.getServiceDescription() + " + " + specialFeature;
    }
}