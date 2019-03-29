package com.github.fitexmage.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.github.fitexmage.ShadowVillageCore;
import com.github.fitexmage.util.Message;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
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
                Message.sendMessage(player, name + "已启动！");
            } else {
                switch (args[0]) {
                    case "version":
                        getVersion(player);
                    case "help":
                        help(player);
                        break;
                    case "empower":
                        empower(args, player);
                        break;
                    case "debug":
                        turnDebug(player);
                        break;
                    case "test":
                        Message.sendMessage(player, "测试");
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

    private void help(Player player) {
        if (player.hasPermission("svc.help")) {
            ItemStack helperBook = new ItemStack(Material.WRITTEN_BOOK);

            BookMeta bookMeta = (BookMeta) helperBook.getItemMeta();
            bookMeta.setAuthor("Fitexmage");
            bookMeta.setTitle("影之乡核心百科");

//            StringBuilder text101 = new StringBuilder();
//            text101.append("      影之乡核心系统\n\n");
//            text101.append("1. 获取插件运行情况：\n");
//            text101.append("/svc\n\n");
//            text101.append("2. 获取插件版本号：\n");
//            text101.append("/svc version\n\n");
//            bookMeta.addPage(text101.toString());

            StringBuilder text201 = new StringBuilder();
            text201.append("      影之乡生态系统\n\n");
            text201.append("1. 影者\n");
            text201.append("隐藏在影之乡暗面的人类。\n\n");
            text201.append("2. 影灵\n");
            text201.append("隐藏在影之乡暗面的生物。\n\n");
            text201.append("3. 影魂\n");
            text201.append("埃索泰尔守护者。\n\n");
            text201.append("4. 影魄\n");
            text201.append("埃索泰尔监督者。\n\n");
            bookMeta.addPage(text201.toString());

            StringBuilder text301 = new StringBuilder();
            text301.append("      影之乡黑市系统\n\n");
            text301.append("1. 剑抽奖：\n");
            text301.append("/svb g 1 钻石数\n\n");
            text301.append("2. 弓抽奖：\n");
            text301.append("/svb g 2 钻石数\n\n");
            text301.append("3. 头盔抽奖：\n");
            text301.append("/svb g 3 钻石数\n\n");
            text301.append("4. 胸甲抽奖：\n");
            text301.append("/svb g 4 钻石数\n\n");
            bookMeta.addPage(text301.toString());

            StringBuilder text302 = new StringBuilder();
            text302.append("      影之乡黑市系统\n\n");
            text302.append("5. 护腿抽奖：\n");
            text302.append("/svb g 5 钻石数\n\n");
            text302.append("6. 靴子抽奖：\n");
            text302.append("/svb g 6 钻石数\n\n");
            text302.append("7. 影之石数量查询：\n");
            text302.append("/svb l shadowstone");
            bookMeta.addPage(text302.toString());

//            StringBuilder text303 = new StringBuilder();
//            text303.append("      影之乡黑市系统\n\n");
//            text303.append("影之石:\n\n");
//            text303.append("影之石是影之乡服务器的通用货币，可通过赞助获得。\n");
//            text303.append("1影之石 = 1RMB。\n");
//            text303.append("影之石可以用于购买各种增值道具。\n");
//            text303.append("服务器换周目时，影之石将通过某种方式保存至新周目使用。");
//            bookMeta.addPage(text303.toString());

            StringBuilder text401 = new StringBuilder();
            text401.append("      影之乡辅助系统\n\n");
            text401.append("1. 开启聊天同步模式：\n");
            text401.append("/svs synchat true\n\n");
            text401.append("2. 关闭聊天同步模式：\n");
            text401.append("/svs synchat false\n\n");
            bookMeta.addPage(text401.toString());

            helperBook.setItemMeta(bookMeta);
            player.getInventory().addItem(helperBook);
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
