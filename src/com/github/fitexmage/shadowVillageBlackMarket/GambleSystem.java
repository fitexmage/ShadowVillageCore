package com.github.fitexmage.shadowVillageBlackMarket;

import com.github.fitexmage.util.EconomyUtil;
import com.github.fitexmage.util.NBTUtil;
import com.github.fitexmage.util.Message;
import com.github.fitexmage.util.Tool;

import net.minecraft.server.v1_7_R4.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GambleSystem {

    public void gambleItem(Player player, int gambleType, String costType, int cost) {
        if (cost >= 10) {
            if (costType.equals("diamond")) {
                int diamondCount = 0;
                int diamondBlockCount = 0;
                for (ItemStack item : Tool.getPlayerItems(player)) {
                    if (item.getType().equals(Material.DIAMOND)) {
                        diamondCount += item.getAmount();
                    } else if (item.getType().equals(Material.DIAMOND_BLOCK)) {
                        diamondBlockCount += item.getAmount();
                    }
                }

                if (cost <= diamondCount + diamondBlockCount * 9) {
                    GambleItemInfo[] gambleItemInfos = GambleItemInfo.getGambleItemInfos(gambleType);
                    int[] weights = getWeights(gambleItemInfos, cost); //根据钻石数决定品质的权重
                    ItemStack gambleItem = getGambleItem(gambleItemInfos, weights, gambleType); //根据权重选取物品
                    tradeWithDiamond(player, cost, diamondBlockCount, gambleItem); //给予玩家物品
                    Message.sendMessage(player, "恭喜你！抽到了好东西！");
                } else {
                    Message.sendMessage(player, "你没有这么多钻石！");
                }
            } else if (costType.equals("money")) {
                if (EconomyUtil.economy.getBalance(player) >= cost) {
                    GambleItemInfo[] gambleItemInfos = GambleItemInfo.getGambleItemInfos(gambleType);
                    int[] weights = getWeights(gambleItemInfos, cost);
                    ItemStack gambleItem = getGambleItem(gambleItemInfos, weights, gambleType);
                    tradeWithMoney(player, cost, gambleItem);
                    Message.sendMessage(player, "恭喜你！抽到了好东西！");
                } else {
                    Message.sendMessage(player, "你没有这么多钱！");
                }
            }
        } else {
            Message.sendMessage(player, "你至少需要花费10金币！");
        }
    }

    public void getPossibilities(Player player, int gambleType, int cost) {
        GambleItemInfo[] gambleItemInfos = GambleItemInfo.getGambleItemInfos(gambleType);
        int[] weights = getWeights(gambleItemInfos, cost);
        int sum = 0;
        for (int weight : weights) {
            sum += weight;
        }
        StringBuilder possibilities = new StringBuilder("");
        possibilities.append("花费" + cost + "游戏币所获得的装备概率一览：\n");
        for (int i = 0; i < gambleItemInfos.length; i++) {
            possibilities.append(gambleItemInfos[i].getItemName() + ": " + ((float) weights[i] / sum) * 100 + "%\n");
        }
        Message.sendMessage(player, possibilities.toString());
    }

    private int[] getWeights(GambleItemInfo[] gambleItemInfos, int cost) {
        int[] weights = new int[gambleItemInfos.length];
        for (int i = 0; i < gambleItemInfos.length; i++) {
            if (i == gambleItemInfos.length - 1) {
                if (cost > gambleItemInfos[i].getLimit()) {
                    for (int j = 0; j < i + 1; j++) {
                        weights[j] = (cost - gambleItemInfos[j].getLimit()) * gambleItemInfos[j].getBasicWeight();
                    }
                }
            } else {
                if (cost > gambleItemInfos[i].getLimit() && cost <= gambleItemInfos[i + 1].getLimit()) {
                    for (int j = 0; j < i + 1; j++) {
                        weights[j] = (cost - gambleItemInfos[j].getLimit()) * gambleItemInfos[j].getBasicWeight();
                    }
                    break;
                }
            }
        }
        return weights;
    }

    private ItemStack getGambleItem(GambleItemInfo[] gambleItemInfos, int[] weights, int type) {
        int total = 0;
        for (int weight : weights) {
            total += weight;
        }
        int randomType = (int) (Math.random() * total);
        ItemStack gambleItem = null;
        for (int i = 0; i < gambleItemInfos.length; i++) {
            if (randomType < weights[i]) {
                GambleItemInfo gambleItemInfo = gambleItemInfos[i];
                if (gambleItemInfo.getData() == 99) {
                    gambleItem = ShadowItem.getServerEquipment(type, true);
                    Message.broadcastMessage("腐竹的力量重现于世！");
                } else {
                    gambleItem = new ItemStack(gambleItemInfo.getMaterial()); //确定物品品质
                    randomEnchant(gambleItem, gambleItemInfo, type); //随机附魔
                    if (type == 1) {
                        gambleItem = getChangedGambleSword(gambleItem, gambleItemInfo); //改变攻击
                    } else if (type == 3 ||
                            type == 4 ||
                            type == 5 ||
                            type == 6) {
                        gambleItem = getChangedGambleArmor(gambleItem, gambleItemInfo, type); //改变最大生命
                    }
                    if ((int) (Math.random() * 50) == 0) {
                        gambleItem = NBTUtil.getUnbreakableItem(gambleItem);
                    }
                }
                break;
            }
            randomType -= weights[i];
        }
        return gambleItem;
    }

    private void randomEnchant(ItemStack gambleItem, GambleItemInfo gambleItemInfo, int type) {
        GambleEnchantInfo[] gambleEnchantInfos = GambleEnchantInfo.getGambleEnchants(type);

        String displayName = "";
        ItemMeta meta = gambleItem.getItemMeta();
        for (GambleEnchantInfo gambleEnchantInfo : gambleEnchantInfos) {
            if ((int) (Math.random() * 6) == 0) {
                int randomLevel = (int) (Math.random() * gambleEnchantInfo.getMaxLevel()) + 1;
                meta.addEnchant(gambleEnchantInfo.getEnchantment(), randomLevel, true);
                displayName += gambleEnchantInfo.getName();
            }
        }
        displayName += gambleItemInfo.getItemName();
        meta.setDisplayName(displayName);
        gambleItem.setItemMeta(meta);
    }

    private ItemStack getChangedGambleSword(ItemStack gambleSword, GambleItemInfo gambleItemInfo) {
        int damage = (int) (Math.random() * (Math.pow(gambleItemInfo.getData(), 2) * 1.5 - (gambleItemInfo.getData() / 2))) + gambleItemInfo.getData() / 2;

        gambleSword = NBTUtil.getNBTTagItem(gambleSword, new NBTTagCompound[]{NBTUtil.attackTag(damage)});
        return gambleSword;
    }

    private ItemStack getChangedGambleArmor(ItemStack gambleArmor, GambleItemInfo gambleItemInfo, int type) {
        int health = (int) (Math.random() * (gambleItemInfo.getData() + 1)) + gambleItemInfo.getData();
        gambleArmor = NBTUtil.getNBTTagItem(gambleArmor, new NBTTagCompound[]{NBTUtil.healthTag(health)});
        if (type == 6) {
            double speed = (double) gambleItemInfo.getData() / 20;
            gambleArmor = NBTUtil.getNBTTagItem(gambleArmor, new NBTTagCompound[]{NBTUtil.speedTag(speed)});
        }
        return gambleArmor;
    }

    private void tradeWithDiamond(Player player, int cost, int diamondBlockCount, ItemStack gambleItem) {
        Inventory inventory = player.getInventory();
        if (cost <= diamondBlockCount * 9) {
            inventory.removeItem(new ItemStack(Material.DIAMOND_BLOCK, cost / 9));
            if (cost % 9 != 0) {
                inventory.removeItem(new ItemStack(Material.DIAMOND_BLOCK, 1));
                inventory.addItem(new ItemStack(Material.DIAMOND, 9 - cost % 9));
            }
        } else {
            inventory.removeItem(new ItemStack(Material.DIAMOND, cost));
        }
        inventory.addItem(gambleItem);
    }

    private void tradeWithMoney(Player player, int cost, ItemStack gambleItem) {
        EconomyUtil.economy.withdrawPlayer(player, cost);
        Inventory inventory = player.getInventory();
        inventory.addItem(gambleItem);
    }
}
