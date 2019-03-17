package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.shadowVillageBlackMarket.ShadowItem;
import com.github.fitexmage.util.Message;
import com.github.fitexmage.util.Tool;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.npc.EntityControllers;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ShadowBeast extends ShadowEntity {
    private static final int id = 10002;
    private static final String name = "影灵";
    public static int despawnReason = 0;

    private final double health = 300.0;
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

    void spawn(boolean force, EntityType type) {
        if (force) {
            count = 5;
            prepareCountDown = 1;
        } else {
            List<Player> realOnlinePlayers = Tool.getRealPlayers(Bukkit.getWorld("world"));
            count = (int) (Math.random() * realOnlinePlayers.size()) + 1;
            prepareCountDown = (int) (Math.random() * maxPrepareCountDown) + 1;
        }
        teleportCountDown = 0;
        despawnReason = 0;

        spawn(Bukkit.getWorld("world").getSpawnLocation().add(0, 35, 0));
        setBukkitEntityType(type);
        setProtected(false);
        getBukkitEntity().setMaxHealth(health);
        getBukkitEntity().setHealth(health);
        getNavigator().getLocalParameters().speedModifier(speed);
    }

    @Override
    void action() {
        if (prepareCountDown > 0) {
            prepareCountDown--;
        } else {
            if (teleportCountDown == 0) {
                List<Player> realOnlinePlayers = Tool.getRealPlayers(Bukkit.getWorld("world"));
                if (realOnlinePlayers.size() != 0) {
                    teleportCountDown = (int) (Math.random() * maxTeleportCountDown) + 3;
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

    @Override
    void teleport(Player player) {
        Location location = new Location(player.getWorld(),
                player.getLocation().getX() + (int) (Math.random() * 10) - 5,
                player.getLocation().getY(),
                player.getLocation().getZ() + (int) (Math.random() * 10) - 5,
                0f,
                0f);
        teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
        if (Message.debug) {
            Message.broadcastMessage("传送至" + player.getDisplayName());
        }
    }

    @Override
    public void dropItem() {
        if ((int) (Math.random() * 3) == 0) {
            ItemStack dropItem1 = new ItemStack(Material.DIAMOND_BLOCK, (int) (Math.random() * 3) + 1);
            getEntity().getWorld().dropItem(getEntity().getLocation(), dropItem1);
        }

        if ((int) (Math.random() * 5) == 0) {
            ItemStack dropItem2 = ShadowItem.getShadowSpiritBook();
            getEntity().getWorld().dropItem(getEntity().getLocation(), dropItem2);
        }
    }
}
