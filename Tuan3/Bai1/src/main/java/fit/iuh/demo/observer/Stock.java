package fit.iuh.demo.observer;

/**
 * Concrete Subject - Cổ phiếu
 * Khi giá thay đổi sẽ thông báo cho các nhà đầu tư
 */
public class Stock extends Subject {
    private String symbol;
    private double price;
    
    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double newPrice) {
        double oldPrice = this.price;
        this.price = newPrice;
        
        String message = String.format("Cổ phiếu %s: Giá thay đổi từ %.2f -> %.2f (%.2f%%)", 
            symbol, oldPrice, newPrice, ((newPrice - oldPrice) / oldPrice) * 100);
        
        notifyObservers(message);
    }
}