package fit.iuh.demo.observer;

/**
 * Concrete Observer - Nhà đầu tư
 * Nhận thông báo khi giá cổ phiếu thay đổi
 */
public class Investor implements Observer {
    private String name;
    
    public Investor(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public void update(String message) {
        System.out.println("📈 Nhà đầu tư " + name + " nhận thông báo: " + message);
    }
}