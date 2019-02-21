package com.github.fitexmage.shadowVillageBlackMarket;

import org.bukkit.Material;

class GambleItemInfo {
    private int id;
    private String itemName;
    private Material material;
    private int basicWeight;
    private int limit;
    private int data;

    private GambleItemInfo(int id, String itemName, Material material, int basicWeight, int limit, int data) {
        this.id = id;
        this.itemName = itemName;
        this.material = material;
        this.basicWeight = basicWeight;
        this.limit = limit;
        this.data = data;
    }

    static GambleItemInfo[] getGambleItemInfos(int gambleType) {
        switch (gambleType) {
            case 1:
                return new GambleItemInfo[]{
                        new GambleItemInfo(10001, "影之木剑", Material.WOOD_SWORD, 100, 0, 4),
                        new GambleItemInfo(10002, "影之石剑", Material.STONE_SWORD, 200, 5, 5),
                        new GambleItemInfo(10003, "影之铁剑", Material.IRON_SWORD, 350, 15, 6),
                        new GambleItemInfo(10004, "影之钻石剑", Material.DIAMOND_SWORD, 500, 50, 7),
                        new GambleItemInfo(19999, "&e腐竹的锐利", Material.DIAMOND_SWORD, 550, 200, 7)
                };
            case 2:
                return new GambleItemInfo[]{
                        new GambleItemInfo(20001, "影之皮革帽子", Material.LEATHER_HELMET, 100, 0, 4),
                        new GambleItemInfo(20002, "影之铁头盔", Material.IRON_HELMET, 250, 5, 5),
                        new GambleItemInfo(20003, "影之钻石头盔", Material.DIAMOND_HELMET, 1000, 20, 7),
                        new GambleItemInfo(29999, "&e腐竹的掌控", Material.DIAMOND_HELMET, 1200, 50, 7)
                };
            case 3:
                return new GambleItemInfo[]{
                        new GambleItemInfo(30001, "影之皮革外套", Material.LEATHER_CHESTPLATE, 100, 0, 4),
                        new GambleItemInfo(30002, "影之铁胸甲", Material.IRON_CHESTPLATE, 250, 5, 5),
                        new GambleItemInfo(30003, "影之钻石胸甲", Material.DIAMOND_CHESTPLATE, 1000, 20, 7),
                        new GambleItemInfo(39999, "&e腐竹的坚定", Material.DIAMOND_CHESTPLATE, 1200, 50, 7)
                };
            case 4:
                return new GambleItemInfo[]{
                        new GambleItemInfo(40001, "影之皮革裤子", Material.LEATHER_LEGGINGS, 100, 0, 4),
                        new GambleItemInfo(40002, "影之铁护腿", Material.IRON_LEGGINGS, 250, 5, 5),
                        new GambleItemInfo(40003, "影之钻石护腿", Material.DIAMOND_LEGGINGS, 1000, 20, 7),
                        new GambleItemInfo(49999, "&e腐竹的灵动", Material.DIAMOND_LEGGINGS, 1200, 50, 7)
                };
            case 5:
                return new GambleItemInfo[]{
                        new GambleItemInfo(50001, "影之皮革靴子", Material.LEATHER_BOOTS, 100, 0, 4),
                        new GambleItemInfo(50002, "影之铁靴子", Material.IRON_BOOTS, 250, 5, 5),
                        new GambleItemInfo(50003, "影之钻石靴子", Material.DIAMOND_BOOTS, 1000, 20, 7),
                        new GambleItemInfo(59999, "&e腐竹的疾风", Material.DIAMOND_BOOTS, 1200, 50, 7)
                };
            default:
                return new GambleItemInfo[]{};
        }
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public Material getMaterial() {
        return material;
    }

    public int getBasicWeight() {
        return basicWeight;
    }

    public int getLimit() {
        return limit;
    }

    public int getData() {
        return data;
    }
}
