package kr.danso.test.listeners;

import kr.danso.test.IsWar;
import kr.danso.test.impl.SettingGui;
import kr.danso.test.impl.data.WarData;
import kr.danso.test.impl.data.WarPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryHolder;

public class IsWarListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        WarPlayer.load(e.getPlayer());
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            String name = WarData.getWars_loc().get(e.getClickedBlock().getLocation());
            if (name != null) {
                WarData warData = WarData.getWars().get(name);
                e.getPlayer().sendMessage(name);
                if (warData.getWar_spawn() != null) {
                    WarPlayer warPlayer = WarPlayer.players.get(e.getPlayer());
                    warPlayer.setBefore_loc(e.getPlayer().getLocation().clone());
                    warPlayer.setWar(name);
                    e.getPlayer().teleport(warData.getWar_spawn());
                    e.getPlayer().sendMessage("§c§l[War] §f성으로 이동 하였습니다.");
                }
            }
            if (e.getClickedBlock().getType().equals(Material.DIAMOND_BLOCK)) {
                if (WarPlayer.players.get(e.getPlayer()).getBefore_loc() != null) {
                    e.getPlayer().teleport(WarPlayer.players.get(e.getPlayer()).getBefore_loc());
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (SettingGui.getSetting().get(e.getPlayer()) != null) {
            e.setCancelled(true);
            SettingGui.Setting setting = SettingGui.getSetting().get(e.getPlayer());
            WarData warData = WarData.getWars().get(setting.getName());
            Location loc = e.getBlock().getLocation().clone();
            if (setting.getType().equals(SettingGui.Setting.Type.ENTER)) {
                warData.setWar_enter(loc);
                WarData.getWars_loc().put(loc,setting.getName());
                e.getPlayer().sendMessage("입장 설정");
            } else {
                warData.setBlock_loc(loc);
                e.getPlayer().sendMessage("블럭 설정");
            }
            SettingGui.getSetting().remove(e.getPlayer());
        }
        if (e.getBlock().getType().equals(Material.OBSIDIAN)) {
            Player p = e.getPlayer();
            WarPlayer warPlayer =  WarPlayer.players.get(e.getPlayer());
            String name = warPlayer.getWar();
            if (name != null) {
                WarData warData = WarData.getWars().get(name);
                if (e.getBlock().getLocation().equals(warData.getBlock_loc())) {
                    warData.setBlock_heath(warData.getBlock_heath() - 1);
                    if (warPlayer.getBar() == null) {
                        warPlayer.setBar(Bukkit.createBossBar
                                (warData.getBlock_heath() + " HP ", BarColor.RED, BarStyle.SEGMENTED_6));
                    }
                    warPlayer.getBar().setTitle(warData.getBlock_heath() + " HP ");
                    warPlayer.getBar().setProgress((double) warData.getBlock_heath() / 10);
                    warPlayer.getBar().addPlayer(p);
                    warPlayer.getBar().setVisible(true);
                    e.setCancelled(true);
                    if (warData.getBlock_heath() <= 0) {
                        p.sendMessage("§c§l[War] §f전쟁이 종료 되었습니다.");
                        warPlayer.getBar().setTitle("전쟁이 종료 되었습니다.");
                        e.setCancelled(false);
                        Bukkit.getScheduler().runTaskLater(IsWar.getPlugin(), ()-> {
                            warPlayer.getBar().removeAll();
                        },10);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(!(e.getWhoClicked() instanceof Player) || e.getView().getTopInventory() == null ||
                e.getClickedInventory() == null)
            return;
        InventoryHolder inventoryHolder = e.getView().getTopInventory().getHolder();
        if(inventoryHolder instanceof SettingGui) {
            if (e.getInventory().equals(e.getView().getTopInventory()))
                ((SettingGui) inventoryHolder).onClick(e);
        }
    }
}
