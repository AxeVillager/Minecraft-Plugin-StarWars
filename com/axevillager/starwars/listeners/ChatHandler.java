package com.axevillager.starwars.listeners;

import com.axevillager.starwars.player.SWPlayer;
import com.axevillager.starwars.team.TeamManager.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static com.axevillager.starwars.player.SWPlayer.SWPlayers;
import static com.axevillager.starwars.player.SWPlayer.getSWPlayer;

/**
 * Chat created by Børre A. Opedal Lunde on 2017/11/15
 */

public class ChatHandler implements Listener {

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {

        final Player player = event.getPlayer();
        final SWPlayer swPlayer = getSWPlayer(player.getUniqueId());

        if (swPlayer == null)
            return;

        final boolean teamChatEnabled = swPlayer.hasTeamChatEnabled();
        final String teamChatIndicator = teamChatEnabled ? ChatColor.GOLD + "TEAM " : "";
        final ChatColor teamColour = swPlayer.getTeam().getColor();
        final String playerName = player.getName();
        final ChatColor chatColour = teamChatEnabled ? ChatColor.GRAY : ChatColor.WHITE;
        final String message = event.getMessage();
        final String format = teamChatIndicator + teamColour + playerName + chatColour + ": " + message;
        event.setFormat(format);

        if (!swPlayer.hasTeamChatEnabled())
            return;

        event.setCancelled(true);
        sendMessageToTeam(swPlayer.getTeam(), format);
        sendMessageToConsole(format);
    }


    private void sendMessageToTeam(final Teams team, final String message) {
        for (final SWPlayer swPlayer : SWPlayers)
            if (swPlayer.getTeam() == team)
                swPlayer.getPlayer().sendMessage(message);
    }


    private void sendMessageToConsole(final String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }
}