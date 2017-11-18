package com.axevillager.starwars.listeners;

import com.axevillager.starwars.player.SWPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import static com.axevillager.starwars.player.SWPlayer.getSWPlayer;

/**
 * WeaponHandler created by Børre A. Opedal Lunde on 2017/11/02
 */

public class PlayerInteractHandler implements Listener {


    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        final SWPlayer swPlayer = getSWPlayer(event.getPlayer().getUniqueId());
        final Long currentTime = System.currentTimeMillis();
        final String actionName = event.getAction().name();

        event.setCancelled(true);

        if (swPlayer == null || !actionName.contains("RIGHT"))
            return;

        swPlayer.setRightClicking(true);
        swPlayer.setLastRightClickTime(currentTime);
    }
}