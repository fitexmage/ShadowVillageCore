package com.github.fitexmage;

import com.github.fitexmage.util.Message;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.npc.CitizensNPC;
import net.citizensnpcs.npc.EntityControllers;
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

    private final double health = 5000.0;

    private Location targetLocation;

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
        getNavigator().getDefaultParameters().range(100.0f);
        getNavigator().getDefaultParameters().avoidWater(true);
    }

    void randomMove() {
        Location randomLocation = getRandomLocation();
        getNavigator().setTarget(randomLocation);
        targetLocation = randomLocation;
        getBukkitEntity().setHealth(health);
    }

    private Location getRandomLocation() {
        Location randomLocation = getStoredLocation().add((int) (Math.random() * 100) - 50, 0, (int) (Math.random() * 100) - 50);
        return randomLocation;
    }

    void reborn() {
        despawn();
        spawn();
        getNavigator().setTarget(getRandomLocation());
    }

    public void showLocation(Player player) {
        Message.sendMessage(player, "小紫正处于" + getStoredLocation().toString());
    }
}
