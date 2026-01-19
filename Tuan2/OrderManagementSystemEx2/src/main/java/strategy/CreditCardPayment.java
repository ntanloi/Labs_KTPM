package strategy;

public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardHolder;

    public CreditCardPayment(String cardNumber, String cardHolder) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    @Override
    public void pay(double amount) {
        System.out.println("💳 Thanh toán " + amount + " VND qua thẻ tín dụng");
        System.out.println("   Chủ thẻ: " + cardHolder);
        System.out.println("   Số thẻ: **** **** **** " + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("   Giao dịch thành công!");
    }

    @Override
    public String getPaymentMethod() {
        return "Credit Card";
    }
}