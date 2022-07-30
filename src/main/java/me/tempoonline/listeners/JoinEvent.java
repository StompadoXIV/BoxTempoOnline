package me.tempoonline.listeners;

import lombok.val;
import me.tempoonline.BoxTempoOnline;
import me.tempoonline.connections.api.TimeAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    private final TimeAPI timeAPI = BoxTempoOnline.getInstance().getTimeAPI();

    public JoinEvent(BoxTempoOnline main) {
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    void joinEvent(PlayerJoinEvent e) {
        val p = e.getPlayer();

        if (!timeAPI.hasAccount(p))
            timeAPI.createAccount(p);
    }
}