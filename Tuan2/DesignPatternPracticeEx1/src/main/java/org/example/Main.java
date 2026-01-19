package org.example;

import singleton.DatabaseConnectionManager;
import factory.method.DatabaseFactory;
import factory.abstractfactory.*;
import model.Database;

public class Main {
    public static void main(String[] args) {
        System.out.println("========== DEMO SINGLETON PATTERN ==========");

        // Lấy instance Singleton
        DatabaseConnectionManager manager1 = DatabaseConnectionManager.getInstance();
        DatabaseConnectionManager manager2 = DatabaseConnectionManager.getInstance();

        // Kiểm tra cùng một instance
        System.out.println("manager1 == manager2: " + (manager1 == manager2));

        manager1.connect();
        manager2.connect();
        System.out.println("Tổng kết nối: " + manager1.getConnectionCount());

        System.out.println("\n========== DEMO FACTORY METHOD PATTERN ==========");

        // Sử dụng Factory Method
        Database mysql = DatabaseFactory.createDatabase("MYSQL");
        mysql.connect();
        mysql.executeQuery("SELECT * FROM users");
        mysql.disconnect();

        System.out.println();

        Database postgres = DatabaseFactory.createDatabase("POSTGRESQL");
        postgres.connect();
        postgres.executeQuery("SELECT * FROM orders");
        postgres.disconnect();

        System.out.println("\n========== DEMO ABSTRACT FACTORY PATTERN ==========");

        // Sử dụng Abstract Factory
        DatabaseAbstractFactory mysqlFactory = FactoryProducer.getFactory("MYSQL");
        Database db1 = mysqlFactory.createDatabase();
        db1.connect();
        db1.executeQuery("INSERT INTO products VALUES (1, 'Laptop')");
        db1.disconnect();

        System.out.println();

        DatabaseAbstractFactory mongoFactory = FactoryProducer.getFactory("MONGODB");
        Database db2 = mongoFactory.createDatabase();
        db2.connect();
        db2.executeQuery("db.users.find()");
        db2.disconnect();
    }
}