package me.tempoonline.dao;

import lombok.Getter;
import me.tempoonline.BoxTempoOnline;
import me.tempoonline.connections.api.TimeAPI;
import me.tempoonline.model.Rewards;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RewardsDAO {

    private static TimeAPI timeAPI = BoxTempoOnline.getInstance().getTimeAPI();

    @Getter
    private static List<Rewards> rewardsList = new ArrayList<>();

    public static Rewards getRewardTime(Player p) {
        return rewardsList.stream().filter(rewards -> rewards.getTime() == timeAPI.getTime(p.getName())).findFirst().orElse(null);
    }
}