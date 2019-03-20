package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.ShadowVillageCore;
import com.github.fitexmage.util.Message;

import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitScheduler;

public class ShadowBeastSpawner {
    private final ShadowVillageCore plugin;
    private ShadowBeast shadowBeast;

    static final long interval = 20L; // 20L = 1s
    private int taskID = -1;

    ShadowBeastSpawner(ShadowVillageCore plugin) {
        this.plugin = plugin;
        shadowBeast = new ShadowBeast(EntityType.PIG);
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

    public void spawnShadowBeast(boolean force) {
        if (!shadowBeast.isSpawned()) {
            shadowBeast.spawn(force, getRandomType());
            Message.broadcastMessage("§0影灵§c即将降临。");
            Message.violetBroadcastMessage("影灵会攻击它所接触到的玩家，有可能会偷走东西，请前往主城避开它！");

            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            taskID = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    if (shadowBeast.isSpawned() && shadowBeast.count != 0) {
                        shadowBeast.action();
                    } else {
                        shadowBeast.count = 0;
                        ShadowBeast.despawnReason = 1;
                        shadowBeast.despawn();
                        scheduler.cancelTask(taskID);
                    }
                }
            }, 0L, interval);
        }
    }
}
