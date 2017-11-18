package com.axevillager.starwars.world;

import com.axevillager.starwars.commandposts.CommandPost;
import com.axevillager.starwars.commandposts.coruscant.*;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.axevillager.starwars.team.TeamManager.Teams;

/**
 * Map created by Børre A. Opedal Lunde on 2017/11/14
 */

public class WorldManager {

    private static final List<CommandPost> CORUSCANT_COMMAND_POSTS = new ArrayList<>();
    private final Logger logger = Bukkit.getLogger();
    private final Server server = Bukkit.getServer();



    public void copyAndCreateWorlds() {
        for (final Maps map : Maps.values()) {
            final String worldName = map.getName();
            copyWorldFromWorldsFolder(worldName);
            createWorld(worldName);
            setWorldInformation(map);
        }
    }



    private void copyWorldFromWorldsFolder(final String name) {
        final String separator = File.separator;
        final File serverFolder = new File(".").getAbsoluteFile();

        final File worldCopiesFolder = new File(serverFolder + separator + "world copies");
        final boolean isWorldCopiesFolderCreated = worldCopiesFolder.exists() || worldCopiesFolder.mkdirs();

        if (!isWorldCopiesFolderCreated) {
            logger.warning(worldCopiesFolder.getAbsolutePath() + " does not exist!");
            return;
        }

        final File destinationDirectory = new File(serverFolder + separator + name);
        final boolean isDestinationDirectoryCreated = destinationDirectory.exists() || destinationDirectory.mkdirs();

        if (!isDestinationDirectoryCreated) {
            logger.warning(destinationDirectory.getAbsolutePath() + " does not exist!");
            return;
        }

        final File worldFile = new File(worldCopiesFolder + separator + name);
        if (!worldFile.exists()) {
            logger.warning(name + " does not exist at: " + worldFile.getAbsolutePath() + "!");
            return;
        }

        copyFileToDestination(worldFile, destinationDirectory);
    }



    private void copyFileToDestination(final File file, final File destination) {
        try {
            FileUtils.copyDirectory(file, destination);
        } catch (IOException e) {
            logger.warning("Error when attempting to copy \"" + file.getAbsolutePath() +
                    "\" to the destination directory at \"" + destination.getAbsolutePath() + "\".");
            e.printStackTrace();
        }
    }



    private void createWorld(final String name) {
        server.createWorld(new WorldCreator(name));

    }



    private void setWorldInformation(final Maps map) {
        final World world = server.getWorld(map.getName());
        world.setAutoSave(false);
        world.setTime(map.getDayTime());
        world.setGameRuleValue("announceAdvancements", "false");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doFireTick", "false");
    }



    public void unloadAndDeleteWorlds() {
        for (final Maps map : Maps.values()) {
            final String worldName = map.getName();
            final File path = new File(server.getWorld(worldName).getWorldFolder().getPath());
            unloadWorld(worldName);
            deleteWorld(path);
        }
    }



    private void unloadWorld(final String worldName) {
        server.unloadWorld(worldName, true);
    }



    private void deleteWorld(final File file) {
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            Bukkit.getLogger().warning("Error when attempting to delete \"" + file.getAbsolutePath() + "\".");
            e.printStackTrace();
        }
    }



    public void populateCommandPostLists() {
        CORUSCANT_COMMAND_POSTS.add(new CommandPostA());
        CORUSCANT_COMMAND_POSTS.add(new CommandPostB());
        CORUSCANT_COMMAND_POSTS.add(new CommandPostC());
        CORUSCANT_COMMAND_POSTS.add(new CommandPostD());
        CORUSCANT_COMMAND_POSTS.add(new CommandPostE());
        CORUSCANT_COMMAND_POSTS.add(new CommandPostF());
    }





    public enum Maps {

        CORUSCANT {

            @Override
            public World getWorld() {
                return Bukkit.getServer().getWorld("coruscant");
            }

            @Override
            public Teams[] getTeams() {
                return new Teams[] {Teams.RED, Teams.BLUE};
            }

            @Override
            public List<CommandPost> getCommandPosts() {
                return CORUSCANT_COMMAND_POSTS;
            }

            @Override
            public String getName() {
                return "Coruscant";
            }

            @Override
            public long getDayTime() {
                return 20000;
            }
        };

        public abstract Teams[] getTeams();
        public abstract List<CommandPost> getCommandPosts();
        public abstract String getName();
        public abstract World getWorld();
        public abstract long getDayTime();
    }
}