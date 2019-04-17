package com.github.fitexmage;

import com.github.fitexmage.commands.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ShadowVillageCore extends JavaPlugin {
    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        //开关
        svc.enable = true;
        sve.enable = true;
        svb.enable = true;
        svs.enable = true;

        //指令
        getCommand("svc").setExecutor(new svc(this));
        getCommand("sve").setExecutor(new sve(this));
        getCommand("svb").setExecutor(new svb());
        getCommand("svs").setExecutor(new svs());


        //注册事件
        ShadowListener shadowListener = new ShadowListener(this);
        Bukkit.getPluginManager().registerEvents(shadowListener, this);
    }

    @Override
    public void onDisable() {

    }
}