package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.ShadowVillageCore;

public class ShadowManSpawner extends ShadowLivingSpawner {
    ShadowManSpawner(ShadowVillageCore plugin) {
        super(plugin);
    }

    ShadowLiving newEntity(){
        return new ShadowMan();
    }
}
