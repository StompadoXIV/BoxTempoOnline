package me.tempoonline.connections;

import lombok.Getter;
import lombok.val;
import me.tempoonline.connections.model.DatabaseModel;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite implements DatabaseModel {

    @Getter
    private Connection connection;

    public SQLite() {
        openConnection();
        createTables();
    }

    public void openConnection() {
        val file = new File("plugins/BoxTempoOnline/cache/database.db");
        val url = "jdbc:sqlite:" + file;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            Bukkit.getConsoleSender().sendMessage("§6[BoxTempoOnline] §aA conexão com §eSQLite §afoi iniciada com sucesso.");

        } catch (SQLException | ClassNotFoundException e) {
            Bukkit.getConsoleSender().sendMessage("§6[BoxTempoOnline] §aA conexão com §eSQLite §afoi fechada com sucesso.");
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection == null)
            return;

        try {
            connection.close();
            Bukkit.getConsoleSender().sendMessage("§6[BoxTempoOnline] §aA conexão com §eMySQL §afoi fechada com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeQuery(String query) {
        try {
            val preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeUpdate(String query, Object... params) {
        try (val ps = connection.prepareStatement(query)) {
            if (params != null && params.length > 0)
                for (int index = 0; index < params.length; index++)
                    ps.setObject(index + 1, params[index]);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTables() {
        executeQuery("CREATE TABLE IF NOT EXISTS `boxtempoonline_time` (player VARCHAR(24) NOT NULL, time LONG NOT NULL)");
    }
}