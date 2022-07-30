package me.tempoonline;

import lombok.Getter;
import lombok.val;
import me.tempoonline.commands.TimerCommand;
import me.tempoonline.connections.MySQL;
import me.tempoonline.connections.SQLite;
import me.tempoonline.connections.api.TimeAPI;
import me.tempoonline.connections.model.DatabaseModel;
import me.tempoonline.dao.RewardsDAO;
import me.tempoonline.inventories.TimerTOP;
import me.tempoonline.listeners.JoinEvent;
import me.tempoonline.model.Rewards;
import me.tempoonline.runnable.TimerRunnable;
import me.tempoonline.utils.DateManager;
import me.tempoonline.utils.TimeFormatter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BoxTempoOnline extends JavaPlugin {

    @Getter
    private static BoxTempoOnline Instance;
    private ConfigManager settings;

    private List<ItemStack> heads = new ArrayList<>();

    private DatabaseModel databaseModel;
    private TimeAPI timeAPI;

    public void onEnable() {
        Instance = this;
        registerYaml();
        setupDatabase();
        registerRewards();
        registerCommands();
        registerEvents();
        sendMessage();

        new TimerRunnable().runTaskTimerAsynchronously(this, 20L * 60, 20L * 60);
        new TimerTOP().runTaskTimerAsynchronously(this, 20L, 20L * 60 * settings.getTimeTOP());
    }

    public void onDisable() {
        databaseModel.closeConnection();
    }

    private void registerCommands() {
        getCommand("tempo").setExecutor(new TimerCommand());
    }

    private void registerEvents() {
        new JoinEvent(this);
    }

    private void registerYaml() {
        settings = new ConfigManager();
        saveDefaultConfig();
        settings.loadConfig();
        if (!getConfig().getBoolean("MySQL.ativar")) {
            DateManager.createFolder("cache");
        }
        DateManager.createConfig("recompensas");
    }

    private void sendMessage() {
        Bukkit.getConsoleSender().sendMessage("§6[BoxTempoOnline] §fCriado por §b[Stompado]");
        Bukkit.getConsoleSender().sendMessage("§b[Discord] §fhttps://discord.gg/Z6PbQgdweB");
        Bukkit.getConsoleSender().sendMessage("§6[BoxTempoOnline] §aO plugin §6BoxTempoOnline §afoi iniciado com sucesso.");
    }

    private void setupDatabase() {
        databaseModel = getConfig().getBoolean("MySQL.ativar") ? new MySQL() : new SQLite();
        timeAPI = new TimeAPI(databaseModel);
    }

    private void registerRewards() {

        for (val path : DateManager.getConfig("recompensas").getConfigurationSection("Recompensas").getKeys(false)) {

            val key = DateManager.getConfig("recompensas").getConfigurationSection("Recompensas." + path);

            val time = key.getLong("Tempo");

            val isTitle = key.getBoolean("Titulos.Ativar");
            val title = key.getString("Titulos.Titulo");
            val subTitle = key.getString("Titulos.SubTitulo");

            val isSound = key.getBoolean("Sons.Ativar");
            val sound = Sound.valueOf(key.getString("Sons.Som"));

            val commands = key.getStringList("Comandos");

            val message = key.getString("Mensagem").replace("{tempo}", TimeFormatter.format(time * 60000));

            val rewards = new Rewards(time * 60000, title, subTitle, isTitle, isSound, sound, commands, message);
            RewardsDAO.getRewardsList().add(rewards);

        }
    }
}