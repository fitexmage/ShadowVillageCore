package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.ShadowVillageCore;
import com.github.fitexmage.util.Message;

import org.bukkit.scheduler.BukkitScheduler;

public abstract class ShadowLivingSpawner {
    private final ShadowVillageCore plugin;
    private ShadowLiving shadowLiving;
    static final long interval = 20L; // 20L = 1s

    private int taskID = -1;

    ShadowLivingSpawner(ShadowVillageCore plugin) {
        this.plugin = plugin;
        shadowLiving = newEntity();
    }

    abstract ShadowLiving newEntity();

    public void spawnEntity(boolean force) {
        if (!shadowLiving.isSpawned()) {
            shadowLiving.spawn(force);
            Message.broadcastMessage("§0" + shadowLiving.getFullName() + "§c即将降临。");
            Message.violetBroadcastMessage("影会攻击随机玩家并可能偷走东西，请快前往主城或异世界躲避！");

            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            taskID = scheduler.scheduleSyncRepeatingTask(plugin, () -> {
                if (shadowLiving.isSpawned() && shadowLiving.count != 0) {
                    shadowLiving.action();
                } else {
                    shadowLiving.count = 0;
                    shadowLiving.despawn();
                    scheduler.cancelTask(taskID);
                }
            }, 0L, interval);
        }
    }
}
