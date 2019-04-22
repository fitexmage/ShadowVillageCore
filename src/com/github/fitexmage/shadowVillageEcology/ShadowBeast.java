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

public class ShadowBeast extends ShadowLiving {
    private static final int id = 10002;
    private static final String name = "影灵";

    private double health = 200.0;
    private float speed = 0.8f;

    ShadowBeast() {
        super(UUID.randomUUID(), id, name, EntityControllers.createForType(EntityType.PIG), CitizensAPI.getNPCRegistry());

        count = 0;
        prepareCountDown = 0;
        teleportCountDown = 0;

        ShadowBeastTrait shadowBeastTrait = new ShadowBeastTrait("shadowBeastTrait");
        addTrait(shadowBeastTrait);
    }

    void spawn(boolean force) {
        prepare(force);
        setBukkitEntityType(getRandomType());
        spawn(Bukkit.getWorld("world").getSpawnLocation().add(0, 25, 0));
        setProtected(false);
        getBukkitEntity().setMaxHealth(health);
        getBukkitEntity().setHealth(health);
        getNavigator().getLocalParameters().speedModifier(speed);
    }

    private EntityType getRandomType() {
        int randomType = (int) (Math.random() * 3);
        if (randomType == 0) {
            return EntityType.PIG;
        } else if (randomType == 1) {
            return EntityType.SHEEP;
        } else {
            return EntityType.COW;
        }
    }

    @Override
    void action() {
        if (prepareCountDown > 0) {
            prepareCountDown--;
        } else {
            if (teleportCountDown == 0) {
                List<Player> realOnlinePlayers = Tool.getRealPlayers(getBukkitEntity().getWorld(), null);
                if (realOnlinePlayers.size() != 0) {
                    int random = (int) (Math.random() * realOnlinePlayers.size());
                    Player targetPlayer = realOnlinePlayers.get(random);
                    if (targetPlayer.getLocation().distance(Bukkit.getWorld("world").getSpawnLocation()) <= 30) {
                        count--;
                    } else {
                        teleportCountDown = (int) (Math.random() * maxTeleportCountDown) + basicTeleportCountDown;
                        teleport(targetPlayer);
                        getNavigator().setTarget(targetPlayer, true);
                    }
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
        if ((int) (Math.random() * 2) == 0) {
            ItemStack dropItem1 = new ItemStack(Material.DIAMOND_BLOCK, (int) (Math.random() * 3) + 1);
            getEntity().getWorld().dropItem(getEntity().getLocation(), dropItem1);
        }

        if ((int) (Math.random() * 5) == 0) {
            ItemStack dropItem2 = ShadowItem.getShadowSpiritBook();
            getEntity().getWorld().dropItem(getEntity().getLocation(), dropItem2);
        }
    }
}
