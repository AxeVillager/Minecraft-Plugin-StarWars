package com.axevillager.starwars.commandposts.coruscant;

import com.axevillager.starwars.commandposts.CommandPost;
import com.axevillager.starwars.team.TeamManager;
import org.bukkit.Location;

import static com.axevillager.starwars.world.WorldManager.Maps.CORUSCANT;

/**
 * CommandPostD created by Børre A. Opedal Lunde on 2017/11/13
 */

public class CommandPostD extends CommandPost {

    public CommandPostD() {

        final String name = "Command Post D";
        final Location location = new Location(CORUSCANT.getWorld(), 194, 38, -391);
        final int radius = 6;
        final TeamManager.Teams team = TeamManager.Teams.RED;
        final int captureValue = 10;
        final Location spawn = new Location(location.getWorld(), 199, 38, -385, 135, 0);
        final Location[] mapLocations = {new Location(location.getWorld(), 483, 14, -377),
                new Location(location.getWorld(), 483, 14, -451)};

        initializeCommandPost(name, location, radius, team, captureValue, spawn, mapLocations);
    }
}