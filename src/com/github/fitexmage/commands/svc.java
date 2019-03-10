package com.github.fitexmage.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.github.fitexmage.ShadowVillageCore;
import com.github.fitexmage.util.Message;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class svc extends ShadowVillageCommand {
    private final ShadowVillageCore plugin;

    public svc(ShadowVillageCore plugin) {
        this.plugin = plugin;
        name = "影之乡核心系统";
    }

    @Override
    void playerCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (enable) {
            if (args.length == 0) {
                Message.sendMessage(player, "凡人，你为何呼唤我？");
            } else {
                switch (args[0]) {
                    case "version":
                        getVersion(player);
                    case "empower":
                        empower(args, player);
                        break;
                    case "debug":
                        turnDebug(player);
                        break;
                    default:
                        penalty(player);
                        break;
                }
            }
        } else {
            Message.sendMessage(player, name + "未启动。");
        }
    }

    private void getVersion(Player player) {
        if (player.hasPermission("svc.version")) {
            Message.sendMessage(player, "当前版本：" + plugin.getDescription().getVersion());
        }
    }

    private void empower(String[] args, Player player) {
        if (player.hasPermission("svc.empower")) {
            Message.sendMessage(player, "凡人，从现在起，我将赋予你神的能力！");
        } else {
            Message.sendNoPermission(player);
        }
    }

    private void turnDebug(Player player) {
        if (player.hasPermission("svc.debug")) {
            Message.debug = !Message.debug;
            Message.sendMessage(player, "已切换debug模式为" + Message.debug);
        } else {
            Message.sendNoPermission(player);
        }
    }

    private void penalty(Player player) {
        player.getWorld().strikeLightning(player.getLocation());
        player.setFireTicks(1000);
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1000, 100));
        Message.sendMessage(player, "凡人，你怎敢在此胡作非为，你将受到惩罚！");
    }
}
