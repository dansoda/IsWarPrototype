package kr.danso.test.impl;

import kr.danso.test.IsWar;
import kr.danso.test.utils.gui.AbstractGUI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;

public class SettingGui extends AbstractGUI {

    @Getter
    public static HashMap<Player,Setting> setting = new HashMap<>();

    @Getter
    public static class Setting {
        private final String name;
        private final Type type;

        public Setting(String name,Type type) {
            this.name = name;
            this.type = type;
        }

        public enum Type {
            ENTER,BLOCK_LOC
        }
    }

    private IsWar plugin = IsWar.getPlugin();

    private final Player player;
    private String name;

    protected SettingGui(Player player) {
        super("settingGui");
        this.player = player;
    }

    static {
        SettingGui settingGui = new SettingGui(null);
        settingGui.resetData();
        settingGui.setRow(2);
        settingGui.setTitle("§8[설정]");

        settingGui.addFillItem(0,new ItemStack(Material.DIAMOND));
        settingGui.addFillItem(1,new ItemStack(Material.OBSIDIAN));
    }

    @Override
    protected void onPlayerClick(InventoryClickEvent e) {
        e.setCancelled(true);
        if (e.getRawSlot() == 0) {
            player.sendMessage("블럭 부시세요");
            SettingGui.getSetting().put(player,new Setting(name, Setting.Type.ENTER));
            player.closeInventory();
        }
        if (e.getRawSlot() == 1) {
            player.sendMessage("옵시디언 블럭 부시세요");
            SettingGui.getSetting().put(player,new Setting(name, Setting.Type.BLOCK_LOC));
            player.closeInventory();
        }
    }

    @Override
    protected void onPlayerClose(InventoryCloseEvent e) {

    }

    public void openGui(String name) {
        this.name = name;
        Inventory inventory;
        inventory = getInventory();
        if (player == null)
            return;
        player.openInventory(inventory);
    }

    public static void open(Player player,String name) {
        new SettingGui(player).openGui(name);
    }
}
