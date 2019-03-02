package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.util.Message;
import com.github.fitexmage.util.Tool;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.npc.CitizensNPC;
import net.citizensnpcs.npc.EntityControllers;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.LinkedList;
import java.util.UUID;

public class ShadowSoul extends CitizensNPC {
    private static final int id = 10011;
    private static final String name = "影魂";
    public static int deathReason = 0;
    private final double health = 5000.0;
    private final float speed = 1.1f;
    private final double range = 20.0;

    private int totalCount; //总计时
    private int stageCountDown; //距离下一阶段开始时间
    private int stageCount; //阶段持续时间
    private int stageNum; //阶段编号
    private int minHeight; //最小安全高度

    ShadowSoul() {
        super(UUID.randomUUID(), id, name, EntityControllers.createForType(EntityType.CREEPER), CitizensAPI.getNPCRegistry());

        totalCount = 0;
        stageCountDown = 0;
        stageCount = 0;
        stageNum = 0;
        minHeight = 0;

        ShadowSoulTrait shadowSoulTrait = new ShadowSoulTrait("shadowSoulTrait");
        addTrait(shadowSoulTrait);
    }

    void spawn(Player player, Location blockLocation) {
        totalCount = 0;
        stageCountDown = (int) (Math.random() * 5) + 5;
        stageCount = 0;

        spawn(blockLocation);
        setProtected(false);
        getBukkitEntity().setMaxHealth(health);
        getBukkitEntity().setHealth(health);
        getNavigator().getLocalParameters().speedModifier(speed);
        getNavigator().setTarget(player, true);
        Message.entityBroadcastMessage(name, "为了埃索泰尔！");
    }

    void action() {
        totalCount++;
        if (totalCount % 10 == 0) {
            Player nearestPlayer = getNearestPlayer();
            if (nearestPlayer == null) {
                despawn();
                deathReason = 2;
                return;
            } else {
                getNavigator().setTarget(nearestPlayer, true);
            }
        }

        if (stageCountDown > 0) { //阶段未开始
            stageCountDown--;
        } else {
            if (stageNum == -1) {
                stageNum = (int) (Math.random() * 4); //阶段选择
            } else {
                stage(stageNum);
            }
        }
    }

    private void stage(int num) {
        if (stageCount == 0) {
            if (num == 1) stage1(1);
            else if (num == 2) stage2(1);
            else if (num == 3) stage3(1);
            else if (num == 4) stage4(1);
        } else {
            stageCount--;
            if (stageCount == 0) {
                stageCountDown = (int) (Math.random() * 5) + 3; //到下一阶段时间
                stageNum = -1;
                if (num == 1) stage1(2);
                else if (num == 2) stage2(2);
                else if (num == 3) stage3(2);
                else if (num == 4) stage4(2);
            }
        }
    }

    private void stage1(int subStage) {
        if (subStage == 1) {
            Message.broadcastMessage("§4" + name + "已如同幻影！");
            teleport(getNavigator().getTargetAsLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            getNavigator().getLocalParameters().speedModifier(5.0f);
            stageCount = (int) (Math.random() * 5) + 5;
        } else {
            getNavigator().getLocalParameters().speedModifier(speed);
        }
    }

    private void stage2(int subStage) {
        if (subStage == 1) {
            Message.broadcastMessage("§4" + name + "正在召唤空之力！");
            stageCount = (int) (Math.random() * 2) + 1;
        } else {
            setNuke();
        }
    }

    private void stage3(int subStage) {
        if (subStage == 1) {
            StringBuilder message = new StringBuilder("§4" + name + "正在召唤磁之力！");
            minHeight = (int) (Math.random() * 5) + 1;
            message.insert(minHeight + 2, "§a");
            message.insert(minHeight + 7, "§4");
            Message.broadcastMessage(message.toString());
            stageCount = (int) (Math.random() * 2) + 3;
        } else {
            Location location = this.getBukkitEntity().getLocation();
            for (Player player : getNearPlayers()) {
                Location playerLocation = player.getLocation();
                if (location.getY() + minHeight > playerLocation.getY() || location.getY() + minHeight + 3 < playerLocation.getY()) {
                    Tool.damagePlayer(player, 20);
                }
            }
            Message.broadcastMessage("§4磁场扭曲了！");
        }
    }

    private void stage4(int subStage) {
        if (subStage == 1) {
            Message.broadcastMessage("§4" + name + "正在召唤咒之力！");
            stageCount = (int) (Math.random() * 2) + 2;
        } else {
            dispel();
            for (Player player : getNearPlayers()) {
                removePotionEffect(player);
                addPotionEffect(player);
            }
        }
    }

    private void setNuke() {
        Location location = this.getBukkitEntity().getLocation();
        World world = location.getWorld();
        for (int x = -10; x <= 10; x += 5) {
            for (int z = -10; z <= 10; z += 5) {
                Location tntLocation = new Location(world, (double) (location.getBlockX() + x), (double) (world.getHighestBlockYAt(location) + 5), (double) (location.getBlockZ() + z));
                world.spawn(tntLocation, TNTPrimed.class);
            }
        }
    }

    private void dispel() {
        for (PotionEffect potionEffect : this.getBukkitEntity().getActivePotionEffects()) {
            this.getBukkitEntity().removePotionEffect(potionEffect.getType());
        }
    }

    private void removePotionEffect(Player player) {
        if (!player.getActivePotionEffects().isEmpty()) {
            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                if ((int) (Math.random() * 3) == 0) {
                    player.removePotionEffect(potionEffect.getType());
                }
            }
        }
    }

    private void addPotionEffect(Player player) {
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

    private LinkedList<Player> getNearPlayers() {
        Location location = this.getBukkitEntity().getLocation();
        LinkedList<Player> list = new LinkedList<>();
        for (Player player : location.getWorld().getPlayers()) {
            if (!player.hasMetadata("NPC") && player.getGameMode().equals(GameMode.SURVIVAL)) {
                Location playerLocation = player.getLocation();
                if (location.distance(playerLocation) <= range) {
                    list.add(player);
                }
            }
        }
        return list;
    }

    private Player getNearestPlayer() {
        double min = range;
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
