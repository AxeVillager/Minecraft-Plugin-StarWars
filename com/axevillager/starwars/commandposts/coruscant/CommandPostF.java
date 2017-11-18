package com.axevillager.starwars.commandposts.coruscant;

import com.axevillager.starwars.commandposts.CommandPost;
import com.axevillager.starwars.team.TeamManager;
import org.bukkit.Location;

import static com.axevillager.starwars.world.WorldManager.Maps.CORUSCANT;

/**
 * CommandPostF created by Børre A. Opedal Lunde on 2017/11/13
 */

public class CommandPostF extends CommandPost {

    public CommandPostF() {

        final String name = "Command Post F";
        final Location location = new Location(CORUSCANT.getWorld(), 109, 42, -407);
        final int radius = 6;
        final TeamManager.Teams team = TeamManager.Teams.NEUTRAL;
        final int captureValue = 0;
        final Location spawn = new Location(location.getWorld(), 103, 42, -406.5, -90, 0);
        final Location[] mapLocations = {new Location(location.getWorld(), 483, 15, -370),
                new Location(location.getWorld(), 483, 15, -444)};

        initializeCommandPost(name, location, radius, team, captureValue, spawn, mapLocations);
    }
}