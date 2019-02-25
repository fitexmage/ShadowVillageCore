package com.github.fitexmage;

import com.github.fitexmage.commands.svc;
import com.github.fitexmage.commands.sve;
import com.github.fitexmage.commands.svb;
import com.github.fitexmage.shadowVillageEcology.ShadowEntity;
import com.github.fitexmage.shadowVillageEcology.ShadowMan;
import com.github.fitexmage.shadowVillageEcology.ShadowSoul;
import com.github.fitexmage.shadowVillageEcology.ShadowBeast;
import com.github.fitexmage.util.Message;

import net.citizensnpcs.api.event.*;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

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
        if (svc.coreOn) {
            Player player = event.getPlayer();
            Message.sendPlayerMessage(player, "影之乡核心正在运行");
        }
    }

    @EventHandler
    public void onNPCDamaged(NPCDamageByEntityEvent event) {
        if (svc.coreOn) {
            NPC npc = event.getNPC();
            if (event.getDamage() >= npc.getBukkitEntity().getHealth()) {
                npc.despawn();
                if (npc.getId() == ShadowMan.id || npc.getId() == ShadowBeast.id) {
                    ItemStack dropItem = new ItemStack(Material.DIAMOND_BLOCK, 1);
                    npc.getEntity().getWorld().dropItem(npc.getEntity().getLocation(), dropItem);
                }
                if (npc.getId() == ShadowSoul.id) {
                    ItemStack dropItem = new ItemStack(Material.APPLE, 1);
                    npc.getEntity().getWorld().dropItem(npc.getEntity().getLocation(), dropItem);
                }
            }
        }
    }

    @EventHandler
    public void onEntityAttack(NPCDamageEntityEvent event) {
        NPC npc = event.getNPC();
        if (npc.getId() == ShadowBeast.id) {
            if (event.getDamaged() instanceof Player) {
                Player player = (Player) event.getDamaged();
                if (npc instanceof ShadowEntity) {
                    ((ShadowEntity) npc).randomShadowAttack(player);
                }
            }
        } else if (npc.getId() == ShadowSoul.id) {
            if (event.getDamaged() instanceof Player) {
                Player player = (Player) event.getDamaged();
                player.getWorld().strikeLightning(player.getLocation());
            }
        }
    }
}