package com.github.fitexmage;

import com.github.fitexmage.commands.svc;
import com.github.fitexmage.commands.sve;
import com.github.fitexmage.commands.svb;
import com.github.fitexmage.shadowVillageBlackMarket.ShadowItem;
import com.github.fitexmage.shadowVillageEcology.*;
import com.github.fitexmage.util.Message;

import net.citizensnpcs.api.event.*;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ShadowVillageCore extends JavaPlugin implements Listener {
    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        //开关
        svc.coreOn = true;
        sve.ecologyOn = true;
        svb.tradeOn = true;

        //指令
        this.getCommand("svc").setExecutor(new svc());
        this.getCommand("sve").setExecutor(new sve(this));
        this.getCommand("svb").setExecutor(new svb());

        //注册事件
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Message.sendPlayerMessage(player, "影之乡核心正在运行");
    }

    @EventHandler
    public void onNPCDamaged(NPCDamageByEntityEvent event) {
        NPC npc = event.getNPC();
        if (!npc.isProtected()) {
            if (event.getDamage() >= npc.getBukkitEntity().getHealth()) {
                if (npc instanceof ShadowMan) {
                    ((ShadowMan) npc).dropItem();
                } else if (npc instanceof ShadowBeast) {
                    ((ShadowBeast) npc).dropItem();
                } else if (npc instanceof ShadowSoul) {
                    ItemStack dropItem = new ItemStack(Material.APPLE, 1);
                    ShadowSoul.deathReason = 1;
                    npc.getEntity().getWorld().dropItem(npc.getEntity().getLocation(), dropItem);
                }
                npc.despawn();
            }
        }
    }

    @EventHandler
    public void onEntityAttack(NPCDamageEntityEvent event) {
        NPC npc = event.getNPC();
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

    @EventHandler
    public void onPlayerInteraction(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.hasItem()) {
            ItemStack item = event.getItem();
            if (ShadowItem.isShadowSoulBook(item)) {
                Player player = event.getPlayer();
                if (isShadowSoulCircle(event.getClickedBlock().getLocation())) {
                    if (item.getAmount() > 1) {
                        item.setAmount(item.getAmount() - 1);
                        player.setItemInHand(item);
                    } else {
                        player.setItemInHand(null);
                    }
                    SpawnerController.shadowSoulSpawner.spawnShadowSoul(player, event.getClickedBlock().getLocation().add(0, 1, 0));
                } else {
                    Message.sendPlayerMessage(player, "你需要召唤法阵！");
                }
            }
        }
    }

    public boolean isShadowSoulCircle(Location blockLocation) {
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (Math.abs(i) + Math.abs(j) == 0 && !blockLocation.getBlock().getType().equals(Material.REDSTONE_BLOCK) ||
                        (Math.abs(i) + Math.abs(j) == 1 && !blockLocation.clone().add(i, 0, j).getBlock().getType().equals(Material.DIAMOND_BLOCK)) ||
                        (Math.abs(i) + Math.abs(j) == 2 && !blockLocation.clone().add(i, 0, j).getBlock().getType().equals(Material.BEDROCK))) {
                    return false;
                }
            }
        }
        return true;
    }
}