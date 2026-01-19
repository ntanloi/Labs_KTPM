package factory.method;

import model.*;

public class DatabaseFactory {

    // Factory Method
    public static Database createDatabase(String type) {
        if (type == null) {
            return null;
        }

        switch (type.toUpperCase()) {
            case "MYSQL":
                return new MySQLDatabase();
            case "POSTGRESQL":
                return new PostgreSQLDatabase();
            case "MONGODB":
                return new MongoDatabase();
            default:
                throw new IllegalArgumentException("Loại database không hợp lệ: " + type);
        }
    }
}