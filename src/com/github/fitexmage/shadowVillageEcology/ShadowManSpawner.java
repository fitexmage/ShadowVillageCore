package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.ShadowVillageCore;
import com.github.fitexmage.util.Message;

import org.bukkit.scheduler.BukkitScheduler;

public class ShadowManSpawner {
    private final ShadowVillageCore plugin;
    private ShadowMan shadowMan;

    static final long interval = 20L; // 20L = 1s
    private int taskID = -1;

    ShadowManSpawner(ShadowVillageCore plugin) {
        this.plugin = plugin;
        shadowMan = new ShadowMan();
    }

    public void spawnShadowMan(boolean force) {
        if (!shadowMan.isSpawned()) {
            shadowMan.spawn(force);
            Message.broadcastMessage("§0影§c即将降临。");

            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            taskID = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    if (shadowMan.isSpawned() && shadowMan.count != 0) {
                        shadowMan.action();
                    } else {
                        shadowMan.count = 0;
                        shadowMan.despawn();
                        scheduler.cancelTask(taskID);
                    }
                }
            }, 0L, interval);
        }
    }
}
