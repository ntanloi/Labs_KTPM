package decorator;

import org.example.Order;

public class ExpressShippingDecorator extends OrderDecorator {
    private static final double EXPRESS_COST = 50000;

    public ExpressShippingDecorator(Order order) {
        super(order);
    }

    @Override
    public void addFeature() {
        System.out.println("🚀 Thêm dịch vụ: Vận chuyển nhanh");
        System.out.println("   Phí: " + EXPRESS_COST + " VND");
        System.out.println("   Thời gian: Giao trong 24h");
    }

    @Override
    public double calculateTotal() {
        return order.getTotalAmount() + EXPRESS_COST;
    }
}
