package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.util.Message;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.npc.EntityControllers;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShadowBeast extends ShadowEntity {
    public static final int id = 10002;
    private static final String name = "影灵";
    private final double health = 200.0;
    private final float speed = 1.0f;

    private final int maxPrepareCountDown = (int) (1200 / ShadowManSpawner.interval); // 60秒
    private final int maxTeleportCountDown = (int) (400 / ShadowManSpawner.interval); // 20秒

    int count;
    private int prepareCountDown;
    private int teleportCountDown;

    ShadowBeast(EntityType type) {
        super(UUID.randomUUID(), id, name, EntityControllers.createForType(type), CitizensAPI.getNPCRegistry());

        count = 0;
        prepareCountDown = 0;
        teleportCountDown = 0;

        ShadowBeastTrait shadowBeastTrait = new ShadowBeastTrait("shadowBeastTrait");
        addTrait(shadowBeastTrait);
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

        spawn(new Location(Bukkit.getWorld("world"), -281, 69, -25, 0f, 0f));
        setProtected(false);
        getBukkitEntity().setMaxHealth(health);
        getBukkitEntity().setHealth(health);
        getNavigator().getLocalParameters().speedModifier(speed);

        Message.broadcastMessage("§0影§c即将降临。");
    }

    void forcePrepare() {
        count = 5;
        prepareCountDown = 1;

        spawn(new Location(Bukkit.getWorld("world"), -281, 69, -25, 0f, 0f));
        setProtected(false);
        getBukkitEntity().setMaxHealth(health);
        getBukkitEntity().setHealth(health);
        getNavigator().getLocalParameters().speedModifier(speed);

        Message.broadcastMessage("§0影§c即将降临。");
    }

    void action() {
        if (prepareCountDown > 0) {
            prepareCountDown--;
        } else {
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
                    getNavigator().setTarget(targetPlayer, true);
                } else {
                    count = 0;
                }
            } else {
                teleportCountDown--;
                if (teleportCountDown == 0) {
                    count--;
                }
            }
        }
    }

    void teleport(Player player) {
        Location location = new Location(player.getWorld(),
                player.getLocation().getX() + (int) (Math.random() * 20) - 10,
                player.getLocation().getY(),
                player.getLocation().getZ() + (int) (Math.random() * 20) - 10,
                0f,
                0f);
        teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
        if (Message.debug) {
            Message.broadcastMessage("传送至" + player.getDisplayName());
        }
    }
}
