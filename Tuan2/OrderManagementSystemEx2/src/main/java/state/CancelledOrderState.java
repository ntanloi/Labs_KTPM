package state;

import org.example.Order;

public class CancelledOrderState implements OrderState {

    @Override
    public void process(Order order) {
        System.out.println("✗ Đơn hàng đã bị hủy, không thể xử lý!");
    }

    @Override
    public void ship(Order order) {
        System.out.println("✗ Đơn hàng đã bị hủy, không thể vận chuyển!");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("✗ Đơn hàng đã bị hủy, không thể giao hàng!");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("✓ Đơn hàng đã được hủy trước đó.");
        System.out.println("  Tiền đã được hoàn lại.");
    }

    @Override
    public void printStatus() {
        System.out.println("Trạng thái hiện tại: ĐÃ HỦY");
    }
}