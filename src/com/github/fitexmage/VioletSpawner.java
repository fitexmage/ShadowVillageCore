package com.github.fitexmage;

import com.github.fitexmage.util.Message;
import com.github.fitexmage.util.Tool;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
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
                    if (violet.getFightCountDown() > 0) {
                        violet.setFightCountDown(violet.getFightCountDown() - 1);
                    } else {
                        violet.setAngryDegree(0);
                        violet.randomMove();
                    }
                    violet.heal();
                }
            }, 0L, interval);
        }
    }

    public void despawnViolet() {
        violet.despawn();
    }

    public Location getVioletLocation() {
        if (violet.isSpawned()) {
            return violet.getStoredLocation();
        } else {
            return null;
        }
    }

    public boolean teleportViolet(Location location) {
        if (violet.isSpawned()) {
            violet.teleport(location, TeleportCause.PLUGIN);
            violet.randomMove();
            return true;
        } else {
            return false;
        }
    }

    void fight(Entity damager) {
        if (violet.getAngryDegree() == 5) {
            Location violetLocation = violet.getBukkitEntity().getLocation();
            for (Player player : Tool.getRealPlayers(null, GameMode.SURVIVAL)) {
                if (violetLocation.distance(player.getLocation()) <= 20) {
                    player.setHealth(0.0);
                }
            }
            violet.reborn();
            Message.violetBroadcastMessage("哼，不理你们了！");
        } else {
            violet.setFightCountDown(2);
            violet.setAngryDegree(violet.getAngryDegree() + 1);
            violet.getNavigator().setTarget(damager, true);
        }
    }
}
