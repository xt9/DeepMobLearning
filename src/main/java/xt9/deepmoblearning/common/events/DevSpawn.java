package xt9.deepmoblearning.common.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.items.ItemMobChip;

/**
 * Created by xt9 on 2017-06-15.
 */
@Mod.EventBusSubscriber
public class DevSpawn {

    @SubscribeEvent
    public static void addTestChips(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer p = event.player;
        ItemStack stack = new ItemStack(Registry.mobChip, 1, 2);
        ItemMobChip.setTier(stack, 4);
        ItemMobChip.setTotalKillCount(stack, 328);

        // p.inventory.addItemStackToInventory(stack);
    }
}