package org.milkteamc.fun2023;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Fun_2023 extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            // Ignore non-player entities
            return;
        }
        Item item = event.getItem();
        if (item.getItemStack().getType() == Material.APPLE) {
            // Replace apple with rotten flesh
            item.setItemStack(new ItemStack(Material.ROTTEN_FLESH, item.getItemStack().getAmount()));
        } else if (item.getItemStack().getType() == Material.ROTTEN_FLESH) {
            // Replace rotten flesh with apple in player's inventory
            ItemStack stack = item.getItemStack();
            int amount = stack.getAmount();
            Player player = (Player) entity;
            player.getInventory().addItem(new ItemStack(Material.APPLE, amount));
            item.remove();
            event.setCancelled(true);
        }
    }

}


