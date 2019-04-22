package com.github.fitexmage.commands;

import com.github.fitexmage.ShadowVillageCore;
import com.github.fitexmage.VioletSpawner;
import com.github.fitexmage.util.Message;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class zs extends ShadowVillageCommand {
    public static VioletSpawner violetSpawner;

    public zs(ShadowVillageCore plugin) {
        name = "小紫";
        violetSpawner = new VioletSpawner(plugin);
    }

    @Override
    void playerCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (enable) {
            if (args.length == 0) {
                Message.sendMessage(player, name + "已启动！");
            } else {
                switch (args[0].toLowerCase()) {
                    case "spawn":
                        spawn(player);
                        break;
                    case "despawn":
                        despawn(player);
                        break;
                    case "tp":
                        tp(player);
                        break;
                    case "tphere":
                        tphere(player);
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

    private void spawn(Player player) {
        if (player.hasPermission("zs.spawn")) {
            violetSpawner.spawnViolet();
            Message.sendMessage(player, "小紫已启动！");
        } else {
            Message.sendNoPermission(player);
        }
    }

    private void despawn(Player player) {
        if (player.hasPermission("zs.despawn")) {
            violetSpawner.despawnViolet();
            Message.sendMessage(player, "小紫已关闭！");
        } else {
            Message.sendNoPermission(player);
        }
    }

    private void tp(Player player) {
        if (player.hasPermission("zs.tp")) {
            Location violetLocation = violetSpawner.getVioletLocation();
            if (violetLocation != null) {
                player.teleport(violetLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
            } else {
                Message.sendMessage(player, "小紫未启动！");
            }
        } else {
            Message.sendNoPermission(player);
        }
    }

    private void tphere(Player player) {
        if (player.hasPermission("zs.tphere")) {
            if (!violetSpawner.teleportViolet(player.getLocation())) {
                Message.sendMessage(player, "小紫未启动！");
            }
        } else {
            Message.sendNoPermission(player);
        }
    }
}
