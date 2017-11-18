package com.axevillager.starwars.weapon;

import com.axevillager.starwars.player.SWPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Weapon created by Børre A. Opedal Lunde on 2017/11/02
 */

public abstract class Weapon {

    public static final List<Weapon> weaponList = new ArrayList<>();

    private String name;
    private double damage;
    private double headshotDamage;
    private double feetDamage;
    private int ammo;
    private ItemStack item;
    private Particle shootParticle;
    private EntityType bullet;
    private int bulletsAmount;
    private Particle bulletParticle;
    private double bulletSpeed;
    private int fireRate; // milli seconds between shots
    private double range;
    private int zoomAmount;
    private double accuracy;
    private double bonusSneakAccuracy;
    private double bonusAimAccuracy;
    private boolean bulletHasGravity;
    private Sound shootSound;
    private int reloadTime;
    private Sound reloadStartSound;
    private Sound reloadFinishedSound;
    private Sound aimSound;
    private Sound outOfAmmoSound;


    protected void initializeWeapon(String name, double damage, double headshotDamage, double feetDamage, int ammo, ItemStack item, Particle shootParticle, EntityType bullet, int bulletsAmount,
                                    Particle bulletParticle, double bulletSpeed, int fireRate, double range, int zoomAmount, double accuracy, double sneakAccuracy,
                                    double aimAccuracy, boolean bulletHasGravity, Sound shootSound, int reloadTime, Sound reloadStartSound, Sound reloadFinishedSound, Sound aimSound, Sound outOfAmmoSound) {
        this.name = name;
        this.damage = damage;
        this.headshotDamage = headshotDamage;
        this.feetDamage = feetDamage;
        this.ammo = ammo;
        this.item = item;
        this.shootParticle = shootParticle;
        this.bullet = bullet;
        this.bulletsAmount = bulletsAmount;
        this.bulletParticle = bulletParticle;
        this.bulletSpeed = bulletSpeed;
        this.fireRate = fireRate;
        this.range = range;
        this.zoomAmount = zoomAmount;
        this.accuracy = accuracy;
        this.bonusSneakAccuracy = sneakAccuracy;
        this.bonusAimAccuracy = aimAccuracy;
        this.bulletHasGravity = bulletHasGravity;
        this.shootSound = shootSound;
        this.reloadTime = reloadTime;
        this.reloadStartSound = reloadStartSound;
        this.reloadFinishedSound = reloadFinishedSound;
        this.aimSound = aimSound;
        this.outOfAmmoSound = outOfAmmoSound;
        weaponList.add(this);
    }



    public void shoot(final Player player, final double accuracy) {
        player.getWorld().spawnParticle(shootParticle, player.getLocation(), 1);
        for (int i = 0; i < bulletsAmount; i++) {
            shootOnce(player, accuracy);
        }
    }



    private void shootOnce(final Player player, final double accuracy) {
        final Vector direction = player.getEyeLocation().getDirection().normalize().add(calculateAccuracyVector(accuracy));
        final Projectile projectile = (Projectile) player.getWorld().spawnEntity(player.getEyeLocation(), bullet);
        projectile.setShooter(player);
        projectile.setCustomName(name);
        projectile.setGravity(bulletHasGravity);
        projectile.setVelocity(direction.multiply(bulletSpeed));
    }



    private double calculateAccuracy(double number) {
        if (number < 0) number = 0;
        final Random random = new Random();
        final double low = -number;
        return low + (number - low) * random.nextDouble();
    }



    private Vector calculateAccuracyVector(double number) {
        return new Vector(calculateAccuracy(number), calculateAccuracy(number), calculateAccuracy(number));
    }



    public void aim(final SWPlayer swPlayer) {
        final Player player = swPlayer.getPlayer();
        final PotionEffect aimEffect = new PotionEffect(PotionEffectType.SLOW, 20 * 1800, zoomAmount);
        player.addPotionEffect(aimEffect);
        playAimSound(player);
        swPlayer.setAiming(true);
    }



    public void giveWeapon(final Player player) {
        final String weaponName = name + " «" + ammo + "»";
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(weaponName);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }



    public static Weapon getWeapon(final String name) {
        final String weaponName = stripWeaponName(name);
        for (final Weapon weapon : weaponList) {
            final String weaponFromListName = ChatColor.stripColor(weapon.getName());
            if (weaponName.equals(weaponFromListName))
                return weapon;
        }
        return null;
    }



    private static String stripWeaponName(final String name) {
        final char stripChar = '«';
        final char[] nameAsCharArray = name.toCharArray();
        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < nameAsCharArray.length; i++) {
            final char currentChar = nameAsCharArray[i];

            // Skip colour and formatting codes.
            if (currentChar == '§')
                i += 2;

            // End when the strip character is reached.
            if (currentChar == stripChar)
                return builder.toString().trim();

            builder.append(nameAsCharArray[i]);
        }

        return builder.toString().trim();
    }



    public static int getAmmoFromTitle(String name) {
        final char beginChar = '«';
        final char endChar = '»';
        name = name.substring(name.indexOf(beginChar) + 1);
        name = name.substring(0, name.indexOf(endChar));
        return Integer.parseInt(name);
    }



    public void setAmmo(final ItemStack item, final int amount) {
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name + " «" + amount + "»");
        item.setItemMeta(itemMeta);
    }



    public String getName() {
        return name;
    }



    public double getDamage() {
        return damage;
    }



    public double getHeadshotDamage() {
        return headshotDamage;
    }



    public double getFeetDamage() {
        return feetDamage;
    }



    public int getAmmo() {
        return ammo;
    }



    public ItemStack getItem() {
        return item;
    }



    public EntityType getBullet() {
        return bullet;
    }



    public int getBulletsAmount() {
        return bulletsAmount;
    }



    public double getBulletSpeed() {
        return bulletSpeed;
    }



    public int getFireRate() {
        return fireRate;
    }



    public double getRange() {
        return range;
    }



    public int getZoomAmount() {
        return zoomAmount;
    }



    public void playShootSound(final Player player) {
        player.getWorld().playSound(player.getLocation(), shootSound, 5, 1);
    }



    public void playReloadStartSound(final Player player) {
        player.getWorld().playSound(player.getLocation(), reloadStartSound, 2, 1);
    }



    public void playReloadFinishedSound(final Player player) {
        player.getWorld().playSound(player.getLocation(), reloadFinishedSound, 2, 1);
    }



    public void playAimSound(final Player player) {
        player.getWorld().playSound(player.getLocation(), aimSound, 1, 1);
    }



    public void playOutOfAmmoSound(final Player player) {
        player.playSound(player.getLocation(), outOfAmmoSound, 1, 1);
    }



    public double getAccuracy() {
        return accuracy;
    }



    public double getBonusSneakAccuracy() {
        return bonusSneakAccuracy;
    }



    public double getBonusAimAccuracy() {
        return bonusAimAccuracy;
    }



    public Particle getShootParticle() {
        return shootParticle;
    }



    public Particle getBulletParticle() {
        return bulletParticle;
    }



    public int getReloadTime() {
        return reloadTime;
    }
}