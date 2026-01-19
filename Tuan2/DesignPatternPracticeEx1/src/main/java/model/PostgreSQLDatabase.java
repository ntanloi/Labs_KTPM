package model;

public class PostgreSQLDatabase implements Database {
    @Override
    public void connect() {
        System.out.println("✓ Đã kết nối PostgreSQL Database");
    }

    @Override
    public void executeQuery(String query) {
        System.out.println("PostgreSQL executing: " + query);
    }

    @Override
    public void disconnect() {
        System.out.println("✗ Ngắt kết nối PostgreSQL");
    }
}