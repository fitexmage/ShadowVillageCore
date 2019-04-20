package com.github.fitexmage;

import com.github.fitexmage.commands.svb;
import com.github.fitexmage.shadowVillageBlackMarket.ShadowItem;
import com.github.fitexmage.shadowVillageEcology.*;
import com.github.fitexmage.util.Message;
import com.github.fitexmage.util.Tool;

import net.citizensnpcs.api.event.*;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ShadowListener implements Listener {
    private final ShadowVillageCore plugin;

    ShadowListener(ShadowVillageCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Message.sendMessage(player, "影之乡核心正在运行");
        Tool.hidePlayerList();
        Tool.showPlayerList();
    }

    @EventHandler
    public void onPlayerInteraction(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) && event.hasItem() && event.hasBlock()) {
            leftClick(event);
        } else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.hasItem() && event.hasBlock()) {
            rightClick(event);
        }
    }

    private void leftClick(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        if (block.getType().equals(Material.WALL_SIGN)) {
            Sign sign = (Sign) block.getState();
            if (sign.getLine(0).equals("[灵魂绑定]")) {
                svb.bound(player);
            }
        }
    }

    private void rightClick(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        if (ShadowItem.isShadowSoulBook(item)) {
            if (ShadowSoul.isShadowSoulCircle(block.getLocation())) {
                if (item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);
                    player.setItemInHand(item);
                } else {
                    player.setItemInHand(null);
                }
                SpawnerController.shadowSoulSpawner.spawnShadowSoul(player, block.getLocation().add(0, 1, 0));
            } else {
                Message.sendMessage(player, "你需要召唤法阵！");
            }
        }
    }

    @EventHandler()
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 4));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
        }, 20L);
    }

    @EventHandler
    public void onNPCDamaged(NPCDamageEvent event) {
        checkDespawn(event);
    }

    @EventHandler
    public void onNPCDamagedByEntity(NPCDamageByEntityEvent event) {
        checkDespawn(event);
    }

    private void checkDespawn(NPCDamageEvent event) {
        NPC npc = event.getNPC();
        if (!npc.isProtected()) {
            if (event.getDamage() >= npc.getBukkitEntity().getHealth()) {
                if (npc instanceof Violet) {
                    ((Violet) npc).reborn();
                } else {
                    if (npc instanceof ShadowMan) {
                        ((ShadowMan) npc).dropItem();
                    } else if (npc instanceof ShadowBeast) {
                        ((ShadowBeast) npc).dropItem();
                    } else if (npc instanceof ShadowSoul) {
                        ((ShadowSoul) npc).dropItem();
                        ShadowSoul.setDespawnReason(1);
                    }
                    npc.despawn();
                }
            }
        }
    }

    @EventHandler
    public void onNPCDeath(NPCDeathEvent event){

    }

    @EventHandler
    public void onEntityAttack(NPCDamageEntityEvent event) {
        NPC npc = event.getNPC();
        if (npc instanceof ShadowLiving) {
            if (event.getDamaged() instanceof Player) {
                Player player = (Player) event.getDamaged();
                ((ShadowLiving) npc).randomShadowAttack(player);
            }
        } else if (npc instanceof ShadowSoul) {
            if (event.getDamaged() instanceof Player) {
                Player player = (Player) event.getDamaged();
                player.getWorld().strikeLightning(player.getLocation());
            }
        }
    }
}

