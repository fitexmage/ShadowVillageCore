package com.github.fitexmage.util;

import net.minecraft.server.v1_7_R4.*;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBTUtil {
    public static ItemStack getNBTTagItem(ItemStack item, NBTTagCompound[] tags) {
        net.minecraft.server.v1_7_R4.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = NMSItem.hasTag() ? NMSItem.getTag() : new NBTTagCompound();
        NBTTagList modifiers = compound.hasKey("AttributeModifiers") ? (NBTTagList)compound.get("AttributeModifiers") : new NBTTagList();
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

    public static NBTTagCompound healthTag(int health) {
        NBTTagCompound damageCompound = new NBTTagCompound();
        damageCompound.set("AttributeName", new NBTTagString("generic.maxHealth"));
        damageCompound.set("Name", new NBTTagString("health"));
        damageCompound.set("Amount", new NBTTagInt(health));
        damageCompound.set("Operation", new NBTTagInt(0));
        damageCompound.set("UUIDLeast", new NBTTagInt(2));
        damageCompound.set("UUIDMost", new NBTTagInt(2));
        return damageCompound;
    }

    public static NBTTagCompound resistantceTag(int chance) {
        NBTTagCompound damageCompound = new NBTTagCompound();
        damageCompound.set("AttributeName", new NBTTagString("generic.knockbackResistance"));
        damageCompound.set("Name", new NBTTagString("resistance"));
        damageCompound.set("Amount", new NBTTagInt(chance));
        damageCompound.set("Operation", new NBTTagInt(0));
        damageCompound.set("UUIDLeast", new NBTTagInt(2));
        damageCompound.set("UUIDMost", new NBTTagInt(2));
        return damageCompound;
    }

    public static NBTTagCompound speedTag(double speed) {
        NBTTagCompound damageCompound = new NBTTagCompound();
        damageCompound.set("AttributeName", new NBTTagString("generic.movementSpeed"));
        damageCompound.set("Name", new NBTTagString("speed"));
        damageCompound.set("Amount", new NBTTagDouble(speed));
        damageCompound.set("Operation", new NBTTagInt(1));
        damageCompound.set("UUIDLeast", new NBTTagInt(3));
        damageCompound.set("UUIDMost", new NBTTagInt(3));
        return damageCompound;
    }
}
