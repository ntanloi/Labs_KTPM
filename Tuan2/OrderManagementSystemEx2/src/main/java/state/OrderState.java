package state;


import org.example.Order;

public interface OrderState {
    void process(Order order);
    void ship(Order order);
    void deliver(Order order);
    void cancel(Order order);
    void printStatus();
}