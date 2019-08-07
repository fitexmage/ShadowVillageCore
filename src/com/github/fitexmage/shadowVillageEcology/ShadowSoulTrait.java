package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.util.Message;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;

public class ShadowSoulTrait extends Trait {
    @Persist
    private boolean data = false;

    ShadowSoulTrait(String name) {
        super(name);
    }

    @Override
    public void onDespawn() {
        if (ShadowSoul.getDespawnReason() == 1) {
            Message.broadcastMessage("§0影§c消散了！勇士，你成功的证明了自己！");
        } else {
            Message.broadcastMessage("§0影§c消散了......");
        }
    }
}