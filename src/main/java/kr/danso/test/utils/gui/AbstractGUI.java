package kr.danso.test.utils.gui;

import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractGUI implements InventoryHolder {

    protected static final Map<String, GUIData> dataMap = new HashMap<>();

    private final String identifier;

    protected AbstractGUI(String identifier) {
        this.identifier = identifier;
    }

    public final void onClick(InventoryClickEvent e){
        onPlayerClick(e);
    }

    public final void onClose(InventoryCloseEvent e){
        onPlayerClose(e);
    }

    protected abstract void onPlayerClick(InventoryClickEvent e);

    protected abstract void onPlayerClose(InventoryCloseEvent e);

    private GUIData getData(){
        if(!dataMap.containsKey(identifier)){
            dataMap.put(identifier, new GUIData());
        }
        return dataMap.get(identifier);
    }

    public void resetData(){
        dataMap.put(identifier, new GUIData());
    }

    public void setTitle(String title){
        getData().title = title;
    }

    public void setRow(int row) {
        getData().row = row;
    }

    public void addFillItem(int slot, ItemStack itemStack){
        if(itemStack != null)
            getData().items.put(slot, itemStack);
    }

    @Override
    public Inventory getInventory() {
        return buildInventory();
    }

    protected Inventory buildInventory(){
        GUIData guiData = getData();
        Inventory inventory;

        if(guiData.inventoryType != InventoryType.CHEST){
            inventory = Bukkit.createInventory(this, guiData.inventoryType, guiData.title);
        }  else{
            inventory = Bukkit.createInventory(this, guiData.row * 9, guiData.title);
        }

        for (Map.Entry<Integer,ItemStack> entry:guiData.items.entrySet()) {
            inventory.setItem(entry.getKey(),entry.getValue());
        }

        return inventory;
    }

    @Setter
    protected static class GUIData {

        private String title = "";
        private InventoryType inventoryType = InventoryType.CHEST;
        private int row = 6;
        private Map<Integer, ItemStack> items = new HashMap<>();

    }
}
