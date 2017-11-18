package com.axevillager.starwars.events.player;

import com.axevillager.starwars.player.SWPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * PlayerKilledByEntityEvent created by Børre A. Opedal Lunde on 2017/11/04
 */

public class SWPlayerKilledByEntityEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final SWPlayer victim;
    private final Entity killer;
    private final double damage;
    private final EntityDamageEvent.DamageCause cause;

    public SWPlayerKilledByEntityEvent(final SWPlayer victim,
                                       final Entity killer,
                                       final double damage,
                                       final EntityDamageEvent.DamageCause cause) {
        this.victim = victim;
        this.killer = killer;
        this.damage = damage;
        this.cause = cause;
    }


    public SWPlayer getVictim() {
        return victim;
    }

    public Entity getKiller() {
        return killer;
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