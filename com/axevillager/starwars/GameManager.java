package com.axevillager.starwars;

import com.axevillager.starwars.commandposts.CommandPost;
import com.axevillager.starwars.events.player.PlayerRightClickItemEvent;
import com.axevillager.starwars.player.SWPlayer;
import com.axevillager.starwars.weapon.Weapon;
import com.axevillager.starwars.world.WorldManager.Maps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.axevillager.starwars.Main.plugin;
import static com.axevillager.starwars.player.SWPlayer.SWPlayers;
import static com.axevillager.starwars.team.TeamManager.Teams;
import static com.axevillager.starwars.weapon.Weapon.weaponList;
import static com.axevillager.starwars.world.WorldManager.Maps.CORUSCANT;

/**
 * GameManager created by Børre A. Opedal Lunde on 2017/11/16
 */

public class GameManager {

    private static Maps[] ALL_MAPS = {CORUSCANT};
    private static Maps CURRENT_MAP = ALL_MAPS[0];
    private boolean[] stop = {false};


    public Maps[] getAllMaps() {
        return ALL_MAPS;
    }



    public void setCurrentMap(final Maps map) {
        CURRENT_MAP = map;
    }



    public Maps getCurrentMap() {
        return CURRENT_MAP;
    }



    public boolean roundIsFinished() {
        return false;
    }



    void startGameLoop() {
        // Repeat once every 30 ticks (1.5 seconds).
        final int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (Bukkit.getOnlinePlayers().size() > 0)
                updateCurrentMapCommandPosts();
            removeLongLivingEntities();
            listenForGameOver();
        }, 0, 30);


        // Repeat once every 1 tick (50 milli seconds).
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (stop[0]) Bukkit.getScheduler().cancelTask(taskID);
            spawnBulletParticles();
            registerRightClickOnItem();
        }, 0, 1);
    }



    private void removeLongLivingEntities() {
        for (final Entity entity : getCurrentMap().getWorld().getEntities()) {
            if (entity.getTicksLived() > 20 * 1800 && !(entity instanceof Player)) {
                Bukkit.getLogger().info(entity.getName() + " was removed for living longer than 30 minutes.");
                entity.remove();
            }
        }
    }



    private void spawnBulletParticles() {
        final World world = getCurrentMap().getWorld();
        for (final Entity entity : world.getEntities()) {
            for (final Weapon weapon : weaponList) {
                if (entity.getCustomName() != null && entity.getCustomName().equals(weapon.getName()))
                    world.spawnParticle(weapon.getBulletParticle(), entity.getLocation(), 1);
            }
        }
    }



    private void updateCurrentMapCommandPosts() {
        for (final CommandPost commandPosts : getCurrentMap().getCommandPosts())
            commandPosts.update();
    }



    private boolean teamHasNoCommandPosts(final Teams team) {
        return team.getAmountOfCommandPosts() == 0;
    }



    private void listenForGameOver() {
        final ChatColor goldChatColour = ChatColor.GOLD;

        for (final Teams team : getCurrentMap().getTeams()) {
            if (teamHasNoCommandPosts(team)) {

                for (final SWPlayer swPlayer : SWPlayers) {
                    swPlayer.respawn();
                    swPlayer.setInLobby(false);

                    final Player player = swPlayer.getPlayer();
                    if (swPlayer.getTeam() == team) {
                        player.sendMessage(ChatColor.DARK_RED + "Your team has lost the game!");
                    } else {
                        player.sendMessage(goldChatColour + "Your team has won the game!");
                    }
                }

                Bukkit.broadcastMessage(goldChatColour + "Restarting in 15 seconds...");
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ()
                        -> Bukkit.getServer().shutdown(), 20 * 15);
                stop[0] = true;
            }
        }
    }



    private void registerRightClickOnItem() {
        for (final SWPlayer swPlayer : SWPlayers) {
            final Long currentTime = System.currentTimeMillis();
            final Long storedTime = swPlayer.getLastRightClickTime();

            if (currentTime > storedTime + 210)
                swPlayer.setRightClicking(false);

            final ItemStack item = swPlayer.getPlayer().getInventory().getItemInMainHand();
            if (swPlayer.isRightClicking() && item != null && item.getType() != Material.AIR) {
                Bukkit.getPluginManager().callEvent(new PlayerRightClickItemEvent(swPlayer, item));
            }
        }
    }
}