package com.axevillager.starwars.commandposts.coruscant;

import com.axevillager.starwars.commandposts.CommandPost;
import com.axevillager.starwars.team.TeamManager;
import org.bukkit.Location;

import static com.axevillager.starwars.world.WorldManager.Maps.CORUSCANT;

/**
 * CommandPostC created by Børre A. Opedal Lunde on 2017/11/13
 */

public class CommandPostC extends CommandPost {

    public CommandPostC() {

        final String name = "Command Post C";
        final Location location = new Location(CORUSCANT.getWorld(), 202, 38, -424);
        final int radius = 6;
        final TeamManager.Teams team = TeamManager.Teams.NEUTRAL;
        final int captureValue = 0;
        final Location spawn = new Location(location.getWorld(), 196, 38, -423, 90, 0);
        final Location[] mapLocations = {new Location(location.getWorld(), 483, 17, -377),
                new Location(location.getWorld(), 483, 17, -451)};

        initializeCommandPost(name, location, radius, team, captureValue, spawn, mapLocations);
    }
}