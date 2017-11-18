package com.axevillager.starwars.listeners;

import com.axevillager.starwars.kits.Kit;
import com.axevillager.starwars.player.SWPlayer;
import com.axevillager.starwars.scoreboard.SWScoreboard;
import com.axevillager.starwars.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.axevillager.starwars.kits.Kit.getKit;
import static com.axevillager.starwars.player.SWPlayer.getSWPlayer;
import static com.axevillager.starwars.team.TeamManager.*;
import static com.axevillager.starwars.team.TeamManager.Teams.BLUE;
import static com.axevillager.starwars.team.TeamManager.Teams.RED;

/**
 * JoinLeaveListener created by Børre A. Opedal Lunde on 2017/11/11
 */

public class JoinLeaveHandler implements Listener {

    private final TeamManager teamManager = new TeamManager();
    private final SWScoreboard scoreboard = new SWScoreboard();
    private final Teams[] teams = {RED, BLUE};


    /*
     When a player joins the server.
     */
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final SWPlayer swPlayer = new SWPlayer(player);

        scoreboard.assignPlayerToScoreboardTeam(player);
        player.setGameMode(GameMode.SURVIVAL);
        player.setWalkSpeed(0.2F);

        // Assign to team, teleport to spawn and tell the player what team he or she joined.
        teamManager.assignSWPlayerToTeamWithFewestMembers(teams, swPlayer);
        swPlayer.getPlayer().sendMessage(swPlayer.getTeam().getColor() + "You joined " + swPlayer.getTeam().getName() + "!");
        swPlayer.teleportToTeamLobbySpawn();
        swPlayer.setInLobby(true);

        // Cancel the original join message and our my own.
        event.setJoinMessage("");
        sendMessageToEveryoneBut(swPlayer.getPlayer(), swPlayer.getTeam().getColor() + swPlayer.getName() + " joined the game");

        addTeamColourToName(swPlayer);
        setDamageTicks(player);
        makePlayerHealthy(player);
        givePlayerKit(swPlayer);
    }



    /*
     When a player leaves the server.
     */
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final SWPlayer swPlayer = getSWPlayer(event.getPlayer().getUniqueId());

        if (swPlayer == null)
            return;

        final ChatColor teamColour = swPlayer.getTeam().getColor();
        final String playerName = swPlayer.getName();

        swPlayer.removeFromList();
        event.setQuitMessage(teamColour + playerName + " left the game");
    }



    private void sendMessageToEveryoneBut(final Player player, final String message) {
        for (final Player players : Bukkit.getOnlinePlayers())
            if (player != players) players.sendMessage(message);
    }



    private void addTeamColourToName(final SWPlayer swPlayer) {
        final Player player = swPlayer.getPlayer();
        final ChatColor colour = swPlayer.getTeam().getColor();
        player.setPlayerListName(colour + player.getName());
        player.setDisplayName(colour + player.getName());
    }



    private void setDamageTicks(final Player player) {
        player.setNoDamageTicks(2);
        player.setMaximumNoDamageTicks(2);
    }



    private void makePlayerHealthy(final Player player) {
        final double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        player.setHealth(maxHealth);
        player.setFoodLevel(20);
        player.setSaturation(0);
    }



    private void givePlayerKit(final SWPlayer swPlayer) {
        if (swPlayer.getKit() == null) {
            final Kit cloneTrooper = getKit("trooper");
            if (cloneTrooper != null) swPlayer.setKit(cloneTrooper);
        }
        swPlayer.getKit().giveKit(swPlayer);
    }
}