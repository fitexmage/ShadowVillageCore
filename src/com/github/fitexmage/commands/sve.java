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
        TimeSpawner.startSpawner(plugin);
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
        if (svc.coreOn) {
            if (ecologyOn) {
                if (args.length == 0) {
                    Message.sendPlayerMessage(player, "这里是影之乡生态系统！");
                } else {
                    switch (args[0]) {
                        case "spawn":
                            spawn(args, player);
                            break;
                        default:
                            Message.sendPlayerMessage(player, "未知指令！");
                            break;
                    }
                }
            } else {
                Message.sendPlayerMessage(player, "影之乡生态系统未启动。");
            }
        } else {
            Message.sendPlayerMessage(player, "影之乡核心未启动。");
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
                    Message.sendPlayerMessage(sender, "影之乡核心已启动！");
                    break;
                case "stop":
                    ecologyOn = false;
                    Message.sendPlayerMessage(sender, "影之乡核心已关闭。");
                    break;
                case "spawn":
                    spawn(args, sender);
                    break;
                default:
                    Message.sendPlayerMessage(sender, "未知指令！");
                    break;
            }
        }
    }

    private void spawn(String[] args, CommandSender sender) {
        if (args.length == 1) {
            Message.sendPlayerMessage(sender, "请选择要生成的生物！");
        } else if (args.length == 2) {
            if (args[1].equals("shadowman")) {
                if (sender.hasPermission("sve.spawn.shadowman")) {
                    TimeSpawner.shadowManSpawner.spawnShadowMan(true);
                } else {
                    Message.sendPlayerMessage(sender, "你无权这样做！");
                }
            } else if (args[1].equals("shadowspirit")) {
                if (sender.hasPermission("sve.spawn.shadowspirit")) {
                    TimeSpawner.shadowSpiritSpawner.spawnShadowSpirit(true);
                } else {
                    Message.sendPlayerMessage(sender, "你无权这样做！");
                }
            } else {
                Message.sendPlayerMessage(sender, "未知指令！");
            }
        } else {
            Message.sendPlayerMessage(sender, "未知指令！");
        }
    }
}