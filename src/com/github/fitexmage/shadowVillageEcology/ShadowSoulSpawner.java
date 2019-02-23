package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.ShadowVillageCore;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class ShadowSoulSpawner {
    private final ShadowVillageCore plugin;
    private ShadowSoul shadowSoul;

    static final long interval = 20L; // 20L = 1s
    private int taskID = -1;

    public ShadowSoulSpawner(ShadowVillageCore plugin) {
        this.plugin = plugin;
        shadowSoul = new ShadowSoul();
    }

    public void spawnShadowSoul(Player player) {
        shadowSoul.spawn(player);
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        taskID = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (shadowSoul.isSpawned()) {
                    shadowSoul.action();
                } else {
                    shadowSoul.despawn();
                    scheduler.cancelTask(taskID);
                }
            }
        }, 0L, interval);
    }
}
