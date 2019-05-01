package com.github.fitexmage;

import com.github.fitexmage.util.Message;

import com.github.fitexmage.util.NBTUtil;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.npc.CitizensNPC;
import net.citizensnpcs.npc.EntityControllers;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Violet extends CitizensNPC {
    private static final int id = 99999;
    private static final String name = "小紫";

    private final double health = 1000.0;

    private int fightCountDown;
    private int angryDegree;

    Violet() {
        super(UUID.randomUUID(), id, name, EntityControllers.createForType(EntityType.PLAYER), CitizensAPI.getNPCRegistry());
    }

    void spawn() {
        spawn(Bukkit.getWorld("world").getSpawnLocation());
        setProtected(false);
        getBukkitEntity().setMaxHealth(health);
        getBukkitEntity().setHealth(health);
        getTrait(Equipment.class).set(Equipment.EquipmentSlot.HELMET, new ItemStack(Material.DIAMOND_HELMET, 1));
        getTrait(Equipment.class).set(Equipment.EquipmentSlot.CHESTPLATE, new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
        getTrait(Equipment.class).set(Equipment.EquipmentSlot.LEGGINGS, new ItemStack(Material.DIAMOND_LEGGINGS, 1));
        getTrait(Equipment.class).set(Equipment.EquipmentSlot.BOOTS, new ItemStack(Material.DIAMOND_BOOTS, 1));
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
        sword = NBTUtil.getNBTTagItem(sword, new NBTTagCompound[]{NBTUtil.attackTag(9999)});
        getTrait(Equipment.class).set(Equipment.EquipmentSlot.HAND, sword);
        getNavigator().getDefaultParameters().range(200.0f);
        getNavigator().getDefaultParameters().avoidWater(true);
        fightCountDown = 0;
        angryDegree = 0;
    }

    private Location getRandomLocation() {
        Location randomLocation = getBukkitEntity().getLocation().add((int) (Math.random() * 100) - 50, 0, (int) (Math.random() * 100) - 50);
        return randomLocation;
    }

    void randomMove() {
        getNavigator().setTarget(getRandomLocation());
    }

    void reborn() {
        despawn();
        spawn();
        randomMove();
        fightCountDown = 0;
        angryDegree = 0;
    }

    public void showLocation(Player player) {
        Message.sendMessage(player, "小紫正处于" + getBukkitEntity().getLocation().toString());
    }

    void heal() {
        getBukkitEntity().setHealth(health);
    }

    public int getFightCountDown() {
        return fightCountDown;
    }

    public void setFightCountDown(int fightCountDown) {
        this.fightCountDown = fightCountDown;
    }

    public int getAngryDegree() {
        return angryDegree;
    }

    public void setAngryDegree(int angryDegree) {
        this.angryDegree = angryDegree;
    }
}
