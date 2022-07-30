package me.tempoonline.connections;

import lombok.Getter;
import lombok.val;
import me.tempoonline.BoxTempoOnline;
import me.tempoonline.connections.model.DatabaseModel;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MySQL implements DatabaseModel {

    @Getter
    private Connection connection;

    private final FileConfiguration config = BoxTempoOnline.getInstance().getConfig();

    public MySQL() {
        openConnection();
        createTables();
    }

    public void openConnection() {
        val host = config.getString("MySQL.host");
        val user = config.getString("MySQL.user");
        val password = config.getString("MySQL.password");
        val db = config.getString("MySQL.database");
        val url = "jdbc:mysql://" + host + "/" + db + "?autoReconnect=true";

        try {
            connection = DriverManager.getConnection(url, user, password);
            Bukkit.getConsoleSender().sendMessage("§6[BoxTempoOnline] §aA conexão com §eMySQL §afoi iniciada com sucesso.");

        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§6[BoxTempoOnline] §cOcorreu um erro ao tentar fazer conexão com §eMySQL§c.");
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