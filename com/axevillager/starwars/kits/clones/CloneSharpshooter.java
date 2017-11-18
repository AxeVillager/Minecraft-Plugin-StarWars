package com.axevillager.starwars.kits.clones;

import com.axevillager.starwars.kits.Kit;
import com.axevillager.starwars.weapon.Weapon;
import org.bukkit.inventory.ItemStack;

/**
 * CloneSharpshooter created by Børre A. Opedal Lunde on 2017/11/14
 */

public class CloneSharpshooter extends Kit {

    public CloneSharpshooter() {

        final String name = "Sharpshooter";
        final KitType type = KitType.SHARPSHOOTER;
        final Weapon[] weapons = {Weapon.getWeapon("DC-15x")};
        final ItemStack[] armour = null;

        initializeKit(name, type, weapons, armour);
    }
}