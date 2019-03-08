package com.github.fitexmage.shadowVillageBlackMarket;

import com.github.fitexmage.util.NBTUtil;

import net.minecraft.server.v1_7_R4.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ShadowItem {
    public static ItemStack getShadowStone(int amount) {
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

    public static ItemStack getShadowSoulBook() {
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

    public static ItemStack getShadowSpiritBook() {
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

    public static ItemStack getServerEquipment(int type, boolean random) {
        GambleItemInfo gambleItemInfo = GambleItemInfo.getServerEquipmentInfo(type);
        GambleEnchantInfo[] gambleEnchantInfos = GambleEnchantInfo.getGambleEnchants(type);
        ItemStack serverEquipment = new ItemStack(gambleItemInfo.getMaterial());

        ItemMeta meta = serverEquipment.getItemMeta();
        for (GambleEnchantInfo gambleEnchantInfo : gambleEnchantInfos) {
            if (random) {
                meta.addEnchant(gambleEnchantInfo.getEnchantment(), (int) (Math.random() * 5) + 6, true);
            } else {
                meta.addEnchant(gambleEnchantInfo.getEnchantment(), 10, true);
            }
        }
        if (!random) {
            if (type == 3 || type == 4 || type == 5 || type == 6) {
                meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 20, true);
                meta.addEnchant(Enchantment.THORNS, 30, true);
            }
        }
        meta.setDisplayName(gambleItemInfo.getItemName());
        serverEquipment.setItemMeta(meta);

        if (type == 1) {
            serverEquipment = NBTUtil.getNBTTagItem(serverEquipment, new NBTTagCompound[]{NBTUtil.damageTag(9999)});
        } else if (type == 3 ||
                type == 4 ||
                type == 5 ||
                type == 6) {
            serverEquipment = NBTUtil.getNBTTagItem(serverEquipment, new NBTTagCompound[]{NBTUtil.healthTag(100)});

            if (type == 4) {
                serverEquipment = NBTUtil.getNBTTagItem(serverEquipment, new NBTTagCompound[]{NBTUtil.resistantceTag(1)});
            }
            if (type == 6) {
                serverEquipment = NBTUtil.getNBTTagItem(serverEquipment, new NBTTagCompound[]{NBTUtil.speedTag(0.5)});
            }
        }
        serverEquipment = NBTUtil.getUnbreakableItem(serverEquipment);
        return serverEquipment;
    }
}