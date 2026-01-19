package org.example;

import decorator.ExpressShippingDecorator;
import decorator.GiftWrapDecorator;
import decorator.InsuranceDecorator;
import strategy.CashPayment;
import strategy.CreditCardPayment;
import strategy.PayPalPayment;
import strategy.PaymentContext;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════");
        System.out.println("   HỆ THỐNG QUẢN LÝ ĐƠN HÀNG");
        System.out.println("   Áp dụng: State, Strategy, Decorator Pattern");
        System.out.println("════════════════════════════════════════════════════\n");

        // Tạo đơn hàng
        Order order1 = new Order("ORD001", 500000);

        System.out.println(">>> BƯỚC 1: TẠO ĐƠN HÀNG MỚI");
        System.out.println("Đơn hàng #" + order1.getOrderId());
        System.out.println("Giá gốc: " + order1.getTotalAmount() + " VND");
        order1.printStatus();
        System.out.println();

        // ===== DECORATOR PATTERN: Thêm các tính năng =====
        System.out.println(">>> BƯỚC 2: DECORATOR PATTERN - THÊM DỊCH VỤ");

        GiftWrapDecorator giftWrap = new GiftWrapDecorator(order1);
        giftWrap.addFeature();

        ExpressShippingDecorator express = new ExpressShippingDecorator(order1);
        express.addFeature();

        InsuranceDecorator insurance = new InsuranceDecorator(order1);
        insurance.addFeature();

        double finalTotal = order1.getTotalAmount() + 20000 + 50000 + 15000;
        System.out.println("\n✓ Tổng tiền sau khi thêm dịch vụ: " + finalTotal + " VND");
        System.out.println();

        // ===== STRATEGY PATTERN: Chọn phương thức thanh toán =====
        System.out.println(">>> BƯỚC 3: STRATEGY PATTERN - THANH TOÁN");
        PaymentContext paymentContext = new PaymentContext();

        // Thử các phương thức thanh toán khác nhau
        System.out.println("\n--- Phương thức 1: Thẻ tín dụng ---");
        paymentContext.setPaymentStrategy(new CreditCardPayment("1234567890123456", "Nguyen Van A"));
        paymentContext.executePayment(finalTotal);

        System.out.println("\n--- Phương thức 2: PayPal ---");
        paymentContext.setPaymentStrategy(new PayPalPayment("nguyenvana@email.com"));
        paymentContext.executePayment(finalTotal);

        System.out.println("\n--- Phương thức 3: Tiền mặt ---");
        paymentContext.setPaymentStrategy(new CashPayment());
        paymentContext.executePayment(finalTotal);
        System.out.println();

        // ===== STATE PATTERN: Xử lý trạng thái đơn hàng =====
        System.out.println(">>> BƯỚC 4: STATE PATTERN - XỬ LÝ ĐƠN HÀNG");

        System.out.println("\n--- Thử xử lý từ trạng thái Mới tạo ---");
        order1.printStatus();
        order1.process();
        System.out.println();

        System.out.println("--- Thử giao hàng khi Đang xử lý ---");
        order1.printStatus();
        order1.deliver(); // Sẽ báo lỗi vì chưa ship
        System.out.println();

        System.out.println("--- Vận chuyển đơn hàng ---");
        order1.ship();
        System.out.println();

        System.out.println("--- Giao hàng thành công ---");
        order1.printStatus();
        order1.deliver();
        System.out.println();

        System.out.println("--- Thử hủy đơn hàng đã giao ---");
        order1.cancel(); // Sẽ báo lỗi
        System.out.println();

        // ===== TEST CASE 2: Hủy đơn hàng =====
        System.out.println("\n════════════════════════════════════════════════════");
        System.out.println(">>> TEST CASE 2: HỦY ĐƠN HÀNG");
        System.out.println("════════════════════════════════════════════════════\n");

        Order order2 = new Order("ORD002", 300000);
        System.out.println("Tạo đơn hàng #" + order2.getOrderId());
        order2.printStatus();
        System.out.println();

        System.out.println("--- Xử lý đơn hàng ---");
        order2.process();
        System.out.println();

        System.out.println("--- Hủy đơn hàng ở trạng thái Đang xử lý ---");
        order2.cancel();
        System.out.println();

        System.out.println("--- Thử xử lý đơn hàng đã hủy ---");
        order2.process(); // Sẽ báo lỗi
        System.out.println();
    }
}