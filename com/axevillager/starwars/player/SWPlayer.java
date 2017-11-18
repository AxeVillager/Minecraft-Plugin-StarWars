package com.axevillager.starwars.player;

import com.axevillager.starwars.kits.Kit;
import com.axevillager.starwars.kits.Kit.KitType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.UUID;

import static com.axevillager.starwars.listeners.weapon.WeaponReloadHandler.globalReloadIDs;
import static com.axevillager.starwars.team.TeamManager.*;

/**
 * StarWarsPlayer created by Børre A. Opedal Lunde on 2017/11/02
 */

public class SWPlayer {

    public static final ArrayList<SWPlayer> SWPlayers = new ArrayList<>();

    private final Player player;
    private Teams team;
    private boolean rightClicking;
    private long lastRightClickTime;
    private long lastShot;
    private boolean reloading;
    private boolean aiming;
    private boolean capturing;
    private boolean stunned;
    private boolean respawning;
    private boolean inLobby;
    private Kit kit;
    private KitType kitType;
    private boolean teamChat;


    public SWPlayer(final Player player) {
        this.player = player;
        SWPlayers.add(this);
    }



    public static SWPlayer getSWPlayer(final UUID uuid) {
        for (final SWPlayer swPlayer : SWPlayers)
            if (swPlayer.getUniqueID().equals(uuid))
                return swPlayer;
        return null;
    }



    public void removeFromList() {
        SWPlayers.remove(this);
    }



    public String getName() {
        return player.getName();
    }



    public Player getPlayer() {
        return player;
    }



    public UUID getUniqueID() {
        return player.getUniqueId();
    }



    public void setRightClicking(final boolean rightClicking) {
        this.rightClicking = rightClicking;
    }



    public boolean isRightClicking() {
        return rightClicking;
    }



    public void setLastRightClickTime(final long time) {
        this.lastRightClickTime = time;
    }



    public long getLastRightClickTime() {
        return lastRightClickTime;
    }



    public void respawn() {
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setFoodLevel(20);
        player.setSaturation(0);
        teleportToTeamLobbySpawn();
        inLobby = true;
        setRespawning(false);
        getKit().giveKit(this);
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlySpeed(0.1f);
        player.setWalkSpeed(0.2f);
    }



    public void setReloading(final boolean reloading) {
        this.reloading = reloading;
    }



    public boolean isReloading() {
        return reloading;
    }



    public void stopReloading() {
        reloading = false;
        if (!globalReloadIDs.containsKey(this)) return;
        final int value = globalReloadIDs.get(this);
        Bukkit.getScheduler().cancelTask(value);
        globalReloadIDs.remove(this);
    }



    public void setAiming(final boolean aiming) {
        this.aiming = aiming;
    }



    public boolean isAiming() {
        return aiming;
    }

    public void stopAiming() {
        aiming = false;
        player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 1);
        player.removePotionEffect(PotionEffectType.SLOW);
    }



    public void setCapturing(final boolean capturing) {
        this.capturing = capturing;
    }



    public boolean isCapturing() {
        return capturing;
    }



    public void setStunned(final boolean stunned) {
        this.stunned = stunned;
    }



    public boolean isStunned() {
        return stunned;
    }



    public void setLastShot(final Long lastShot) {
        this.lastShot = lastShot;
    }



    public Long getLastShot() {
        return lastShot;
    }



    public void joinTeam(final Teams team) {
        setTeam(team);
        if (!respawning) teleportToTeamLobbySpawn();
        setInLobby(true);
        kit.giveKit(this);
        player.setDisplayName(team.getColor() + getName());
        player.setPlayerListName(team.getColor() + getName());
        player.sendMessage(team.getColor() + "You joined " + team.getName() + ".");
    }



    public void setTeam(final Teams team) {
        this.team = team;
    }



    public Teams getTeam() {
        return team;
    }



    public void teleportToTeamLobbySpawn() {
        player.teleport(team.getLobbySpawnLocation());
    }



    public boolean isOnSameTeam(final SWPlayer otherSWPlayer) {
        return team == otherSWPlayer.getTeam();
    }



    public boolean isRespawning() {
        return respawning;
    }



    public void setRespawning(boolean respawning) {
        this.respawning = respawning;
    }



    public void setInLobby(boolean inLobby) {
        this.inLobby = inLobby;
    }



    public boolean isInLobby() {
        return inLobby;
    }



    public void setKit(final Kit kit) {
        this.kit = kit;
    }



    public Kit getKit() {
        return kit;
    }



    public void setKitType(KitType kitType) {
        this.kitType = kitType;
    }



    public KitType getKitType(KitType kitType) {
        return kitType;
    }

    public void setTeamChatEnabled(boolean teamChat) {
        this.teamChat = teamChat;
    }



    public boolean hasTeamChatEnabled() {
        return teamChat;
    }
}