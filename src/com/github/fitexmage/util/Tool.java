package com.github.fitexmage.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Tool {

    public static int getNumber(String number) {
        Pattern pattern = Pattern.compile("[0-9]*");
        if (pattern.matcher(number).matches()) {
            return Integer.parseInt(number);
        }
        return 1;
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

    public static void damagePlayer(Player player, int damage){
        if (player.getHealth() > damage) {
            player.setHealth(player.getHealth() - damage);
        } else {
            player.setHealth(0);
        }
    }
}
