package decorator;

import org.example.Order;

public class GiftWrapDecorator extends OrderDecorator {
    private static final double GIFT_WRAP_COST = 20000;

    public GiftWrapDecorator(Order order) {
        super(order);
    }

    @Override
    public void addFeature() {
        System.out.println("🎁 Thêm dịch vụ: Gói quà");
        System.out.println("   Phí: " + GIFT_WRAP_COST + " VND");
    }

    @Override
    public double calculateTotal() {
        return order.getTotalAmount() + GIFT_WRAP_COST;
    }
}
