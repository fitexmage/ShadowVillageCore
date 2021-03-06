package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.ShadowVillageCore;
import com.github.fitexmage.Violet;
import com.github.fitexmage.VioletSpawner;
import org.bukkit.Bukkit;

import java.util.Date;

public class SpawnerController {
    public static ShadowManSpawner shadowManSpawner;
    public static ShadowBeastSpawner shadowBeastSpawner;
    public static ShadowSoulSpawner shadowSoulSpawner;

    private static int recordedMinute;
    private static final long interval = 200L; // 20L = 1s

    public static void startSpawner(ShadowVillageCore plugin) {
        if (shadowManSpawner == null) {
            shadowManSpawner = new ShadowManSpawner(plugin);
        }
        if (shadowBeastSpawner == null) {
            shadowBeastSpawner = new ShadowBeastSpawner(plugin);
        }
        if (shadowSoulSpawner == null) {
            shadowSoulSpawner = new ShadowSoulSpawner(plugin);
        }
    }

    public static void startTimer(ShadowVillageCore plugin) {
        recordedMinute = (new Date()).getMinutes();

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            int currertMinute = (new Date()).getMinutes();
            if (recordedMinute != currertMinute) {
                recordedMinute = currertMinute;
                if (recordedMinute == 42 && ((int) (Math.random() * 2) == 0)) {
                    if ((int) (Math.random() * 2) == 0) {
                        shadowManSpawner.spawnEntity(false);
                    } else {
                        shadowBeastSpawner.spawnEntity(false);
                    }
                }
            }
        }, 0L, interval);
    }
}
