package com.axevillager.starwars.packets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

/**
 * Title created by Børre A. Opedal Lunde on 2017/11/04
 */

@SuppressWarnings({"ConstantConditions", "JavaReflectionMemberAccess"})
public class Title {


    private void sendPacket(final Player player, final Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private Class<?> getNMSClass(final String name) {
        final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



    public void sendTitle(final Player player, final String title, final String subtitle, final int in, final int stay, final int out) {
        try {
            final Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
            final Object enumSubtitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
            final Object titleText = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
            final Object subtitleText = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitle + "\"}");
            final Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"));
            final Object titlePacket = titleConstructor.newInstance(enumTitle, titleText);
            final Object subtitlePacket = titleConstructor.newInstance(enumSubtitle, subtitleText);

            sendTimePacket(player, in, stay, out);
            sendPacket(player, titlePacket);
            sendPacket(player, subtitlePacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void sendActionBar(final Player player, final String text) {
        try {
            final Object enumActionBar = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("ACTIONBAR").get(null);
            final Object actionBarText = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + text + "\"}");
            final Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"));
            final Object actionBarPacket = titleConstructor.newInstance(enumActionBar, actionBarText);

            sendPacket(player, actionBarPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void sendTimePacket(final Player player, final int in, final int stay, final int out) {
        try {
            final Constructor<?> timeConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(int.class, int.class, int.class);
            final Object timePacket = timeConstructor.newInstance(in, stay, out);

            sendPacket(player, timePacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}