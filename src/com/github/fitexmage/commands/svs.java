package com.github.fitexmage.commands;

import com.github.fitexmage.util.Message;
import com.github.fitexmage.util.Tool;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;


public class svs extends ShadowVillageCommand {

    public svs() {
        name = "影之乡辅助系统";
    }

    @Override
    void playerCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (enable) {
            if (args.length == 0) {
                Message.sendMessage(player, "这里是" + name + "！");
            } else {
                switch (args[0]) {
                    case "address":
                        getAddress(player, args);
                        break;
                    case "addplayer":
                        addPlayer(player, args);
                        break;
                    case "removeplayer":
                        removePlayer(player, args);
                        break;
                    case "synchat":
                        turnSynChat(player, args);
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

    @Override
    protected void serverCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Message.sendMessage(sender, name + "正在待命！");
        } else {
            switch (args[0]) {
                case "start":
                    enable = true;
                    Message.sendMessage(sender, name + "已启动！");
                    break;
                case "stop":
                    enable = false;
                    Message.sendMessage(sender, name + "已关闭。");
                    break;
                case "synchat":
                    sendSynChat(sender, args);
                    break;
                default:
                    Message.sendUnknown(sender);
                    break;
            }
        }
    }

    private void getAddress(Player player, String[] args) {
        if (player.hasPermission("svs.address")) {
            if (args.length == 1) {
                Message.sendMessage(player, "请选择想要查询的人！");
            } else if (args.length == 2) {
                Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
                if (targetPlayer != null) {
                    Message.sendMessage(player, "该玩家的IP地址是" + targetPlayer.getAddress());
                } else {
                    Message.sendMessage(player, "未发现该玩家！");
                }
            } else {
                Message.sendUnknown(player);
            }
        } else {
            Message.sendNoPermission(player);
        }
    }

    private void addPlayer(Player player, String[] args) {
        if (player.hasPermission("svs.addplayer")) {
            if (args.length == 1) {
                Message.sendMessage(player, "请输入名字!");
            } else {
                String fakeName = args[1];
                if (!Tool.fakeSet.contains(fakeName)) {
                    Tool.hidePlayerList();
                    Tool.fakeSet.add(fakeName);
                    Tool.showPlayerList();
                    Message.sendMessage(player, "添加成功!");
                } else {
                    Message.sendMessage(player, "该名字已在列表中!");
                }

            }
        }
    }

    private void removePlayer(Player player, String[] args) {
        if (player.hasPermission("svs.removeplayer")) {
            if (args.length == 1) {
                Message.sendMessage(player, "请输入名字!");
            } else {
                String fakeName = args[1];
                if (Tool.fakeSet.contains(fakeName)) {
                    Tool.hidePlayerList();
                    Tool.fakeSet.remove(fakeName);
                    Tool.showPlayerList();
                    Message.sendMessage(player, "移除成功!");
                } else {
                    Message.sendMessage(player, "该名字不在列表中!");
                }
            }
        }
    }


    private void turnSynChat(Player player, String[] args) {
        if (player.hasPermission("svs.synchat")) {
            if (args.length == 1) {
                Message.sendMessage(player, "请选择开或关！");
            } else {
                if (args[1].equals("true")) {
                    Message.synSet.add(player.getName());
                    Message.sendMessage(player, "聊天同步已开启！");
                } else if (args[1].equals("false")) {
                    Message.synSet.remove(player.getName());
                    Message.sendMessage(player, "聊天同步已关闭！");
                }
            }
        }
    }

    private void sendSynChat(CommandSender sender, String[] args) {
        if (args.length == 1) {
            Message.sendMessage(sender, "请输入内容！");
        } else {
            String message = "";
            for (int i = 1; i < args.length; i++) {
                message += args[i];
                if (i != args.length - 1) {
                    message += " ";
                }
            }
            Message.violetSynMessage(message);
        }
    }
}
