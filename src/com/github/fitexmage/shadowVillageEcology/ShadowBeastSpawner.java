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
        shadowBeast = new ShadowBeast(getRandomType());
    }

    private EntityType getRandomType() {
        int randomType = (int) (Math.random() * 3);
        if (randomType == 0) {
            return EntityType.CHICKEN;
        } else if (randomType == 1) {
            return EntityType.SHEEP;
        } else {
            return EntityType.COW;
        }
    }

    public void spawnShadowBeast(boolean force) {
        if (!shadowBeast.isSpawned()) {
            shadowBeast.spawn(force);
            Message.broadcastMessage("§0影§c即将降临。");

            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            taskID = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    if (shadowBeast.isSpawned() && shadowBeast.count != 0) {
                        shadowBeast.action();
                    } else {
                        shadowBeast.count = 0;
                        shadowBeast.despawn();
                        scheduler.cancelTask(taskID);
                    }
                }
            }, 0L, interval);
        }
    }
}
