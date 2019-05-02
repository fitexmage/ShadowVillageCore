package com.github.fitexmage.commands;

import com.github.fitexmage.shadowVillageBlackMarket.GambleSystem;
import com.github.fitexmage.shadowVillageBlackMarket.ShadowItem;
import com.github.fitexmage.util.EconomyUtil;
import com.github.fitexmage.util.Message;

import com.github.fitexmage.util.NBTUtil;
import com.github.fitexmage.util.Tool;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class svb extends ShadowVillageCommand {
    private GambleSystem gambleSystem;

    public svb() {
        name = "影之乡黑市系统";
        gambleSystem = new GambleSystem();
    }

    @Override
    void playerCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (enable) {
            if (args.length == 0) {
                Message.sendMessage(player, "这里是" + name + "！");
            } else {
                switch (args[0].toLowerCase()) {
                    case "give":
                        give(player, args);
                        break;
                    case "set":
                        set(player, args);
                        break;
                    case "lookup":
                        lookup(player, args);
                        break;
                    case "l":
                        lookup(player, args);
                        break;
                    case "gamble":
                        gamble(player, args);
                        break;
                    case "g":
                        gamble(player, args);
                        break;
                    case "bound":
                        bound(player);
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

    private void give(Player player, String[] args) {
        if (args.length == 1) {
            Message.sendMessage(player, "请输入要给予的物品！");
        } else {
            switch (args[1]) {
                case "shadowstone":
                    if (player.hasPermission("svb.give.shadowstone")) {
                        ItemStack shadowStone;
                        if (args.length == 2) {
                            shadowStone = ShadowItem.getShadowStone(1);
                        } else {
                            shadowStone = ShadowItem.getShadowStone(Tool.getNumber(args[2]));
                        }
                        player.getInventory().addItem(shadowStone);
                        Message.sendMessage(player, "已获得影之石！");
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                case "shadowsoulbook":
                    if (player.hasPermission("svb.give.shadowsoulbook")) {
                        player.getInventory().addItem(ShadowItem.getShadowSoulBook());
                        Message.sendMessage(player, "已获得影魂之书！");
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                case "shadowspiritbook":
                    if (player.hasPermission("svb.give.shadowspiritbook")) {
                        player.getInventory().addItem(ShadowItem.getShadowSpiritBook());
                        Message.sendMessage(player, "已获得影魄之书！");
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                case "shadowlordbooktop":
                    if (player.hasPermission("svb.give.shadowlordbooktop")) {
                        player.getInventory().addItem(ShadowItem.getShadowLordBookTop());
                        Message.sendMessage(player, "已获得影主之书（上）！");
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                case "shadowlordbookbottom":
                    if (player.hasPermission("svb.give.shadowlordbookbottom")) {
                        player.getInventory().addItem(ShadowItem.getShadowLordBookBottom());
                        Message.sendMessage(player, "已获得影主之书（下）！");
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                case "serverequipment":
                    if (player.hasPermission("svb.give.serverequipment")) {
                        player.getInventory().addItem(ShadowItem.getServerEquipment(1, false));
                        player.getInventory().addItem(ShadowItem.getServerEquipment(2, false));
                        player.getInventory().addItem(ShadowItem.getServerEquipment(3, false));
                        player.getInventory().addItem(ShadowItem.getServerEquipment(4, false));
                        player.getInventory().addItem(ShadowItem.getServerEquipment(5, false));
                        player.getInventory().addItem(ShadowItem.getServerEquipment(6, false));
                        Message.sendMessage(player, "已获得腐竹套装！");
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                default:
                    Message.sendUnknown(player);
                    break;
            }
        }
    }

    private void set(Player player, String[] args) {
        if (args.length == 1) {
            Message.sendMessage(player, "请输入想要设置的属性!");
        } else {
            switch (args[1]) {
                case "attack":
                    if (player.hasPermission("svb.set.attack")) {
                        if (args.length != 2) {
                            int attack = Tool.getNumber(args[2]);
                            attack = attack > 999 ? 999 : attack;
                            ItemStack itemInHand = player.getItemInHand();
                            if (itemInHand != null && itemInHand.getType() != Material.AIR) {
                                itemInHand = NBTUtil.getNBTTagItem(itemInHand, new NBTTagCompound[]{NBTUtil.attackTag(attack)});
                                player.setItemInHand(itemInHand);
                            } else {
                                Message.sendMessage(player, "你的手上没有东西！");
                            }
                        } else {
                            Message.sendMessage(player, "请输入伤害量！");
                        }
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                case "health":
                    if (player.hasPermission("svb.set.health")) {
                        if (args.length != 2) {
                            int health = Tool.getNumber(args[2]);
                            health = health > 100 ? 100 : health;
                            ItemStack itemInHand = player.getItemInHand();
                            if (itemInHand != null && itemInHand.getType() != Material.AIR) {
                                itemInHand = NBTUtil.getNBTTagItem(itemInHand, new NBTTagCompound[]{NBTUtil.healthTag(health)});
                                player.setItemInHand(itemInHand);
                            } else {
                                Message.sendMessage(player, "你的手上没有东西！");
                            }
                        } else {
                            Message.sendMessage(player, "请输入生命值！");
                        }
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
                                Message.sendMessage(player, "你的手上没有东西！");
                            }
                        } else {
                            Message.sendMessage(player, "请输入名字！");
                        }
                    }
                    break;
                default:
                    Message.sendUnknown(player);
                    break;
            }
        }
    }

    private void lookup(Player player, String[] args) {
        if (args.length == 1) {
            Message.sendMessage(player, "请输入要查询的物品！");
        } else {
            switch (args[1]) {
                case "shadowstone":
                    if (player.hasPermission("svb.lookup.shadowstone")) {
                        int shadowStoneCount = 0;

                        for (ItemStack item : Tool.getPlayerItems(player)) {
                            if (ShadowItem.isShadowStone(item)) {
                                shadowStoneCount += item.getAmount();
                            }
                        }
                        Message.sendMessage(player, "你拥有" + shadowStoneCount + "个影之石。");
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                case "possibilities":
                    if (player.hasPermission("svb.lookup.possibilities")) {
                        if (args.length != 2) {
                            if (args.length != 3) {
                                GambleSystem gambleSystem = new GambleSystem();
                                switch (args[2]) {
                                    case "1":
                                        gambleSystem.getPossibilities(player, 1, Tool.getNumber(args[3]));
                                        break;
                                    case "2":
                                        gambleSystem.getPossibilities(player, 2, Tool.getNumber(args[3]));
                                        break;
                                    case "3":
                                        gambleSystem.getPossibilities(player, 3, Tool.getNumber(args[3]));
                                        break;
                                    case "4":
                                        gambleSystem.getPossibilities(player, 4, Tool.getNumber(args[3]));
                                        break;
                                    case "5":
                                        gambleSystem.getPossibilities(player, 5, Tool.getNumber(args[3]));
                                        break;
                                    case "6":
                                        gambleSystem.getPossibilities(player, 6, Tool.getNumber(args[3]));
                                        break;
                                    default:
                                        Message.sendUnknown(player);
                                        break;
                                }
                            } else {
                                Message.sendMessage(player, "请输入数量！");
                            }
                        } else {
                            Message.sendMessage(player, "请选择物品！");
                        }
                    }
                    break;
                default:
                    Message.sendUnknown(player);
                    break;
            }
        }
    }

    private void gamble(Player player, String[] args) {
        if (args.length == 1) {
            Message.sendMessage(player, "请输入想要得到的物品！");
        } else {
            switch (args[1]) {
                case "1":
                    if (player.hasPermission("svb.gamble.sword")) {
                        if (args.length == 2) {
                            gambleSystem.gambleItem(player, 1, "money", 1);
                        } else {
                            gambleSystem.gambleItem(player, 1, "money", Tool.getNumber(args[2]));
                        }
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                case "2":
                    if (player.hasPermission("svb.gamble.bow")) {
                        if (args.length == 2) {
                            gambleSystem.gambleItem(player, 2, "money", 1);
                        } else {
                            gambleSystem.gambleItem(player, 2, "money", Tool.getNumber(args[2]));
                        }
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                case "3":
                    if (player.hasPermission("svb.gamble.helmet")) {
                        if (args.length == 2) {
                            gambleSystem.gambleItem(player, 3, "money", 1);
                        } else {
                            gambleSystem.gambleItem(player, 3, "money", Tool.getNumber(args[2]));
                        }
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                case "4":
                    if (player.hasPermission("svb.gamble.chestplate")) {
                        if (args.length == 2) {
                            gambleSystem.gambleItem(player, 4, "money", 1);
                        } else {
                            gambleSystem.gambleItem(player, 4, "money", Tool.getNumber(args[2]));
                        }
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                case "5":
                    if (player.hasPermission("svb.gamble.leggings")) {
                        if (args.length == 2) {
                            gambleSystem.gambleItem(player, 5, "money", 1);
                        } else {
                            gambleSystem.gambleItem(player, 5, "money", Tool.getNumber(args[2]));
                        }
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                case "6":
                    if (player.hasPermission("svb.gamble.boots")) {
                        if (args.length == 2) {
                            gambleSystem.gambleItem(player, 6, "money", 1);
                        } else {
                            gambleSystem.gambleItem(player, 6, "money", Tool.getNumber(args[2]));
                        }
                    } else {
                        Message.sendNoPermission(player);
                    }
                    break;
                default:
                    Message.sendUnknown(player);
                    break;
            }
        }
    }

    public static void bound(Player player) {
        if (player.hasPermission("svb.bound")) {
            ItemStack itemInHand = player.getItemInHand();
            if (itemInHand != null && itemInHand.getType() != Material.AIR) {
                if (ShadowItem.isBounded(itemInHand)) {
                    Message.sendMessage(player, "物品已经被灵魂绑定了！");
                } else if (EconomyUtil.economy.getBalance(player) >= 10) {
                    Message.sendMessage(player, "你的金钱不足！");
                } else {
                    ItemMeta meta = itemInHand.getItemMeta();
                    if (meta.hasLore()) {
                        meta.getLore().add("灵魂绑定");
                    } else {
                        meta.setLore(Collections.singletonList("灵魂绑定"));
                    }
                    itemInHand.setItemMeta(meta);
                    EconomyUtil.economy.withdrawPlayer(player, 10);
                    Message.sendMessage(player, "灵魂绑定成功！花费了10金币！");
                }
            } else {
                Message.sendMessage(player, "请选择要绑定的物品！");
            }
        }
    }
}
