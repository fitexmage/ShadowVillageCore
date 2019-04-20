package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.ShadowVillageCore;

public abstract class ShadowEntitySpawner {
    final ShadowVillageCore plugin;
    static final long interval = 20L; // 20L = 1s
    int taskID = -1;

    ShadowEntitySpawner(ShadowVillageCore plugin) {
        this.plugin = plugin;
    }
}
