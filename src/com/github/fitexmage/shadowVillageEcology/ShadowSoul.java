package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.util.Message;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.npc.CitizensNPC;
import net.citizensnpcs.npc.EntityControllers;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class ShadowSoul extends CitizensNPC {
    public static final int id = 10011;
    private static final String name = "影魂";
    private final double health = 5000.0;
    private final float speed = 1.0f;
    private final int range = 20;

    private int totalCount; //总计时
    private int stageCountDown; //距离下一阶段开始时间
    private int stageCount; //阶段持续时间
    private int stageNum; //阶段编号
    private int minHeight; //最小安全高度

    ShadowSoul() {
        super(UUID.randomUUID(), id, name, EntityControllers.createForType(EntityType.PIG), CitizensAPI.getNPCRegistry());

        totalCount = 0;
        stageCountDown = 0;
        stageCount = 0;
        stageNum = 0;
        minHeight = 0;

        ShadowSoulTrait shadowSoulTrait = new ShadowSoulTrait("shadowSoulTrait");
        addTrait(shadowSoulTrait);
    }

    void spawn(Player player) {
        totalCount = 0;
        stageCountDown = (int) (Math.random() * 5) + 5;
        stageCount = 0;

        spawn(player.getLocation());
        setProtected(false);
        getBukkitEntity().setMaxHealth(health);
        getBukkitEntity().setHealth(health);
        getNavigator().getLocalParameters().speedModifier(speed);
        getNavigator().setTarget(getNearestPlayer(), true);
    }

    void action() {
        totalCount++;
        if (totalCount % 10 == 0) {
            Player nearestPlayer = getNearestPlayer();
            if (nearestPlayer == null) {
                despawn();
                return;
            } else {
                getNavigator().setTarget(nearestPlayer, true);
            }
        }

        if (stageCountDown > 0) { //阶段未开始
            stageCountDown--;
        } else { //阶段准备
            if (stageCount == 0) {
                stageNum = (int) (Math.random() * 4);
                switch (stageNum) {
                    case 0:
                        Message.broadcastMessage("§4影魂已如同幻影！");
                        getNavigator().getLocalParameters().speedModifier(5.0f);
                        stageCount = (int) (Math.random() * 5) + 5;
                        break;
                    case 1:
                        Message.broadcastMessage("§4影魂正在召唤空之力！");
                        stageCount = (int) (Math.random() * 2) + 1;
                        break;
                    case 2:
                        StringBuilder message = new StringBuilder("§4影魂正在召唤磁之力！");
                        minHeight = (int) (Math.random() * 5) + 1;
                        message.insert(minHeight + 2, "§a");
                        message.insert(minHeight + 7, "§4");
                        Message.broadcastMessage(message.toString());
                        stageCount = (int) (Math.random() * 2) + 3;
                        break;
                    case 3:
                        Message.broadcastMessage("§4影魂正在召唤咒之力！");
                        stageCount = (int) (Math.random() * 2) + 1;
                        break;
                    default:
                        break;
                }
            } else { //阶段开始
                stageCount--;
                if (stageCount == 0) {
                    stageCountDown = (int) (Math.random() * 5) + 3; //到下一阶段时间
                    Location location = this.getBukkitEntity().getLocation();
                    switch (stageNum) {
                        case 0:
                            getNavigator().getLocalParameters().speedModifier(speed);
                            break;
                        case 1:
                            World world = location.getWorld();
                            for (int x = -10; x <= 10; x += 5) {
                                for (int z = -10; z <= 10; z += 5) {
                                    Location tntLocation = new Location(world, (double) (location.getBlockX() + x), (double) (world.getHighestBlockYAt(location) + 64), (double) (location.getBlockZ() + z));
                                    world.spawn(tntLocation, TNTPrimed.class);
                                }
                            }
                            break;
                        case 2:
                            for (Player player : location.getWorld().getPlayers()) {
                                if (!player.hasMetadata("NPC") && player.getGameMode().equals(GameMode.SURVIVAL)) {
                                    Location playerLocation = player.getLocation();
                                    if (location.distance(playerLocation) <= range) {
                                        if (location.getY() + minHeight > playerLocation.getY() || location.getY() + minHeight + 3 < playerLocation.getY()) {
                                            if (player.getHealth() > 16) {
                                                player.setHealth(player.getHealth() - 16);
                                            } else {
                                                player.setHealth(0);
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 3:
                            for (Player player : location.getWorld().getPlayers()) {
                                if (!player.hasMetadata("NPC") && player.getGameMode().equals(GameMode.SURVIVAL)) {
                                    Location playerLocation = player.getLocation();
                                    if (location.distance(playerLocation) <= range) {
                                        //消除状态
                                        if (!player.getActivePotionEffects().isEmpty()) {
                                            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                                                if ((int) (Math.random() * 3) == 0) {
                                                    player.removePotionEffect(potionEffect.getType());
                                                }
                                            }
                                        }

                                        //添加状态
                                        switch ((int) (Math.random() * 5)) {
                                            case 0:
                                                player.setFireTicks(100);
                                                break;
                                            case 1:
                                                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 100));
                                                break;
                                            case 2:
                                                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 100));
                                                break;
                                            case 3:
                                                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 100));
                                                break;
                                            case 4:
                                                player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 100));
                                                break;
                                        }
                                    }
                                }
                            }
                            break;
                    }
                }
            }
        }
    }

    private Player getNearestPlayer() {
        double min = 50.0;
        Player nearestPlayer = null;
        for (Player player : getBukkitEntity().getWorld().getPlayers()) {
            if (!player.hasMetadata("NPC") && player.getGameMode().equals(GameMode.SURVIVAL)) {
                Location playerLocation = player.getLocation();
                double distance = getBukkitEntity().getLocation().distance(playerLocation);
                if (distance <= min) {
                    min = distance;
                    nearestPlayer = player;
                }
            }
        }
        return nearestPlayer;
    }
}
