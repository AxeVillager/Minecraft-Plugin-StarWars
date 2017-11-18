package com.axevillager.starwars.events.player;

import com.axevillager.starwars.player.SWPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * PlayerRightClickItemEvent created by Børre A. Opedal Lunde on 2017/11/11
 */

public class PlayerRightClickItemEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final SWPlayer swPlayer;
    private final ItemStack item;

    public PlayerRightClickItemEvent(final SWPlayer swPlayer, final ItemStack item) {
        this.swPlayer = swPlayer;
        this.item = item;
    }

    public SWPlayer getSwPlayer() {
        return swPlayer;
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}