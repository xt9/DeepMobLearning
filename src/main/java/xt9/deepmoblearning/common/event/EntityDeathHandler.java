package xt9.deepmoblearning.common.event;

import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by xt9 on 2017-06-11.
 */
@Mod.EventBusSubscriber
public class EntityDeathHandler {

    @SubscribeEvent
    public static void entityDeath(LivingDeathEvent event) {
        if(event.getSource().getEntity() instanceof EntityPlayer && event.getEntityLiving() instanceof EntityCow) {
            EntityPlayer player = (EntityPlayer) event.getSource().getEntity();

            player.inventory.addItemStackToInventory(new ItemStack(Items.COAL, 64));
        }
    }
}
