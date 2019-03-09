package com.github.fitexmage.commands;

import com.github.fitexmage.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class svs extends ShadowVillageCommand {

    public svs() {
        name = "影之乡辅助系统";
    }

    @Override
    void playerCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (commandOn) {
            if (args.length == 0) {
                Message.sendMessage(player, "这里是" + name + "！");
            } else {
                switch (args[0]) {
                    case "help":
                        help(player);
                        break;
                    case "address":
                        getAddress(args, player);
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

    private void help(Player player) {
        if (player.hasPermission("svs.help")) {
            ItemStack helperBook = new ItemStack(Material.WRITTEN_BOOK);

            BookMeta bookMeta = (BookMeta) helperBook.getItemMeta();
            bookMeta.setAuthor("Fitexmage");
            bookMeta.setTitle("影之乡核心百科");

            StringBuilder text101 = new StringBuilder();
            text101.append("      影之乡核心系统\n\n");
            text101.append("1. 获取插件版本号：\n");
            text101.append("/svc version\n\n");
            text101.append("2. 获得《影之乡核心百科》（没错就是你看的这个）：\n");
            text101.append("/svc help\n\n");
            bookMeta.addPage(text101.toString());

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

            StringBuilder text303 = new StringBuilder();
            text303.append("      影之乡黑市系统\n\n");
            text303.append("影之石:\n\n");
            text303.append("影之石是影之乡服务器的通用货币，可通过赞助获得。\n");
            text303.append("1影之石 = 1RMB。\n");
            text303.append("影之石可以用于购买各种增值道具。\n");
            text303.append("服务器换周目时，影之石将通过某种方式保存至新周目使用。");
            bookMeta.addPage(text303.toString());

            helperBook.setItemMeta(bookMeta);
            player.getInventory().addItem(helperBook);
        }
    }

    private void getAddress(String[] args, Player player) {
        if (player.hasPermission("svs.address")) {
            if (args.length == 1) {
                Message.sendMessage(player, "请选择想要查的人！");
            } else if (args.length == 2) {
                Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
                if (targetPlayer != null) {
                    Message.sendMessage(player, "该玩家的地址是" + targetPlayer.getAddress());
                } else {
                    Message.sendMessage(player, "未发现该玩家！");
                }
            } else {
                Message.sendUnknown(player);
            }
        } else {
            Message.sendNoPermission(player);
        }
    }
}
