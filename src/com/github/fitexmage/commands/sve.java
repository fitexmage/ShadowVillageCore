package com.github.fitexmage.commands;

import com.github.fitexmage.*;
import com.github.fitexmage.shadowVillageEcology.*;
import com.github.fitexmage.util.Message;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sve extends ShadowVillageCommand {
    public static boolean ecologyOn;

    public sve(ShadowVillageCore plugin) {
        SpawnerController.startSpawner(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            playerCommand(sender, args); //玩家输入指令
        } else {
            serverCommand(sender, args); //控制台输入指令
        }
        return true;
    }

    @Override
    protected void playerCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (ecologyOn) {
            if (args.length == 0) {
                Message.sendPlayerMessage(player, "这里是影之乡生态系统！");
            } else {
                switch (args[0]) {
                    case "summon":
                        summon(args, player);
                        break;
                    default:
                        Message.sendUnknown(player);
                        break;
                }
            }
        } else {
            Message.sendPlayerMessage(player, "影之乡生态系统未启动。");
        }
    }

    @Override
    protected void serverCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Message.sendPlayerMessage(sender, "影之乡生态系统正在待命！");
        } else {
            switch (args[0]) {
                case "start":
                    ecologyOn = true;
                    Message.sendPlayerMessage(sender, "影之乡生态系统已启动！");
                    break;
                case "stop":
                    ecologyOn = false;
                    Message.sendPlayerMessage(sender, "影之乡生态系统已关闭。");
                    break;
                default:
                    Message.sendUnknown(sender);
                    break;
            }
        }
    }

    private void summon(String[] args, Player player) {
        if (args.length == 1) {
            Message.sendPlayerMessage(player, "请选择要生成的生物！");
        } else if (args.length == 2) {
            switch (args[1]) {
                case "shadowman":
                    if (player.hasPermission("sve.summon.shadowman")) {
                        SpawnerController.shadowManSpawner.spawnShadowMan(true);
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                case "shadowbeast":
                    if (player.hasPermission("sve.summon.shadowbeast")) {
                        SpawnerController.shadowBeastSpawner.spawnShadowBeast(true);
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