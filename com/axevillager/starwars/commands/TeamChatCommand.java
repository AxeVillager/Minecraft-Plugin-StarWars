package com.axevillager.starwars.commands;

import com.axevillager.starwars.player.SWPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.axevillager.starwars.player.SWPlayer.getSWPlayer;

/**
 * TeamChatCommand created by Børre A. Opedal Lunde on 2017/11/15
 */

public class TeamChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String s, final String[] args) {

        final ChatColor errorColour = ChatColor.RED;
        final ChatColor goldColour = ChatColor.GOLD;

        if (!cmd.getName().equalsIgnoreCase("toggleTeamChat"))
            return true;

        if (!(sender instanceof Player)) {
            sender.sendMessage(errorColour + "Only players can perform this command.");
            return true;
        }

        if (!sender.hasPermission("starwars.toggleteamchat")) {
            sender.sendMessage(errorColour + "You don't have permission to perform this command.");
            return true;
        }

        final Player player = (Player) sender;
        final SWPlayer swPlayer = getSWPlayer(player.getUniqueId());

        if (swPlayer == null) {
            sender.sendMessage(errorColour + "Something went wrong. Reconnecting to the server is likely to solve the issue.");
            return true;
        }

        if (swPlayer.hasTeamChatEnabled()) {
            sender.sendMessage(goldColour + "Team Chat is disabled.");
            swPlayer.setTeamChatEnabled(false);
            return true;
        }

        sender.sendMessage(goldColour + "Team Chat is enabled.");
        swPlayer.setTeamChatEnabled(true);
        return true;
    }
}