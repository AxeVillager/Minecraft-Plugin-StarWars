package com.axevillager.starwars.commandposts.coruscant;

import com.axevillager.starwars.commandposts.CommandPost;
import com.axevillager.starwars.team.TeamManager;
import org.bukkit.Location;

import static com.axevillager.starwars.world.WorldManager.Maps.CORUSCANT;

/**
 * CommandPostA created by Børre A. Opedal Lunde on 2017/11/13
 */

public class CommandPostA extends CommandPost {

    public CommandPostA() {
        final String name = "Command Post A";
        final Location location = new Location(CORUSCANT.getWorld(), 101, 49, -459);
        final int radius = 6;
        final TeamManager.Teams team = TeamManager.Teams.BLUE;
        final int captureValue = 10;
        final Location spawn = new Location(location.getWorld(), 106, 49, -454, -110, 0);
        final Location[] mapLocations = {new Location(location.getWorld(), 483, 19, -370),
                new Location(location.getWorld(), 483, 19, -444)};

        initializeCommandPost(name, location, radius, team, captureValue, spawn, mapLocations);
    }
}