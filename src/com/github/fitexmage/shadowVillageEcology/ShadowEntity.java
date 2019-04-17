package com.github.fitexmage.shadowVillageEcology;

import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.CitizensNPC;
import net.citizensnpcs.npc.EntityController;

import java.util.UUID;

public class ShadowEntity extends CitizensNPC {

    public ShadowEntity(UUID uuid, int id, String name, EntityController entityController, NPCRegistry registry) {
        super(uuid, id, name, entityController, registry);
    }
}
