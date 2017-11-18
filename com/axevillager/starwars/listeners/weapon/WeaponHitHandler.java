package com.axevillager.starwars.listeners.weapon;

import com.axevillager.starwars.events.player.SWPlayerKilledByEntityEvent;
import com.axevillager.starwars.events.weapon.WeaponHitEvent;
import com.axevillager.starwars.player.SWPlayer;
import com.axevillager.starwars.weapon.Weapon;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;

import static com.axevillager.starwars.player.SWPlayer.getSWPlayer;
import static com.axevillager.starwars.weapon.Weapon.getWeapon;

/**
 * BulletListener created by Børre A. Opedal Lunde on 2017/11/11
 */

public class WeaponHitHandler implements Listener {


    /*
     Listen to call for the WeaponHitEvent.
     */
    @EventHandler
    public void onProjectileHit(final ProjectileHitEvent event) {
        final Projectile projectile = event.getEntity();
        final ProjectileSource projectileSource = projectile.getShooter();

        if (!(projectileSource instanceof Player))
            return;

        final Player player = (Player) projectileSource;
        final SWPlayer swPlayer = getSWPlayer(player.getUniqueId());
        final Block hitBlock = event.getHitBlock();
        final Entity hitEntity = event.getHitEntity();

        Bukkit.getPluginManager().callEvent(new WeaponHitEvent(swPlayer, projectile, hitEntity, hitBlock));
    }



    /*
     When you hit something with a weapon.
     */
    @EventHandler
    public void onWeaponHit(final WeaponHitEvent event) {
        final Material teamMaterial = event.getShooter().getTeam().getBulletHitEffectMaterial();
        final Projectile bullet = event.getProjectile();
        final Location bulletLocation = bullet.getLocation();
        final Weapon weapon = getWeapon(bullet.getCustomName());
        final Entity hitEntity = event.getHitEntity();
        final Block hitBlock = event.getHitBlock();

        if (weapon == null)
            return;

        // TODO Calculate if bullet traveled past its range.

        showParticleEffects(hitBlock, bulletLocation, teamMaterial);

        if (hitEntity == null || !(hitEntity instanceof Player))
            return;

        final SWPlayer victim = getSWPlayer(hitEntity.getUniqueId());
        final SWPlayer shooter = event.getShooter();

        if (shooter.isOnSameTeam(victim) || victim == null)
            return;

        final Player player = victim.getPlayer();
        final Projectile projectile = event.getProjectile();
        final Player shooterPlayer = shooter.getPlayer();
        player.damage(calculateDamage(projectile, player, weapon));
        playHitSound(shooterPlayer);
        projectile.remove();

        if (player.getLastDamage() >= player.getHealth())
            Bukkit.getPluginManager().callEvent(new SWPlayerKilledByEntityEvent(victim,
                    shooterPlayer, player.getLastDamage(), player.getLastDamageCause().getCause()));
    }



    private void showParticleEffects(final Block hitBlock, final Location bulletlocation, final Material material) {
        if (hitBlock != null) {
            hitBlock.getWorld().playEffect(hitBlock.getLocation(), Effect.STEP_SOUND, material);
        } else {
            bulletlocation.getWorld().playEffect(bulletlocation, Effect.STEP_SOUND, material);
        }
    }



    private void playHitSound(final Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
    }



    private double calculateDamage(final Projectile projectile, final Player victim, final Weapon weapon) {
        final double projectileY = projectile.getLocation().getY();
        final double playerY = victim.getLocation().getY();
        final double neckHeight = playerY + 1.36;

        if (projectileY > neckHeight)
            return weapon.getHeadshotDamage();
        return weapon.getDamage();
    }
}