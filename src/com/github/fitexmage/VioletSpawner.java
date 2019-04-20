package com.github.fitexmage;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.scheduler.BukkitScheduler;

public class VioletSpawner {
    private final ShadowVillageCore plugin;
    private Violet violet;
    private static final long interval = 200L;

    public VioletSpawner(ShadowVillageCore plugin) {
        this.plugin = plugin;
        violet = new Violet();
    }

    public void spawnViolet() {
        if (!violet.isSpawned()) {
            violet.spawn();

            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            scheduler.scheduleSyncRepeatingTask(plugin, () -> {
                if (violet.isSpawned()) {
                    violet.randomMove();
                }
            }, 0L, interval);
        }
    }

    public void despawnViolet() {
        violet.despawn();
    }

    public Location getVioletLocation() {
        return violet.getStoredLocation();
    }

    public void teleportViolet(Location location) {
        violet.teleport(location, TeleportCause.PLUGIN);
    }
}
