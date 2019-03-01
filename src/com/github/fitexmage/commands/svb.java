package com.github.fitexmage.commands;

import com.github.fitexmage.shadowVillageBlackMarket.GambleSystem;
import com.github.fitexmage.shadowVillageBlackMarket.ShadowItem;
import com.github.fitexmage.util.Message;

import com.github.fitexmage.util.NBTUtil;
import com.github.fitexmage.util.Tool;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class svb extends ShadowVillageCommand {
    public static boolean tradeOn;

    public svb() {

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
            if (tradeOn) {
                if (args.length == 0) {
                    Message.sendPlayerMessage(player, "这里是影之乡黑市！");
                } else {
                    switch (args[0]) {
                        case "give":
                            give(args, player);
                            break;
                        case "lookup":
                            lookup(args, player);
                            break;
                        case "l":
                            lookup(args, player);
                            break;
                        case "gamble":
                            gamble(args, player);
                            break;
                        case "g":
                            gamble(args, player);
                            break;
                        case "set":
                            set(args, player);
                            break;
                        default:
                            Message.sendPlayerMessage(player, "未知指令！");
                            break;
                    }
                }
            } else {
                Message.sendPlayerMessage(player, "影之乡黑市未启动。");
            }
        } else {
            Message.sendPlayerMessage(player, "影之乡核心未启动。");
        }
    }

    @Override
    protected void serverCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Message.sendPlayerMessage(sender, "影之乡黑市正在待命！");
        } else {
            switch (args[0]) {
                case "start":
                    tradeOn = true;
                    Message.sendPlayerMessage(sender, "影之乡黑市已启动！");
                    break;
                case "stop":
                    tradeOn = false;
                    Message.sendPlayerMessage(sender, "影之乡黑市已关闭。");
                    break;
                default:
                    Message.sendPlayerMessage(sender, "未知指令！");
                    break;
            }
        }
    }

    private void give(String[] args, Player player) {
        if (args.length == 1) {
            Message.sendPlayerMessage(player, "请选择要给予的物品！");
        } else {
            switch (args[1]) {
                case "shadowstone":
                    if (player.hasPermission("svb.give.shadowstone")) {
                        ItemStack shadowStone;
                        if (args.length == 2) {
                            shadowStone = ShadowItem.shadowStone(1);
                        } else {
                            shadowStone = ShadowItem.shadowStone(Tool.getNumber(args[2]));
                        }
                        player.getInventory().addItem(shadowStone);
                    } else {
                        Message.sendPlayerMessage(player, "你无权这样做！");
                    }
                    break;
                case "shadowsoulbook":
                    if (player.hasPermission("svb.give.shadowsoulbook")) {
                        player.getInventory().addItem(ShadowItem.shadowSoulBook());
                    } else {
                        Message.sendPlayerMessage(player, "你无权这样做！");
                    }
                    break;
                default:
                    Message.sendPlayerMessage(player, "未知指令！");
                    break;
            }
        }
    }

    private void lookup(String[] args, Player player) {
        if (args.length == 1) {
            Message.sendPlayerMessage(player, "请选择要查询的物品！");
        } else {
            if (args[1].equals("shadowstone")) {
                if (player.hasPermission("svb.lookup.shadowstone")) {
                    int shadowstoneCount = 0;

                    for (ItemStack item : Tool.getPlayerItems(player)) {
                        if (ShadowItem.isShadowStone(item)) {
                            shadowstoneCount += item.getAmount();
                        }
                    }
                    Message.sendPlayerMessage(player, "你拥有" + shadowstoneCount + "个影之石。");
                } else {
                    Message.sendPlayerMessage(player, "你无权这样做！");
                }
            } else {
                Message.sendPlayerMessage(player, "未知指令！");
            }
        }
    }

    private void gamble(String[] args, Player player) {
        if (args.length == 1) {
            Message.sendPlayerMessage(player, "请选择要得到的物品！");
        } else {
            GambleSystem gambleSystem = new GambleSystem();
            switch (args[1]) {
                case "1":
                    if (player.hasPermission("svb.gamble.weapon")) {
                        if (args.length == 2) {
                            gambleSystem.gambleItem(player, 1, "diamond", 1);
                        } else {
                            gambleSystem.gambleItem(player, 1, "diamond", Tool.getNumber(args[2]));
                        }
                    } else {
                        Message.sendPlayerMessage(player, "你无权这样做！");
                    }
                    break;
                case "2":
                    if (player.hasPermission("svb.gamble.helmet")) {
                        if (args.length == 2) {
                            gambleSystem.gambleItem(player, 2, "diamond", 1);
                        } else {
                            gambleSystem.gambleItem(player, 2, "diamond", Tool.getNumber(args[2]));
                        }
                    } else {
                        Message.sendPlayerMessage(player, "你无权这样做！");
                    }
                    break;
                case "3":
                    if (player.hasPermission("svb.gamble.chestplate")) {
                        if (args.length == 2) {
                            gambleSystem.gambleItem(player, 3, "diamond", 1);
                        } else {
                            gambleSystem.gambleItem(player, 3, "diamond", Tool.getNumber(args[2]));
                        }
                    } else {
                        Message.sendPlayerMessage(player, "你无权这样做！");
                    }
                    break;
                case "4":
                    if (player.hasPermission("svb.gamble.leggings")) {
                        if (args.length == 2) {
                            gambleSystem.gambleItem(player, 4, "diamond", 1);
                        } else {
                            gambleSystem.gambleItem(player, 4, "diamond", Tool.getNumber(args[2]));
                        }
                    } else {
                        Message.sendPlayerMessage(player, "你无权这样做！");
                    }
                    break;
                case "5":
                    if (player.hasPermission("svb.gamble.boots")) {
                        if (args.length == 2) {
                            gambleSystem.gambleItem(player, 5, "diamond", 1);
                        } else {
                            gambleSystem.gambleItem(player, 5, "diamond", Tool.getNumber(args[2]));
                        }
                    } else {
                        Message.sendPlayerMessage(player, "你无权这样做！");
                    }
                    break;
                default:
                    Message.sendPlayerMessage(player, "未知指令！");
                    break;
            }
        }
    }

    private void set(String[] args, Player player) {
        if (args.length == 1) {
            Message.sendPlayerMessage(player, "请选择想要设置的属性");
        } else {
            switch (args[1]) {
                case "damage":
                    if (player.hasPermission("svb.set.damage")) {
                        if (args.length != 2) {
                            int damage = Tool.getNumber(args[2]);
                            damage = damage > 999 ? 999 : damage;
                            ItemStack itemInHand = player.getItemInHand();
                            if (itemInHand != null && itemInHand.getType() != Material.AIR) {
                                itemInHand = NBTUtil.getNBTTagItem(itemInHand, new NBTTagCompound[]{NBTUtil.damageTag(damage)});
                                player.setItemInHand(itemInHand);
                            } else {
                                Message.sendPlayerMessage(player, "你的手上没有东西！");
                            }
                        } else {
                            Message.sendPlayerMessage(player, "请输入伤害量！");
                        }
                    } else {
                        Message.sendPlayerMessage(player, "你无权这样做！");
                    }
                    break;
                case "name":
                    if (player.hasPermission("svb.set.name")) {
                        if (args.length != 2) {
                            String name = args[2];
                            name = name.replaceAll("&", "§");
                            ItemStack itemInHand = player.getItemInHand();
                            if (itemInHand != null && itemInHand.getType() != Material.AIR) {
                                ItemMeta itemMeta = itemInHand.getItemMeta();
                                itemMeta.setDisplayName(name);
                                itemInHand.setItemMeta(itemMeta);
                                player.setItemInHand(itemInHand);
                            } else {
                                Message.sendPlayerMessage(player, "你的手上没有东西！");
                            }
                        } else {
                            Message.sendPlayerMessage(player, "请输入名字！");
                        }
                    }
                    break;
                default:
                    Message.sendPlayerMessage(player, "未知指令！");
                    break;
            }
        }
    }
}
