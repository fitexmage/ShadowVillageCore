package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.ShadowVillageCore;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.npc.EntityControllers;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.UUID;

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
            if (force) {
                shadowMan.forcePrepare();
            } else {
                shadowMan.prepare();
            }

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
