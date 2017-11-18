package com.axevillager.starwars.events.weapon;

import com.axevillager.starwars.player.SWPlayer;
import com.axevillager.starwars.weapon.Weapon;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * WeaponShootAttemptEvent created by Børre A. Opedal Lunde on 2017/11/12
 */

public class WeaponShootAttemptEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final SWPlayer swPlayer;
    private final Weapon weapon;

    public WeaponShootAttemptEvent(final SWPlayer swPlayer,
                            final Weapon weapon) {
        this.swPlayer = swPlayer;
        this.weapon = weapon;
    }

    public SWPlayer getSWPlayer() {
        return this.swPlayer;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}