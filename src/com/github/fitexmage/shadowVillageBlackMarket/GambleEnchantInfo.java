package com.github.fitexmage.shadowVillageBlackMarket;

import org.bukkit.enchantments.Enchantment;

class GambleEnchantInfo {
    private Enchantment enchantment;
    private String name;
    private int maxLevel;

    private GambleEnchantInfo(Enchantment enchantment, String name, int maxLevel) {
        this.enchantment = enchantment;
        this.name = name;
        this.maxLevel = maxLevel;
    }

    static GambleEnchantInfo[][] getGambleEnchants() {
        return new GambleEnchantInfo[][]{
                { //武器
                        new GambleEnchantInfo(Enchantment.DAMAGE_ARTHROPODS, "节肢杀手之", 5),
                        new GambleEnchantInfo(Enchantment.FIRE_ASPECT, "火焰附加之", 2),
                        new GambleEnchantInfo(Enchantment.KNOCKBACK, "击退之", 2),
                        new GambleEnchantInfo(Enchantment.LOOT_BONUS_MOBS, "掠夺之", 3),
                        new GambleEnchantInfo(Enchantment.DAMAGE_ALL, "锋利之", 5),
                        new GambleEnchantInfo(Enchantment.DAMAGE_UNDEAD, "亡灵杀手之", 5),
                        new GambleEnchantInfo(Enchantment.DURABILITY, "耐久之", 3)
                },
                { //头盔
                        new GambleEnchantInfo(Enchantment.WATER_WORKER, "水下速掘之", 1),
                        new GambleEnchantInfo(Enchantment.PROTECTION_EXPLOSIONS, "爆炸保护之", 10),
                        new GambleEnchantInfo(Enchantment.PROTECTION_FIRE, "火焰保护之", 7),
                        new GambleEnchantInfo(Enchantment.PROTECTION_PROJECTILE, "弹射物保护之", 10),
                        new GambleEnchantInfo(Enchantment.PROTECTION_ENVIRONMENTAL, "保护之", 15),
                        new GambleEnchantInfo(Enchantment.OXYGEN, "水下呼吸之", 3),
                        new GambleEnchantInfo(Enchantment.THORNS, "荆棘之", 3),
                        new GambleEnchantInfo(Enchantment.DURABILITY, "耐久之", 3)
                },
                { //胸甲
                        new GambleEnchantInfo(Enchantment.PROTECTION_EXPLOSIONS, "爆炸保护之", 10),
                        new GambleEnchantInfo(Enchantment.PROTECTION_FIRE, "火焰保护之", 7),
                        new GambleEnchantInfo(Enchantment.PROTECTION_PROJECTILE, "弹射物保护之", 10),
                        new GambleEnchantInfo(Enchantment.PROTECTION_ENVIRONMENTAL, "保护之", 15),
                        new GambleEnchantInfo(Enchantment.THORNS, "荆棘之", 3),
                        new GambleEnchantInfo(Enchantment.DURABILITY, "耐久之", 3)
                },
                { //护腿
                        new GambleEnchantInfo(Enchantment.PROTECTION_EXPLOSIONS, "爆炸保护之", 10),
                        new GambleEnchantInfo(Enchantment.PROTECTION_FIRE, "火焰保护之", 7),
                        new GambleEnchantInfo(Enchantment.PROTECTION_PROJECTILE, "弹射物保护之", 10),
                        new GambleEnchantInfo(Enchantment.PROTECTION_ENVIRONMENTAL, "保护之", 15),
                        new GambleEnchantInfo(Enchantment.THORNS, "荆棘之", 3),
                        new GambleEnchantInfo(Enchantment.DURABILITY, "耐久之", 3)
                },
                { //靴子
                        new GambleEnchantInfo(Enchantment.PROTECTION_EXPLOSIONS, "爆炸保护之", 10),
                        new GambleEnchantInfo(Enchantment.PROTECTION_FALL, "摔落保护之", 4),
                        new GambleEnchantInfo(Enchantment.PROTECTION_FIRE, "火焰保护之", 7),
                        new GambleEnchantInfo(Enchantment.PROTECTION_PROJECTILE, "弹射物保护之", 10),
                        new GambleEnchantInfo(Enchantment.PROTECTION_ENVIRONMENTAL, "保护之", 15),
                        new GambleEnchantInfo(Enchantment.THORNS, "荆棘之", 3),
                        new GambleEnchantInfo(Enchantment.DURABILITY, "耐久之", 3)
                }
        };
    }

    Enchantment getEnchantment() {
        return enchantment;
    }

    String getName() {
        return name;
    }

    int getMaxLevel() {
        return maxLevel;
    }
}
