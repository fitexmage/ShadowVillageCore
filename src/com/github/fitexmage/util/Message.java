package com.github.fitexmage.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Message {
    public static boolean debug = false;

    private static String coreTitle = "§c[影之乡核心]";
    private static String violetTitle = "§5[小紫]";

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(coreTitle + message);
    }

    public static void broadcastMessage(String message) {
        Bukkit.getServer().broadcastMessage(coreTitle + message);
    }

    public static void violetBroadcastMessage(String message) {
        Bukkit.getServer().broadcastMessage(violetTitle + message);
    }

    public static void sendUnknown(CommandSender sender) {
        sendMessage(sender, "未知指令！");
    }

    public static void sendNoPermission(CommandSender sender) {
        sendMessage(sender, "你无权这样做！");
    }

    public static void entityBroadcastMessage(String name, String message) {
        Bukkit.getServer().broadcastMessage("<" + "§2" + name + "§f" + ">" + " " + message);
    }

    public static void entitySendMessage(String name, Player player, String message) {
        player.sendMessage("<" + "§2" + name + "§f" + ">" + " " + message);
    }
}
