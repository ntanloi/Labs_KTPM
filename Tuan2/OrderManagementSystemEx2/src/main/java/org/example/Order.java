package org.example;
import state.OrderState;
import state.NewOrderState;

public class Order {
    private String orderId;
    private double totalAmount;
    private OrderState currentState;

    public Order(String orderId, double totalAmount) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.currentState = new NewOrderState(); // Trạng thái ban đầu
    }

    public void setState(OrderState state) {
        this.currentState = state;
    }

    public OrderState getState() {
        return currentState;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    // Các phương thức ủy quyền cho State
    public void process() {
        currentState.process(this);
    }

    public void ship() {
        currentState.ship(this);
    }

    public void deliver() {
        currentState.deliver(this);
    }

    public void cancel() {
        currentState.cancel(this);
    }

    public void printStatus() {
        currentState.printStatus();
    }
}