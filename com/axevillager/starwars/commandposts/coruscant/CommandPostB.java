package com.axevillager.starwars.commandposts.coruscant;

import com.axevillager.starwars.commandposts.CommandPost;
import com.axevillager.starwars.team.TeamManager;
import org.bukkit.Location;

import static com.axevillager.starwars.world.WorldManager.Maps.CORUSCANT;

/**
 * CommandPostB created by Børre A. Opedal Lunde on 2017/11/13
 */

public class CommandPostB extends CommandPost {

    public CommandPostB() {

        final String name = "Command Post B";
        final Location location = new Location(CORUSCANT.getWorld(), 161, 48, -488);
        final int radius = 6;
        final TeamManager.Teams team = TeamManager.Teams.NEUTRAL;
        final int captureValue = 0;
        final Location spawn = new Location(location.getWorld(), 161, 48, -493, 0, 0);
        final Location[] mapLocations = {new Location(location.getWorld(), 483, 21, -374),
                new Location(location.getWorld(), 483, 21, -448)};

        initializeCommandPost(name, location, radius, team, captureValue, spawn, mapLocations);
    }
}