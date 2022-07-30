package me.tempoonline.inventories;

import me.tempoonline.BoxTempoOnline;
import me.tempoonline.ConfigManager;
import me.tempoonline.utils.BoxUtils;
import me.tempoonline.utils.ItemBuilder;
import me.tempoonline.utils.TimeFormatter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import lombok.val;

public class TimerTOP extends BukkitRunnable {

    private static List<ItemStack> heads = BoxTempoOnline.getInstance().getHeads();
    private static ConfigManager settings = BoxTempoOnline.getInstance().getSettings();

    public void run() {

        if (!heads.isEmpty())
            heads.clear();

        try (val stm = BoxTempoOnline.getInstance().getDatabaseModel().getConnection().prepareStatement("SELECT * FROM boxtempoonline_time ORDER BY time DESC LIMIT 10")) {

            val rs = stm.executeQuery();
            val pos = new AtomicInteger(1);

            while (rs.next()) {

                val playerName = rs.getString("player");
                val tlPlayer = settings.getTitlePlayer().replace("{jogador}", playerName.toUpperCase()).replace("{posicao}", "" + pos.get());

                val time = TimeFormatter.format(BoxTempoOnline.getInstance().getTimeAPI().getTime(playerName));

                List<String> lore = settings.getLorePlayer();
                lore = lore.stream().map(l -> l.replace("{jogador}", playerName).replace("{tempo}", time).replace("{posicao}", "" + pos.get())).collect(Collectors.toList());

                val head = new ItemBuilder(BoxUtils.getPlayerHead(playerName)).setName(tlPlayer).setLore(lore).build();

                heads.add(head);
                pos.getAndIncrement();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}