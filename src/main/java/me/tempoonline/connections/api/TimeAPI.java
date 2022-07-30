package me.tempoonline.connections.api;

import lombok.val;
import me.tempoonline.connections.model.DatabaseModel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class TimeAPI {

    private DatabaseModel databaseModel;

    public TimeAPI(DatabaseModel databaseModel) {
        this.databaseModel = databaseModel;
    }

    public void createAccount(Player p) {
        try {
            val pst = databaseModel.getConnection().prepareStatement("INSERT INTO boxtempoonline_time (player, time)VALUES(?,?)");
            pst.setString(1, p.getName());
            pst.setLong(2, 0);

            pst.execute();
            pst.close();

            Bukkit.getConsoleSender().sendMessage("§6[BoxTempoOnline] §aA conta do jogador §6" + p.getName() + " §afoi registrada com sucesso.");

        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§6[BoxTempoOnline] §cOuve um erro ao criar a contade §6" + p.getName() + " §cErro:");
            e.printStackTrace();
        }
    }

    public String getValue(String table, String column, Object value, int columnIndex) {
        String valueFinally = null;
        try {
            val preparedStatement = databaseModel.getConnection().prepareStatement("SELECT * FROM " + table + " WHERE " + column + "='" + value + "'");
            val resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                valueFinally = resultSet.getString(columnIndex);

        } catch (Exception ignored) {
        }
        return valueFinally;
    }

    public boolean hasAccount(Player p) {
        return getValue("boxtempoonline_time", "player", p.getName(), 1) != null;
    }

    public long getTime(String playerName) {
        return Long.parseLong(getValue("boxtempoonline_time", "player", playerName, 2));
    }

    public void addTime(Player p, long value) {
        databaseModel.executeUpdate("UPDATE `boxtempoonline_time` SET time = ? WHERE player='" + p.getName() + "'", getTime(p.getName()) + value);
    }
}