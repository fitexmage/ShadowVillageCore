package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.ShadowVillageCore;
import com.github.fitexmage.util.Message;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class ShadowSoulSpawner extends ShadowEntitySpawner{
    private ShadowSoul shadowSoul;
    ShadowSoulSpawner(ShadowVillageCore plugin) {
        super(plugin);
        shadowSoul = new ShadowSoul();
    }

    public void spawnShadowSoul(Player player, Location blockLocation) {
        if (!shadowSoul.isSpawned()) {
            Message.broadcastMessage(player.getDisplayName() + "召唤出了" + shadowSoul.getName() + "!");
            shadowSoul.spawn(player, blockLocation);

            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            taskID = scheduler.scheduleSyncRepeatingTask(plugin, () -> {
                if (shadowSoul.isSpawned()) {
                    shadowSoul.action();
                } else {
                    shadowSoul.despawn();
                    scheduler.cancelTask(taskID);
                }
            }, 0L, interval);
        }
    }
}
