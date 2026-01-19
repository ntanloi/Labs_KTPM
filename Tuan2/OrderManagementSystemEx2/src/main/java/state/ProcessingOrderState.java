package state;

import org.example.Order;

public class ProcessingOrderState implements OrderState {

    @Override
    public void process(Order order) {
        System.out.println("✗ Đơn hàng đã đang trong quá trình xử lý!");
    }

    @Override
    public void ship(Order order) {
        System.out.println("✓ Đóng gói đơn hàng #" + order.getOrderId());
        System.out.println("  Vận chuyển đơn hàng...");
        System.out.println("  Chuyển sang trạng thái Đã giao...");
        order.setState(new DeliveredOrderState());
    }

    @Override
    public void deliver(Order order) {
        System.out.println("✗ Đơn hàng chưa được vận chuyển!");
        System.out.println("  Vui lòng gọi ship() trước.");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("✓ Hủy đơn hàng #" + order.getOrderId() + " (đang xử lý)");
        System.out.println("  Hoàn tiền: " + order.getTotalAmount() + " VND");
        order.setState(new CancelledOrderState());
    }

    @Override
    public void printStatus() {
        System.out.println("Trạng thái hiện tại: ĐANG XỬ LÝ");
    }
}