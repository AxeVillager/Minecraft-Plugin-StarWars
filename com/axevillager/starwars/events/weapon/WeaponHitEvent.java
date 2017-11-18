package com.axevillager.starwars.events.weapon;

import com.axevillager.starwars.player.SWPlayer;
import com.axevillager.starwars.weapon.Weapon;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * WeaponHitEntityEvent created by Børre A. Opedal Lunde on 2017/11/03
 */

public class WeaponHitEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final SWPlayer shooter;
    private final Projectile projectile;
    private final Entity hitEntity;
    private final Block hitBlock;

    public WeaponHitEvent(final SWPlayer shooter, final Projectile projectile,
                          final Entity hitEntity, final Block hitBlock) {
        this.shooter = shooter;
        this.projectile = projectile;
        this.hitEntity = hitEntity;
        this.hitBlock = hitBlock;
    }


    public SWPlayer getShooter() {
        return shooter;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public Entity getHitEntity() {
        return hitEntity;
    }

    public Block getHitBlock() {
        return hitBlock;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}