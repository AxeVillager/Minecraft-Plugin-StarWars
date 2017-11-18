package com.axevillager.starwars.events.commandpost;

import com.axevillager.starwars.commandposts.CommandPost;
import com.axevillager.starwars.player.SWPlayer;
import com.axevillager.starwars.team.TeamManager;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

/**
 * CommandPostCaptureEvent created by Børre A. Opedal Lunde on 2017/11/13
 */

public class CommandPostCaptureEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final List<SWPlayer> capturingSWPlayers;
    private final TeamManager.Teams capturingTeam;
    private final CommandPost commandPost;

    public CommandPostCaptureEvent(final List<SWPlayer> capturingSWPlayers, final CommandPost commandPost, final TeamManager.Teams capturingTeam) {
        this.capturingSWPlayers = capturingSWPlayers;
        this.capturingTeam = capturingTeam;
        this.commandPost = commandPost;
    }

    public List<SWPlayer> getCapturingSWPlayers() {
        return capturingSWPlayers;
    }

    public TeamManager.Teams getCapturingTeam() {
        return capturingTeam;
    }

    public CommandPost getCommandPost() {
        return commandPost;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}