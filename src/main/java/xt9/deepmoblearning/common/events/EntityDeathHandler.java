package xt9.deepmoblearning.common.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xt9.deepmoblearning.common.items.ItemDataModel;
import xt9.deepmoblearning.common.items.ItemDeepLearner;
import xt9.deepmoblearning.common.mobs.MobMetaData;
import xt9.deepmoblearning.common.util.DataModel;

/**
 * Created by xt9 on 2017-06-11.
 */
@Mod.EventBusSubscriber
public class EntityDeathHandler {

    @SubscribeEvent
    public static void entityDeath(LivingDeathEvent event) {

        if(event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            // Update the chips in the players deep learner(s)
            updateDeepLearnerOnEntityDeath(player.inventory.mainInventory, event, player);
            updateDeepLearnerOnEntityDeath(player.inventory.offHandInventory, event, player);
        }
    }

    private static void updateDeepLearnerOnEntityDeath(NonNullList<ItemStack> inventory, LivingDeathEvent event, EntityPlayer player) {
        for(ItemStack inventoryStack : inventory) {
            if (inventoryStack.getItem() instanceof ItemDeepLearner) {
                NonNullList<ItemStack> deepLearnerInternalInv = ItemDeepLearner.getContainedItems(inventoryStack);
                for (ItemStack stack : deepLearnerInternalInv) {
                    if (stack.getItem() instanceof ItemDataModel) {
                        MobMetaData meta = DataModel.getMobMetaData(stack);

                        if (meta.entityLivingMatchesMob(event.getEntityLiving())) {
                            DataModel.increaseMobKillCount(stack, player);
                        }
                    }
                    ItemDeepLearner.setContainedItems(inventoryStack, deepLearnerInternalInv);
                }
            }
        }
    }
}
