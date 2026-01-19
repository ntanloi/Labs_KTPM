package strategy;

public class CashPayment implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("💵 Thanh toán " + amount + " VND bằng tiền mặt");
        System.out.println("   Khách hàng thanh toán khi nhận hàng (COD)");
        System.out.println("   Giao dịch sẽ được xác nhận khi giao hàng!");
    }

    @Override
    public String getPaymentMethod() {
        return "Cash on Delivery";
    }
}