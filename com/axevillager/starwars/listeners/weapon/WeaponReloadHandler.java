package com.axevillager.starwars.listeners.weapon;

import com.axevillager.starwars.events.weapon.WeaponReloadAttemptEvent;
import com.axevillager.starwars.events.weapon.WeaponReloadEvent;
import com.axevillager.starwars.packets.Title;
import com.axevillager.starwars.player.SWPlayer;
import com.axevillager.starwars.weapon.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.Map;

import static com.axevillager.starwars.Main.plugin;
import static com.axevillager.starwars.player.SWPlayer.getSWPlayer;
import static com.axevillager.starwars.weapon.Weapon.getWeapon;

/**
 * WeaponReloadHandler created by Børre A. Opedal Lunde on 2017/11/16
 */

public class WeaponReloadHandler implements Listener {

    public final static Map<SWPlayer, Integer> globalReloadIDs = new HashMap<>();
    private final PluginManager pluginManager = Bukkit.getPluginManager();



    /*
     Call the WeaponReloadAttemptEvent when a player drops a weapon.
     */
    @EventHandler
    public void onPlayerItemDrop(final PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        final SWPlayer swPlayer = getSWPlayer(player.getUniqueId());

        if (swPlayer == null)
            return;

        final ItemStack item = event.getItemDrop().getItemStack();
        final String itemName = item.getItemMeta().getDisplayName();
        final Weapon weapon = getWeapon(itemName);

        if (weapon == null)
            return;

        event.setCancelled(true);
        pluginManager.callEvent(new WeaponReloadAttemptEvent(swPlayer, weapon));
    }



    /*
     If it is possible to reload the weapon, call the WeaponReloadEvent.
     */
    @EventHandler
    public void onWeaponReloadAttempt(final WeaponReloadAttemptEvent event) {
        final SWPlayer swPlayer = event.getSWPlayer();
        final Weapon weapon = event.getWeapon();

        if (swPlayer.isReloading() || swPlayer.isInLobby() || swPlayer.isRespawning())
            return;

        pluginManager.callEvent(new WeaponReloadEvent(swPlayer, weapon, weapon.getAmmo()));
    }



    /*
     Reload event.
     */
    @EventHandler
    public void onWeaponReload(final WeaponReloadEvent event) {
        final SWPlayer swPlayer = event.getSWPlayer();
        final Player player = swPlayer.getPlayer();
        final Weapon weapon = event.getWeapon();
        final Title title = new Title();

        swPlayer.setReloading(true);
        swPlayer.stopAiming();
        weapon.playReloadStartSound(player);
        title.sendActionBar(player, ChatColor.YELLOW + "Reloading");

        final int reloadTaskID = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            weapon.setAmmo(player.getInventory().getItemInMainHand(), weapon.getAmmo());
            weapon.playReloadFinishedSound(player);
            swPlayer.setReloading(false);
            player.updateInventory();
        }, weapon.getReloadTime());

        // The reload task ID is put in the list so it is possible to stop the task from elsewhere.
        globalReloadIDs.put(swPlayer, reloadTaskID);
    }



    /*
     Stop reloading if the player changes item in hand while reloading.
     */
    @EventHandler
    public void onPlayerItemHeld(final PlayerItemHeldEvent event) {
        final SWPlayer swPlayer = getSWPlayer(event.getPlayer().getUniqueId());

        if (swPlayer == null)
            return;

        if (swPlayer.isReloading())
            swPlayer.stopReloading();
    }
}