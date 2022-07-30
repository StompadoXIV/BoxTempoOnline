package me.tempoonline.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Sound;

import java.util.List;

@AllArgsConstructor
@Getter
public class Rewards {

    private long time;
    private String title, subtitle;
    private boolean isAtiveTitle;
    private boolean isSound;
    private Sound sound;
    private List<String> commands;
    private String message;

}