package me.tempoonline.connections.model;

import java.sql.Connection;

public interface DatabaseModel {

    void openConnection();

    void closeConnection();

    void executeQuery(String query);

    void executeUpdate(String query, Object... values);

    void createTables();

    Connection getConnection();

}
