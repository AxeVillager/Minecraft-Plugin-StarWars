package com.axevillager.starwars.weapon.weapons;

import com.axevillager.starwars.weapon.Weapon;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

/**
 * DC15A created by Børre A. Opedal Lunde on 2017/11/11
 */

public class DC15A extends Weapon {

    public DC15A() {

        final String name = ChatColor.YELLOW + "DC-15A";
        final double damage = 1.5;
        final double headshotDamage = 1.8;
        final double feetDamage = 1;
        final int ammo = 30;
        final ItemStack item = new ItemStack(Material.INK_SACK, 1, (byte) 1);
        final Particle shootParticle = Particle.SMOKE_LARGE;
        final EntityType bullet = EntityType.SNOWBALL;
        final int bulletsAmount = 1;
        final Particle bulletParticle = Particle.CRIT;
        final double bulletSpeed = 8;
        final int fireRate = 140;
        final double range = 64;
        final int zoomAmount = 3;
        final double accuracy = 0.15;
        final double sneakAccuracy = 0.1;
        final double aimAccuracy = 0.11;
        final boolean bulletHasGravity = false;
        final Sound shootSound = Sound.BLOCK_NOTE_SNARE;
        final int reloadTime = 50;
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