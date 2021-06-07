package kr.danso.test.impl.data;

import kr.danso.test.utils.LocationUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@SerializableAs("CookData")
public class WarData implements ConfigurationSerializable {
    @Getter
    private static HashMap<Location,String> wars_loc = new HashMap<>();
    @Getter
    private static HashMap<String,WarData> wars = new HashMap<>();

    private Location war_enter;
    private Location war_spawn;
    private Location block_loc;
    private int block_heath;

    private WarData() {

    }

    public WarData(String name, Player player) {
        this.war_enter = new Location(Bukkit.getWorld("world"),10,10,10);
        this.war_spawn = player.getLocation().clone();
        this.block_loc = new Location(Bukkit.getWorld("world"),10,10,10);
        this.block_heath = 10;
        WarData.wars.put(name,this);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("war_enter", LocationUtils.getLocation(war_enter));
        map.put("war_spawn", LocationUtils.getLocation(war_spawn));
        map.put("block_loc", LocationUtils.getLocation(block_loc));
        map.put("block_heath", this.block_heath);
        return map;
    }

    public void load() {

    }

    public void save() {

    }
}
