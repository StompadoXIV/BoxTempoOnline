package me.tempoonline;

import lombok.Getter;
import lombok.val;

import java.util.List;

@Getter
public class ConfigManager {

    private String playerNotFound;
    private String imTime;
    private String playerTime;

    private String nameTOP;
    private int timeTOP;
    private String titlePlayer;
    private List<String> lorePlayer;

    public void loadConfig() {

        val config = BoxTempoOnline.getInstance().getConfig();

        playerNotFound = config.getString("Mensagens.JogadorNaoEncontrado");
        imTime = config.getString("Mensagens.SeuTempo");
        playerTime = config.getString("Mensagens.TempoDoJogador");

        nameTOP = config.getString("Inventarios.TempoTOP.Nome").replace("&", "§");
        timeTOP = config.getInt("Inventarios.TempoTOP.Tempo");
        titlePlayer = config.getString("Inventarios.TempoTOP.Titulo");
        lorePlayer = config.getStringList("Inventarios.TempoTOP.Lore");

    }
}