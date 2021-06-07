package kr.danso.test.impl.data;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;


@Data
public final class WarPlayer {

    public static HashMap<Player,WarPlayer> players = new HashMap<>();

    private String war;
    private Location before_loc;
    private BossBar bar;

    public static void load(Player p) {
        players.put(p,new WarPlayer());
    }

}
