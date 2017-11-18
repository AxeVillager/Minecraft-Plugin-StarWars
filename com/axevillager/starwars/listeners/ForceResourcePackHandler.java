package com.axevillager.starwars.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import static org.bukkit.event.player.PlayerResourcePackStatusEvent.*;

/**
 * ResourcePackHandler created by Børre A. Opedal Lunde on 2017/11/15
 */

public class ForceResourcePackHandler implements Listener {

    @EventHandler
    public void onResourcePackStatusEvent(final PlayerResourcePackStatusEvent event) {
        final Player player = event.getPlayer();
        final Status status = event.getStatus();
        final ChatColor darkRed = ChatColor.DARK_RED;

        // Kick player for he or she declined the resource pack request.
        if (status == Status.DECLINED)
            player.kickPlayer(darkRed + "You must accept the Resource Pack request to play on this server.");

        // Kick player for he or she failed the download of the resource pack.
        if (status == Status.FAILED_DOWNLOAD)
            player.kickPlayer(darkRed + "The download of the Resource Pack failed, please try again.");
    }
}