package state;

import org.example.Order;

public class DeliveredOrderState implements OrderState {

    @Override
    public void process(Order order) {
        System.out.println("✗ Đơn hàng đã được giao, không thể xử lý lại!");
    }

    @Override
    public void ship(Order order) {
        System.out.println("✗ Đơn hàng đã được giao, không thể vận chuyển lại!");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("✓ Cập nhật trạng thái đơn hàng #" + order.getOrderId());
        System.out.println("  Đơn hàng đã được giao thành công!");
        System.out.println("  Cảm ơn quý khách đã mua hàng.");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("✗ Không thể hủy đơn hàng đã được giao!");
        System.out.println("  Vui lòng liên hệ bộ phận hỗ trợ để đổi/trả hàng.");
    }

    @Override
    public void printStatus() {
        System.out.println("Trạng thái hiện tại: ĐÃ GIAO");
    }
}