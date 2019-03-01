package com.github.fitexmage.shadowVillageBlackMarket;


import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ShadowItem {
    public static ItemStack shadowStone(int amount) {
        ItemStack shadowStone = new ItemStack(Material.EMERALD, amount);
        ItemMeta meta = shadowStone.getItemMeta();
        meta.setDisplayName("§5影之石");
        meta.setLore(Collections.singletonList("影之乡货币"));
        shadowStone.setItemMeta(meta);
        return shadowStone;
    }

    public static boolean isShadowStone(ItemStack item) {
        if (item.getType().equals(Material.EMERALD)) {
            if (item.hasItemMeta() && item.getItemMeta().getLore().get(0).equals("影之乡货币")) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack shadowSoulBook() {
        ItemStack shadowSoulBook = new ItemStack(Material.BOOK, 1);
        ItemMeta itemMeta = shadowSoulBook.getItemMeta();
        itemMeta.setDisplayName("§5影魂之书");
        itemMeta.setLore(Collections.singletonList("以虚空为影，以力量为魂。"));
        shadowSoulBook.setItemMeta(itemMeta);
        return shadowSoulBook;
    }

    public static boolean isShadowSoulBook(ItemStack item) {
        if (item.getType().equals(Material.BOOK)) {
            if (item.hasItemMeta() && item.getItemMeta().getLore().get(0).equals("以虚空为影，以力量为魂。")) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack shadowSpiritBook() {
        ItemStack shadowSpiritBook = new ItemStack(Material.BOOK, 1);
        ItemMeta itemMeta = shadowSpiritBook.getItemMeta();
        itemMeta.setDisplayName("§0影魄之书");
        itemMeta.setLore(Collections.singletonList("以混沌为影，以源能为魄。"));
        shadowSpiritBook.setItemMeta(itemMeta);
        return shadowSpiritBook;
    }

    public static boolean isShadowSpiritBook(ItemStack item) {
        if (item.getType().equals(Material.BOOK)) {
            if (item.hasItemMeta() && item.getItemMeta().getLore().get(0).equals("以混沌为影，以源能为魄。")) {
                return true;
            }
        }
        return false;
    }
}