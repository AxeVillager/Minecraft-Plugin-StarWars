package com.axevillager.starwars.events.commandpost;

import com.axevillager.starwars.commandposts.CommandPost;
import com.axevillager.starwars.player.SWPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * PlayerCaptureCommandPostAttemptEvent created by Børre A. Opedal Lunde on 2017/11/17
 */

public class PlayerCaptureCommandPostAttemptEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final CommandPost commandPost;
    private final SWPlayer swPlayer;

    public PlayerCaptureCommandPostAttemptEvent(final CommandPost commandPost, final SWPlayer swPlayer) {
        this.commandPost = commandPost;
        this.swPlayer = swPlayer;
    }

    public CommandPost getCommandPost() {
        return commandPost;
    }

    public SWPlayer getSWPlayer() {
        return swPlayer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}