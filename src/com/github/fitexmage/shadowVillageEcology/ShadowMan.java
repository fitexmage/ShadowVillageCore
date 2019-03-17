package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.shadowVillageBlackMarket.ShadowItem;
import com.github.fitexmage.util.Message;
import com.github.fitexmage.util.Tool;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.trait.Equipment;
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
    private static final int id = 10001;
    private static final String name = "影者";
    private final double health = 300.0;

    private final int maxPrepareCountDown = (int) (1200 / ShadowManSpawner.interval); // 60秒
    private final int maxTeleportCountDown = (int) (200 / ShadowManSpawner.interval); // 10秒

    int count;
    private int prepareCountDown;
    private int teleportCountDown;

    private Player targetPlayer;
    private Location recordLocation;

    ShadowMan() {
        super(UUID.randomUUID(), id, name, EntityControllers.createForType(EntityType.PLAYER), CitizensAPI.getNPCRegistry());

        count = 0;
        prepareCountDown = 0;
        teleportCountDown = 0;

        targetPlayer = null;
        recordLocation = null;

        ShadowManTrait shadowManTrait = new ShadowManTrait("shadowManTrait");
        addTrait(shadowManTrait);
    }

    void spawn(boolean force) {
        if (force) {
            count = 5;
            prepareCountDown = 1;
        } else {
            List<Player> realOnlinePlayers = Tool.getRealPlayers(Bukkit.getWorld("world"));
            count = (int) (Math.random() * realOnlinePlayers.size()) + 1;
            prepareCountDown = (int) (Math.random() * maxPrepareCountDown) + 10;
        }

        spawn(Bukkit.getWorld("world").getSpawnLocation().add(0, 35, 0));
        getTrait(LookClose.class).lookClose(true);
        getTrait(Equipment.class).set(Equipment.EquipmentSlot.HELMET, new ItemStack(Material.SKULL_ITEM, 1, (short) 1));
        setProtected(false);
        getBukkitEntity().setMaxHealth(health);
        getBukkitEntity().setHealth(health);
    }

    @Override
    void action() {
        if (prepareCountDown > 0) {
            prepareCountDown--;
        } else {
            if (teleportCountDown == 0) {
                List<Player> realOnlinePlayers = Tool.getRealPlayers(Bukkit.getWorld("world"));
                if (realOnlinePlayers.size() != 0) {
                    int random = (int) (Math.random() * realOnlinePlayers.size());
                    Player targetPlayer = realOnlinePlayers.get(random);

                    if (targetPlayer.getItemInHand().hasItemMeta() &&
                            targetPlayer.getItemInHand().getItemMeta().hasLore() &&
                            targetPlayer.getItemInHand().getItemMeta().getLore().get(0).equals("影无法靠近你。")) {
                        count--;
                    } else {
                        teleportCountDown = (int) (Math.random() * maxTeleportCountDown) + 3;
                        teleport(targetPlayer);
                    }

                } else {
                    count = 0;
                }
            } else {
                if (isPlayerMove()) {
                    teleportCountDown = 0;
                    count--;
                    detect();
                } else {
                    teleportCountDown--;
                    if (teleportCountDown == 0) {
                        count--;
                    }
                }
            }
        }
    }

    @Override
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
        recordLocation = targetPlayer.getLocation();
    }

    private boolean isPlayerMove() {
        Location location = targetPlayer.getLocation();
        return !(recordLocation.distance(location) <= 0.5);
    }

    private void detect() {
        Message.broadcastMessage("§4" + targetPlayer.getDisplayName() + "被" + name + "发现了!");
        randomShadowAttack(targetPlayer);
    }

    @Override
    public void dropItem() {
        if ((int) (Math.random() * 3) == 0) {
            ItemStack dropItem1 = new ItemStack(Material.DIAMOND_BLOCK, (int) (Math.random() * 2) + 1);
            getEntity().getWorld().dropItem(getEntity().getLocation(), dropItem1);
        }

        if ((int) (Math.random() * 5) == 0) {
            ItemStack dropItem2 = ShadowItem.getShadowSoulBook();
            getEntity().getWorld().dropItem(getEntity().getLocation(), dropItem2);
        }
    }
}
