package com.axevillager.starwars.kits;

import com.axevillager.starwars.player.SWPlayer;
import com.axevillager.starwars.weapon.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

import static com.axevillager.starwars.team.TeamManager.Teams;

/**
 * Kit created by Børre A. Opedal Lunde on 2017/11/14
 */

public abstract class Kit {

    public final static List<Kit> kits = new ArrayList<>();

    private String name;
    private KitType type;
    private Weapon[] weapons;
    private ItemStack[] armour;


    protected void initializeKit(String name, KitType type, Weapon[] weapons, ItemStack[] armour) {
        this.name = name;
        this.type = type;
        this.weapons = weapons;
        this.armour = armour;
        kits.add(this);
    }



    public static Kit getKit(final String name) {
        for (final Kit kit : kits) {
            final String kitFromListName = kit.getName();
            if (name.equalsIgnoreCase(kitFromListName))
                return kit;
        }
        return null;
    }



    public void giveKit(final SWPlayer swPlayer) {
        final Player player = swPlayer.getPlayer();
        final PlayerInventory inventory = player.getInventory();

        swPlayer.setKitType(type);
        inventory.clear();
        for (final Weapon weapon : weapons) weapon.giveWeapon(player);

        if (armour == null) {
            if (swPlayer.getTeam() == Teams.RED) {
                inventory.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                inventory.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                inventory.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                inventory.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
            } else {
                inventory.setHelmet(new ItemStack(Material.IRON_HELMET));
                inventory.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                inventory.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                inventory.setBoots(new ItemStack(Material.IRON_BOOTS));
            }
        } else {
            inventory.setHelmet(armour[0]);
            inventory.setChestplate(armour[1]);
            inventory.setLeggings(armour[2]);
            inventory.setBoots(armour[3]);
        }
    }



    public String getName() {
        return name;
    }



    public KitType getKitType() {
        return type;
    }



    public Weapon[] getWeapons() {
        return weapons;
    }



    public ItemStack[] getArmour() {
        return armour;
    }





    public enum KitType {
        INFANTRY {
            @Override
            public String getName() {
                return "Infantry";
            }
        }, SHARPSHOOTER {
            @Override
            public String getName() {
                return "Sharpshooter";
            }
        };

        public abstract String getName();
    }
}