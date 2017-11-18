package com.axevillager.starwars.kits.clones;

import com.axevillager.starwars.kits.Kit;
import com.axevillager.starwars.weapon.Weapon;
import org.bukkit.inventory.ItemStack;

/**
 * CloneTrooper created by Børre A. Opedal Lunde on 2017/11/14
 */

public class CloneTrooper extends Kit {

    public CloneTrooper() {

        final String name = "Trooper";
        final KitType type = KitType.INFANTRY;
        final Weapon[] weapons = {Weapon.getWeapon("DC-15A")};
        final ItemStack[] armour = null;

        initializeKit(name, type, weapons, armour);
    }
}