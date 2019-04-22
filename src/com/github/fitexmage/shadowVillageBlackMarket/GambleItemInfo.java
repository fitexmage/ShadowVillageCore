package com.github.fitexmage.shadowVillageBlackMarket;

import org.bukkit.Material;

class GambleItemInfo {
    private String itemName;
    private Material material;
    private int basicWeight;
    private int limit;
    private int data;

    private GambleItemInfo(String itemName, Material material, int basicWeight, int limit, int data) {
        this.itemName = itemName;
        this.material = material;
        this.basicWeight = basicWeight;
        this.limit = limit;
        this.data = data;
    }

    static GambleItemInfo[] getGambleItemInfos(int type) {
        switch (type) {
            case 1:
                return new GambleItemInfo[]{
                        new GambleItemInfo("影之木剑", Material.WOOD_SWORD, 1, 0, 4),
                        new GambleItemInfo("影之石剑", Material.STONE_SWORD, 2, 50, 5),
                        new GambleItemInfo("影之铁剑", Material.IRON_SWORD, 5, 200, 6),
                        new GambleItemInfo("影之钻石剑", Material.DIAMOND_SWORD, 10, 500, 7),
                        new GambleItemInfo("§5腐竹的锐利", Material.DIAMOND_SWORD, 1, 5000, 99)
                };
            case 2:
                return new GambleItemInfo[]{
                        new GambleItemInfo("影之弓", Material.BOW, 1, 0, 1),
                        new GambleItemInfo("§5腐竹的精准", Material.BOW, 1, 2000, 99)
                };
            case 3:
                return new GambleItemInfo[]{
                        new GambleItemInfo("影之皮革帽子", Material.LEATHER_HELMET, 1, 0, 2),
                        new GambleItemInfo("影之铁头盔", Material.IRON_HELMET, 3, 100, 6),
                        new GambleItemInfo("影之钻石头盔", Material.DIAMOND_HELMET, 6, 200, 10),
                        new GambleItemInfo("§5腐竹的掌控", Material.DIAMOND_HELMET, 1, 2000, 99)
                };
            case 4:
                return new GambleItemInfo[]{
                        new GambleItemInfo("影之皮革外套", Material.LEATHER_CHESTPLATE, 1, 0, 2),
                        new GambleItemInfo("影之铁胸甲", Material.IRON_CHESTPLATE, 3, 100, 6),
                        new GambleItemInfo("影之钻石胸甲", Material.DIAMOND_CHESTPLATE, 6, 200, 10),
                        new GambleItemInfo("§5腐竹的坚定", Material.DIAMOND_CHESTPLATE, 1, 2000, 99)
                };
            case 5:
                return new GambleItemInfo[]{
                        new GambleItemInfo("影之皮革裤子", Material.LEATHER_LEGGINGS, 100, 0, 2),
                        new GambleItemInfo("影之铁护腿", Material.IRON_LEGGINGS, 300, 100, 6),
                        new GambleItemInfo("影之钻石护腿", Material.DIAMOND_LEGGINGS, 600, 200, 10),
                        new GambleItemInfo("§5腐竹的灵动", Material.DIAMOND_LEGGINGS, 100, 2000, 99)
                };
            case 6:
                return new GambleItemInfo[]{
                        new GambleItemInfo("影之皮革靴子", Material.LEATHER_BOOTS, 100, 0, 2),
                        new GambleItemInfo("影之铁靴子", Material.IRON_BOOTS, 300, 100, 6),
                        new GambleItemInfo("影之钻石靴子", Material.DIAMOND_BOOTS, 600, 200, 10),
                        new GambleItemInfo("§5腐竹的疾风", Material.DIAMOND_BOOTS, 100, 2000, 99)
                };
            default:
                return new GambleItemInfo[]{};
        }
    }

    static GambleItemInfo getServerEquipmentInfo(int type) {
        GambleItemInfo[] gambleItemInfos = getGambleItemInfos(type);
        return gambleItemInfos[gambleItemInfos.length - 1];
    }

    String getItemName() {
        return itemName;
    }

    Material getMaterial() {
        return material;
    }

    int getBasicWeight() {
        return basicWeight;
    }

    int getLimit() {
        return limit;
    }

    int getData() {
        return data;
    }
}
