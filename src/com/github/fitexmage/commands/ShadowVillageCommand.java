package com.github.fitexmage.commands;

import com.github.fitexmage.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class ShadowVillageCommand implements CommandExecutor {
    String name;
    public static boolean commandOn;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            playerCommand(sender, args); //玩家输入指令
        } else {
            serverCommand(sender, args); //控制台输入指令
        }
        return true;
    }

    void playerCommand(CommandSender sender, String[] args) {

    }

    protected void serverCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Message.sendMessage(sender, name + "正在待命！");
        } else {
            switch (args[0]) {
                case "start":
                    commandOn = true;
                    Message.sendMessage(sender, name + "已启动！");
                    break;
                case "stop":
                    commandOn = false;
                    Message.sendMessage(sender, name + "已关闭。");
                    break;
                default:
                    Message.sendUnknown(sender);
                    break;
            }
        }
    }
}
