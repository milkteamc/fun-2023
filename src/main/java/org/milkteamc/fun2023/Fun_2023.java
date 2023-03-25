package org.milkteamc.fun2023;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Fun_2023 extends JavaPlugin implements Listener {


    private final Map<Item, Integer> appleCounts = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("start fun-2023 for milkteamc");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("stop fun-2023 for milkteamc");
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        Item item = event.getEntity();

        // check is it apple
        if (item.getItemStack().getType() == Material.APPLE) {
            int appleCount = item.getItemStack().getAmount();
            // change to ROTTEN_FLESH
            item.setItemStack(new ItemStack(Material.ROTTEN_FLESH, appleCount));

            // save apple count
            appleCounts.put(item, appleCount);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        // check is it player
        if (inventory != null && inventory.equals(event.getWhoClicked().getInventory())) {
            ItemStack currentItem = event.getCurrentItem();

            // check is it ROTTEN_FLESH
            if (currentItem != null && currentItem.getType() == Material.ROTTEN_FLESH) {
                Item item = event.getWhoClicked().getWorld().dropItem(event.getWhoClicked().getLocation(), new ItemStack(Material.APPLE, appleCounts.get(event.getCurrentItem())));

                // delete apple count from MAP
                appleCounts.remove(event.getCurrentItem());

                // delete ROTTEN_FLESH
                event.setCurrentItem(null);

                // dont disappear qwq
                item.setPickupDelay(0);
            }
        }
    }
}
