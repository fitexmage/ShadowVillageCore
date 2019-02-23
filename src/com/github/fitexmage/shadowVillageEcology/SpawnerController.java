package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.ShadowVillageCore;
import com.github.fitexmage.commands.svc;
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
        countTime(plugin);
    }

    private static void countTime(ShadowVillageCore plugin) {
        recordedMinute = (new Date()).getMinutes();

        Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                if (svc.coreOn) {
                    int currertMinute = (new Date()).getMinutes();
                    if (recordedMinute != currertMinute) {
                        recordedMinute = currertMinute;
                        if (recordedMinute == 42) {
                            int random = (int) (Math.random() * 2);
                            if (random == 0) {
                                shadowManSpawner.spawnShadowMan(false);
                            } else {
                                shadowBeastSpawner.spawnShadowSpirit(false);
                            }
                        }
                    }
                }
            }
        }, 0L, interval);
    }
}
