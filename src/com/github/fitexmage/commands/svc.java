package com.github.fitexmage.commands;

import com.github.fitexmage.util.Message;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class svc extends ShadowVillageCommand {
    public static boolean coreOn;

    public svc() {

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
        if (coreOn) {
            if (args.length == 0) {
                Message.sendPlayerMessage(player, "凡人，你为何呼唤我？");
            } else {
                switch (args[0]) {
                    case "empower":
                        empower(args, player);
                        break;
                    case "address":
                        getAddress(args, player);
                        break;
                    case "help":
                        help(player);
                        break;
                    case "debug":
                        turnDebug(player);
                        break;
                    default:
                        penalty(player);
                        break;
                }
            }
        } else {
            Message.sendPlayerMessage(player, "影之乡核心未启动。");
        }
    }

    @Override
    protected void serverCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Message.sendPlayerMessage(sender, "影之乡核心正在待命！");
        } else {
            switch (args[0]) {
                case "start":
                    coreOn = true;
                    Message.sendPlayerMessage(sender, "影之乡核心已启动！");
                    break;
                case "stop":
                    coreOn = false;
                    Message.sendPlayerMessage(sender, "影之乡核心已关闭。");
                    break;
                default:
                    Message.sendPlayerMessage(sender, "未知指令！");
                    break;
            }
        }
    }

    private void empower(String[] args, Player player) {
        if (player.hasPermission("svc.empower")) {
            Message.sendPlayerMessage(player, "凡人，从现在起，我将赋予你神的能力！");
        } else {
            Message.sendPlayerMessage(player, "凡人，你无权这样做！");
        }
    }

    private void getAddress(String[] args, Player player) {
        if (player.hasPermission("svc.address")) {
            if (args.length == 1) {
                Message.sendPlayerMessage(player, "请选择想要查的人！");
            } else if (args.length == 2) {
                Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
                if (targetPlayer != null) {
                    Message.sendPlayerMessage(player, "该玩家的地址是" + targetPlayer.getAddress());
                } else {
                    Message.sendPlayerMessage(player, "未发现该玩家！");
                }
            } else {
                Message.sendPlayerMessage(player, "未知指令！");
            }
        } else {
            Message.sendPlayerMessage(player, "凡人，你无权这样做！");
        }
    }

    private void help(Player player) {
        if (player.hasPermission("svc.help")) {
            ItemStack helperBook = new ItemStack(Material.WRITTEN_BOOK);

            BookMeta bookMeta = (BookMeta) helperBook.getItemMeta();
            bookMeta.setAuthor("Fitexmage");
            bookMeta.setTitle("影之乡核心百科");

            StringBuilder text1 = new StringBuilder();
            text1.append("影之乡核心系统\n\n");
            text1.append("1.获得《影之乡核心百科》：\n");
            text1.append("/svc help\n\n");
            bookMeta.addPage(text1.toString());

            StringBuilder text2 = new StringBuilder();
            text2.append("影之乡黑市系统\n\n");
            text2.append("1.武器抽奖：\n");
            text2.append("/svb gamble weapon 钻石数\n\n");
            text2.append("2.影之石数量查询：\n");
            text2.append("/svb lookup shadowstone");
            bookMeta.addPage(text2.toString());

            StringBuilder text5 = new StringBuilder();
            text5.append("影之石\n\n");
            text5.append("影之石是影之乡服务器的通用货币，可通过赞助获得。\n");
            text5.append("1影之石 = 1RMB。\n");
            text5.append("每个玩家在拿到白名单后都可以获得一块影之石。\n");
            text5.append("影之石可以用于购买各种增值道具。\n");
            text5.append("服务器换周目时，影之石将通过某种方式保存至新周目使用。");
            bookMeta.addPage(text5.toString());

            helperBook.setItemMeta(bookMeta);
            player.getInventory().addItem(helperBook);
        }
    }

    private void turnDebug(Player player) {
        if (player.hasPermission("svc.debug")) {
            Message.debug = !Message.debug;
            Message.sendPlayerMessage(player, "已切换debug模式为" + Message.debug);
        } else {
            Message.sendPlayerMessage(player, "凡人，你无权这样做！");
        }
    }

    private void penalty(Player player) {
        player.getWorld().strikeLightning(player.getLocation());
        player.setFireTicks(1000);
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1000, 100));
        Message.sendPlayerMessage(player, "凡人，你怎敢在此胡作非为，你将受到惩罚！");
    }
}
