package model;

public interface Database {
    void connect();
    void executeQuery(String query);
    void disconnect();
}