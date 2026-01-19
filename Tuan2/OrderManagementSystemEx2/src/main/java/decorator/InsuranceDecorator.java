package decorator;

import org.example.Order;

public class InsuranceDecorator extends OrderDecorator {
    private static final double INSURANCE_COST = 15000;

    public InsuranceDecorator(Order order) {
        super(order);
    }

    @Override
    public void addFeature() {
        System.out.println("🛡️ Thêm dịch vụ: Bảo hiểm đơn hàng");
        System.out.println("   Phí: " + INSURANCE_COST + " VND");
        System.out.println("   Bảo vệ đơn hàng khỏi hư hỏng và mất mát");
    }

    @Override
    public double calculateTotal() {
        return order.getTotalAmount() + INSURANCE_COST;
    }
}