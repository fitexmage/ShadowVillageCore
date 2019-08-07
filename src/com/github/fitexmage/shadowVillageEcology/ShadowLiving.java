package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.shadowVillageBlackMarket.ShadowItem;
import com.github.fitexmage.util.Message;
import com.github.fitexmage.util.Tool;

import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.CitizensNPC;
import net.citizensnpcs.npc.EntityController;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.UUID;

public abstract class ShadowLiving extends ShadowEntity {
    final int maxPrepareCountDown = (int) (1200 / ShadowLivingSpawner.interval); // 60秒
    final int basicPrepareCountDown = 5;
    final int maxTeleportCountDown = (int) (200 / ShadowLivingSpawner.interval); // 10秒
    final int basicTeleportCountDown = 5;

    int count;
    int prepareCountDown;
    int teleportCountDown;

    ShadowLiving(UUID uuid, int id, String name, EntityController entityController, NPCRegistry registry) {
        super(uuid, id, name, entityController, registry);
    }

    void prepare(boolean force){
        if (force) {
            count = 5;
            prepareCountDown = 5;
        } else {
            List<Player> realOnlinePlayers = Tool.getRealPlayers(Bukkit.getWorld("world"), null);
            count = (int) (Math.random() * realOnlinePlayers.size()) + 1;
            prepareCountDown = (int) (Math.random() * maxPrepareCountDown) + basicPrepareCountDown;
        }
        teleportCountDown = 0;
    }

    abstract void spawn(boolean force);

    abstract void action();

    abstract void teleport(Player player);

    public abstract void dropItem();

    public void randomShadowAttack(Player player) {
        String name = getFullName();
        String playerName = player.getDisplayName();
        switch ((int) (Math.random() * 7)) {
            case 0:
                player.setFireTicks(200);
                Message.broadcastMessage(playerName + "被" + name + "点燃了!");
                break;
            case 1:
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 600, 100));
                Message.broadcastMessage(playerName + "被" + name + "致盲了!");
                break;
            case 2:
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 3));
                Message.broadcastMessage(playerName + "被" + name + "缠住了!");
                break;
            case 3:
                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 600, 3));
                Message.broadcastMessage(playerName + "被" + name + "榨干了!");
                break;
            case 4:
                player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 3));
                Message.broadcastMessage(playerName + "被" + name + "腐蚀了!");
                break;
            default:
                List<ItemStack> targetPlayerItems = Tool.getPlayerItems(player);
                if (targetPlayerItems.size() != 0) {
                    ItemStack removedItem = targetPlayerItems.get((int) (Math.random() * targetPlayerItems.size()));
                    if (ShadowItem.isBounded(removedItem)) {
                        Message.broadcastMessage(name + "想要夺取物品，但是失手了！");
                    }else{
                        int removedAmount = (int) (Math.random() * removedItem.getAmount()) + 1;
                        removedItem.setAmount(removedAmount);
                        player.getInventory().removeItem(removedItem);
                        Message.broadcastMessage(playerName + "被" + name + "掠夺走" + removedItem.getType() + " " + removedAmount + "个！");
                    }
                }
                break;
        }
    }
}
