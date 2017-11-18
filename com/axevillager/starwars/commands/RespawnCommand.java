package com.axevillager.starwars.commands;

import com.axevillager.starwars.events.player.SWPlayerKilledEvent;
import com.axevillager.starwars.player.SWPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.axevillager.starwars.player.SWPlayer.getSWPlayer;

/**
 * RespawnCommands created by Børre A. Opedal Lunde on 2017/11/16
 */

public class RespawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String s, final String[] args) {

        final ChatColor errorColour = ChatColor.RED;

        if (!cmd.getName().equalsIgnoreCase("respawn"))
            return true;

        if (!(sender instanceof Player)) {
            sender.sendMessage(errorColour + "Only players can perform this command.");
            return true;
        }

        if (!sender.hasPermission("starwars.respawn")) {
            sender.sendMessage(errorColour + "You don't have permission to perform this command.");
            return true;
        }

        final Player player = (Player) sender;
        final SWPlayer swPlayer = getSWPlayer(player.getUniqueId());

        if (swPlayer == null) {
            sender.sendMessage(errorColour + "Something went wrong. Reconnecting to the server is likely to solve the issue.");
            return true;
        }

        if (swPlayer.isInLobby()) {
            sender.sendMessage(errorColour + "You cannot respawn while in the lobby.");
            return true;
        }

        if (swPlayer.isRespawning()) {
            sender.sendMessage(errorColour + "Only a fool would attempt to respawn whilst respawning!");
            return true;
        }

        Bukkit.getPluginManager().callEvent(new SWPlayerKilledEvent(swPlayer, 0, null));
        return true;
    }

}