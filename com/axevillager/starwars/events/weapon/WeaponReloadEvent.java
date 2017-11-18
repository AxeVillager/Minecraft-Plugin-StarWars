package com.axevillager.starwars.events.weapon;

import com.axevillager.starwars.player.SWPlayer;
import com.axevillager.starwars.weapon.Weapon;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * WeaponReloadEvent created by Børre A. Opedal Lunde on 2017/11/03
 */

public class WeaponReloadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final SWPlayer swPlayer;
    private final Weapon weapon;
    private final int reloadAmount;

    public WeaponReloadEvent(final SWPlayer swPlayer,
                             final Weapon weapon,final int reloadAmount) {
        this.swPlayer = swPlayer;
        this.weapon = weapon;
        this.reloadAmount = reloadAmount;
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

    public int getReloadAmount() {
        return reloadAmount;
    }
}