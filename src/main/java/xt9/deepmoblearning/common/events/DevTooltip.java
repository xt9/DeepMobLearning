package xt9.deepmoblearning.common.events;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import xt9.deepmoblearning.common.util.KeyboardHelper;

import java.util.List;

/**
 * Created by xt9 on 2017-06-15.
 */
/*
    [11:02 AM] xalcon: register for the ItemTooltipEvent, check if the itemstack has nbt and if yes,
                        convert the nbt to a string (tagcompound.toString()) and add that to the tooltip ¯\_(ツ)_/¯ */
@Mod.EventBusSubscriber(Side.CLIENT)
public class DevTooltip {

    @SubscribeEvent
    public static void onToolTip(ItemTooltipEvent event) {
        List list = event.getToolTip();
        ItemStack stack = event.getItemStack();
        if(stack.isEmpty()) {
            return;
        }

        if(stack.hasTagCompound()) {
            if(!KeyboardHelper.isHoldingCTRL()) {
                list.add(I18n.format("deepmoblearning.devonly.holdcontrol"));
            } else {
                list.add(stack.getTagCompound().toString());
            }
        }
    }
}
