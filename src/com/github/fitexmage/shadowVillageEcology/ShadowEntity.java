package com.github.fitexmage.shadowVillageEcology;

import com.github.fitexmage.util.Message;
import com.github.fitexmage.util.Tool;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.CitizensNPC;
import net.citizensnpcs.npc.EntityController;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.UUID;

public abstract class ShadowEntity extends CitizensNPC {
    ShadowEntity(UUID uuid, int id, String name, EntityController entityController, NPCRegistry registry) {
        super(uuid, id, name, entityController, registry);
    }

    abstract void prepare();

    abstract void forcePrepare();

    abstract void action();

    abstract void teleport(Player player);

    public void randomShadowAttack(Player player) {
        String name = getFullName();
        int randomAction = (int) (Math.random() * 8);
        if (randomAction == 0) {
            player.setFireTicks(200);
            Message.broadcastMessage(player.getDisplayName() + "被" + name + "点燃了!");
        } else if (randomAction == 1) {
            player.setFoodLevel(1);
            Message.broadcastMessage(player.getDisplayName() + "被" + name + "榨干了!");
        } else if (randomAction == 2) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1200, 100));
            Message.broadcastMessage(player.getDisplayName() + "被" + name + "致盲了!");
        } else if (randomAction == 3) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1200, 3));
            Message.broadcastMessage(player.getDisplayName() + "被" + name + "缠住了!");
        } else {
            List<ItemStack> targetPlayerItems = Tool.getPlayerItems(player);
            if (targetPlayerItems.size() != 0) {
                ItemStack removedItem = targetPlayerItems.get((int) (Math.random() * targetPlayerItems.size()));
                int removedAmount = (int) (Math.random() * removedItem.getAmount()) + 1;
                removedItem.setAmount(removedAmount);
                player.getInventory().removeItem(removedItem);
                Message.broadcastMessage(player.getDisplayName() + "被" + name + "掠夺走" + removedItem.getType() + " " + removedAmount + "个！");
            }
        }
    }
}
