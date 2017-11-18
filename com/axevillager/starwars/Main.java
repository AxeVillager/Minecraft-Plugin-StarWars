package com.axevillager.starwars;

import com.axevillager.starwars.commands.ChangeTeamCommand;
import com.axevillager.starwars.commands.KitCommand;
import com.axevillager.starwars.commands.RespawnCommand;
import com.axevillager.starwars.commands.TeamChatCommand;
import com.axevillager.starwars.kits.clones.CloneEngineer;
import com.axevillager.starwars.kits.clones.CloneSharpshooter;
import com.axevillager.starwars.kits.clones.CloneTrooper;
import com.axevillager.starwars.listeners.*;
import com.axevillager.starwars.listeners.commandpost.PlayerCaptureCommandPostHandler;
import com.axevillager.starwars.listeners.commandpost.PlayerTeleportToCommandPostHandler;
import com.axevillager.starwars.listeners.weapon.WeaponAimHandler;
import com.axevillager.starwars.listeners.weapon.WeaponHitHandler;
import com.axevillager.starwars.listeners.weapon.WeaponReloadHandler;
import com.axevillager.starwars.listeners.weapon.WeaponShootHandler;
import com.axevillager.starwars.player.SWPlayer;
import com.axevillager.starwars.scoreboard.SWScoreboard;
import com.axevillager.starwars.weapon.weapons.DC15A;
import com.axevillager.starwars.weapon.weapons.DC15X;
import com.axevillager.starwars.weapon.weapons.DP23;
import com.axevillager.starwars.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static com.axevillager.starwars.listeners.weapon.WeaponReloadHandler.globalReloadIDs;
import static com.axevillager.starwars.player.SWPlayer.SWPlayers;

/**
 * Main created by Børre A. Opedal Lunde on 2017/11/02
 */

public class Main extends JavaPlugin {

    public static Plugin plugin;
    private final ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();
    private final WorldManager worldManager = new WorldManager();
    private final GameManager gameManager = new GameManager();


    public Main() {
        plugin = this;
    }



    public void onEnable() {
        consoleSender.sendMessage(ChatColor.GREEN + "" + this + " has been enabled!");
        registerEverything();
        new SWScoreboard().setupScoreboard();
        worldManager.copyAndCreateWorlds();
        worldManager.populateCommandPostLists();
        gameManager.startGameLoop();
    }



    public void onDisable() {
        final ChatColor red = ChatColor.RED;
        consoleSender.sendMessage(red + "" + this + " has been disabled!");
        for (final SWPlayer swPlayer : SWPlayers)
            swPlayer.getPlayer().kickPlayer(red + "Restarting...");
        worldManager.unloadAndDeleteWorlds();
        globalReloadIDs.clear();
        SWPlayers.clear();
    }



    private void registerEverything() {
        registerListeners();
        registerCommands();
        registerWeapons();
        registerKits();
    }



    private void registerListeners() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new WeaponShootHandler(), this);
        pluginManager.registerEvents(new WeaponAimHandler(), this);
        pluginManager.registerEvents(new WeaponReloadHandler(), this);
        pluginManager.registerEvents(new WeaponHitHandler(), this);
        pluginManager.registerEvents(new PlayerCaptureCommandPostHandler(), this);
        pluginManager.registerEvents(new PlayerTeleportToCommandPostHandler(), this);
        pluginManager.registerEvents(new JoinLeaveHandler(), this);
        pluginManager.registerEvents(new DeathHandler(), this);
        pluginManager.registerEvents(new HungerHandler(), this);
        pluginManager.registerEvents(new ChatHandler(), this);
        pluginManager.registerEvents(new PlayerInteractHandler(), this);
        pluginManager.registerEvents(new MultipleItemEventsHandler(), this);
        pluginManager.registerEvents(new ForceResourcePackHandler(), this);
    }



    private void registerCommands() {
        getCommand("toggleTeamChat").setExecutor(new TeamChatCommand());
        getCommand("changeteam").setExecutor(new ChangeTeamCommand());
        getCommand("respawn").setExecutor(new RespawnCommand());
        getCommand("class").setExecutor(new KitCommand());
    }



    private void registerWeapons() {
        new DC15A();
        new DP23();
        new DC15X();
    }



    private void registerKits() {
        new CloneTrooper();
        new CloneEngineer();
        new CloneSharpshooter();
    }
}