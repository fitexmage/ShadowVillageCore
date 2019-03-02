package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.ShadowVillageCore;
import com.github.fitexmage.util.Message;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class ShadowSoulSpawner {
    private final ShadowVillageCore plugin;
    private ShadowSoul shadowSoul;

    static final long interval = 20L; // 20L = 1s
    private int taskID = -1;

    ShadowSoulSpawner(ShadowVillageCore plugin) {
        this.plugin = plugin;
        shadowSoul = new ShadowSoul();
    }

    public void spawnShadowSoul(Player player, Location blockLocation) {
        if (!shadowSoul.isSpawned()) {
            shadowSoul.spawn(player, blockLocation);
            Message.broadcastMessage("§4" + player.getDisplayName() + "召唤出了" + shadowSoul.getName() + "!");

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
}
