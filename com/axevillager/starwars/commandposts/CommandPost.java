package com.axevillager.starwars.commandposts;

import com.axevillager.starwars.player.SWPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.Map.Entry;

import static com.axevillager.starwars.player.SWPlayer.SWPlayers;
import static com.axevillager.starwars.team.TeamManager.Teams;

/**
 * CommandPost created by Børre A. Opedal Lunde on 2017/11/04
 */

public abstract class CommandPost {

    private final List<Location> commandPostBeamLocations = new ArrayList<>();
    private Location[] commandPostMapLocations;
    private String name;
    private Location location;
    private int captureRadius;
    private Teams commandPostTeam;
    private Teams attemptedCaptureTeam;
    private int captureValue;
    private boolean underAttack;
    private Location spawn;
    private final int lowestCaptureValue = 0;
    private final int highestCaptureValue = 10;


    protected void initializeCommandPost(final String name, final Location location, final int captureRadius,
                                         final Teams team, final int captureValue, final Location spawn,
                                         final Location[] commandPostMapSpawnLocations) {
        this.name = name;
        this.location = location;
        this.captureRadius = captureRadius;
        this.commandPostTeam = team;
        this.attemptedCaptureTeam = team;
        this.captureValue = captureValue;
        this.spawn = spawn;
        this.commandPostMapLocations = commandPostMapSpawnLocations;
        addBeamLocationsToList();
    }



    public void update() {
        updateCapturing();
        buildCommandPostBeam();
        updateSpawnMap();
    }



    private void addBeamLocationsToList() {
        for (int i = 2; i < 7; i++)
            commandPostBeamLocations.add(new Location(location.getWorld(),
                    location.getX(), location.getY() + i, location.getZ()));
    }



