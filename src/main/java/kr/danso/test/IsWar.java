package kr.danso.test;

import kr.danso.test.impl.data.WarData;
import kr.danso.test.impl.data.WarPlayer;
import kr.danso.test.listeners.IsWarListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public final class IsWar extends JavaPlugin {

    private static IsWar plugin;

    static {
        ConfigurationSerialization.registerClass(WarData.class);
    }

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        getCommand("isw").setExecutor(new IsWarManagerCommand());
        getServer().getPluginManager().registerEvents(new IsWarListener(), this);
        Bukkit.getServer().getOnlinePlayers().forEach(player -> WarPlayer.load(player));
    }

    @Override
    public void onDisable() {

    }

    public static IsWar getPlugin() {
        return plugin;
    }
}
