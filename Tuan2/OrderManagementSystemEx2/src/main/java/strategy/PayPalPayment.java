package strategy;

public class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("💰 Thanh toán " + amount + " VND qua PayPal");
        System.out.println("   Email: " + email);
        System.out.println("   Chuyển hướng đến PayPal...");
        System.out.println("   Giao dịch thành công!");
    }

    @Override
    public String getPaymentMethod() {
        return "PayPal";
    }
}