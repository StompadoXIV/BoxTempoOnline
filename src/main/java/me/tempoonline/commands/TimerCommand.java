package me.tempoonline.commands;

import lombok.val;
import me.tempoonline.BoxTempoOnline;
import me.tempoonline.ConfigManager;
import me.tempoonline.connections.api.TimeAPI;
import me.tempoonline.utils.BoxUtils;
import me.tempoonline.utils.Scroller;
import me.tempoonline.utils.TimeFormatter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class TimerCommand implements CommandExecutor {

    private final TimeAPI timeAPI = BoxTempoOnline.getInstance().getTimeAPI();
    private final ConfigManager settings = BoxTempoOnline.getInstance().getSettings();

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] a) {

        if (s instanceof ConsoleCommandSender) {
            s.sendMessage("§cO console não executa esse comando.");
            return true;
        }

        val p = (Player) s;
        if (a.length > 0) {
            if (a[0].equalsIgnoreCase("ver")) {

                if (a.length < 2) {
                    BoxUtils.sendMessage(p, "§cUtilize o comando da seguinte forma: /tempo ver (jogador).");
                    return true;
                }

                val t = Bukkit.getPlayerExact(a[1]);
                if (t == null) {
                    BoxUtils.sendMessage(p, settings.getPlayerNotFound());
                    return true;
                }

                BoxUtils.sendMessage(p, settings.getPlayerTime().replace("{jogador}", t.getName()).replace("{tempo}", TimeFormatter.format(timeAPI.getTime(t.getName()))));

            } else if (a[0].equalsIgnoreCase("top")) {

                val scroller = new Scroller.ScrollerBuilder().withName(settings.getNameTOP()).withItems(BoxTempoOnline.getInstance().getHeads()).build();
                scroller.open(p);

                BoxUtils.playSound(p, Sound.CHEST_OPEN);
            } else {

                p.sendMessage("");
                p.sendMessage("§6§lBoxTempoOnline §8» §7Comandos:");
                p.sendMessage("");
                p.sendMessage("§6/tempo §7- §fVeja seu tempo online no servidor");
                p.sendMessage("§6/tempo ver (jogador) §7- §fVeja o tempo online de um jogador");
                p.sendMessage("§6/tempo top §7- §fVeja os jogadores com mais tempo jogados");
                p.sendMessage("");

            }

        } else {
            BoxUtils.sendMessage(p, settings.getImTime().replace("{tempo}", TimeFormatter.format(timeAPI.getTime(p.getName()))));
        }
        return false;
    }
}