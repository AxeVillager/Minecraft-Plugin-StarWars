package com.axevillager.starwars.listeners.commandpost;

import com.axevillager.starwars.GameManager;
import com.axevillager.starwars.commandposts.CommandPost;
import com.axevillager.starwars.events.commandpost.PlayerTeleportToCommandPostAttemptEvent;
import com.axevillager.starwars.events.commandpost.PlayerTeleportToCommandPostEvent;
import com.axevillager.starwars.packets.Title;
import com.axevillager.starwars.player.SWPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;

import java.util.List;

import static com.axevillager.starwars.team.TeamManager.Teams;

/**
 * PlayerTeleportToCommandPostHandler created by Børre A. Opedal Lunde on 2017/11/17
 */

public class PlayerTeleportToCommandPostHandler implements Listener {

    private final PluginManager pluginManager = Bukkit.getPluginManager();
    private final Title title = new Title();



    /*
     Call the PlayerTeleportToCommandPostAttemptEvent when a player clicks on a spawn block on a spawn map.
     */
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Location targetLocation = player.getTargetBlock(null, 80).getLocation();
        final SWPlayer swPlayer = SWPlayer.getSWPlayer(player.getUniqueId());
        final GameManager gameManager = new GameManager();
        final List<CommandPost> commandPosts = gameManager.getCurrentMap().getCommandPosts();

        if (swPlayer == null)
            return;

        final Teams playerTeam = swPlayer.getTeam();

        if (playerTeam == null || !swPlayer.isInLobby())
            return;

        for (final CommandPost commandPost : commandPosts) {
            for (final Location spawnBlockLocation : commandPost.getCommandPostMapLocations()) {
                if (targetLocation.equals(spawnBlockLocation) &&
                        targetLocation.getBlock() != null &&
                        targetLocation.getBlock().getType() == Material.WOOL) {

                    pluginManager.callEvent(new PlayerTeleportToCommandPostAttemptEvent(swPlayer, commandPost));
                }
            }
        }
    }



    /*
     If it is possible to teleport to the Command Post, call the PlayerTeleportToCommandPostEvent.
     */
    @EventHandler
    public void onPlayerTeleportToCommandPostAttempt(final PlayerTeleportToCommandPostAttemptEvent event) {
        final CommandPost commandPost = event.getCommandPost();
        final Teams attemptedCaptureTeam = commandPost.getAttemptedCaptureTeam();
        final boolean commandPostIsUnderAttack = commandPost.isUnderAttack();
        final Teams commandPostTeam = commandPost.getTeam();
        final ChatColor errorColor = ChatColor.DARK_RED;
        final SWPlayer swPlayer = event.getSWPlayer();
        final Teams playerTeam = swPlayer.getTeam();
        final Player player = swPlayer.getPlayer();

        if (commandPostIsUnderAttack && commandPostTeam == playerTeam) {
            title.sendActionBar(player, errorColor + "You cannot spawn at the Command Post for it is under attack!");
            return;
        }

        if (commandPostTeam == Teams.NEUTRAL && attemptedCaptureTeam == playerTeam) {
            title.sendActionBar(player, errorColor + "You cannot spawn at the Command Post for it is not fully captured yet!");
            return;
        }

        if (commandPostTeam != playerTeam) {
            title.sendActionBar(player, errorColor + "You cannot spawn at the Command Post for it is not controlled by your team!");
            return;
        }

        pluginManager.callEvent(new PlayerTeleportToCommandPostEvent(swPlayer, commandPost));
    }



    /*
     Teleport to Command Post event.
     */
    @EventHandler
    public void onPlayerTeleportToCommandPost(final PlayerTeleportToCommandPostEvent event) {
        final CommandPost commandPost = event.getCommandPost();
        final SWPlayer swPlayer = event.getSWPlayer();
        final Player player = swPlayer.getPlayer();

        title.sendActionBar(player, ChatColor.YELLOW + "Spawning at " + commandPost.getName());
        player.teleport(commandPost.getSpawnLocation());
        swPlayer.setInLobby(false);
    }
}