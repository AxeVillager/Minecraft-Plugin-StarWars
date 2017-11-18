package com.axevillager.starwars.commandposts.coruscant;

import com.axevillager.starwars.commandposts.CommandPost;
import com.axevillager.starwars.team.TeamManager;
import org.bukkit.Location;

import static com.axevillager.starwars.world.WorldManager.Maps.CORUSCANT;

/**
 * CommandPostE created by Børre A. Opedal Lunde on 2017/11/13
 */

public class CommandPostE extends CommandPost {

    public CommandPostE() {

        final String name = "Command Post E";
        final Location location = new Location(CORUSCANT.getWorld(), 161, 38, -342);
        final int radius = 6;
        final TeamManager.Teams team = TeamManager.Teams.NEUTRAL;
        final int captureValue = 0;
        final Location spawn = new Location(location.getWorld(), 161, 38, -333, -180, 0);
        final Location[] mapLocations = {new Location(location.getWorld(), 483, 10, -374),
                new Location(location.getWorld(), 483, 10, -448)};

        initializeCommandPost(name, location, radius, team, captureValue, spawn, mapLocations);
    }
}