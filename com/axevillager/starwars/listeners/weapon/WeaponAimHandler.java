package com.axevillager.starwars.listeners.weapon;

import com.axevillager.starwars.events.weapon.WeaponAimAttemptEvent;
import com.axevillager.starwars.events.weapon.WeaponAimEvent;
import com.axevillager.starwars.player.SWPlayer;
import com.axevillager.starwars.weapon.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import static com.axevillager.starwars.player.SWPlayer.getSWPlayer;
import static com.axevillager.starwars.weapon.Weapon.getWeapon;

/**
 * WeaponAimHandler created by Børre A. Opedal Lunde on 2017/11/16
 */

public class WeaponAimHandler implements Listener {

    private final PluginManager pluginManager = Bukkit.getPluginManager();



    /*
     Attempt to aim - call the WeaponAimEvent.
     */
    @EventHandler
    public void onAimAttempt(final PlayerInteractEvent event) {
        final SWPlayer swPlayer = getSWPlayer(event.getPlayer().getUniqueId());
        final String actionName = event.getAction().name();
        final ItemStack item = event.getItem();

        if (!actionName.contains("LEFT") || item == null)
            return;

        final String itemName = event.getItem().getItemMeta().getDisplayName();

        if (itemName == null)
            return;

        final Weapon weapon = getWeapon(itemName);

        if (weapon == null)
            return;

        event.setCancelled(true);
        pluginManager.callEvent(new WeaponAimAttemptEvent(swPlayer, weapon));
    }



    /*
     If it is possible to aim the weapon, call the WeaponAimEvent.
     */
    @EventHandler
    public void onWeaponAimAttempt(final WeaponAimAttemptEvent event) {
        final SWPlayer swPlayer = event.getSWPlayer();
        final Weapon weapon = event.getWeapon();

        if (swPlayer.isInLobby() || swPlayer.isReloading() || swPlayer.isRespawning())
            return;

        pluginManager.callEvent(new WeaponAimEvent(swPlayer, weapon));
    }



    /*
     Weapon aim event.
     */
    @EventHandler
    public void onWeaponAim(final WeaponAimEvent event) {
        final SWPlayer swPlayer = event.getSWPlayer();
        final Player player = swPlayer.getPlayer();
        final Weapon weapon = event.getWeapon();

        if (swPlayer.isAiming()) {
            swPlayer.stopAiming();
            return;
        }

        player.setSprinting(false);
        swPlayer.setAiming(true);
        weapon.aim(swPlayer);
    }



    /*
     Stop sprinting if you are aiming.
     */
    @EventHandler
    public void onPlayerToggleSprint(final PlayerToggleSprintEvent event) {
        final SWPlayer swPlayer = getSWPlayer(event.getPlayer().getUniqueId());

        if (swPlayer == null)
            return;

        if (swPlayer.isAiming())
            event.setCancelled(true);
    }



    /*
     Stop aiming if the player changes item in hand while aiming.
     */
    @EventHandler
    public void onPlayerItemHeld(final PlayerItemHeldEvent event) {
        final SWPlayer swPlayer = getSWPlayer(event.getPlayer().getUniqueId());

        if (swPlayer == null)
            return;

        if (swPlayer.isAiming())
            swPlayer.stopAiming();
    }
}