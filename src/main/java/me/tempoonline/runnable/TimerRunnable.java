package me.tempoonline.runnable;

import lombok.val;
import me.tempoonline.BoxTempoOnline;
import me.tempoonline.connections.api.TimeAPI;
import me.tempoonline.dao.RewardsDAO;
import me.tempoonline.methods.RewardsMethods;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerRunnable extends BukkitRunnable {

    private final TimeAPI timeAPI = BoxTempoOnline.getInstance().getTimeAPI();

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            timeAPI.addTime(player, 60000);

            val reward = RewardsDAO.getRewardTime(player);
            if (reward != null) {
                RewardsMethods.rewardsPlayer(player, reward);
            }
        });
    }
}