    private Teams getCapturingTeam() {
        final HashMap<Teams, Integer> membersOfTeamInsideArea = new HashMap<>();
        final List<SWPlayer> playersInsideArea = getLivingSWPlayersInsideRadius();

        if (playersInsideArea == null || playersInsideArea.size() == 0)
            return null;

        // Populate the membersOfTeamInsideArea array list.
        for (final SWPlayer swPlayer : playersInsideArea) {
            final Teams swPlayersTeam = swPlayer.getTeam();
            final int amount = membersOfTeamInsideArea.get(swPlayersTeam) != null ? membersOfTeamInsideArea.get(swPlayersTeam) + 1 : 1;
            membersOfTeamInsideArea.put(swPlayersTeam, amount);
        }

        // List of a map that contains teams with members inside the area.
        final List<Entry<Teams, Integer>> linkedList = new LinkedList<>(membersOfTeamInsideArea.entrySet());
        linkedList.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue())); // Sort the list

        // A map that is to contain teams and the respective amount of members of a commandPostTeam, inside the area.
        final Map<Teams, Integer> mapWithTeamsAndMembersInOrder = new LinkedHashMap<>();

        // Populate the map with teams sorted highest to lowest based on their amount of members.
        for (final Entry<Teams, Integer> entry : linkedList)
            mapWithTeamsAndMembersInOrder.put(entry.getKey(), entry.getValue());

        // listOfTeams contains the list of teams from highest to lowest (amount of members).
        final List<Teams> listOfTeams = new ArrayList<>(mapWithTeamsAndMembersInOrder.keySet());

        // Return the dominant capturing force. If there is none, return neutral.
        if (mapWithTeamsAndMembersInOrder.size() > 1 &&
                mapWithTeamsAndMembersInOrder.get(listOfTeams.get(0))
                        .equals(mapWithTeamsAndMembersInOrder.get(listOfTeams.get(1))))
            return Teams.NEUTRAL;
        return listOfTeams.get(0);
    }



    private ArrayList<SWPlayer> getLivingSWPlayersInsideRadius() {
        final ArrayList<SWPlayer> swPlayerArrayList = new ArrayList<>();
        for (final SWPlayer swPlayer : SWPlayers)
            if (isSWPlayerInsideRadius(swPlayer) && !swPlayer.isRespawning() && swPlayer.getPlayer().getGameMode() != GameMode.SPECTATOR)
                swPlayerArrayList.add(swPlayer);
        return swPlayerArrayList;
    }



    private boolean isSWPlayerInsideRadius(final SWPlayer swPlayer) {
        return swPlayer.getPlayer().getLocation().distance(location) < captureRadius;
    }



    private void updateCapturing() { // TODO Move to a handler?
        updateCaptureValue();
        updateCommandPostTeam();
    }



    private void updateCaptureValue() {
        final Teams capturingTeam = getCapturingTeam();
        final boolean noOneIsCapturing = capturingTeam == null;
        underAttack = false;

        if (capturingTeam == Teams.NEUTRAL)
            return;

        // Regenerate the Command Post to fully captured or neutral state if no one is capturing.
        if (noOneIsCapturing) {
            if (commandPostTeam != Teams.NEUTRAL && captureValue < highestCaptureValue) {
                increaseCaptureValue();
            }
            else if (commandPostTeam == Teams.NEUTRAL && captureValue > lowestCaptureValue) {
                decreaseCaptureValue();
            }
        }

        if (noOneIsCapturing || getCapturingTeam().getMembers().size() == 0)
            return;

        // A new enemy team is capturing the Command Post.
        if (isBeingCapturedByDifferentEnemyTeam() && attemptedCaptureTeam != Teams.NEUTRAL) {
            underAttack = true;
            decreaseCaptureValue();
        }

        // The same enemy team is capturing the Command Post.
        if (isBeingCapturedBySameEnemyTeam()) {
            underAttack = true;
            increaseCaptureValue();
        }

        // The owner team of the Command Post is recapturing it.
        if (isBeingCapturedByOwnTeam() && captureValue < highestCaptureValue) {
            increaseCaptureValue();
        }
    }



    private void updateCommandPostTeam() { // TODO Move to a handler?
        final Teams capturingTeam = getCapturingTeam();

        // Don't continue if there is no one capturing the Command Post.
        if (capturingTeam == null || capturingTeam == Teams.NEUTRAL || capturingTeam.getMembers().size() == 0)
            return;

        // Neutralize the flag when it is neutralized.
        if (isNeutral()) {
            if (commandPostTeam != Teams.NEUTRAL) {
                Bukkit.broadcastMessage("~~~" + name + " is neutralized!~~~");
                commandPostTeam.setAmountOfCommandPosts(commandPostTeam.getAmountOfCommandPosts() - 1);
            }
            commandPostTeam = Teams.NEUTRAL;
            attemptedCaptureTeam = capturingTeam;
        }


        // Set command post team to the capturing team.
        if (isFullyCaptured() && commandPostTeam != attemptedCaptureTeam) {
            commandPostTeam = attemptedCaptureTeam;
            commandPostTeam.setAmountOfCommandPosts(commandPostTeam.getAmountOfCommandPosts() + 1);
            Bukkit.broadcastMessage(commandPostTeam.getColor() + "~~~" + name + " is captured by " + commandPostTeam.getName() + "!~~~");
        }
    }



    @SuppressWarnings("deprecation")
    private void buildCommandPostBeam() { // TODO Move to a handler?
        final ItemStack teamPrimaryItem = commandPostTeam.getCommandPostBeamItem();
        final ItemStack teamUnderAttackItem = commandPostTeam.getCommandPostUnderAttackItem();
        final ItemStack attemptUnderAttackItem = attemptedCaptureTeam.getCommandPostUnderAttackItem();

        for (final Location location : commandPostBeamLocations) {
            if (isFullyCaptured() || isNeutral()) {
                buildBlockAtLocation(location, teamPrimaryItem);
            }

            else if (isBeingCapturedByDifferentEnemyTeam() && commandPostTeam != Teams.NEUTRAL) {
                buildBlockAtLocation(location, teamUnderAttackItem);
            }

            else if (isBeingCapturedByDifferentEnemyTeam() && commandPostTeam == Teams.NEUTRAL) {
                buildBlockAtLocation(location, attemptUnderAttackItem);
            }

            else if (isBeingCapturedBySameEnemyTeam()) {
                buildBlockAtLocation(location, attemptUnderAttackItem);
            }
        }
    }



    private void updateSpawnMap() { // TODO Move to a handler?
        final ItemStack teamPrimaryItem = commandPostTeam.getCommandPostBeamItem();
        final ItemStack teamUnderAttackItem = commandPostTeam.getCommandPostUnderAttackItem();
        final ItemStack attemptUnderAttackItem = attemptedCaptureTeam.getCommandPostUnderAttackItem();

        for (final Location location : commandPostMapLocations) {
            if (isFullyCaptured() || isNeutral()) {
                buildBlockAtLocation(location, teamPrimaryItem);
            }

            else if (isBeingCapturedByDifferentEnemyTeam() && commandPostTeam != Teams.NEUTRAL) {
                buildBlockAtLocation(location, teamUnderAttackItem);
            }

            else if (isBeingCapturedByDifferentEnemyTeam() && commandPostTeam == Teams.NEUTRAL) {
                buildBlockAtLocation(location, attemptUnderAttackItem);
            }

            else if (isBeingCapturedBySameEnemyTeam()) {
                buildBlockAtLocation(location, attemptUnderAttackItem);
            }
        }
    }



    @SuppressWarnings("deprecation")
    private void buildBlockAtLocation(final Location location, final ItemStack item) {
        location.getBlock().setTypeIdAndData(item.getTypeId(), item.getData().getData(), false);
    }



    // Being captured by a team that is not the attempted capture team.
    private boolean isBeingCapturedByDifferentEnemyTeam() {
        return commandPostTeam != getCapturingTeam() && attemptedCaptureTeam != getCapturingTeam();
    }



    // Being captured by the same team that is the attempted capture team.
    private boolean isBeingCapturedBySameEnemyTeam() {
        return commandPostTeam != getCapturingTeam() && attemptedCaptureTeam == getCapturingTeam();
    }



    // Being captured by the same team that owns the Command Post.
    private boolean isBeingCapturedByOwnTeam() {
        return commandPostTeam == getCapturingTeam();
    }



    private void increaseCaptureValue() {
        captureValue++;
    }



    private void decreaseCaptureValue() {
        captureValue--;
    }



    private boolean isFullyCaptured() {
        return captureValue == highestCaptureValue;
    }



    private boolean isNeutral() {
        return captureValue == lowestCaptureValue;
    }



    public String getName() {
        return name;
    }



    public Location getSpawnLocation() {
        return spawn;
    }



    public Location[] getCommandPostMapLocations() {
        return commandPostMapLocations;
    }



    public Teams getTeam() {
        return commandPostTeam;
    }



    public Teams getAttemptedCaptureTeam() {
        return attemptedCaptureTeam;
    }



    public boolean isUnderAttack() {
        return underAttack;
    }
}