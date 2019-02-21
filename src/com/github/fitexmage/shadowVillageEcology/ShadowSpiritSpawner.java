package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.ShadowVillageCore;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitScheduler;

public class ShadowSpiritSpawner {
    private final ShadowVillageCore plugin;
    private ShadowSpirit shadowSpirit;

    static final long interval = 20L; // 20L = 1s
    private int taskID = -1;

    ShadowSpiritSpawner(ShadowVillageCore plugin) {
        this.plugin = plugin;
        shadowSpirit = new ShadowSpirit(getRandomType());
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

    public void spawnShadowSpirit(boolean force) {
        if (shadowSpirit.count == 0) {
            if (force) {
                shadowSpirit.forcePrepare();
            } else {
                shadowSpirit.prepare();
            }

            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            taskID = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    if (shadowSpirit.isSpawned() && shadowSpirit.count != 0) {
                        shadowSpirit.action();
                    } else {
                        shadowSpirit.count = 0;
                        shadowSpirit.despawn();
                        scheduler.cancelTask(taskID);
                    }
                }
            }, 0L, interval);
        }
    }
}
