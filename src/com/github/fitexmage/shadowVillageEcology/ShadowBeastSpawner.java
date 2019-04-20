package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.ShadowVillageCore;

public class ShadowBeastSpawner extends ShadowLivingSpawner {
    ShadowBeastSpawner(ShadowVillageCore plugin) {
        super(plugin);
    }

    ShadowLiving newLiving() {
        return new ShadowBeast();
    }
}