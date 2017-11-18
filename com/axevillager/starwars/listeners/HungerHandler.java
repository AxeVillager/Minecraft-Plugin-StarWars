package com.axevillager.starwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * HungerHandler created by Børre A. Opedal Lunde on 2017/11/14
 */

public class HungerHandler implements Listener {

    @EventHandler
    public void onLoseHunger(final FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}