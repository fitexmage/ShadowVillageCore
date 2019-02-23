package com.github.fitexmage.util;

import net.minecraft.server.v1_7_R4.*;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBTUtil {
    public static ItemStack getNBTTagItem(ItemStack item, NBTTagCompound[] tags) {
        net.minecraft.server.v1_7_R4.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (NMSItem.hasTag()) ? NMSItem.getTag() : new NBTTagCompound();
        NBTTagList modifiers = new NBTTagList();
        for (NBTTagCompound tag : tags) {
            modifiers.add(tag);
        }
        compound.set("AttributeModifiers", modifiers);
        NMSItem.setTag(compound);
        item = CraftItemStack.asBukkitCopy(NMSItem);
        return item;
    }

    public static ItemStack getUnbreakableItem(ItemStack item) {
        net.minecraft.server.v1_7_R4.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (NMSItem.hasTag()) ? NMSItem.getTag() : new NBTTagCompound();
        compound.set("Unbreakable", new NBTTagByte((byte) 1));
        NMSItem.setTag(compound);
        item = CraftItemStack.asBukkitCopy(NMSItem);
        return item;
    }

    public static NBTTagCompound damageTag(int damage) {
        NBTTagCompound damageCompound = new NBTTagCompound();
        damageCompound.set("AttributeName", new NBTTagString("generic.attackDamage"));
        damageCompound.set("Name", new NBTTagString("damage"));
        damageCompound.set("Amount", new NBTTagInt(damage));
        damageCompound.set("Operation", new NBTTagInt(0));
        damageCompound.set("UUIDLeast", new NBTTagInt(1));
        damageCompound.set("UUIDMost", new NBTTagInt(1));
        return damageCompound;
    }


}
