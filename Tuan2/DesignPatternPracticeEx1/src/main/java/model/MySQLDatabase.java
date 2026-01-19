package model;

public class MySQLDatabase implements Database {
    @Override
    public void connect() {
        System.out.println("✓ Đã kết nối MySQL Database");
    }

    @Override
    public void executeQuery(String query) {
        System.out.println("MySQL executing: " + query);
    }

    @Override
    public void disconnect() {
        System.out.println("✗ Ngắt kết nối MySQL");
    }
}