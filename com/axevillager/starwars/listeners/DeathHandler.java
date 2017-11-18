package com.axevillager.starwars.listeners;

import com.axevillager.starwars.events.player.SWPlayerKilledByEntityEvent;
import com.axevillager.starwars.events.player.SWPlayerKilledEvent;
import com.axevillager.starwars.packets.Title;
import com.axevillager.starwars.player.SWPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.PluginManager;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

import static com.axevillager.starwars.Main.plugin;
import static com.axevillager.starwars.player.SWPlayer.getSWPlayer;

/**
 * DeathEvent created by Børre A. Opedal Lunde on 2017/11/04
 */

public class DeathHandler implements Listener {

    private final PluginManager pluginManager = Bukkit.getPluginManager();


    /*
     When a player is damaged by anything.
     */
    @EventHandler
    public void onPlayerDamage(final EntityDamageEvent event) {

        if (!(event.getEntity() instanceof Player))
            return;

        final Player player = (Player) event.getEntity();
        final double damage = event.getDamage();
        final EntityDamageEvent.DamageCause cause = event.getCause();

        if (!(damage >= player.getHealth()))
            return;

        final SWPlayer swPlayer = getSWPlayer(player.getUniqueId());

        if (swPlayer == null)
            return;

        event.setCancelled(true);
        pluginManager.callEvent(new SWPlayerKilledEvent(swPlayer, damage, cause));
    }



    /*
     When a player is damaged by an entity.
     */
    @EventHandler
    public void onPlayerDamageByEntity(final EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Player))
            return;

        final Player player = (Player) event.getEntity();
        final Entity damager = event.getDamager();
        final double damage = event.getDamage();
        final EntityDamageEvent.DamageCause cause = event.getCause();

        if (!(damage >= player.getHealth()))
            return;

        final SWPlayer swPlayer = getSWPlayer(player.getUniqueId());

        if (swPlayer == null)
            return;

        event.setCancelled(true);
        pluginManager.callEvent(new SWPlayerKilledByEntityEvent(swPlayer, damager, damage, cause));
    }



    /*
     When a SWPlayer is killed.
     */
    @EventHandler
    public void onPlayerKilled(final SWPlayerKilledEvent event) {
        doRespawnProcess(event.getSWPlayer(), null);
    }



    /*
     When a SWPlayer is killed by an entity.
     */
    @EventHandler
    public void onPlayerKilledByEntity(final SWPlayerKilledByEntityEvent event) {
        final SWPlayer victim = event.getVictim();
        final Entity killer = event.getKiller();
        final String killerName = event.getKiller().getName();
        final String killerMessage = ChatColor.DARK_RED + getRandomKillerMessage(victim.getName());

        doRespawnProcess(victim, killerName);
        if (killer instanceof Player) sendKillMessageTwice((Player) killer, killerMessage);
    }



    /*
     Send the kill message in the action bar twice.
     */
    private void sendKillMessageTwice(final Player killer, final String killerMessage) {
        final Title title = new Title();
        title.sendActionBar(killer, killerMessage);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> title.sendActionBar(killer, killerMessage), 40);
    }



    /*
     Randomly choose one of the existing death messages.
     */
    private String getRandomDeathMessage(final String name) {
        final String[] deathMessages = {name + " will go to jail for murder is illegal",
                name + " splattered your brains all over the floor",
                name + " is clearly superior to you",
                name + " rekt you",
                name + " sent you back to the abyss",
                "brb, " + name + " is removing you from this world",
                name + " purged the evil that was you",
                name + " cleansed the world of you",
                name + " melted you to ash",
                name + " turned your intestines into animal food",
                name + " deemed you unworthy",
                name + " crushed your hopes and dreams",
                name + " sent you to the afterlife",
                name + " ended your contract with life",
                "From " + name + "'s point of view, you were evil",
                name + " had the high ground",
                "You were not the droid " + name + " was looking for",
                name + " turned you into a mystery for Monsieur Poirot"};
        return deathMessages[new Random().nextInt(deathMessages.length)];
    }



    /*
     Randomly choose one of the existing kill messages.
     */
    private String getRandomKillerMessage(final String name) {
        final String[] killMessages = {"You murdered " + name + " for a greater cause",
                "You splattered " + name + "'s brains all over the floor",
                "You are clearly superior to " + name,
                "You rekt " + name,
                "Sending " + name + " back to the abyss",
                "brb removing " + name + " from this world",
                "Purging the evil that was " + name,
                "You cleansed the world of " + name,
                "Hold my raccoon, melting " + name + " to ash",
                "You turned " + name + "'s intestines into animal food",
                name + " was deemed unworthy",
                "You crushed " + name + "'s hopes and dreams",
                "You have sent " + name + " to the afterlife",
                name + "'s contract with life was suddenly terminated",
                "From your point of view, " + name + " was evil",
                "You had the high ground over " + name,
                name + " is not the droid I'm looking for",
                "You turned " + name + " into a mystery for Monsieur Poirot"};
        return killMessages[new Random().nextInt(killMessages.length)];
    }



    /*
     Makes the player spectator and shows time until respawn.
     When the time reaches zero the player is respawned.
     */
    private void doRespawnProcess(final SWPlayer swPlayer, final String killerName) {
        final Player player = swPlayer.getPlayer();
        final Title title = new Title();
        final String deathMessage = ChatColor.DARK_RED + getRandomDeathMessage(killerName);
        final DecimalFormatSymbols englishFormatSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        final DecimalFormat decimalFormat = new DecimalFormat("0.0", englishFormatSymbols);
        final int respawnTime = 6;
        final double[] time = new double[1];
        time[0] = respawnTime;

        adjustLocationIfBelowMap(player);
        player.setGameMode(GameMode.SPECTATOR);
        player.setFlySpeed(0);
        swPlayer.stopReloading();
        swPlayer.stopAiming();
        swPlayer.setRespawning(true);

        // Begin the repeating task that sends the respawn countdown and death message to the player.
        final int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            final String formattedTime = decimalFormat.format(time[0]);
            title.sendTitle(player, "", ChatColor.DARK_RED + "Respawning in " + formattedTime, 0, 10, 5);
            if (killerName != null) title.sendActionBar(player, deathMessage);
            time[0] -= 0.1;
        }, 0, 2);

        // Cancel the repeating task when the respawn time has passed.
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            Bukkit.getScheduler().cancelTask(taskID);
            swPlayer.respawn();
        }, respawnTime * 20);
    }



    /*
     If the player's height location is below 0, teleport the
     player to the adjusted location (height value to 0).
     */
    private void adjustLocationIfBelowMap(final Player player) {
        final Location location = player.getLocation();
        if (location.getY() < 0)
            player.teleport(new Location(location.getWorld(),
                    location.getX(), 0, location.getZ(),
                    location.getYaw(), location.getPitch()));
    }
}