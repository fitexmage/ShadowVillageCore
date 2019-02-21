package com.github.fitexmage.shadowVillageEcology;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.CitizensNPC;
import net.citizensnpcs.npc.EntityController;
import net.citizensnpcs.npc.EntityControllers;
import org.bukkit.entity.EntityType;

import java.util.UUID;

public class ShadowSoul extends CitizensNPC {
    public static final int id = 10011;
    private static final String name = "影魂";
    private final double health = 10000.0;

    public ShadowSoul() {
        super(UUID.randomUUID(), id, name, EntityControllers.createForType(EntityType.PLAYER), CitizensAPI.getNPCRegistry());
    }


}
