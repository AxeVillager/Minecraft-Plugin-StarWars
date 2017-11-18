package com.axevillager.starwars.commands;

import com.axevillager.starwars.GameManager;
import com.axevillager.starwars.player.SWPlayer;
import com.axevillager.starwars.team.TeamManager;
import com.axevillager.starwars.team.TeamManager.Teams;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.axevillager.starwars.player.SWPlayer.getSWPlayer;

/**
 * TeamCommands created by Børre A. Opedal Lunde on 2017/11/16
 */

public class ChangeTeamCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String s, final String[] args) {

        final ChatColor errorColour = ChatColor.RED;

        if (!cmd.getName().equalsIgnoreCase("changeTeam"))
            return true;

        if (!(sender instanceof Player)) {
            sender.sendMessage(errorColour + "Only players can perform this command.");
            return true;
        }

        if (!sender.hasPermission("starwars.changeteam")) {
            sender.sendMessage(errorColour + "You don't have permission to perform this command.");
            return true;
        }

        final Player player = (Player) sender;
        final SWPlayer swPlayer = getSWPlayer(player.getUniqueId());

        if (swPlayer == null) {
            sender.sendMessage(errorColour + "Something went wrong. Reconnecting to the server is likely to solve the issue.");
            return true;
        }

        final List<Teams> possibleTeamsToJoin = calculatePossibleTeamsToJoin(swPlayer);

        if (possibleTeamsToJoin.size() == 0) {
            sender.sendMessage(errorColour + "You cannot change teams for the teams would become imbalanced.");
            return true;
        }

        final TeamManager teamManager = new TeamManager();
        final Teams teamWithFewestMembers = teamManager.getTeamWithFewestMembers(possibleTeamsToJoin);
        swPlayer.joinTeam(teamWithFewestMembers);
        return true;
    }



    private List<Teams> calculatePossibleTeamsToJoin(final SWPlayer swPlayer) {
        final GameManager gameManager = new GameManager();
        final Teams[] teamsOnMap = gameManager.getCurrentMap().getTeams();
        final List<Teams> listOfPossibleTeamsToJoin = new ArrayList<>();
        final Teams team = swPlayer.getTeam();
        final int currentTeamMembers = team.getAmountOfMembers();

        for (final Teams mapTeam : teamsOnMap) {
            final int mapTeamMembers = mapTeam.getAmountOfMembers();
            if (currentTeamMembers >= mapTeamMembers && team != mapTeam)
                listOfPossibleTeamsToJoin.add(mapTeam);
        }
        return listOfPossibleTeamsToJoin;
    }
}