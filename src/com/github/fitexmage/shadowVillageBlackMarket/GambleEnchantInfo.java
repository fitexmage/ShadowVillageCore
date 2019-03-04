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

    static GambleEnchantInfo[] getGambleEnchants(int gambleType) {
        switch (gambleType) {
            case 1:
                return new GambleEnchantInfo[]{
                        new GambleEnchantInfo(Enchantment.DAMAGE_ARTHROPODS, "节肢杀手之", 5),
                        new GambleEnchantInfo(Enchantment.FIRE_ASPECT, "火焰附加之", 2),
                        new GambleEnchantInfo(Enchantment.KNOCKBACK, "击退之", 2),
                        new GambleEnchantInfo(Enchantment.LOOT_BONUS_MOBS, "掠夺之", 3),
                        new GambleEnchantInfo(Enchantment.DAMAGE_UNDEAD, "亡灵杀手之", 5),
                        new GambleEnchantInfo(Enchantment.DURABILITY, "耐久之", 3)
                };
            case 2:
                return new GambleEnchantInfo[]{ //弓
                        new GambleEnchantInfo(Enchantment.ARROW_FIRE, "火矢之", 1),
                        new GambleEnchantInfo(Enchantment.ARROW_INFINITE, "无限之", 1),
                        new GambleEnchantInfo(Enchantment.ARROW_DAMAGE, "力量之", 5),
                        new GambleEnchantInfo(Enchantment.ARROW_KNOCKBACK, "冲击之", 2)
                };
            case 3:
                return new GambleEnchantInfo[]{ //头盔
                        new GambleEnchantInfo(Enchantment.WATER_WORKER, "水下速掘之", 1),
                        new GambleEnchantInfo(Enchantment.PROTECTION_EXPLOSIONS, "爆炸保护之", 10),
                        new GambleEnchantInfo(Enchantment.PROTECTION_FIRE, "火焰保护之", 7),
                        new GambleEnchantInfo(Enchantment.PROTECTION_PROJECTILE, "弹射物保护之", 10),
                        new GambleEnchantInfo(Enchantment.PROTECTION_ENVIRONMENTAL, "保护之", 10),
                        new GambleEnchantInfo(Enchantment.OXYGEN, "水下呼吸之", 3),
                        new GambleEnchantInfo(Enchantment.THORNS, "荆棘之", 3),
                        new GambleEnchantInfo(Enchantment.DURABILITY, "耐久之", 3)
                };
            case 4:
                return new GambleEnchantInfo[]{ //胸甲
                        new GambleEnchantInfo(Enchantment.PROTECTION_EXPLOSIONS, "爆炸保护之", 10),
                        new GambleEnchantInfo(Enchantment.PROTECTION_FIRE, "火焰保护之", 7),
                        new GambleEnchantInfo(Enchantment.PROTECTION_PROJECTILE, "弹射物保护之", 10),
                        new GambleEnchantInfo(Enchantment.PROTECTION_ENVIRONMENTAL, "保护之", 10),
                        new GambleEnchantInfo(Enchantment.THORNS, "荆棘之", 3),
                        new GambleEnchantInfo(Enchantment.DURABILITY, "耐久之", 3)
                };
            case 5:
                return new GambleEnchantInfo[]{ //护腿
                        new GambleEnchantInfo(Enchantment.PROTECTION_EXPLOSIONS, "爆炸保护之", 10),
                        new GambleEnchantInfo(Enchantment.PROTECTION_FIRE, "火焰保护之", 7),
                        new GambleEnchantInfo(Enchantment.PROTECTION_PROJECTILE, "弹射物保护之", 10),
                        new GambleEnchantInfo(Enchantment.PROTECTION_ENVIRONMENTAL, "保护之", 10),
                        new GambleEnchantInfo(Enchantment.THORNS, "荆棘之", 3),
                        new GambleEnchantInfo(Enchantment.DURABILITY, "耐久之", 3)
                };
            case 6:
                return new GambleEnchantInfo[]{ //靴子
                        new GambleEnchantInfo(Enchantment.PROTECTION_EXPLOSIONS, "爆炸保护之", 10),
                        new GambleEnchantInfo(Enchantment.PROTECTION_FALL, "摔落保护之", 4),
                        new GambleEnchantInfo(Enchantment.PROTECTION_FIRE, "火焰保护之", 7),
                        new GambleEnchantInfo(Enchantment.PROTECTION_PROJECTILE, "弹射物保护之", 10),
                        new GambleEnchantInfo(Enchantment.PROTECTION_ENVIRONMENTAL, "保护之", 10),
                        new GambleEnchantInfo(Enchantment.THORNS, "荆棘之", 3),
                        new GambleEnchantInfo(Enchantment.DURABILITY, "耐久之", 3)
                };
            default:
                return new GambleEnchantInfo[]{};
        }
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
