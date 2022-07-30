package me.tempoonline.methods;

import me.tempoonline.model.Rewards;
import me.tempoonline.utils.BoxUtils;
import org.bukkit.entity.Player;

public class RewardsMethods {

    public static void rewardsPlayer(Player player, Rewards rewards) {
        BoxUtils.runCommandList(player, rewards.getCommands());
        BoxUtils.sendMessage(player, rewards.getMessage());
        if (rewards.isSound())
            BoxUtils.playSound(player, rewards.getSound());

        if (rewards.isAtiveTitle())
            BoxUtils.sendTitle(player, rewards.getTitle(), rewards.getSubtitle());
    }
}