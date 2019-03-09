package com.github.fitexmage;

import com.github.fitexmage.shadowVillageBlackMarket.ShadowItem;
import com.github.fitexmage.shadowVillageEcology.ShadowBeast;
import com.github.fitexmage.shadowVillageEcology.ShadowMan;
import com.github.fitexmage.shadowVillageEcology.ShadowSoul;
import com.github.fitexmage.shadowVillageEcology.SpawnerController;
import com.github.fitexmage.util.Message;

import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.event.NPCDamageEntityEvent;
import net.minecraft.server.v1_7_R4.*;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ShadowListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Message.sendMessage(player, "影之乡核心正在运行");
    }

    @EventHandler
    public void onPlayerInteraction(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.hasItem()) {
            org.bukkit.inventory.ItemStack item = event.getItem();
            if (ShadowItem.isShadowSoulBook(item)) {
                Player player = event.getPlayer();
                if (ShadowSoul.isShadowSoulCircle(event.getClickedBlock().getLocation())) {
                    if (item.getAmount() > 1) {
                        item.setAmount(item.getAmount() - 1);
                        player.setItemInHand(item);
                    } else {
                        player.setItemInHand(null);
                    }
                    SpawnerController.shadowSoulSpawner.spawnShadowSoul(player, event.getClickedBlock().getLocation().add(0, 1, 0));
                } else {
                    Message.sendMessage(player, "你需要召唤法阵！");
                }
            }
        }
    }

    @EventHandler
    public void onNPCDamaged(NPCDamageByEntityEvent event) {
        net.citizensnpcs.api.npc.NPC npc = event.getNPC();
        if (!npc.isProtected()) {
            if (event.getDamage() >= npc.getBukkitEntity().getHealth()) {
                if (npc instanceof ShadowMan) {
                    ((ShadowMan) npc).dropItem();
                } else if (npc instanceof ShadowBeast) {
                    ((ShadowBeast) npc).dropItem();
                } else if (npc instanceof ShadowSoul) {
                    org.bukkit.inventory.ItemStack dropItem = new org.bukkit.inventory.ItemStack(org.bukkit.Material.APPLE, 1);
                    ShadowSoul.deathReason = 1;
                    npc.getEntity().getWorld().dropItem(npc.getEntity().getLocation(), dropItem);
                }
                npc.despawn();
            }
        }
    }

    @EventHandler
    public void onEntityAttack(NPCDamageEntityEvent event) {
        net.citizensnpcs.api.npc.NPC npc = event.getNPC();
        if (npc instanceof ShadowBeast) {
            if (event.getDamaged() instanceof Player) {
                Player player = (Player) event.getDamaged();
                ((ShadowBeast) npc).randomShadowAttack(player);
            }
        } else if (npc instanceof ShadowSoul) {
            if (event.getDamaged() instanceof Player) {
                Player player = (Player) event.getDamaged();
                player.getWorld().strikeLightning(player.getLocation());
            }
        }
    }
}
