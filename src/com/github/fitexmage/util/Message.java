package com.github.fitexmage.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class Message {
    public static boolean debug = false;

    private static String kernelTitle = "§c[影之乡核心]";

    public static void sendPlayerMessage(CommandSender sender, String message) {
        sender.sendMessage(kernelTitle + message);
    }

    public static void broadcastMessage(String message) {
        Bukkit.getServer().broadcastMessage(kernelTitle + message);
    }

    public static void sendUnknown(CommandSender sender) {
        sendPlayerMessage(sender, "未知指令！");
    }

    public static void sendNoPermission(CommandSender sender) {
        sendPlayerMessage(sender, "你无权这样做！");
    }
}
