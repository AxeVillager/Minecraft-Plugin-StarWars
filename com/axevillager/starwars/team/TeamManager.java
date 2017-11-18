package com.axevillager.starwars.team;

import com.axevillager.starwars.player.SWPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static com.axevillager.starwars.player.SWPlayer.SWPlayers;
import static com.axevillager.starwars.world.WorldManager.Maps.CORUSCANT;

/**
 * Teams created by Børre A. Opedal Lunde on 2017/11/11
 */

public class TeamManager {


    public void assignSWPlayerToTeamWithFewestMembers(final Teams[] teams, final SWPlayer swPlayer) {
        final List<Teams> teamsList = new ArrayList<>();
        teamsList.addAll(Arrays.asList(teams));
        swPlayer.setTeam(getTeamWithFewestMembers(teamsList));
    }



    public void assignEverySWPlayerToTeams(final Teams[] teams) {
        for (final SWPlayer swPlayer : SWPlayers)
            assignSWPlayerToTeamWithFewestMembers(teams, swPlayer);
    }



    public Teams getTeamWithFewestMembers(final List<Teams> teams) {
        final Map<Teams, Integer> teamAndMembersMap = new HashMap<>();

        for (final Teams team : teams)
            teamAndMembersMap.put(team, team.getAmountOfMembers());

        final List<Map.Entry<Teams, Integer>> linkedList = new LinkedList<>(teamAndMembersMap.entrySet());
        linkedList.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        final Map<Teams, Integer> sortedMap = new LinkedHashMap<>();

        for (final Map.Entry<Teams, Integer> entry : linkedList)
            sortedMap.put(entry.getKey(), entry.getValue());

        final List<Teams> teamList = new ArrayList<>(sortedMap.keySet());
        Collections.reverse(teamList);

        return teamList.get(0);
    }





    public enum Teams {

        BLUE {
            @Override
            public String getName() {
                return "team Blue";
            }

            @Override
            public ChatColor getColor() {
                return ChatColor.BLUE;
            }

            @Override
            public Location getLobbySpawnLocation() {
                return new Location(CORUSCANT.getWorld(), 500, 8, -448, 90, 0);
            }

            @Override
            public ItemStack getCommandPostBeamItem() {
                // Blue wool.
                return new ItemStack(Material.WOOL, 1, (byte) 11);
            }

            @Override
            public ItemStack getCommandPostUnderAttackItem() {
                // Light blue wool.
                return new ItemStack(Material.WOOL, 1, (byte) 3);
            }

            @Override
            public Material getBulletHitEffectMaterial() {
                return Material.LAPIS_BLOCK;
            }
        }, RED {
            @Override
            public String getName() {
                return "team Red";
            }

            @Override
            public ChatColor getColor() {
                return ChatColor.RED;
            }

            @Override
            public Location getLobbySpawnLocation() {
                return new Location(CORUSCANT.getWorld(), 500, 8, -374, 90, 0);
            }

            @Override
            public ItemStack getCommandPostBeamItem() {
                // Red wool.
                return new ItemStack(Material.WOOL, 1, (byte) 14);
            }

            @Override
            public ItemStack getCommandPostUnderAttackItem() {
                // Pink wool.
                return new ItemStack(Material.WOOL, 1, (byte) 6);
            }

            @Override
            public Material getBulletHitEffectMaterial() {
                return Material.REDSTONE_BLOCK;
            }
        }, NEUTRAL {
            @Override
            public String getName() {
                return "Gaia";
            }

            @Override
            public ChatColor getColor() {
                return ChatColor.WHITE;
            }

            @Override
            public Location getLobbySpawnLocation() {
                return null;
            }

            @Override
            public ItemStack getCommandPostBeamItem() {
                return new ItemStack(Material.WOOL, 1, (byte) 0);
            }

            @Override
            public ItemStack getCommandPostUnderAttackItem() {
                return new ItemStack(Material.WOOL, 1, (byte) 0);
            }

            @Override
            public Material getBulletHitEffectMaterial() {
                return null;
            }
        };

        private int amountOfCommandPosts = 1;

        public int getAmountOfCommandPosts() {
            return amountOfCommandPosts;
        }

        public void setAmountOfCommandPosts(final int amount) {
            amountOfCommandPosts = amount;
        }

        public int getAmountOfMembers() {
            int counter = 0;
            for (final SWPlayer swPlayer : SWPlayers)
                if (swPlayer.getTeam() == this)
                    counter++;
            return counter;
        }

        public List<SWPlayer> getMembers() {
            final List<SWPlayer> membersOfTeam = new ArrayList<>();
            for (final SWPlayer swPlayer : SWPlayers)
                if (swPlayer.getTeam() == this)
                    membersOfTeam.add(swPlayer);
            return membersOfTeam;
        }

        public abstract String getName();
        public abstract ChatColor getColor();
        public abstract Location getLobbySpawnLocation();
        public abstract ItemStack getCommandPostBeamItem();
        public abstract ItemStack getCommandPostUnderAttackItem();
        public abstract Material getBulletHitEffectMaterial();
    }
}