package decorator;

import org.example.Order;

public abstract class OrderDecorator {
    protected Order order;

    public OrderDecorator(Order order) {
        this.order = order;
    }

    public abstract void addFeature();
    public abstract double calculateTotal();
}