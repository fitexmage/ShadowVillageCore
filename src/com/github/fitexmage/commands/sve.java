package com.github.fitexmage.commands;

import com.github.fitexmage.*;
import com.github.fitexmage.shadowVillageEcology.*;
import com.github.fitexmage.util.Message;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sve extends ShadowVillageCommand {

    public sve(ShadowVillageCore plugin) {
        name = "影之乡生态系统";
        SpawnerController.startSpawner(plugin);
    }

    @Override
    void playerCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (enable) {
            if (args.length == 0) {
                Message.sendMessage(player, "这里是" + name + "！");
            } else {
                switch (args[0]) {
                    case "summon":
                        summon(player, args);
                        break;
                    default:
                        Message.sendUnknown(player);
                        break;
                }
            }
        } else {
            Message.sendMessage(player, name + "未启动。");
        }
    }

    private void summon(Player player, String[] args) {
        if (args.length == 1) {
            Message.sendMessage(player, "请选择要生成的生物！");
        } else if (args.length == 2) {
            switch (args[1]) {
                case "shadowman":
                    if (player.hasPermission("sve.summon.shadowman")) {
                        SpawnerController.shadowManSpawner.spawnEntity(true);
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                case "shadowbeast":
                    if (player.hasPermission("sve.summon.shadowbeast")) {
                        SpawnerController.shadowBeastSpawner.spawnEntity(true);
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                case "shadowsoul":
                    if (player.hasPermission("sve.summon.shadowsoul")) {
                        SpawnerController.shadowSoulSpawner.spawnShadowSoul(player, player.getLocation());
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                default:
                    Message.sendUnknown(player);
                    break;
            }
        } else {
            Message.sendUnknown(player);
        }
    }
}