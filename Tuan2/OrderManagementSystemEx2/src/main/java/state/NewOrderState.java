package state;

import org.example.Order;

public class NewOrderState implements OrderState {

    @Override
    public void process(Order order) {
        System.out.println("✓ Kiểm tra thông tin đơn hàng #" + order.getOrderId());
        System.out.println("  Tổng tiền: " + order.getTotalAmount() + " VND");
        System.out.println("  Chuyển sang trạng thái Đang xử lý...");
        order.setState(new ProcessingOrderState());
    }

    @Override
    public void ship(Order order) {
        System.out.println("✗ Không thể vận chuyển đơn hàng ở trạng thái Mới tạo!");
        System.out.println("  Vui lòng xử lý đơn hàng trước.");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("✗ Không thể giao hàng cho đơn hàng ở trạng thái Mới tạo!");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("✓ Hủy đơn hàng #" + order.getOrderId());
        System.out.println("  Hoàn tiền: " + order.getTotalAmount() + " VND");
        order.setState(new CancelledOrderState());
    }

    @Override
    public void printStatus() {
        System.out.println("Trạng thái hiện tại: MỚI TẠO");
    }
}
