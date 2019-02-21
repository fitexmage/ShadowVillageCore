package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.util.Message;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.npc.EntityController;
import net.citizensnpcs.npc.EntityControllers;
import net.citizensnpcs.trait.LookClose;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.*;

public class ShadowMan extends ShadowEntity {
    public static final int id = 10001;
    private static final String name = "影者";
    private final double health = 200.0;

    private final int maxPrepareCountDown = (int) (1200 / ShadowManSpawner.interval); // 60秒
    private final int maxTeleportCountDown = (int) (200 / ShadowManSpawner.interval); // 10秒

    int count;
    private int prepareCountDown;
    private int teleportCountDown;

    private Player targetPlayer;
    private double targetPlayerX, targetPlayerY, targetPlayerZ;

    ShadowMan() {
        super(UUID.randomUUID(), id, name, EntityControllers.createForType(EntityType.PLAYER), CitizensAPI.getNPCRegistry());

        targetPlayer = null;
        count = 0;
        prepareCountDown = 0;
        teleportCountDown = 0;

        ShadowManTrait shadowManTrait = new ShadowManTrait("shadowManTrait");
        addTrait(shadowManTrait);
    }

    void prepare() {
        List<Player> onlinePlayers = Bukkit.getWorld("world").getPlayers();
        List<Player> realOnlinePlayers = new ArrayList<>();
        for (Player player : onlinePlayers) {
            if (!player.hasMetadata("NPC")) {
                realOnlinePlayers.add(player);
            }
        }
        count = (int) (Math.random() * realOnlinePlayers.size()) + 1;
        prepareCountDown = (int) (Math.random() * maxPrepareCountDown) + 1;

        spawn(Bukkit.getWorld("world").getSpawnLocation().subtract(0, 10, 0));
        getTrait(LookClose.class).lookClose(true);
        getTrait(Equipment.class).set(Equipment.EquipmentSlot.HELMET, new ItemStack(Material.SKULL_ITEM, 1, (short) 1));
        setProtected(false);
        getBukkitEntity().setMaxHealth(health);
        getBukkitEntity().setHealth(health);

        Message.broadcastMessage("§0影§c即将降临。");
    }

    void forcePrepare() {
        count = 5;
        prepareCountDown = 1;

        spawn(Bukkit.getWorld("world").getSpawnLocation().subtract(0, 10, 0));
        getTrait(LookClose.class).lookClose(true);
        getTrait(Equipment.class).set(Equipment.EquipmentSlot.HELMET, new ItemStack(Material.SKULL_ITEM, 1, (short) 1));
        setProtected(false);
        getBukkitEntity().setMaxHealth(health);
        getBukkitEntity().setHealth(health);

        Message.broadcastMessage("§0影§c即将降临。");
    }

    void action() {
        if (prepareCountDown == 0) {
            if (teleportCountDown == 0) {
                List<Player> onlinePlayers = Bukkit.getWorld("world").getPlayers();
                List<Player> realOnlinePlayers = new ArrayList<>();
                for (Player player : onlinePlayers) {
                    if (!player.hasMetadata("NPC")) {
                        realOnlinePlayers.add(player);
                    }
                }
                if (realOnlinePlayers.size() != 0) {
                    teleportCountDown = (int) (Math.random() * maxTeleportCountDown) + 1;
                    int random = (int) (Math.random() * realOnlinePlayers.size());
                    Player targetPlayer = realOnlinePlayers.get(random);
                    teleport(targetPlayer);
                } else {
                    count = 0;
                }
            } else {
                if (checkPlayerMove()) {
                    teleportCountDown = 0;
                    count--;
                    detect();
                    if (Message.debug) {
                        Message.broadcastMessage("检测到移动实体。"); //测试用
                    }
                } else {
                    teleportCountDown--;
                    if (teleportCountDown == 0) {
                        count--;
                    }
                }
            }
        } else {
            prepareCountDown--;
        }
    }

    void teleport(Player player) {
        recordTargetPlayer(player);
        Vector direction = player.getLocation().getDirection();
        Location location = new Location(player.getWorld(),
                player.getLocation().getX() - 3 * direction.getX(),
                player.getLocation().getY(),
                player.getLocation().getZ() - 3 * direction.getZ(),
                0f,
                0f);
        teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
        if (Message.debug) {
            Message.broadcastMessage("传送至" + targetPlayer.getDisplayName());
        }
    }

    private void recordTargetPlayer(Player player) {
        targetPlayer = player;
        Location location = targetPlayer.getLocation();
        targetPlayerX = location.getX();
        targetPlayerY = location.getY();
        targetPlayerZ = location.getZ();
    }

    private boolean checkPlayerMove() {
        Location location = targetPlayer.getLocation();
        return !((targetPlayerX == location.getX()) &&
                (targetPlayerY == location.getY()) &&
                (targetPlayerZ == location.getZ()));
    }

    private void detect() {
        Message.broadcastMessage(targetPlayer.getDisplayName() + "被影者发现了!");
        randomShadowAttack(targetPlayer);
    }
}
