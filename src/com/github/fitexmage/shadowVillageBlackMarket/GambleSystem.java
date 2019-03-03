package com.github.fitexmage.shadowVillageBlackMarket;

import com.github.fitexmage.util.NBTUtil;
import com.github.fitexmage.util.Message;
import com.github.fitexmage.util.Tool;

import net.minecraft.server.v1_7_R4.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GambleSystem {

    public void gambleItem(Player player, int gambleType, String costType, int cost) {
        if (cost > 0) {
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
                    exchangeDiamond(player, cost, diamondBlockCount, gambleItem); //给予玩家物品
                    Message.sendMessage(player, "恭喜你！抽到了好东西！");
                } else {
                    Message.sendMessage(player, "你没有这么多钻石！");
                }
            }
        } else {
            Message.sendMessage(player, "数量必须为正数！");
        }
    }

    public void getPossibilities(Player player, int gambleType, int cost) {
        GambleItemInfo[] gambleItemInfos = GambleItemInfo.getGambleItemInfos(gambleType);
        int[] weights = getWeights(gambleItemInfos, cost);
        int sum = 0;
        for (int weight : weights) {
            sum += weight;
        }
        StringBuilder possibilities = new StringBuilder();
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
                gambleItem = new ItemStack(gambleItemInfo.getMaterial()); //确定物品品质
                randomEnchant(gambleItem, gambleItemInfo, type); //随机附魔
                if (type == 1) {
                    gambleItem = getChangedGambleSword(gambleItem, gambleItemInfo); //改变攻击
                }
                if (gambleItemInfo.getId() == 19999 ||
                        gambleItemInfo.getId() == 29999 ||
                        gambleItemInfo.getId() == 39999 ||
                        gambleItemInfo.getId() == 49999 ||
                        gambleItemInfo.getId() == 59999 ||
                        gambleItemInfo.getId() == 69999) {
                    gambleItem = NBTUtil.getUnbreakableItem(gambleItem);
                    Message.broadcastMessage("腐竹的力量重现于世！");
                } else if ((int) (Math.random() * 50) == 0) {
                    gambleItem = NBTUtil.getUnbreakableItem(gambleItem);
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

        if (gambleItemInfo.getId() == 19999 ||
                gambleItemInfo.getId() == 29999 ||
                gambleItemInfo.getId() == 39999 ||
                gambleItemInfo.getId() == 49999 ||
                gambleItemInfo.getId() == 59999 ||
                gambleItemInfo.getId() == 69999) {
            for (GambleEnchantInfo gambleEnchantInfo : gambleEnchantInfos) {
                meta.addEnchant(gambleEnchantInfo.getEnchantment(), 10, true);
            }
            if (gambleItemInfo.getId() == 39999 ||
                    gambleItemInfo.getId() == 49999 ||
                    gambleItemInfo.getId() == 59999 ||
                    gambleItemInfo.getId() == 69999) {
                meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 20, true);
            }
        } else {
            for (GambleEnchantInfo gambleEnchantInfo : gambleEnchantInfos) {
                int randomChoice = (int) (Math.random() * 8);
                if (randomChoice == 0) {
                    int randomLevel = (int) (Math.random() * gambleEnchantInfo.getMaxLevel()) + 1;
                    meta.addEnchant(gambleEnchantInfo.getEnchantment(), randomLevel, true);
                    displayName += gambleEnchantInfo.getName();
                }
            }
        }
        displayName += gambleItemInfo.getItemName();
        meta.setDisplayName(displayName);
        gambleItem.setItemMeta(meta);
    }

    private ItemStack getChangedGambleSword(ItemStack gambleSword, GambleItemInfo gambleItemInfo) {
        int damage;
        if (gambleItemInfo.getId() == 19999) {
            damage = 9999;
        } else {
            damage = (int) (Math.random() * (Math.pow(gambleItemInfo.getData(), 2) * 1.5 - (gambleItemInfo.getData() / 2))) + gambleItemInfo.getData() / 2;
        }
        gambleSword = NBTUtil.getNBTTagItem(gambleSword, new NBTTagCompound[]{NBTUtil.damageTag(damage)});
        return gambleSword;
    }

    private void exchangeDiamond(Player player, int cost, int diamondBlockCount, ItemStack gambleSword) {
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
        inventory.addItem(gambleSword);
    }
}
