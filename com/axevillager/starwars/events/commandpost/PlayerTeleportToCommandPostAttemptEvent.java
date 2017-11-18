package com.axevillager.starwars.events.commandpost;

import com.axevillager.starwars.commandposts.CommandPost;
import com.axevillager.starwars.player.SWPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * PlayerTeleportToCommandPostAttemptEvent created by Børre A. Opedal Lunde on 2017/11/17
 */

public class PlayerTeleportToCommandPostAttemptEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final CommandPost commandPost;
    private final SWPlayer swPlayer;

    public PlayerTeleportToCommandPostAttemptEvent(final SWPlayer swPlayer, final CommandPost commandPost) {
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