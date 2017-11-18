package com.axevillager.starwars.listeners.commandpost;

import com.axevillager.starwars.commandposts.CommandPost;
import com.axevillager.starwars.events.commandpost.PlayerCaptureCommandPostAttemptEvent;
import com.axevillager.starwars.events.commandpost.PlayerTeleportToCommandPostEvent;
import com.axevillager.starwars.player.SWPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * PlayerCaptureCommandPostHandler created by Børre A. Opedal Lunde on 2017/11/17
 */

public class PlayerCaptureCommandPostHandler implements Listener {


    @EventHandler
    public void onPlayerCaptureCommandPostAttempt(final PlayerCaptureCommandPostAttemptEvent event) {
        final CommandPost commandPost = event.getCommandPost();
        final SWPlayer swPlayer = event.getSWPlayer();
    }



    @EventHandler
    public void onPlayerCaptureCommandPost(final PlayerTeleportToCommandPostEvent event) {
        final CommandPost commandPost = event.getCommandPost();
        final SWPlayer swPlayer = event.getSWPlayer();
    }
}