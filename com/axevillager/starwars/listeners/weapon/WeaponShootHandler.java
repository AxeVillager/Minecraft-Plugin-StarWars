package com.axevillager.starwars.listeners.weapon;

import com.axevillager.starwars.events.player.PlayerRightClickItemEvent;
import com.axevillager.starwars.events.weapon.WeaponReloadAttemptEvent;
import com.axevillager.starwars.events.weapon.WeaponShootAttemptEvent;
import com.axevillager.starwars.events.weapon.WeaponShootEvent;
import com.axevillager.starwars.player.SWPlayer;
import com.axevillager.starwars.weapon.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;

import static com.axevillager.starwars.weapon.Weapon.getAmmoFromTitle;
import static com.axevillager.starwars.weapon.Weapon.getWeapon;

/**
 * WeaponShootHandler created by Børre A. Opedal Lunde on 2017/11/16
 */

public class WeaponShootHandler implements Listener {

    private final PluginManager pluginManager = Bukkit.getPluginManager();



    /*
     Call the WeaponShootAttemptEvent when a player right clicks a weapon.
     */
    @EventHandler
    public void onRightClickItem(final PlayerRightClickItemEvent event) {
        final String itemInMainHandName = event.getItem().getItemMeta().getDisplayName();
        final SWPlayer swPlayer = event.getSwPlayer();

        if (itemInMainHandName == null)
            return;

        final Weapon weapon = getWeapon(itemInMainHandName);

        if (weapon == null)
            return;

        pluginManager.callEvent(new WeaponShootAttemptEvent(swPlayer, weapon));
    }



    /*
     Attempt to shoot - if possible: call the WeaponShootEvent.
     */
    @EventHandler
    public void onWeaponShootAttempt(final WeaponShootAttemptEvent event) {
        final Long currentTime = System.currentTimeMillis();
        final SWPlayer swPlayer = event.getSWPlayer();
        final Weapon weapon = event.getWeapon();
        final String itemInMainHandName = swPlayer.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName();

        if (swPlayer.getLastShot() == null)
            swPlayer.setLastShot(0L);

        if (swPlayer.getLastShot() > currentTime - weapon.getFireRate() ||
                swPlayer.isReloading() ||
                swPlayer.isRespawning() ||
                swPlayer.isStunned() ||
                swPlayer.isInLobby())
            return;

        if (getAmmoFromTitle(itemInMainHandName) <= 0) {
            weapon.playOutOfAmmoSound(swPlayer.getPlayer());
            pluginManager.callEvent(new WeaponReloadAttemptEvent(swPlayer, weapon));
            return;
        }

        pluginManager.callEvent(new WeaponShootEvent(swPlayer, weapon));
    }



    /*
     Shoot event.
     */
    @EventHandler
    public void onWeaponShoot(final WeaponShootEvent event) {
        final Long currentTime = System.currentTimeMillis();
        final SWPlayer swPlayer = event.getSWPlayer();
        final Weapon weapon = event.getWeapon();

        double accuracy = weapon.getAccuracy();

        if (swPlayer.isAiming())
            accuracy -= weapon.getBonusAimAccuracy();

        if (swPlayer.getPlayer().isSneaking())
            accuracy -= weapon.getBonusSneakAccuracy();

        swPlayer.setLastShot(currentTime);
        weapon.playShootSound(swPlayer.getPlayer());
        weapon.shoot(swPlayer.getPlayer(), accuracy);


        final ItemStack item = swPlayer.getPlayer().getInventory().getItemInMainHand();
        final ItemMeta itemMeta = item.getItemMeta();
        final String weaponName = weapon.getName();
        itemMeta.setDisplayName(weaponName + " «" + (getAmmoFromTitle(itemMeta.getDisplayName()) - 1) + "»");
        item.setItemMeta(itemMeta);
        swPlayer.getPlayer().updateInventory();
    }
}