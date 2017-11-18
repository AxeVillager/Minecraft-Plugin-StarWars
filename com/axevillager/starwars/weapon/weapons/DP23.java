package com.axevillager.starwars.weapon.weapons;

import com.axevillager.starwars.weapon.Weapon;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

/**
 * DP23 created by Børre A. Opedal Lunde on 2017/11/13
 */

public class DP23 extends Weapon {

    public DP23() {

        final String name = "DP-23";
        final double damage = 5;
        final double headshotDamage = 6;
        final double feetDamage = 1;
        final int ammo = 2;
        final ItemStack item = new ItemStack(Material.INK_SACK, 1, (byte) 1);
        final Particle shootParticle = Particle.SMOKE_LARGE;
        final EntityType bullet = EntityType.SNOWBALL;
        final int bulletsAmount = 8;
        final Particle bulletParticle = Particle.SWEEP_ATTACK;
        final double bulletSpeed = 8;
        final int fireRate = 1500;
        final double range = 16;
        final int zoomAmount = 2;
        final double accuracy = 0.3;
        final double sneakAccuracy = 0.08;
        final double aimAccuracy = 0.08;
        final boolean bulletHasGravity = false;
        final Sound shootSound = Sound.ENTITY_GENERIC_EXPLODE;
        final int reloadTime = 40;
        final Sound reloadStartSound = Sound.BLOCK_PISTON_EXTEND;
        final Sound reloadFinishedSound = Sound.BLOCK_PISTON_CONTRACT;
        final Sound aimSound = Sound.ENTITY_BAT_TAKEOFF;
        final Sound outOfAmmoSound = Sound.BLOCK_COMPARATOR_CLICK;

        initializeWeapon(name, damage, headshotDamage, feetDamage, ammo, item, shootParticle,
                bullet, bulletsAmount, bulletParticle, bulletSpeed,
                fireRate, range, zoomAmount, accuracy, sneakAccuracy,
                aimAccuracy, bulletHasGravity, shootSound, reloadTime, reloadStartSound,
                reloadFinishedSound, aimSound, outOfAmmoSound);
    }

}