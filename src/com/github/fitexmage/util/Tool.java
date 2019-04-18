package com.github.fitexmage.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

public class Tool {

    public static HashSet<String> fakeSet = new HashSet<>();

    public static int getNumber(String number) {
        if(number.length() < 10){
            Pattern pattern = Pattern.compile("[0-9]*");
            if (pattern.matcher(number).matches()) {
                return Integer.parseInt(number);
            }
        }
        return 1;
    }

    public static List<Player> getRealPlayers(World world) {
        List<Player> realOnlinePlayers = new ArrayList<>();
        for (Player player : world.getPlayers()) {
            if (!player.hasMetadata("NPC")) {
                realOnlinePlayers.add(player);
            }
        }
        return realOnlinePlayers;
    }

    public static Player getPlayer(String name) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    public static List<ItemStack> getPlayerItems(Player player) {
        List<ItemStack> playerItems = new ArrayList<>();
        for (ItemStack item : player.getInventory()) {
            if (item != null && item.getType() != Material.AIR) {
                playerItems.add(item);
            }
        }
        return playerItems;
    }

    public static void damagePlayer(Player player, int damage) {
        if (player.getHealth() > damage) {
            player.setHealth(player.getHealth() - damage);
        } else {
            player.setHealth(0);
        }
    }

    public static void hidePlayerList() {
        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            sendAllPlayerPacket(onlinePlayer.getPlayerListName(), false, 0);
        }

        for (String fakePlayerName : fakeSet) {
            sendAllPlayerPacket("§2" + fakePlayerName, false, 0);
        }
    }

    public static void showPlayerList() {
        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            sendAllPlayerPacket(onlinePlayer.getPlayerListName(), true, ((CraftPlayer) onlinePlayer).getHandle().ping);
        }

        for (String fakeName : fakeSet) {
            sendAllPlayerPacket("§2" + fakeName, true, 10);
        }
    }

    private static void sendAllPlayerPacket(String playerName, boolean online, int ping) {
        PacketContainer playerPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        playerPacket.getStrings().write(0, playerName);
        playerPacket.getBooleans().write(0, online);
        playerPacket.getIntegers().write(0, ping);

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, playerPacket);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
