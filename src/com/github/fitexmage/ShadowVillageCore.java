package com.github.fitexmage;

import com.github.fitexmage.commands.*;
import com.github.fitexmage.shadowVillageBlackMarket.*;
import com.github.fitexmage.shadowVillageEcology.*;
import com.github.fitexmage.util.Message;

import net.citizensnpcs.api.event.*;
import net.citizensnpcs.api.npc.NPC;
import net.minecraft.server.v1_7_R4.*;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
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
        svc.commandOn = true;
        sve.commandOn = true;
        svb.commandOn = true;
        svs.commandOn = true;

        //指令
        getCommand("svc").setExecutor(new svc(this));
        getCommand("sve").setExecutor(new sve(this));
        getCommand("svb").setExecutor(new svb());
        getCommand("svs").setExecutor(new svs());


        //注册事件
        ShadowListener shadowListener = new ShadowListener();
        Bukkit.getPluginManager().registerEvents(shadowListener, this);
    }

    @Override
    public void onDisable() {

    }
}