package strategy;

public class PaymentContext {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void executePayment(double amount) {
        if (paymentStrategy == null) {
            System.out.println("✗ Chưa chọn phương thức thanh toán!");
            return;
        }
        paymentStrategy.pay(amount);
    }

    public String getCurrentPaymentMethod() {
        if (paymentStrategy == null) {
            return "Chưa chọn";
        }
        return paymentStrategy.getPaymentMethod();
    }
}