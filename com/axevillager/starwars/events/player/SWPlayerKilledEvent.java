package com.axevillager.starwars.events.player;

import com.axevillager.starwars.player.SWPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * PlayerKilledEvent created by Børre A. Opedal Lunde on 2017/11/04
 */

public class SWPlayerKilledEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final SWPlayer swPlayer;
    private final double damage;
    private final EntityDamageEvent.DamageCause cause;

    public SWPlayerKilledEvent(final SWPlayer swPlayer, final double damage, final EntityDamageEvent.DamageCause cause) {
        this.swPlayer = swPlayer;
        this.damage = damage;
        this.cause = cause;
    }


    public SWPlayer getSWPlayer() {
        return swPlayer;
    }

    public double getDamage() {
        return damage;
    }

    public EntityDamageEvent.DamageCause getCause() {
        return cause;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}