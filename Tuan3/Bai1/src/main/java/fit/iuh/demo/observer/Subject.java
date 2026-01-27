package fit.iuh.demo.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject abstract class cho Observer Pattern
 */
public abstract class Subject {
    private List<Observer> observers = new ArrayList<>();
    
    public void attach(Observer observer) {
        observers.add(observer);
        System.out.println("Observer đã được đăng ký theo dõi");
    }
    
    public void detach(Observer observer) {
        observers.remove(observer);
        System.out.println("Observer đã hủy đăng ký theo dõi");
    }
    
    protected void notifyObservers(String message) {
        System.out.println("Đang thông báo cho " + observers.size() + " observers...");
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
    
    // Getter for observers list
    protected List<Observer> getObservers() {
        return observers;
    }
}