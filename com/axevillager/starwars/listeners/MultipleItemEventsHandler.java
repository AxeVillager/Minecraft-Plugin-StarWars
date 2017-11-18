package com.axevillager.starwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

/**
 * ItemHandler created by Børre A. Opedal Lunde on 2017/11/16
 */

public class MultipleItemEventsHandler implements Listener {


    @EventHandler
    public void onItemDamage(final PlayerItemDamageEvent event) {
        event.setCancelled(true);
    }



    @SuppressWarnings("deprecation")
    @EventHandler
    public void onItemPickup(final PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }



    @EventHandler
    public void onSwapItemInHands(final PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
    }



    @EventHandler
    public void onPlayerDrop(final PlayerDropItemEvent event) {
        event.setCancelled(true);
    }



    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        if (event.getSlotType() == InventoryType.SlotType.ARMOR)
            event.setCancelled(true);
    }



    @EventHandler
    public void onCraftingEvent(final CraftItemEvent event) {
        event.setCancelled(true);
    }
}