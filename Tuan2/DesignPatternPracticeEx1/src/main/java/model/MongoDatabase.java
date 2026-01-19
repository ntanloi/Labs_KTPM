package model;

public class MongoDatabase implements Database {
    @Override
    public void connect() {
        System.out.println("✓ Đã kết nối MongoDB");
    }

    @Override
    public void executeQuery(String query) {
        System.out.println("MongoDB executing: " + query);
    }

    @Override
    public void disconnect() {
        System.out.println("✗ Ngắt kết nối MongoDB");
    }
}