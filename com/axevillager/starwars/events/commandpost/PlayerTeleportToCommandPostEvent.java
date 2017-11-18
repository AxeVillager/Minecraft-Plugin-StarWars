package com.axevillager.starwars.events.commandpost;

import com.axevillager.starwars.commandposts.CommandPost;
import com.axevillager.starwars.player.SWPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * TeleportToCommandPostEvent created by Børre A. Opedal Lunde on 2017/11/04
 */

public class PlayerTeleportToCommandPostEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final SWPlayer swPlayer;
    private final CommandPost commandPost;

    public PlayerTeleportToCommandPostEvent(SWPlayer swPlayer, CommandPost commandPost) {
        this.swPlayer = swPlayer;
        this.commandPost = commandPost;
    }

    public SWPlayer getSWPlayer() {
        return swPlayer;
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