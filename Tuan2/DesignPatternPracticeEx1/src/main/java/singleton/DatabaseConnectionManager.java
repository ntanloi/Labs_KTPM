package singleton;

public class DatabaseConnectionManager {
    // Instance duy nhất (Eager Initialization)
    private static DatabaseConnectionManager instance = new DatabaseConnectionManager();

    private int connectionCount = 0;

    // Private constructor để ngăn tạo instance từ bên ngoài
    private DatabaseConnectionManager() {
        System.out.println("DatabaseConnectionManager được khởi tạo!");
    }

    // Phương thức public để lấy instance
    public static DatabaseConnectionManager getInstance() {
        return instance;
    }

    // Các phương thức nghiệp vụ
    public void connect() {
        connectionCount++;
        System.out.println("Kết nối thành công! Tổng số kết nối: " + connectionCount);
    }

    public int getConnectionCount() {
        return connectionCount;
    }
}