package com.axevillager.starwars.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * Scoreboards created by Børre A. Opedal Lunde on 2017/11/15
 */

public class SWScoreboard {

    private final Scoreboard scoreboard;


    public SWScoreboard() {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    }



    public void setupScoreboard() {
        final Team team;
        final String teamName = "team";
        if (scoreboard.getTeam(teamName) == null) team = scoreboard.registerNewTeam(teamName);
        else team = scoreboard.getTeam(teamName);
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
    }



    public void assignPlayerToScoreboardTeam(final Player player) {
        scoreboard.getTeam("team").addEntry(player.getName());
        player.setScoreboard(scoreboard);
    }
}