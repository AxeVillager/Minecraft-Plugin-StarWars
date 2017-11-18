package com.axevillager.starwars.kits.clones;

import com.axevillager.starwars.kits.Kit;
import com.axevillager.starwars.weapon.Weapon;
import org.bukkit.inventory.ItemStack;

/**
 * CloneEngineer created by Børre A. Opedal Lunde on 2017/11/14
 */

public class CloneEngineer extends Kit {

    public CloneEngineer() {

        final String name = "Engineer";
        final KitType type = KitType.INFANTRY;
        final Weapon[] weapons = {Weapon.getWeapon("DP-23")};
        final ItemStack[] armour = null;

        initializeKit(name, type, weapons, armour);
    }
}