package com.axevillager.starwars.commands;

import com.axevillager.starwars.kits.Kit;
import com.axevillager.starwars.player.SWPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.axevillager.starwars.kits.Kit.kits;
import static com.axevillager.starwars.player.SWPlayer.getSWPlayer;

/**
 * KitCommand created by Børre A. Opedal Lunde on 2017/11/15
 */

public class KitCommand implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String s, final String[] args) {

        final ChatColor errorColour = ChatColor.RED;
        final ChatColor goldColour = ChatColor.GOLD;

        if (!cmd.getName().equalsIgnoreCase("class"))
            return true;

        if (!(sender instanceof Player)) {
            sender.sendMessage(errorColour + "Only players can perform this command.");
            return true;
        }

        if (!sender.hasPermission("starwars.class")) {
            sender.sendMessage(errorColour + "You don't have permission to perform this command.");
            return true;
        }

        final Player player = (Player) sender;
        final SWPlayer swPlayer = getSWPlayer(player.getUniqueId());

        if (swPlayer == null) {
            sender.sendMessage(errorColour + "Something went wrong. Reconnecting to the server is likely to solve the issue.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(errorColour + "Please specify a class.");
            return true;
        }

        final String firstArgument = args[0];

        for (final Kit kit : kits) {
            if (kit.getName().equalsIgnoreCase(firstArgument)) {

                swPlayer.setKit(kit);
                final String kitName = swPlayer.getKit().getName();

                if (!swPlayer.isInLobby()) {
                    sender.sendMessage(goldColour + "You have selected the " + kitName + " class. You will receive it when you go to the lobby.");
                    return true;
                }

                swPlayer.getKit().giveKit(swPlayer);
                sender.sendMessage(goldColour + "You have been given the " + kitName + " class.");
                return true;
            }
        }

        sender.sendMessage(errorColour + "The class \"" + firstArgument + "\" does not exist.");
        return true;
    }



    /*
     Return a list of possible kits to choose from when you attempt to tab complete the classes.
     */
    public List<String> onTabComplete(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        final List<String> kitList = new ArrayList<>();
        final String firstArgument = args[0].toLowerCase();

        if (!cmd.getName().equalsIgnoreCase("class") || args.length != 1 || !(sender instanceof Player))
            return null;

        for (final Kit kit : kits) {
            final String kitNameLowerCase = kit.getName().toLowerCase();

            if (firstArgument.equals(""))
                kitList.add(kitNameLowerCase);
            else if (kitNameLowerCase.startsWith(firstArgument))
                kitList.add(kitNameLowerCase);
        }

        Collections.sort(kitList);
        return kitList;
    }
}