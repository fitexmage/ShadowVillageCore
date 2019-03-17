package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.util.Message;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;

public class ShadowBeastTrait extends Trait {
    @Persist
    private boolean data = false;

    ShadowBeastTrait(String name) {
        super(name);
    }

    @Override
    public void onDespawn() {
        if (ShadowBeast.despawnReason == 1) {
            Message.broadcastMessage("§0影§c消散了，但总感觉它仍注视着你......");
        }
    }
}
